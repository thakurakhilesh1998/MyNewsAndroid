package com.example.mynewsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynewsapp.Modal.NewsDataModal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class NewsArticlesAdapter extends RecyclerView.Adapter<NewsArticlesAdapter.Holder> {
    Context context;
    ArrayList<NewsDataModal> newsDataModals;

    public NewsArticlesAdapter(Context context, ArrayList<NewsDataModal> newsDataModals) {
        this.context = context;
        this.newsDataModals = newsDataModals;
    }

    @NonNull
    @Override
    public NewsArticlesAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsitem, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsArticlesAdapter.Holder holder, final int position) {

        if (newsDataModals.get(position).getDate() == null) {
            holder.tvPublishDate.setText(context.getResources().getString(R.string.datenotav));
        } else {
            holder.tvPublishDate.setText(context.getResources().getString(R.string.publish) + newsDataModals.get(position).getDate().substring(0, 9));
        }

        if (newsDataModals.get(position).getDesc() == null) {
            holder.tvDes.setText(context.getResources().getString(R.string.desnotav));
        } else {
            holder.tvDes.setText(newsDataModals.get(position).getDesc());
        }
        if (newsDataModals.get(position).getTitle() == null) {
            holder.tvTitle.setText(context.getResources().getString(R.string.titlena));
        } else {
            holder.tvTitle.setText(newsDataModals.get(position).getTitle());
        }
        if (newsDataModals.get(position).getSource() == null) {
            holder.tvSource.setText(context.getResources().getString(R.string.sourcena));
        } else {
            holder.tvSource.setText(context.getResources().getString(R.string.source) + newsDataModals.get(position).getSource());
        }
        if (newsDataModals.get(position).getImageUrl() != null) {
            Picasso.get().load(newsDataModals.get(position).getImageUrl()).into(holder.ivNews);
        } else {
            holder.ivNews.setImageResource(R.drawable.noimage);
        }


        holder.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newsDataModals.get(position).getUrlToSource() != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsDataModals.get(position).getUrlToSource()));
                    context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.open)));
                } else {
                    Toast.makeText(context, context.getResources().getString(R.string.notfound), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsDataModals.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        ImageView ivNews;
        TextView tvTitle;
        TextView tvPublishDate;
        TextView tvDes;
        TextView tvSource;
        TextView tvLink;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ivNews = itemView.findViewById(R.id.ivNews);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPublishDate = itemView.findViewById(R.id.tvPublishDate);
            tvDes = itemView.findViewById(R.id.tvDes);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvLink = itemView.findViewById(R.id.tvLink);
        }
    }
}
