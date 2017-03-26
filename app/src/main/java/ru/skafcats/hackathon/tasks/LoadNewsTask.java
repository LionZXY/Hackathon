package ru.skafcats.hackathon.tasks;

import android.os.Bundle;
import android.os.Parcel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ru.skafcats.hackathon.enums.TaskType;
import ru.skafcats.hackathon.interfaces.IExecutor;
import ru.skafcats.hackathon.interfaces.ITask;
import ru.skafcats.hackathon.models.NewsArticle;
import ru.skafcats.hackathon.services.MultiResultReciever;

/**
 * Created by Nikita Kulikov on 26.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class LoadNewsTask extends ITask {
    public static final String EXTRA_NEWS_LIST = "news.load";

    public LoadNewsTask(Parcel in) {
        super(TaskType.NETWORK_TASK, 11);
    }

    public LoadNewsTask() {
        super(TaskType.NETWORK_TASK, 11);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof LoadNewsTask;
    }

    @Override
    public void runInBackground(IExecutor executor) {
        try {
            ArrayList<NewsArticle> news = new ArrayList<>();
            Document doc = Jsoup.connect("http://consumer.huawei.com/ru/press/news/index-1.htm#anchor").get();
            Elements elements_titles = doc.select("div[class=\"left\"] img");
            for (int i = 0; i < elements_titles.size(); i++) {
                news.add(new NewsArticle(elements_titles.get(i).attr("alt"), elements_titles.get(i).attr("src")));
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(EXTRA_NEWS_LIST, news);
            executor.onProgressNotify(MultiResultReciever.CODE_RESULT_FINISH_TASK, bundle);
        } catch (IOException e) {
            e.printStackTrace();
            executor.onProgressNotify(MultiResultReciever.CODE_RESULT_ERROR_TASK, null);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Creator<ITask> CREATOR = new Creator<ITask>() {
        @Override
        public ITask createFromParcel(Parcel in) {
            return new LoadNewsTask(in);
        }

        @Override
        public ITask[] newArray(int size) {
            return new LoadNewsTask[size];
        }
    };
}
