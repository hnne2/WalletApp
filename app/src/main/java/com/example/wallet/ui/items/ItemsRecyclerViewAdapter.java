package com.example.wallet.ui.items;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;
import com.example.wallet.ui.lk.GlideApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ItemsRecyclerViewAdapter extends RecyclerView.Adapter<ItemsRecyclerViewAdapter.ViewHolder> {
    interface onItemClickListener{
        void onItemClickListener(ItemsItem itemsItem,int position);
    }

    private final  List<ItemsItem> itemsList;
    private final LayoutInflater inflater;
    private final onItemClickListener onItemClickListener;
    Context context;
    ItemsViewModel itemsViewModel;
    int personId;
    public ItemsRecyclerViewAdapter(int personId, ItemsViewModel itemsViewModel,Context context,List<ItemsItem> itemsList, ItemsRecyclerViewAdapter.onItemClickListener onItemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.itemsList = itemsList;
        this.onItemClickListener = onItemClickListener;
        this.context=context;
        this.itemsViewModel=itemsViewModel;
        this.personId=personId;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.items_items,parent,false);
        return new ItemsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemsRecyclerViewAdapter.ViewHolder holder, int position) {
        ItemsItem item = itemsList.get(position);

        Log.d("TAG", "onBindViewHolder: "+item.imageitems);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        if (item.imageitems !=null){
        StorageReference ItemsImage = storageRef.child(item.imageitems);
            GlideApp.with(context)
                    .load(ItemsImage)
                    .into(holder.imageViewItemsRecyclerView);
        } else holder.imageViewItemsRecyclerView.setImageResource(R.drawable.avatar);
        if (item.chanelavatar !=null){
        StorageReference chanelAvatarItem = storageRef.child(item.chanelavatar);
            GlideApp.with(context)
                    .load(chanelAvatarItem)
                    .into(holder.chanelAvatarItemsRecyclerView);
        }else holder.chanelAvatarItemsRecyclerView.setImageResource(R.drawable.avatar);

        holder.nameChanelItemsRecyclerView.setText(item.namechanel);
        holder.descriptionstemsRecyclerView.setText(item.description);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClickListener(item,position));
        holder.countLikes.setText(String.valueOf(item.likes));
        boolean isLikedPost = false;
        if (item.wholikedsId!=null){   //проверка лайнут ли пост
            String[] wholikedIdArr = item.wholikedsId.split(",");
            for (int i = 0; i < wholikedIdArr.length; i++) {
                if (wholikedIdArr[i].equals(String.valueOf(personId))){
                    isLikedPost=true;
                }
            }
        }
        if (isLikedPost){   //если лайкнут изменить картинку кнопки лайка
            Log.d("TAG", "isLikedPost: liked");
            holder.likeImageButton.setImageResource(R.drawable.likered);

        }

        holder.likeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isLikedPost = false;
                if (item.wholikedsId!=null){   //проверка лайнут ли пост
                    String[] wholikedIdArr = item.wholikedsId.split(",");
                    for (int i = 0; i < wholikedIdArr.length; i++) {
                        if (wholikedIdArr[i].equals(String.valueOf(personId))){
                            isLikedPost=true;
                        }
                    }
                }
                if (!isLikedPost) {
                    item.likes = item.likes + 1;
                    holder.countLikes.setText(String.valueOf(item.likes));
                    item.wholikedsId = item.wholikedsId + personId + ",";
                    itemsViewModel.updateItems(item);
                    holder.likeImageButton.setImageResource(R.drawable.likered);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView chanelAvatarItemsRecyclerView;
        ImageView imageViewItemsRecyclerView;
        ImageButton likeImageButton, dissLikeImageButton;
        TextView nameChanelItemsRecyclerView;
        TextView descriptionstemsRecyclerView;
        TextView countLikes;
        public ViewHolder(View itemView) {
            super(itemView);
            likeImageButton = itemView.findViewById(R.id.LikeimageButton);
            dissLikeImageButton=itemView.findViewById(R.id.disLikeimageButton);
             chanelAvatarItemsRecyclerView = itemView.findViewById(R.id.chanelAvatarItemsRecyclerView);
             imageViewItemsRecyclerView = itemView.findViewById(R.id.imageViewItemsRecyclerView);
             nameChanelItemsRecyclerView = itemView.findViewById(R.id.nameChanelItemsRecyclerView);
             descriptionstemsRecyclerView = itemView.findViewById(R.id.descriptionstemsRecyclerView);
             countLikes=itemView.findViewById(R.id.countLIkes);
        }
    }{

    }
}
