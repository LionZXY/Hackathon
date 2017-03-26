package ru.skafcats.hackathon;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ru.skafcats.hackathon.helpers.TaskHelper;
import ru.skafcats.hackathon.interfaces.ITaskAnswerListener;
import ru.skafcats.hackathon.tasks.TestTask;

public class MainActivity extends AppCompatActivity implements ITaskAnswerListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskHelper.addListener(MainActivity.this, new TestTask(), MainActivity.this);
            }
        });
    }

    @Override
    public void onAnswer(Bundle data) {
        Log.i("MainActivity", data.getInt("random") + " Выполнение в UI потоке: " + (Looper.myLooper() == Looper.getMainLooper()));
    }
}
