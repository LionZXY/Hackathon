package vasidmi.ru.huawei;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by vasidmi on 26/03/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {


    List<NewsModel> mNews = Collections.emptyList();
    private Context mContext;

    public NewsAdapter(Context context,List<NewsModel> news){
        this.mContext = context;
        this.mNews = news;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View newsView = inflater.inflate(R.layout.news_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(newsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsModel news = mNews.get(position);
        holder.title.setText(news.getTitle());
        Picasso.with(mContext).load(news.getURL()).into(holder.news_img);
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView title;
        AppCompatImageView news_img;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (AppCompatTextView) itemView.findViewById(R.id.news_item_title);
            news_img = (AppCompatImageView) itemView.findViewById(R.id.news_item_image);
        }
    }

}