package ru.skafcats.hackathon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ru.skafcats.hackathon.adapters.NewsAdapter;
import ru.skafcats.hackathon.helpers.TaskHelper;
import ru.skafcats.hackathon.interfaces.ITaskAnswerListener;
import ru.skafcats.hackathon.models.NewsArticle;
import ru.skafcats.hackathon.tasks.LoadNewsTask;

public class MainActivity extends AppCompatActivity implements ITaskAnswerListener {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_news);

        recyclerView = (RecyclerView) findViewById(R.id.news_list);

        TaskHelper.addListener(this, new LoadNewsTask(), this);
    }

    @Override
    public void onAnswer(Bundle data) {
        if (data != null) {
            ArrayList<NewsArticle> news = data.getParcelableArrayList(LoadNewsTask.EXTRA_NEWS_LIST);
            if (news != null) {
                NewsAdapter mNewsAdapter = new NewsAdapter(MainActivity.this, news);
                recyclerView.setAdapter(mNewsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            }
        }
    }
}
