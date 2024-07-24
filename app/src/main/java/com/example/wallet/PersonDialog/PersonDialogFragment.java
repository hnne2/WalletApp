package com.example.wallet.PersonDialog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wallet.MyApiService;
import com.example.wallet.R;
import com.example.wallet.databinding.PersonDialogBinding;
import com.example.wallet.models.Person;
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
   private String username;
    PersonDialogBinding binding;
   @Inject
   @Named("withToken")
   MyApiService apiService;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding =PersonDialogBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        appPerson = getActivity().getIntent().getParcelableExtra("person");
        root.setBackgroundResource(R.drawable.zakrugl);
        ConstraintLayout constraintLayout = root.findViewById(R.id.personDialogConstraintLayout);
        constraintLayout.setVisibility(View.GONE);
        apiService.userByLogin(username).enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                Person DialogPerson = response.body();
                if (DialogPerson!=null){

                    if (DialogPerson.getFrendslistid()!=null){
                    binding.friendCaount.setText(String.valueOf(response.body().getFrendslistid().split(",").length));
                        if (ifMyFriend(appPerson.getFrendslistid(), DialogPerson.getId())){
                            binding.AddTofriend.setVisibility(View.GONE );
                        }
                    }
                    constraintLayout.setVisibility(View.VISIBLE);
                    binding.FiotextView.setText(DialogPerson.getUserfio());
                    binding.CityTextView.setText(DialogPerson.getCity());
                    binding.freindButton.setText(String.valueOf(DialogPerson.getCapital()));
                    //диалог со списком

                    binding.friendCaount.setOnClickListener(v -> {
                        DialogWithFrend dialogWithFrend= new DialogWithFrend(DialogPerson.getFrendslistid(),DialogPerson.getUserfio());
                        FragmentManager fragmentManager = getChildFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.PersonDialogFragmentConstraiLayout, dialogWithFrend);
                        fragmentTransaction.commit();

                    });
                    Log.e("my", "dialogUserByLOginSuccses");
                    binding.AddTofriend.setOnClickListener(v -> {
                        appPerson.setFrendslistid(appPerson.getFrendslistid()+","+DialogPerson.getId());
                        apiService.updatePerson(appPerson).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call1, Response<ResponseBody> response1) {
                                if (response1.body()!=null){
                                Toast.makeText(getContext(),DialogPerson.getUserfio()+" добавлен в друзья",Toast.LENGTH_SHORT).show();}else  Toast.makeText(getContext(),"Ошибка",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call1, Throwable t) {

                            }
                        });
                    });
                }else Log.e("my", "dialogUserByLOginEror");
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });

        return root;
    }



    public void setUsername(String username) {
        this.username = username;
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
