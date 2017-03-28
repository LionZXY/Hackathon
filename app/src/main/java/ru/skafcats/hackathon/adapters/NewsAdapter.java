package ru.skafcats.hackathon.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import ru.skafcats.hackathon.R;
import ru.skafcats.hackathon.models.NewsArticle;

/**
 * Created by Василий on 26.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {


    List<NewsArticle> mNews = Collections.emptyList();
    private Context mContext;

    public NewsAdapter(Context context, List<NewsArticle> news) {
        this.mContext = context;
        this.mNews = news;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View newsView = inflater.inflate(R.layout.item_news, parent, false);

        ViewHolder viewHolder = new ViewHolder(newsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        NewsArticle news = mNews.get(position);
        holder.news_progress.setVisibility(View.VISIBLE);
        holder.title.setText(news.getTitle());
        holder.news_date.setText(news.getDate());
        Picasso.with(mContext).load(news.getURL()).into(holder.news_img, new Callback() {
            @Override
            public void onSuccess() {
                holder.news_progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView title;
        AppCompatImageView news_img;
        AppCompatTextView news_date;
        ProgressBar news_progress;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (AppCompatTextView) itemView.findViewById(R.id.news_item_title);
            news_img = (AppCompatImageView) itemView.findViewById(R.id.news_item_image);
            news_date = (AppCompatTextView) itemView.findViewById(R.id.news_item_date);
            news_progress = (ProgressBar) itemView.findViewById(R.id.news_item_progress);
        }
    }

}