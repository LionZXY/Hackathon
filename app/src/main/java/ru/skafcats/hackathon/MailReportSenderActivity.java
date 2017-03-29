package ru.skafcats.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ru.skafcats.hackathon.helpers.PreferenceHelper;
import ru.skafcats.hackathon.helpers.TaskHelper;
import ru.skafcats.hackathon.interfaces.ITaskAnswerListener;
import ru.skafcats.hackathon.models.MailReport;
import ru.skafcats.hackathon.services.MultiResultReciever;
import ru.skafcats.hackathon.tasks.MailSendTask;

/**
 * Created by Nikita Kulikov on 29.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class MailReportSenderActivity extends Activity implements ITaskAnswerListener {
    private ImageView image = null;
    private ProgressBar progressBar = null;
    private TextView progressText = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_process);
        image = (ImageView) findViewById(R.id.progress_image);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressText = (TextView) findViewById(R.id.progress_text);


        if (getIntent() != null && getIntent().getParcelableExtra(MailReport.TAG) != null) {
            MailReport mailReport = getIntent().getParcelableExtra(MailReport.TAG);
            mailReport.setMailTo(/*"huawei.task@best-bmstu.ru"*/"nikita@kulikof.ru");
            mailReport.setMailFrom(new PreferenceHelper(this).getLogin("nickname") + "@mg.lionzxy.ru");
            TaskHelper.addListener(this, new MailSendTask(mailReport), this);
        } else {
            progressBar.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
            image.setImageResource(R.drawable.ic_clear_red_200dp);
            progressText.setText("Отсуствует MailReport");
        }
    }

    @Override
    public void onAnswer(Bundle data) {
        if (data != null) {
            int requestCode = data.getInt("resultCode");
            switch (requestCode) {
                case MultiResultReciever.CODE_RESULT_PROGRESS_UPDATE_TASK:
                    progressText.setText(data.getString(MailSendTask.STAGE_PROGRESS_NOTIFY, progressText.getText().toString()));
                    break;
                case MultiResultReciever.CODE_RESULT_ERROR_TASK:
                    progressBar.setVisibility(View.GONE);
                    image.setVisibility(View.VISIBLE);
                    image.setImageResource(R.drawable.ic_clear_red_200dp);
                    break;
                case MultiResultReciever.CODE_RESULT_FINISH_TASK:
                    progressBar.setVisibility(View.GONE);
                    image.setVisibility(View.VISIBLE);
                    image.setImageResource(R.drawable.ic_check_green_200dp);
                    progressText.setText("Отчет отправлен!");
                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MailReportSenderActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    }
}
