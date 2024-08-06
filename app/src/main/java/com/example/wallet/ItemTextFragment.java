package com.example.wallet;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wallet.ui.items.itemsFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ItemTextFragment extends Fragment {

    TextView itemTextView;
    public static final String itemTextBundleKey = "itemTextBundleKey";
    String itemText;
    Button exitbutton;
    @Inject
    public ItemTextFragment() {
    }
    public static ItemTextFragment newInstance(String itemText){
        ItemTextFragment itemTextFragment= new ItemTextFragment();
        Bundle args= new Bundle();
        args.putString(itemTextBundleKey,itemText);
        itemTextFragment.setArguments(args);
        return itemTextFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_item_text, container, false);
        if (getArguments()!=null){
            itemText = getArguments().getString(itemTextBundleKey);
        }
        itemTextView=view.findViewById(R.id.itemFragmentTextView);
        itemTextView.setText(itemText);
        itemTextView.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }
}