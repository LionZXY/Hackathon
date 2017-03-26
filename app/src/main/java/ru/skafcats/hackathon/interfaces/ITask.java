package ru.skafcats.hackathon.interfaces;

import android.os.Parcelable;

import ru.skafcats.hackathon.enums.TaskType;

/**
 * Created by lionzxy on 25.03.17.
 */

public abstract class ITask implements Parcelable {
    private TaskType taskType = null;
    private int taskCode = -1;

    public ITask(TaskType taskType, int taskCode) {
        this.taskType = taskType;
        this.taskCode = taskCode;
    }


    public TaskType getTaskType() {
        return this.taskType;
    }

    @Override
    public int describeContents() {
        return hashCode();
    }


    @Override
    public int hashCode() {
        return taskCode;
    }

    @Override
    public abstract boolean equals(Object obj);

    public abstract void runInBackground(IExecutor executor);

}

