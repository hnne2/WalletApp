package com.example.wallet.ui.rankings;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wallet.R;

import java.util.List;

/********************************************
 *     Created by DailyCoding on 15-May-21.  *
 ********************************************/

public class RegionAdapter extends BaseAdapter {

    private Context context;
    private List<RankingRegion> regionList;

    public RegionAdapter(Context context, List<RankingRegion> regionList) {
        this.context = context;
        this.regionList = regionList;
    }

    @Override
    public int getCount() {
        return regionList != null ? regionList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return regionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Здесь оставляем как есть, это вид элемента, когда спиннер закрыт
        return createItemView(i, viewGroup);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Если это первый элемент, возвращаем пустой макет с нулевой высотой
        if (position == 0) {
            View emptyView = new View(context);
            emptyView.setLayoutParams(new AbsListView.LayoutParams(0, 0));
            return emptyView;
        }
        // Для остальных элементов используем обычный макет
        return createItemView(position, parent);
    }

    private View createItemView(int i, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_rankings_region, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.ItemRankingsRegiontextView);
        //ImageView image = rootView.findViewById(R.id.ItemRankingsRegionimageView);

        RankingRegion region = regionList.get(i);
        txtName.setText(region.region);
       // image.setImageResource(region.ImageRes);

        return rootView;
    }
}