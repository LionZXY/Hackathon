package ru.skafcats.hackathon.tasks;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.ByteString;
import ru.skafcats.hackathon.enums.TaskType;
import ru.skafcats.hackathon.helpers.FileHelper;
import ru.skafcats.hackathon.interfaces.IExecutor;
import ru.skafcats.hackathon.interfaces.ITask;
import ru.skafcats.hackathon.models.MailReport;
import ru.skafcats.hackathon.services.MultiResultReciever;

/**
 * Created by Nikita Kulikov on 27.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class MailSendTask extends ITask {
    public final static String MAILGUN_API_USERNAME = "api";
    public final static String MAILGUN_URL = "https://api.mailgun.net/v3/mg.lionzxy.ru/messages";
    public final static String SECRET_API_KEY_NOT_SEE_ON_THIS_PLEASE = "key-b89a27ca1a23f8309f8576878519fea6";
    public static final String TAG = "MailSendTask";
    public static final String STAGE_PROGRESS_NOTIFY = "mail.sender.notify";
    private MailReport mailObject = null;
    private OkHttpClient client = new OkHttpClient();

    public MailSendTask(MailReport mailobject) {
        super(TaskType.NETWORK_TASK, 15);
        this.mailObject = mailobject;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof MailSendTask))
            return false;
        return ((MailSendTask) obj).mailObject != null && ((MailSendTask) obj).mailObject.equals(mailObject);
    }

    @Override
    public void runInBackground(IExecutor executor) {
        Bundle bundle = new Bundle();
        bundle.putString(STAGE_PROGRESS_NOTIFY, "Получены данные. Формирование запроса началось...");
        if (mailObject != null)
            executor.onProgressNotify(MultiResultReciever.CODE_RESULT_PROGRESS_UPDATE_TASK, bundle);
        else {
            executor.onProgressNotify(MultiResultReciever.CODE_RESULT_ERROR_TASK, bundle);
            return;
        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        MediaType mediaType = MediaType.parse("form-data");

        for (File file : mailObject.getAttachment())
            builder.addFormDataPart("attachment", FileHelper.getNameByFile(file), RequestBody.create(mediaType, file));

        builder.addFormDataPart("from", mailObject.getMailFrom());
        builder.addFormDataPart("to", mailObject.getMailTo());
        builder.addFormDataPart("subject", mailObject.getTitle());
        builder.addFormDataPart("text", mailObject.getText());

        Request request = new Request.Builder().url(MAILGUN_URL).post(builder.build()).
                addHeader("Authentication", buildAuthHeader(SECRET_API_KEY_NOT_SEE_ON_THIS_PLEASE)).build();

        bundle.putString(STAGE_PROGRESS_NOTIFY, "Отправка данных на сервер...");
        executor.onProgressNotify(MultiResultReciever.CODE_RESULT_PROGRESS_UPDATE_TASK, bundle);
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                executor.onProgressNotify(MultiResultReciever.CODE_RESULT_FINISH_TASK, bundle);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while send request", e);
            executor.onProgressNotify(MultiResultReciever.CODE_RESULT_ERROR_TASK, bundle);
            return;
        }
    }

    private static String buildAuthHeader(String mailgunApiKey) {
        String authString = String.format("%s:%s", MAILGUN_API_USERNAME, mailgunApiKey);
        ByteString authData = ByteString.encodeUtf8(authString);
        return "Basic " + authData.base64();
    }

    public MailSendTask(Parcel in) {
        this((MailReport) in.readParcelable(MailReport.class.getClassLoader()));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mailObject, 0);
    }

    public static final Creator<ITask> CREATOR = new Creator<ITask>() {
        @Override
        public ITask createFromParcel(Parcel in) {
            return new MailSendTask(in);
        }

        @Override
        public ITask[] newArray(int size) {
            return new MailSendTask[size];
        }
    };
}
