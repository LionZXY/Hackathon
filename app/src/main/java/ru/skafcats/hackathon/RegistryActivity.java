package ru.skafcats.hackathon;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Nikita Kulikov on 29.03.17.
 * <p>
 * Возможно полное или частичное копирование
 */

public class RegistryActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);
    }
}
