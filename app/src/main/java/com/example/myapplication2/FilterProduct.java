package com.example.myapplication2;
import android.widget.Filter;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.Article;
import com.example.myapplication2.ArticlesAdaptor;

import java.util.ArrayList;

public class FilterProduct extends Filter{
    private ArticlesAdaptor adapter;
    private ArrayList<Article> filterList;
    RecyclerView recyclerView;

    public FilterProduct(ArticlesAdaptor adapter, ArrayList<Article> filterList){
        this.adapter=adapter;
        this.filterList=filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        ArrayList<Article> filteredModels=new ArrayList<>();

        FilterResults results=new FilterResults();
        //validation data for search query
        if(constraint!=null&& constraint.length()>0){
            //search filed not empty,searching something,perform search


            //change to uppecase to make it insensitive
            constraint=constraint.toString().toUpperCase();
            //store our filtered list

            for(int i=0;i<filterList.size();i++){
                //check
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint)||
                        filterList.get(i).getBody().toUpperCase().contains(constraint)){
                    //add filtered data tolist
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count=filteredModels.size();
            results.values=filteredModels;
        }

        else{
        //search filed empty not searching,return original/all/complete laist

            results.count=filterList.size();
            results.values=filteredModels;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
      //  adapter.filterList=(ArrayList<Article>) results.values;
        adapter.setData((filterList));
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
//refresh adapter
    }
}