package ru.skafcats.hackathon.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

import java.util.HashMap;

import ru.skafcats.hackathon.enums.TaskType;
import ru.skafcats.hackathon.helpers.TaskHelper;
import ru.skafcats.hackathon.interfaces.ITask;
import ru.skafcats.hackathon.interfaces.ITaskAnswerListener;

/**
 * Created by Nikita Kulikov on 25.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class TaskExecutorService extends Service {
    public static final String TAG = "TaskService";
    public MultiResultReciever multiResultReciever = null;
    private static TaskExecutorService INSTANCE = null;
    public HashMap<TaskType, ThreadTask> taskThread = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        multiResultReciever = new MultiResultReciever(new Handler());
        ThreadTask tmpThread;
        Log.i(TAG, "UI: " + (Looper.myLooper() == Looper.getMainLooper()) + ". Запуск потоков...");
        for (TaskType taskType : TaskType.values()) {
            tmpThread = new ThreadTask(multiResultReciever);
            tmpThread.start();
            taskThread.put(taskType, tmpThread);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        INSTANCE = null;

        for (ThreadTask threadTask : taskThread.values())
            threadTask.interrupt();
        multiResultReciever.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        for (Pair<ITask, ITaskAnswerListener> pair : TaskHelper.flushBuffer())
            addListener(pair.first, pair.second);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Nullable
    public static TaskExecutorService getInstance() {
        return INSTANCE;
    }

    public void addListener(ITask task, ITaskAnswerListener listener) {
        if (task != null && listener != null) {
            multiResultReciever.addListener(task, listener);
            ThreadTask threadTask = taskThread.get(task.getTaskType());
            threadTask.addTask(task);
        }
    }
}
