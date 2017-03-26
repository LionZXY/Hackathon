package vasidmi.ru.huawei;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver mBroadcastReceiver;
    RecyclerView newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBroadcastReceiver = new mbroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("load");
        registerReceiver(mBroadcastReceiver, intentFilter);
        Intent LoadNewsIntent = new Intent(this, LoadNews.class);
        newsList = (RecyclerView) findViewById(R.id.news_list);
        startService(LoadNewsIntent);
    }

    public class mbroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<String> titles = intent.getStringArrayListExtra("Titles");
            ArrayList<String> urls = intent.getStringArrayListExtra("Urls");
            ArrayList<NewsModel> data;
            data = NewsModel.fetchNews(titles.size(), titles, urls);
            NewsAdapter mNewsAdapter = new NewsAdapter(context, data);
            newsList.setAdapter(mNewsAdapter);
            newsList.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
