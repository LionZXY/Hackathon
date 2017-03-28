package ru.skafcats.hackathon.helpers;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import ru.skafcats.hackathon.MailReportInputActivity;

/**
 * Created by Nikita Kulikov on 29.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class PermissionHelper {
    public static boolean checkPermissionAndRequest(Activity activity, String permission) {
        if (ContextCompat.checkSelfPermission(activity,
                permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{permission},
                    MailReportInputActivity.REQUEST_PERMISSION);
            return false;
        } else return true;
    }
}
