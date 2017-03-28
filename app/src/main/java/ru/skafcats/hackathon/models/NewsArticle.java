package ru.skafcats.hackathon.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import ru.skafcats.hackathon.helpers.TaskHelper;
import ru.skafcats.hackathon.interfaces.ITaskAnswerListener;
import ru.skafcats.hackathon.tasks.ImageLoader;

/**
 * Created by Nikita Kulikov on 26.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class NewsArticle implements Parcelable {
    private String title;
    private String img_url;
    private String date;

    public NewsArticle(String title, String img_url, String date) {
        this.title = title;
        this.img_url = img_url;
        this.date = date;
    }

    public NewsArticle(Parcel in) {
        title = in.readString();
        img_url = in.readString();
        date = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getURL() {
        return img_url;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(img_url);
        dest.writeString(date);
    }

    public void loadImage(final ImageView imageView) {
        TaskHelper.addListener(imageView.getContext(), new ImageLoader(img_url), new ITaskAnswerListener() {
            @Override
            public void onAnswer(Bundle data) {
                if (data != null) {
                    byte[] bytes = data.getByteArray("image");
                    if (bytes != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        });
    }

    public static final Creator<NewsArticle> CREATOR = new Creator<NewsArticle>() {
        @Override
        public NewsArticle createFromParcel(Parcel in) {
            return new NewsArticle(in);
        }

        @Override
        public NewsArticle[] newArray(int size) {
            return new NewsArticle[size];
        }
    };
}
