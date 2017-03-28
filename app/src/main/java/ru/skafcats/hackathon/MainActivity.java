package ru.skafcats.hackathon;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import ru.skafcats.hackathon.adapters.NewsAdapter;
import ru.skafcats.hackathon.helpers.TaskHelper;
import ru.skafcats.hackathon.interfaces.ITaskAnswerListener;
import ru.skafcats.hackathon.models.NewsArticle;
import ru.skafcats.hackathon.navigation.NavigationDrawer;
import ru.skafcats.hackathon.tasks.LoadNewsTask;

public class MainActivity extends AppCompatActivity implements ITaskAnswerListener {
    RecyclerView recyclerView;
    NavigationDrawer mNavigationDrawer;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_news);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mNavigationDrawer = new NavigationDrawer(this, mToolbar);
        recyclerView = (RecyclerView) findViewById(R.id.news_list);
        TaskHelper.addListener(this, new LoadNewsTask(), this);

        /*MailReport mailReport = new MailReport("Test", "Это тест");
        mailReport.setMailFrom("nikita@mg.lionzxy.ru");
        mailReport.setMailTo("nikita@kulikof.ru");
        MailSendTask mailSendTask = new MailSendTask(mailReport);
        TaskHelper.addListener(this, mailSendTask, new ITaskAnswerListener() {
            @Override
            public void onAnswer(Bundle data) {
                if (data != null) {
                    Log.d("Test", data.getString(MailSendTask.STAGE_PROGRESS_NOTIFY, "Ничего тут нет"));
                }
            }
        });*/
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
