package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class NewsRecyclerViewAdapter  extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder> {
    Context mContext;
    List<NewsItem> mNews;

    public NewsRecyclerViewAdapter(Context mContext, ArrayList<NewsItem> mNews) {
        this.mContext = mContext;
        this.mNews = mNews;
    }


    @Override
    public NewsRecyclerViewAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.card_news_item, parent, shouldAttachToParentImmediately);
        NewsViewHolder viewHolder = new NewsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NewsRecyclerViewAdapter.NewsViewHolder holder, int position) {
        holder.bind(position);
    }

    void setNews(List<NewsItem> news) {
        mNews = news;

    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView title;
        TextView description;
//        TextView date;

        public NewsViewHolder( View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.cardtitle);
            description = (TextView) itemView.findViewById(R.id.carddescription);
            img = (ImageView) itemView.findViewById(R.id.cardimg);
//            date = (TextView) itemView.findViewById(R.id.date);
        }

        void bind( int listIndex) {
 //           title.setText("Title: " + mNews.get(listIndex).getTitle());
 //           description.setText("Description: " + mNews.get(listIndex).getDescription());
//            date.setText("Date: " + mNews.get(listIndex).getPublishedAt());

            title.setText(mNews.get(listIndex).getTitle());
            description.setText(mNews.get(listIndex).getPublishedAt() + " . " + mNews.get(listIndex).getDescription());

            String url = mNews.get(listIndex).getUrltoImage();

            if(url != null) {
                Picasso.get()
                        .load(url)
                        .into(img);
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String urlString = mNews.get(getAdapterPosition()).getUrl();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
            mContext.startActivity(browserIntent);
        }
    }


}
