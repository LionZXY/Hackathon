package ru.skafcats.hackathon.tasks;

import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.util.Log;

import java.util.Random;

import ru.skafcats.hackathon.enums.TaskType;
import ru.skafcats.hackathon.interfaces.IExecutor;
import ru.skafcats.hackathon.interfaces.ITask;
import ru.skafcats.hackathon.services.MultiResultReciever;

/**
 * Created by lionzxy on 26.03.17.
 */

public class TestTask extends ITask {

    public static final String TAG = "TestTask";

    public TestTask() {
        super(TaskType.NETWORK_TASK, 10);
    }

    public TestTask(Parcel in) {
        super(TaskType.NETWORK_TASK, 10);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TestTask;
    }

    @Override
    public void runInBackground(IExecutor executor) {
        Log.i(TAG, "Выполнение таска. В UI: " + (Looper.myLooper() == Looper.getMainLooper()));

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Random random = new Random();
        Bundle bundle = new Bundle();
        bundle.putInt("random", random.nextInt());
        executor.onProgressNotify(MultiResultReciever.CODE_RESULT_FINISH_TASK, bundle);
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Do nothing
    }

    public static final Creator<ITask> CREATOR = new Creator<ITask>() {
        @Override
        public ITask createFromParcel(Parcel in) {
            return new TestTask(in);
        }

        @Override
        public ITask[] newArray(int size) {
            return new TestTask[size];
        }
    };
}
