package ru.skafcats.hackathon.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.skafcats.hackathon.R;

/**
 * Created by vasidmi on 28/03/2017.
 */

public class NewsFragment extends Fragment {

    public static final String TAG = "NewsFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activty_news, container, false);
    }
}
