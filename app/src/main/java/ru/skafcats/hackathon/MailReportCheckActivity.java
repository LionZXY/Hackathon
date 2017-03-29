package ru.skafcats.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.Map;

import ru.skafcats.hackathon.helpers.PreferenceHelper;
import ru.skafcats.hackathon.models.UserModel;

/**
 * Created by Nikita Kulikov on 29.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class MailReportCheckActivity extends AppCompatActivity {
    private TableLayout tableLayout = null;
    private UserModel userModel = null;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report_check);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Проверьте данные");

        mToolbar.findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MailReportCheckActivity.this, MailReportInputActivity.class);
                startActivity(intent);
            }
        });

        tableLayout = (TableLayout) findViewById(R.id.table);

        userModel = new PreferenceHelper(this).getUser();
        for (Map.Entry<String, String> row : userModel.getTable().entrySet())
            addRow(row.getKey(), row.getValue());
    }

    public void addRow(String field1, String field2) {
        View v = LayoutInflater.from(this).inflate(R.layout.item_tablerow, null);
        TextView fieldOne = (TextView) v.findViewById(R.id.fieldOne);
        TextView fieldTwo = (TextView) v.findViewById(R.id.fieldTwo);
        fieldOne.setText(field1);
        fieldTwo.setText(field2);
        tableLayout.addView(v);
    }
}
