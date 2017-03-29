package ru.skafcats.hackathon;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.io.File;

import ru.skafcats.hackathon.adapters.AttachmentAdapter;
import ru.skafcats.hackathon.helpers.PreferenceHelper;
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
    private MailReport mailReport = new MailReport(null, null);
    private Toolbar mToolbar;

    private EditText title = null;
    private EditText description = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_report_input);

        title = (EditText) findViewById(R.id.title);
        description = (EditText) findViewById(R.id.description);

        if (savedInstanceState != null && savedInstanceState.getParcelable(MailReport.TAG) != null) {
            mailReport = savedInstanceState.getParcelable(MailReport.TAG);

            title.setText(mailReport.getTitle());
            description.setText(mailReport.getText());
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Введите отчет");

        mToolbar.findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MailReportInputActivity.this, MailReportSenderActivity.class);
                mailReport.setText(description.getText().toString());
                mailReport.setTitle(title.getText().toString());

                intent.putExtra(MailReport.TAG, mailReport);
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) findViewById(R.id.attachment_list);
        adapter = new AttachmentAdapter(this, this, mailReport);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        title.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mailReport.setTitle(title.getText().toString());
                return true;
            }
        });

        description.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                mailReport.setText(description.getText().toString());
                return true;
            }
        });


        File file = new PreferenceHelper(this).getUser().save(this);
        if (file != null)
            mailReport.addAttachment(file);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable(MailReport.TAG, mailReport);
        super.onSaveInstanceState(outState, outPersistentState);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
