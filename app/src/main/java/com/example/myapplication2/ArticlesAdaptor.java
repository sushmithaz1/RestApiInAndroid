package com.example.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArticlesAdaptor extends RecyclerView.Adapter<ArticleViewHolder> {

    public ArrayList<Article> articles,filterList;
    private FilterProduct filter;
    private Context context;

    public ArticlesAdaptor() {
        articles=new ArrayList<>();
        this.filterList=articles;
    }

    public void setData(ArrayList<Article> articles){
        this.articles=articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View articleview=layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new ArticleViewHolder(articleview);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article=articles.get(position);
        Picasso.get().load(article.image).into(holder.image);
        holder.title.setText(article.title);
        holder.body.setText(article.body);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

     public Filter getFilter() {
        filter=new FilterProduct(this,filterList);
        return filter;
    }
}
