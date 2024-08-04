package com.example.wallet.ui.items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;

import java.util.List;

public class ItemsRecyclerViewAdapter extends RecyclerView.Adapter<ItemsRecyclerViewAdapter.ViewHolder> {
    interface onItemClickListener{
        void onItemClickListener(ItemsItem itemsItem,int position);
    }

    private final  List<ItemsItem> itemsList;
    private final LayoutInflater inflater;
    private final onItemClickListener onItemClickListener;


    public ItemsRecyclerViewAdapter(Context context,List<ItemsItem> itemsList, ItemsRecyclerViewAdapter.onItemClickListener onItemClickListener) {
        this.inflater = LayoutInflater.from(context);
        this.itemsList = itemsList;
        this.onItemClickListener = onItemClickListener;
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
        holder.chanelAvatarItemsRecyclerView.setImageResource(R.drawable.avatar);
        holder.imageViewItemsRecyclerView.setImageResource(R.drawable.avatar);
        holder.nameChanelItemsRecyclerView.setText(item.nameChanel);
        holder.descriptionstemsRecyclerView.setText(item.description);
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClickListener(item,position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView chanelAvatarItemsRecyclerView;
        ImageView imageViewItemsRecyclerView;
        TextView nameChanelItemsRecyclerView;
        TextView descriptionstemsRecyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
             chanelAvatarItemsRecyclerView = itemView.findViewById(R.id.chanelAvatarItemsRecyclerView);
             imageViewItemsRecyclerView = itemView.findViewById(R.id.imageViewItemsRecyclerView);
             nameChanelItemsRecyclerView = itemView.findViewById(R.id.nameChanelItemsRecyclerView);
             descriptionstemsRecyclerView = itemView.findViewById(R.id.descriptionstemsRecyclerView);
        }
    }{

    }
}
