package ru.skafcats.hackathon.services;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.CopyOnWriteArrayList;

import ru.skafcats.hackathon.interfaces.IExecutor;
import ru.skafcats.hackathon.interfaces.ITask;

/**
 * Created by lionzxy on 25.03.17.
 */

public class ThreadTask extends Thread {
    public static final String TAG = "ThreadTask";
    private MultiResultReciever resultReceiver = null;
    private final CopyOnWriteArrayList<ITask> tasks = new CopyOnWriteArrayList<>();

    ThreadTask(MultiResultReciever resultReceiver) {
        this.resultReceiver = resultReceiver;
    }

    @Override
    public void run() {
        super.run();
        ITask task = null;

        while (tasks.size() > 0 && !isInterrupted()) {
            synchronized (tasks) {
                task = tasks.remove(tasks.size() - 1);
            }
            final ITask finalTask = task;
            task.runInBackground(new IExecutor() {
                @Override
                public void onProgressNotify(int resultCode, Bundle data) {
                    if (data == null)
                        data = new Bundle();
                    data.putParcelable("task", finalTask);
                    resultReceiver.send(resultCode, data);
                }
            });
        }
        if (!isInterrupted())
            try {
                synchronized (tasks) {
                    tasks.wait();
                }
                if (!isInterrupted())
                    run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        synchronized (tasks) {
            tasks.clear();
        }

    }

    public void addTask(ITask task) {
        synchronized (tasks) {
            if (task != null) {
                Log.d(TAG, "Добавление задачи...");
                if (tasks.contains(task)) {
                    tasks.remove(task);
                    tasks.add(task);
                } else tasks.add(task);
                tasks.notifyAll();
            }
        }
    }

    public void removeTask(ITask task) {
        synchronized (tasks) {
            if (task != null) {
                tasks.remove(task);
            }
        }
    }
}
