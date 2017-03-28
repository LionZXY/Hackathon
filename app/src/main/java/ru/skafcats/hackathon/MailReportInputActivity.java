package ru.skafcats.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;

import ru.skafcats.hackathon.adapters.AttachmentAdapter;
import ru.skafcats.hackathon.models.MailReport;

/**
 * Created by Nikita Kulikov on 29.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class MailReportInputActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION = 20;
    public static final int FILE_SELECT = 21;

    private RecyclerView recyclerView = null;
    private AttachmentAdapter adapter = null;
    private MailReport mailReport = new MailReport("Empty", "Empty");
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report_input);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.attachment_list);
        adapter = new AttachmentAdapter(this, this, mailReport);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    File file = new File(data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH));
                    Log.i("MailActivity", file.getAbsolutePath());
                    mailReport.addAttachment(file);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new MaterialFilePicker()
                            .withActivity(this)
                            .withRequestCode(FILE_SELECT)
                            .withHiddenFiles(true)
                            .start();
                }
                return;
            }

        }
    }
}
