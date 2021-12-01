package com.example.covid_info;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder> {
    private List<String> mDataNames; //Названия стран
    private List<Integer> mDataInts;//Цифры
    private List<String> mDataCodes;//Коды стран

    private LayoutInflater mInflater;
    //private ItemClickListener itemClickListener;

    ListRecyclerViewAdapter(Context context, List<String> Names,List<String> Codes, List<Integer> Numbers){
        this.mInflater = LayoutInflater.from(context);
        this.mDataNames = Names;
        this.mDataInts = Numbers;
        this.mDataCodes = Codes;
    }
    @NonNull
    @Override
    public ListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.country_item_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListRecyclerViewAdapter.ViewHolder holder, int position) {
        String name = mDataNames.get(position);
        Integer number = mDataInts.get(position);
        String code = mDataCodes.get(position);
        holder.name.setText(name);
        holder.number.setText(code);

    }

    @Override
    public int getItemCount() {
        return mDataNames.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView number;

        public ViewHolder(View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tvCountryName);
            number = (TextView) itemView.findViewById(R.id.tvCountryCases);
        }
    }
}

