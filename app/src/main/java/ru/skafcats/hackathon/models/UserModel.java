package ru.skafcats.hackathon.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nikita Kulikov on 29.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class UserModel implements Parcelable {
    private String name = "Авторизуйтесь";
    private String surname = "";
    private String login = "Login";
    private String phone = "Phone";
    private String phoneModel = "Huawei SuperPhone 9000";

    private UserModel(Parcel in) {
        name = in.readString();
        surname = in.readString();
        login = in.readString();
        phone = in.readString();
        phoneModel = in.readString();
    }


    public UserModel(String login) {
        if (login == null)
            login = "Unknown Login";
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public String getPhone() {
        return phone;
    }


    public String getPhoneModel() {
        return phoneModel;
    }

    public UserModel setName(String name) {
        if (name != null)
            this.name = name;
        return this;
    }

    public UserModel setUsername(String surname) {
        if (surname != null)
            this.surname = surname;
        return this;
    }

    public UserModel setPhoneNumber(String phoneNumber) {
        if (phoneNumber != null)
            this.phone = phoneNumber;
        return this;
    }

    public UserModel setPhoneModel(String phoneModel) {
        if (phoneModel != null)
            this.phoneModel = phoneModel;
        return this;
    }

    public HashMap<String, String> getTable() {
        HashMap<String, String> table = new HashMap<>();
        table.put("Имя", name + " " + surname);
        table.put("Имя пользователя", login);
        table.put("Телефон", phone);
        table.put("Модель телефона", phoneModel);
        return table;
    }

    public File save(Context context) {
        File file = new File(context.getCacheDir(), "info.csv");
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
            for (Map.Entry<String, String> row : getTable().entrySet())
                outputStreamWriter.write(row.getKey() + '\t' + row.getValue() + '\n');
            outputStreamWriter.close();
        } catch (Exception e) {
            Log.e("UserModel", "While save", e);
            return null;
        }
        return file;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(login);
        dest.writeString(phone);
        dest.writeString(phoneModel);
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
