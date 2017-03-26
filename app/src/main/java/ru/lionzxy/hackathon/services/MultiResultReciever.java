package ru.lionzxy.hackathon.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.lionzxy.hackathon.interfaces.ITask;
import ru.lionzxy.hackathon.interfaces.ITaskAnswerListener;

/**
 * Created by Nikita Kulikov on 26.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class MultiResultReciever extends ResultReceiver {
    public static final int CODE_RESULT_FINISH_TASK = 10;
    public static final int CODE_RESULT_ERROR_TASK = 9;
    public static final int CODE_RESULT_PROGRESS_UPDATE_TASK = 8;

    private HashMap<ITask, List<ITaskAnswerListener>> taskListeners = new HashMap<ITask, List<ITaskAnswerListener>>();

    public MultiResultReciever(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultData != null) {
            ITask task = resultData.getParcelable("task");
            if (task != null) {
                List<ITaskAnswerListener> taskAnswerListeners = taskListeners.get(task);
                for (int i = 0; i < taskAnswerListeners.size(); i++)
                    if (taskAnswerListeners.get(i) != null)
                        taskAnswerListeners.get(i).onAnswer(resultData);

                if (resultCode == CODE_RESULT_FINISH_TASK || resultCode == CODE_RESULT_ERROR_TASK)
                    taskListeners.remove(task);
            }
        }
    }

    public void addListener(ITask task, ITaskAnswerListener iTaskAnswerListener) {
        if (task != null && iTaskAnswerListener != null) {
            if (taskListeners.containsKey(task)) {
                List<ITaskAnswerListener> taskAnswerListeners = taskListeners.get(task);
                if (taskAnswerListeners != null)
                    taskAnswerListeners.add(iTaskAnswerListener);
                else {
                    taskAnswerListeners = new ArrayList<>();
                    taskAnswerListeners.add(iTaskAnswerListener);
                    taskListeners.put(task, taskAnswerListeners);
                }
            } else {
                List<ITaskAnswerListener> taskAnswerListeners = new ArrayList<>();
                taskAnswerListeners.add(iTaskAnswerListener);
                taskListeners.put(task, taskAnswerListeners);
            }
        }
    }

    public void onDestroy() {
        //TODO
    }
}
