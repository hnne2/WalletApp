package com.example.wallet.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.MainActivity;
import com.example.wallet.R;
import com.example.wallet.models.Person;
import com.example.wallet.ui.lk.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RequstFriendsViewAdapter extends RecyclerView.Adapter<RequstFriendsViewAdapter.RequestViewHolder>{


    private final Context context;

    private final RequstFriendsViewAdapter.OnRequstFriendsClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<RequestItem> requestItems;
    private final FriendsViewModel friendsViewModel;
    private final Person appPerson;
    private final LifecycleOwner lifecycleOwner;

    public RequstFriendsViewAdapter(LifecycleOwner lifecycleOwner,Person appPerson, FriendsViewModel friendsViewModel, Context context, List<RequestItem> requestItems, RequstFriendsViewAdapter.OnRequstFriendsClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.requestItems = requestItems;
        this.inflater = LayoutInflater.from(context);
        this.context=context;
        this.friendsViewModel=friendsViewModel;
        this.appPerson =appPerson;
        this.lifecycleOwner=lifecycleOwner;

    }
    @Override
    public RequstFriendsViewAdapter.RequestViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.items_friends_request, parent, false);
        return new RequstFriendsViewAdapter.RequestViewHolder(view);
    }
    public interface OnRequstFriendsClickListener {
        void onFrendsClick(RequestItem requestItem, int position);
    }

    @Override
    public void onBindViewHolder(RequstFriendsViewAdapter.RequestViewHolder holder, int position) {
        RequestItem requestItem = requestItems.get(position);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        if (requestItem.getAvatarRes() !=null){
            if (requestItem.getAvatarRes().length()>0) {
                StorageReference ItemsImage = storageRef.child("images/" + requestItem.getAvatarRes());
                GlideApp.with(context)
                        .load(ItemsImage)
                        .into(holder.imageview);
                Log.d("TAG", "onBindViewHolder: imahge" + requestItem.getAvatarRes());
            } }

        holder.nameView.setText(requestItem.getName());
        holder.asseptButton.setOnClickListener(v -> {
            friendsViewModel.addFriend(requestItem.getId(),appPerson.getId());
            requestItems.remove(position);
            notifyItemRemoved(position);
        });
        holder.declineImageButtonl.setOnClickListener(v -> {
            String updatedFriendsListStr = Arrays.stream(appPerson.getFriendrequest().split(","))
                    .map(String::trim)
                    .filter(id -> !id.equals(String.valueOf(requestItem.getId())))
                    .collect(Collectors.joining(","));
            appPerson.setFriendrequest(updatedFriendsListStr);
            Log.d("TAG", "onBindViewHolder: "+updatedFriendsListStr);
            friendsViewModel.updatePerson(appPerson);
            requestItems.remove(position);
            notifyItemRemoved(position);
        });
        holder.itemView.setOnClickListener(v -> onClickListener.onFrendsClick(requestItem, position));
        holder.usernameView.setText(requestItem.username);

    }

    @Override
    public int getItemCount() {
        return requestItems.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageview;
        final TextView nameView,usernameView;
        final Button asseptButton;
        final ImageButton declineImageButtonl;
        RequestViewHolder(View view){
            super(view);
            imageview = view.findViewById(R.id.requstFriendItemAvatar);
            nameView = view.findViewById(R.id.requstFriendItemName);
           asseptButton= view.findViewById(R.id.buttonAcceptRequstFriend);
           declineImageButtonl = view.findViewById(R.id.declineRequstFriendimageButton);
           usernameView= view.findViewById(R.id.requstFriendItemUserName);
        }
    }
}

