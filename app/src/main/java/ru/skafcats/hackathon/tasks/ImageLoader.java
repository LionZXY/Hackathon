package ru.skafcats.hackathon.tasks;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import ru.skafcats.hackathon.enums.TaskType;
import ru.skafcats.hackathon.interfaces.IExecutor;
import ru.skafcats.hackathon.interfaces.ITask;
import ru.skafcats.hackathon.services.MultiResultReciever;

/**
 * Created by Nikita Kulikov on 26.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class ImageLoader extends ITask {
    private static final String TAG = "ImageLoader";
    private String url = null;

    public ImageLoader(Parcel in) {
        super(TaskType.NETWORK_TASK, 12);
        in.readString();
    }

    public ImageLoader(String url) {
        super(TaskType.NETWORK_TASK, 12);
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ImageLoader && obj != null && ((ImageLoader) obj).url != null && url != null && url.equals(((ImageLoader) obj).url);
    }

    @Override
    public void runInBackground(IExecutor executor) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();

            Response response = client.newCall(request).execute();

            Bundle bundle = new Bundle();
            bundle.putByteArray("image", response.body().bytes());
            executor.onProgressNotify(MultiResultReciever.CODE_RESULT_FINISH_TASK, bundle);
        } catch (Exception ex) {
            Log.e(TAG, "Ошибка при загрузке изображения", ex);
            executor.onProgressNotify(MultiResultReciever.CODE_RESULT_ERROR_TASK, null);
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
    }

    public static final Creator<ITask> CREATOR = new Creator<ITask>() {
        @Override
        public ITask createFromParcel(Parcel in) {
            return new ImageLoader(in);
        }

        @Override
        public ITask[] newArray(int size) {
            return new ImageLoader[size];
        }
    };
}
