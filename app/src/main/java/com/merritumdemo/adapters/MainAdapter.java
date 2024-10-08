package com.merritumdemo.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meritumads.settings.MsAdsBanners;
import com.meritumads.settings.MsAdsSdk;
import com.merritumdemo.R;
import com.merritumdemo.activites.TestActivityFragment;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<Holder> {

    public ArrayList<Item> items = new ArrayList<Item>();
    public RecyclerView recyclerView;
    public FragmentManager fragmentManager;

    public MainAdapter(FragmentManager fragmentManager, RecyclerView recyclerView) {
        this.fragmentManager = fragmentManager;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;

        if(viewType == Item.SIMPLE_ITEM){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_layout, parent, false);
        }else if(viewType == Item.IN_LIST_BANNER){
            view = LayoutInflater.from(parent.getContext()).inflate(com.meritumads.R.layout.msads_in_list_layout, parent, false);
        }
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        switch (getItemViewType(position)){
            case Item.SIMPLE_ITEM:
                bindSimpleItem(holder, position);
                break;
            case Item.IN_LIST_BANNER:
                bindInListBanner(holder, position);
                break;
        }

    }

    private void bindInListBanner(Holder holder, int position) {

        View view = holder.itemView;

        InListBannerItem item = (InListBannerItem)items.get(position);

        //method for calling in list banners
        //recyclerview or scrollview is important for better performance of showing video in list view.
        //It could be also without recyclerview or scrollview
        //Also please define what instance is view in what you are attaching content
        //In this case it it RelativeLayout, it can be something else(LinearLayout)
        MsAdsBanners.getInstance(item.getDeveloperId(), (RelativeLayout)view, recyclerView);


    }

    private void bindSimpleItem(Holder holder, int position) {

        View view = holder.itemView;

        TextView text = view.findViewById(R.id.text);
        text.setText("Position od item: " + String.valueOf(position));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestActivityFragment fragment = new TestActivityFragment();
                fragment.show(fragmentManager, "test_dialog");
                MsAdsSdk.getInstance().pauseVideo("csh_news_banner");


            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getTypeItem();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Item item){
        items.add(item);
        notifyDataSetChanged();
    }

    public void addItem(int position, Item item){
        items.add(position, item);
        notifyDataSetChanged();
    }
}
