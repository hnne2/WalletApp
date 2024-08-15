package com.example.wallet.PersonDialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.wallet.MyApiService;
import com.example.wallet.R;
import com.example.wallet.databinding.PersonDialogBinding;
import com.example.wallet.models.Person;
import com.example.wallet.ui.home.FriendsViewModel;
import com.example.wallet.ui.lk.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@AndroidEntryPoint
public class PersonDialogFragment extends Fragment {
    Person appPerson;
    @Inject
    public PersonDialogFragment(){}
    private String username = "";
    private static final String bundleUsernameKey = "bundleUsernameKey";

    PersonDialogBinding binding;

    @Inject
    @Named("withToken")
    MyApiService apiService;
    ProgressBar progressBar;
    public static PersonDialogFragment newInstance(String username){
        PersonDialogFragment personDialogFragment = new PersonDialogFragment();
        Bundle args = new Bundle();
        args.putString(bundleUsernameKey,username);

        personDialogFragment.setArguments(args);
        return personDialogFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =PersonDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        FriendsViewModel friendsViewModel = new ViewModelProvider(this).get(FriendsViewModel.class);
        progressBar =root.findViewById(R.id.progressBarPersonDialog);
        appPerson = getActivity().getIntent().getParcelableExtra("person");
        ConstraintLayout constraintLayout = root.findViewById(R.id.personDialogConstraintLayout);
        constraintLayout.setVisibility(View.GONE);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        if (getArguments()!=null){
            username=getArguments().getString(bundleUsernameKey);
        }
        apiService.userByLogin(username).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                Person dialogPerson = response.body();
                dialogPerson.setCapital(66666);
                friendsViewModel.updatePerson(dialogPerson);
                if (dialogPerson!=null){
                    if (dialogPerson.getAvatarlink()!=null){
                    if (dialogPerson.getAvatarlink().length()>0) {
                        StorageReference imageRef = storageRef.child("images/" + dialogPerson.getAvatarlink());
                        GlideApp.with(getContext())
                                .load(imageRef)
                                .into(binding.AvatarImageView);
                    } }
                    if (dialogPerson.getFrendslistid()!=null){
                        if (appPerson.getFrendslistid().length()>0){
                            binding.friendCaount.setText(String.valueOf(response.body().getFrendslistid().split(",").length));
                            if (ifMyFriend(appPerson.getFrendslistid(), dialogPerson.getId())){  // проверка на друзья
                                binding.AddTofriend.setText("удалить из друзей");
                            }
                            if (dialogPerson.getIsOpen_acc()==1 || ifMyFriend(appPerson.getFrendslistid(), dialogPerson.getId())){//проверка на закрытый профиль
                                binding.freindButton.setText(String.valueOf(dialogPerson.getCapital()));
                            } else  binding.freindButton.setText("скрыт");
                        }

                    }
                    if (appPerson.getFriendrequest().length()>0){
                        if (ifMyFriend(appPerson.getFriendrequest(),dialogPerson.getId())){  //проверка на запросы
                            binding.AddTofriend.setText("Принять заявку");
                        }
                    }
                    constraintLayout.setVisibility(View.VISIBLE);
                    binding.FiotextView.setText(dialogPerson.getUserfio());
                    binding.CityTextView.setText(dialogPerson.getCity());
                    progressBar.setVisibility(View.INVISIBLE);


                    binding.friendCaount.setOnClickListener(v -> { //диалог со списком
                        if (!binding.friendCaount.getText().toString().equals("0")){
                            DialogWithFrend dialogWithFrend=DialogWithFrend.newInstance(dialogPerson.getFrendslistid(),dialogPerson.getUserfio());
                            FragmentManager fragmentManager = getChildFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.add(R.id.PersonDialogFragmentConstraiLayout, dialogWithFrend);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                    // один из трех вариантов кнопки
                    binding.AddTofriend.setOnClickListener(v -> {
                        progressBar.setVisibility(View.VISIBLE);
                        if (binding.AddTofriend.getText().equals("Принять заявку")){ // если есть в запросах на друзья при клики добавляем
                            friendsViewModel.addFriend(dialogPerson.getId(),appPerson.getId());
                            binding.AddTofriend.setVisibility(View.GONE);
                            Log.d("TAG", "Принять заявку: ");
                        }
                        if (binding.AddTofriend.getText().equals("отравить заявку")){ //если ни в др и ни в заявках
                            friendsViewModel.addTofriendrequests(appPerson.getId(),dialogPerson.getId());
                            binding.AddTofriend.setVisibility(View.GONE);
                            Log.d("TAG", "отравить заявку");

                        }
                        if (binding.AddTofriend.getText().equals("удалить из друзей")){ //если в др при клике удалить
                            String updatedFriendsListStr = Arrays.stream(appPerson.getFrendslistid().split(",")) //удаление id из списка запросов
                                    .map(String::trim)
                                    .filter(id -> !id.equals(String.valueOf(dialogPerson.getId())))
                                    .collect(Collectors.joining(","));
                            appPerson.setFrendslistid(updatedFriendsListStr);                  //
                            String updatedFriendsListStrDialogPerson = Arrays.stream(dialogPerson.getFrendslistid().split(",")) //удаление id из списка запросов
                                    .map(String::trim)
                                    .filter(id -> !id.equals(String.valueOf(appPerson.getId())))
                                    .collect(Collectors.joining(","));
                            dialogPerson.setFrendslistid(updatedFriendsListStrDialogPerson);
                            dialogPerson.setCapital(66666);
                            friendsViewModel.updatePerson(dialogPerson);
                            friendsViewModel.updatePerson(appPerson);
                            binding.AddTofriend.setVisibility(View.GONE);
                        }
                    });
                }else Log.e("my", "dialogUserByLOginEror");
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e("my", "failure");
            }
        });
        friendsViewModel.getUpdateSuccses().observe(getViewLifecycleOwner(), new Observer<Person>() {
            @Override
            public void onChanged(Person person) {
                binding.progressBarPersonDialog.setVisibility(View.INVISIBLE);
            }
        });
        friendsViewModel.getMutableAddFriendRequestSuccses().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.progressBarPersonDialog.setVisibility(View.INVISIBLE);
            }
        });
        friendsViewModel.getMutableAddFriendSuccses().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.progressBarPersonDialog.setVisibility(View.INVISIBLE);
            }
        });
        return root;
    }



    public boolean ifMyFriend(String frendListId,int dialodPersonId){
        String[] idArr= frendListId.split(",");
        for (String id:idArr) {
            if (Integer.parseInt(id)==dialodPersonId){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
