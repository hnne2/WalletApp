package com.example.wallet.ui.lk;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.wallet.FoundSms.FoundSms;
import com.example.wallet.R;
import com.example.wallet.sharedPref.SheredPrefsRepository;
import com.example.wallet.databinding.FragmentLkBinding;
import com.example.wallet.models.Person;
import com.example.wallet.ui.avtarization.avtarization;
import com.example.wallet.ui.splashActivity.SplashActivityViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;
@AndroidEntryPoint
public class lkFragment extends Fragment {
    @Inject
    SheredPrefsRepository msheredPrefsRepository;
    private static final int REQUEST_CALL_PERMISSION = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private FragmentLkBinding binding;
    Person person;
    SplashActivityViewModel splashActivityViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

         splashActivityViewModel = new ViewModelProvider(this).get(SplashActivityViewModel.class);
        binding = FragmentLkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FoundSms foundSms = new FoundSms();
        person = getActivity().getIntent().getParcelableExtra("person");
        BottomNavigationView navView = getActivity().findViewById(R.id.nav_view);

        ImageView avatarImageView = root.findViewById(R.id.AvatarImageView);
        avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_IMAGE_PICK);
                } else {
                    openGallery();
                }


            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/"+person.getAvatarlink());
        GlideApp.with(this)
                .load(imageRef)
                .into(avatarImageView);

        binding.swipeRefreshLayoutLKkFragment.setOnRefreshListener(() ->{
            Log.d("TAG", "onCreateView: "+person.toString());
            person = foundSms.updateCapital(person, getActivity().getContentResolver());
            binding.FiotextView.setText(person.getUserfio());
            if (person.getFrendslistid() != null) {
                binding.freindButton.setText(String.valueOf(person.getFrendslistid().split(",").length));
            }
            List<BankItemReciclerView> banks = new ArrayList<>();
            banks.add(new BankItemReciclerView("Sberbank", person.getBalansinsber()+"р", R.drawable.spericon));
            banks.add(new BankItemReciclerView("Tincoff", person.getBalansintinkoff()+"р", R.drawable.tinkofficon));
            splashActivityViewModel.updatePerson(person);
        });

        splashActivityViewModel.getUpdateSuccses().observe(getViewLifecycleOwner(), aBoolean -> {
            binding.swipeRefreshLayoutLKkFragment.setRefreshing(false);
        });

        binding.exitImageButton.setOnClickListener(v -> {
            msheredPrefsRepository.pootLogPas("logPassEncode","empty");
            startActivity(new Intent(getContext(), avtarization.class));
            getActivity().finish();

        });

        binding.FiotextView.setText(person.getUserfio());
        if (person.getFrendslistid() != null) {
            binding.freindButton.setText(String.valueOf(person.getFrendslistid().split(",").length));
        }

        List<BankItemReciclerView> banks = new ArrayList<>();
        banks.add(new BankItemReciclerView("Sberbank", person.getBalansintinkoff()+"р", R.drawable.spericon));
        banks.add(new BankItemReciclerView("Tincoff", person.getBalansinsber()+"р", R.drawable.tinkofficon));

        WallatsRecyclerViewAdapter.OnStateClickListener WalletClickListener = (bank, position) -> {
            if (bank.BankName.equals("Sberbank")) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
                } else {
                    callUSSD();
                }
            }
        };

        binding.BalansRecyclerView.setAdapter(new WallatsRecyclerViewAdapter(getContext(), banks, WalletClickListener));
        binding.BalansRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.freindButton.setOnClickListener(v -> navView.setSelectedItemId(R.id.navigation_home));
        binding.buttonPlaceInFriend.setOnClickListener(v -> navView.setSelectedItemId(R.id.navigation_home));

        return root;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                binding.AvatarImageView.setImageURI(selectedImageUri);
                compressAndUploadImage(selectedImageUri);
            }}
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Toast.makeText(getContext(), "код"+ requestCode, Toast.LENGTH_SHORT).show();
        if (requestCode == REQUEST_IMAGE_PICK) {
            openGallery();
        }
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callUSSD();
            } else {
                Toast.makeText(getContext(), "Предоставьте разрешение", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void callUSSD() {
        String ussdCode = "*900*01#";
        String encodedUssd = Uri.encode(ussdCode);
        String ussdUri = "tel:" + encodedUssd;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(ussdUri));
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void uploadCompressedImageToFirebaseStorage(byte[] imageData){
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    // Создаем ссылку на файл, который будет сохранен в Firebase Storage
    StorageReference imageRef = storageRef.child("images/" + person.getId()+ ".jpg");
    // Загружаем файл
        UploadTask uploadTask = imageRef.putBytes(imageData);

    // Отслеживаем процесс загрузки
                uploadTask.addOnSuccessListener(taskSnapshot -> {
        // Получаем ссылку на загруженный файл
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Здесь вы можете использовать ссылку на загруженный файл
                String downloadUrl = uri.toString();
                person.setAvatarlink(imageRef.getName());
                splashActivityViewModel.updatePerson(person);
                Toast.makeText(getContext(), "Изображение загружено", Toast.LENGTH_SHORT).show();
            }
        });
    }).addOnFailureListener(exception -> {
        // Обработка ошибок
        Toast.makeText(getContext(), "Ошибка загрузки: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
    });
    }
    private void compressAndUploadImage(Uri imageUri) {
        try {
            // Получаем InputStream из URI изображения
            InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);

            // Конвертируем InputStream в Bitmap
            Bitmap originalBitmap = BitmapFactory.decodeStream(imageStream);

            // Опционально: Сжимаем изображение, уменьшая его разрешение
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, originalBitmap.getWidth() / 2, originalBitmap.getHeight() / 2, true);

            // Создаем ByteArrayOutputStream, чтобы хранить сжатое изображение
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Сжимаем изображение в формате JPEG с качеством 75% (можно настроить)
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);

            // Преобразуем ByteArrayOutputStream в массив байтов
            byte[] imageData = baos.toByteArray();

            // Загружаем сжатое изображение в Firebase Storage
            uploadCompressedImageToFirebaseStorage(imageData);

        } catch (Exception e) {
            Toast.makeText(getContext(), "Ошибка сжатия изображения: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
