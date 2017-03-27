package ru.skafcats.hackathon.helpers;

import java.io.File;

/**
 * Created by Nikita Kulikov on 27.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class FileHelper {

    public static String getNameByFile(File file) {
        String toExit = file.getAbsolutePath();
        int slashIndex = toExit.lastIndexOf("/") + 1;
        toExit = toExit.substring(slashIndex, toExit.indexOf(".", slashIndex)) + toExit.substring(toExit.lastIndexOf("."));
        return toExit;
    }
}
