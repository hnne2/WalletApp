package com.example.wallet.PersonDialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.wallet.MyApiService;
import com.example.wallet.R;
import com.example.wallet.databinding.PersonDialogBinding;
import com.example.wallet.models.Person;
import com.example.wallet.ui.lk.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Inject;
import javax.inject.Named;
import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.ResponseBody;
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
        progressBar =root.findViewById(R.id.progressBarPersonDialog);
        appPerson = getActivity().getIntent().getParcelableExtra("person");
        ConstraintLayout constraintLayout = root.findViewById(R.id.personDialogConstraintLayout);
        constraintLayout.setVisibility(View.GONE);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/"+appPerson.getAvatarlink());
        GlideApp.with(this)
                .load(imageRef)
                .into(binding.AvatarImageView);
        if (getArguments()!=null){
            username=getArguments().getString(bundleUsernameKey);
        }
        apiService.userByLogin(username).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                Person dialogPerson = response.body();
                if (dialogPerson!=null){
                    if (dialogPerson.getFrendslistid()!=null){
                    binding.friendCaount.setText(String.valueOf(response.body().getFrendslistid().split(",").length));
                        if (ifMyFriend(appPerson.getFrendslistid(), dialogPerson.getId())){
                            binding.AddTofriend.setVisibility(View.GONE );
                        }
                    }
                    constraintLayout.setVisibility(View.VISIBLE);
                    binding.FiotextView.setText(dialogPerson.getUserfio());
                    binding.CityTextView.setText(dialogPerson.getCity());
                    binding.freindButton.setText(String.valueOf(dialogPerson.getCapital()));
                    progressBar.setVisibility(View.INVISIBLE);

                    //диалог со списком

                    binding.friendCaount.setOnClickListener(v -> {
                        if (!binding.friendCaount.getText().toString().equals("0")){

                        DialogWithFrend dialogWithFrend=DialogWithFrend.newInstance(dialogPerson.getFrendslistid(),dialogPerson.getUserfio());
                        FragmentManager fragmentManager = getChildFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.PersonDialogFragmentConstraiLayout, dialogWithFrend);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        }

                    });
                    Log.e("my", "dialogUserByLOginSuccses");
                    binding.AddTofriend.setOnClickListener(v -> {
                        progressBar.setVisibility(View.VISIBLE);
                        appPerson.setFrendslistid(appPerson.getFrendslistid()+","+dialogPerson.getId());
                        apiService.updatePerson(appPerson).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call1, Response<ResponseBody> response1) {
                                if (response1.body()!=null){
                                    progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(getContext(),dialogPerson.getUserfio()+" добавлен в друзья",Toast.LENGTH_SHORT).show();}
                                    else  {Toast.makeText(getContext(),"Ошибка",Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call1, Throwable t) {
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    });
                }else Log.e("my", "dialogUserByLOginEror");
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.e("my", "failure");
            }
        });
        return root;
    }



    public boolean ifMyFriend(String frendListId,int dialodPersonId){
        String[] idArr= frendListId.split(",");
        for (String id:idArr
             ) {
            if (Integer.parseInt(id)==dialodPersonId){
                return true;
            }
        }
        return false;
    }

}
