package com.example.wallet.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.wallet.R;

import java.util.List;

public class FrendsRecyclerViewAdapter extends RecyclerView.Adapter<FrendsRecyclerViewAdapter.ViewHolder>{

    public interface OnStateClickListener{
        void onStateClick(FrendsItem frend, int position);
    }

    private final FrendsRecyclerViewAdapter.OnStateClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<FrendsItem> frends;

    public FrendsRecyclerViewAdapter(Context context, List<FrendsItem> frends, FrendsRecyclerViewAdapter.OnStateClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.frends = frends;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public FrendsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_main_friends, parent, false);
        return new FrendsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FrendsRecyclerViewAdapter.ViewHolder holder, int position) {
        FrendsItem frend = frends.get(position);
        holder.imageview.setImageResource(frend.getAvatarRes());
        holder.nameView.setText(frend.getName());
        holder.capitalView.setText(frend.getCapital());
        holder.PlaceView.setText(frend.getPlace());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onStateClick(frend, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return frends.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageview;
        final TextView nameView, capitalView,PlaceView;
        ViewHolder(View view){
            super(view);
            imageview = view.findViewById(R.id.frendsItemAvatar);
            nameView = view.findViewById(R.id.frendsItemName);
            capitalView = view.findViewById(R.id.frendsItemCapital);
            PlaceView = view.findViewById(R.id.frendsItemPlace);
        }
    }
}