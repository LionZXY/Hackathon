package ru.lionzxy.hackathon.tasks;

import android.os.Bundle;
import android.os.Looper;
import android.os.Parcel;
import android.util.Log;

import ru.lionzxy.hackathon.enums.TaskType;
import ru.lionzxy.hackathon.interfaces.IExecutor;
import ru.lionzxy.hackathon.interfaces.ITask;
import ru.lionzxy.hackathon.services.MultiResultReciever;

/**
 * Created by lionzxy on 26.03.17.
 */

public class TestTask extends ITask {

    public static final String TAG = "TestTask";

    public TestTask() {
        super(TaskType.NETWORK_TASK);
    }

    public TestTask(Parcel in) {
        super(TaskType.NETWORK_TASK);
    }

    @Override
    public int hashCode() {
        return 10;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof TestTask;
    }

    @Override
    public void runInBackground(IExecutor executor) {
        Log.i(TAG, "Выполнение таска. В UI: " + (Looper.myLooper() == Looper.getMainLooper()));

        executor.onProgressNotify(MultiResultReciever.CODE_RESULT_FINISH_TASK, new Bundle());
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
