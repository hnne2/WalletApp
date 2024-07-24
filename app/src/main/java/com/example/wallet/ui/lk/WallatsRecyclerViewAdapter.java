package com.example.wallet.ui.lk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.wallet.R;

import java.util.List;

public class WallatsRecyclerViewAdapter  extends RecyclerView.Adapter<WallatsRecyclerViewAdapter.ViewHolder>{

    public interface OnStateClickListener{
        void onStateClick(BankItemReciclerView bank, int position);
    }

    private final OnStateClickListener onClickListener;

    private final LayoutInflater inflater;
    private final List<BankItemReciclerView> banks;

    public WallatsRecyclerViewAdapter(Context context, List<BankItemReciclerView> banks, OnStateClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.banks = banks;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public WallatsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_balans, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WallatsRecyclerViewAdapter.ViewHolder holder, int position) {
        BankItemReciclerView bank = banks.get(position);
        holder.imageview.setImageResource(bank.ImageViewRes);
        holder.nameView.setText(bank.BankName);
        holder.capitalView.setText(bank.BalansCount);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                onClickListener.onStateClick(bank, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return banks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageview;
        final TextView nameView, capitalView;
        ViewHolder(View view){
            super(view);
            imageview = view.findViewById(R.id.BankImageView);
            nameView = view.findViewById(R.id.BankNameTextView);
            capitalView = view.findViewById(R.id.BalansCountTextVIew);
        }
    }
}