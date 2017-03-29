package ru.skafcats.hackathon.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Nikita Kulikov on 27.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class MailReport implements Parcelable {
    public static final String REGEXP_EMAIL_ADDRESS = "^(.+@.+[.].+)$";
    public static final String TAG = "MailReport";
    public static final long MAX_BYTES = 1024 * 20;

    private String title = null;
    private String text = null;
    private String mailTo = null;
    private String mailFrom = null;
    private ArrayList<File> attachment = new ArrayList<>();

    public MailReport(String title, String text) {
        if (title == null)
            title = "NULL";
        if (text == null)
            text = "NULL";

        this.title = title;
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean setMailTo(String to) {
        if (to != null && to.length() > 0) {
            if (to.matches(REGEXP_EMAIL_ADDRESS)) {
                this.mailTo = to;
                return true;
            }
        }
        return false;
    }

    public boolean setMailFrom(String from) {
        if (from != null && from.length() > 0) {
            if (from.matches(REGEXP_EMAIL_ADDRESS)) {
                this.mailFrom = from;
                return true;
            }
        }
        return false;
    }


    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getMailTo() {
        return mailTo;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    public boolean addAttachment(File file) {
        try {
            if (file != null && file.exists() && file.isFile() && file.length() < MAX_BYTES) {
                attachment.add(file);
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, "Error when attachment added", e);
        }
        return false;
    }

    public ArrayList<File> getAttachment() {
        return attachment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public MailReport(Parcel in) {
        title = in.readString();
        text = in.readString();
        mailTo = in.readString();
        mailFrom = in.readString();
        Serializable maybeAttachment = in.readSerializable();
        if (maybeAttachment != null && maybeAttachment instanceof ArrayList)
            attachment = (ArrayList<File>) maybeAttachment;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(mailTo);
        dest.writeString(mailFrom);
        dest.writeSerializable(attachment);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MailReport))
            return false;
        MailReport mailReport = (MailReport) obj;
        if (mailReport.attachment != null && mailReport.attachment.size() == attachment.size()) {
            for (int i = 0; i < attachment.size(); i++)
                if (!attachment.get(i).equals(mailReport.attachment.get(i)))
                    return false;
        } else return false;
        return mailReport.title != null && mailReport.title.equals(title) &&
                mailReport.text != null && mailReport.text.equals(text) &&
                mailReport.mailTo != null && mailReport.mailTo.equals(mailTo) &&
                mailReport.mailFrom != null && mailReport.mailFrom.equals(mailFrom);
    }

    public static final Creator<MailReport> CREATOR = new Creator<MailReport>() {
        @Override
        public MailReport createFromParcel(Parcel in) {
            return new MailReport(in);
        }

        @Override
        public MailReport[] newArray(int size) {
            return new MailReport[size];
        }
    };
}
