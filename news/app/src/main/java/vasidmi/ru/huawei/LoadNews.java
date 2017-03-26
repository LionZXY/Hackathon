package vasidmi.ru.huawei;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by vasidmi on 26/03/2017.
 */

public class LoadNews extends IntentService {

    private final String TAG = "FetchNewsTag";

    public LoadNews() {
        super("LoadNews");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {
            ArrayList<String> titles = new ArrayList<String>();
            ArrayList<String> img_url = new ArrayList<String>();
            Document doc = Jsoup.connect("http://consumer.huawei.com/ru/press/news/index-1.htm#anchor").get();
            Elements elements_titles = doc.select("div[class=\"left\"] img");
            for (int i = 0; i < elements_titles.size(); i++) {
                Log.e("YAY", "" + elements_titles.get(i).attr("alt"));
                Log.e("YAY", "" + elements_titles.get(i).attr("src"));
                titles.add(i, elements_titles.get(i).attr("alt"));
                img_url.add(i, elements_titles.get(i).attr("src"));
            }
            Intent responseIntent = new Intent();
            responseIntent.setAction("load");
            responseIntent.putExtra("Titles", titles);
            responseIntent.putExtra("Urls", img_url);
            sendBroadcast(responseIntent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
