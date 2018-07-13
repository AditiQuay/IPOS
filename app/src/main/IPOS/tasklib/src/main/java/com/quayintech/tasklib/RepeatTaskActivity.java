package com.quayintech.tasklib;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.quayintech.tasklib.fragment.RepeatFragment;
import com.quayintech.tasklib.fragment.RepeatUntilFragment;

public class RepeatTaskActivity extends AppCompatActivity implements RepeatFragment.OnFragmentInteractionListener ,RepeatUntilFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repeat_task);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
