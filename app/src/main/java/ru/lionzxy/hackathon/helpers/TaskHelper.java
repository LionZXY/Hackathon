package ru.lionzxy.hackathon.helpers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import ru.lionzxy.hackathon.interfaces.ITask;
import ru.lionzxy.hackathon.interfaces.ITaskAnswerListener;
import ru.lionzxy.hackathon.services.TaskExecutorService;

/**
 * Created by lionzxy on 26.03.17.
 */

public class TaskHelper {
    public static final String TAG = "TaskHelper";
    public static final List<Pair<ITask, ITaskAnswerListener>> buffer = new ArrayList<>();

    public static void addListener(Context context, ITask task, ITaskAnswerListener taskAnswerListener) {
        if (context != null && task != null && taskAnswerListener != null) {
            if (TaskExecutorService.getInstance() == null) {
                createService(context);
                synchronized (buffer) {
                    buffer.add(new Pair<>(task, taskAnswerListener));
                }
            } else {
                try {
                    TaskExecutorService.getInstance().addListener(task, taskAnswerListener);
                } catch (NullPointerException npe) {
                    Log.e(TAG, "Ошибка при добавлении задачи", npe);
                }
            }
        }
    }

    public static List<Pair<ITask, ITaskAnswerListener>> flushBuffer() {
        List<Pair<ITask, ITaskAnswerListener>> toExit;
        synchronized (buffer) {
            toExit = new ArrayList<>(buffer);
            buffer.clear();
        }
        return toExit;
    }

    private static void createService(Context context) {
        Log.i(TAG, "Создание сервиса...");
        Intent intent = new Intent(context, TaskExecutorService.class);
        context.startService(intent);
    }
}
