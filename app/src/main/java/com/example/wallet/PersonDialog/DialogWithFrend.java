package com.example.wallet.PersonDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.MyApiService;
import com.example.wallet.R;
import com.example.wallet.models.Person;
import com.example.wallet.ui.home.FrendsItem;
import com.example.wallet.ui.home.FrendsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class DialogWithFrend extends Fragment {
    public static final String friendsIdStringBundelKey="friendsIdStringBundelKey";
    public static final String namePersonStringBundelKey="namePersonStringBundelKey";
    String frendsIdString;
    String namePerson;
    ProgressBar progressBar;
    @Inject
   public DialogWithFrend(){
    }
    @Inject
    @Named("withToken")
    MyApiService apiService;
    public static DialogWithFrend newInstance(String frendsIdString, String namePerson){
        DialogWithFrend dialogWithFrend = new DialogWithFrend();
        Bundle args = new Bundle();
        args.putString(friendsIdStringBundelKey,frendsIdString);
        args.putString(namePersonStringBundelKey,namePerson);
        dialogWithFrend.setArguments(args);
        return dialogWithFrend;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frends_list_dialog, container, false);
        if (getArguments()!=null){
            frendsIdString=getArguments().getString(friendsIdStringBundelKey);
            namePerson=getArguments().getString(namePersonStringBundelKey);
        }
        List<FrendsItem> frendsItemList = new ArrayList<>();
            root.setBackgroundResource(R.drawable.zakrugl);
            progressBar= root.findViewById(R.id.progressBarFriends_list_dialog);
            progressBar.setVisibility(View.VISIBLE);
        TextView NamePerson =root.findViewById(R.id.NamePersonFrendsListTextView);
        NamePerson.setText(namePerson);
            FrendsRecyclerViewAdapter.OnFrendsClickListener onFrendsClickListener = new FrendsRecyclerViewAdapter.OnFrendsClickListener() {
            @Override
            public void onFrendsClick(FrendsItem frend, int position) {
                PersonDialogFragment personDialogFragment =  PersonDialogFragment.newInstance(frend.getUsername());
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frendlistConstraintLayoit,personDialogFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };
        FrendsRecyclerViewAdapter frendsRecyclerViewAdapter = new FrendsRecyclerViewAdapter(getContext(),frendsItemList, onFrendsClickListener);

        apiService.getFrindsList(frendsIdString).enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                if (response.body()!=null){
                frendsItemList.clear();
                Collections.sort(response.body(), Comparator.comparing(Person::getCapital).reversed());
                for (int i = 0; i < response.body().size(); i++) {
                    frendsItemList.add(new FrendsItem(response.body().get(i).getAvatarlink(),response.body().get(i).userfio,response.body().get(i).getUsername(),String.valueOf(i+1),String.valueOf(response.body().get(i).getCapital())));
                }
                RecyclerView frendsRecyclerView =root.findViewById(R.id.frendsDialogRecyclerView);
                frendsRecyclerView.setAdapter(new FrendsRecyclerViewAdapter(getContext(),frendsItemList, onFrendsClickListener));
                frendsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    progressBar.setVisibility(View.INVISIBLE);
                }else{ Toast.makeText(getContext(),"Не удалось получить список друзей",Toast.LENGTH_SHORT);

                    progressBar.setVisibility(View.INVISIBLE);}
            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return root;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
