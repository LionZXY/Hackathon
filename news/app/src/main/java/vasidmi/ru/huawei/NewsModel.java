package vasidmi.ru.huawei;

import java.util.ArrayList;

/**
 * Created by vasidmi on 26/03/2017.
 */

public class NewsModel {
    private String title;
    private String img_url;

    public NewsModel(String title, String img_url) {
        this.title = title;
        this.img_url = img_url;
    }

    public String getTitle() {
        return title;
    }

    public String getURL() {
        return img_url;
    }

    public static ArrayList<NewsModel> fetchNews(int total,ArrayList<String> titles,ArrayList<String> urls) {
        ArrayList<NewsModel> news = new ArrayList<NewsModel>();
        for (int i = 0; i < total; i++) {
            news.add(i,new NewsModel(titles.get(i),urls.get(i)));
        }
        return news;
    }
}
