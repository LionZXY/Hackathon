package ru.lionzxy.hackathon.interfaces;

import android.os.Parcel;
import android.os.Parcelable;

import ru.lionzxy.hackathon.enums.TaskType;

/**
 * Created by lionzxy on 25.03.17.
 */

public abstract class ITask implements Parcelable {
    private TaskType taskType = null;

    public ITask(TaskType taskType) {
        this.taskType = taskType;
    }


    public TaskType getTaskType() {
        return this.taskType;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }




    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

    public abstract void runInBackground(IExecutor executor);

}

