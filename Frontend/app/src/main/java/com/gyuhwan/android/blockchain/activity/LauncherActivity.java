package com.gyuhwan.android.blockchain.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gyuhwan.android.blockchain.R;

import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.functions.Action;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Completable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribe(new Action() {
                               @Override
                               public void run() throws Exception {
                                   startActivity(new Intent(getApplicationContext(),MainActivity.class));
                               }
                           }
                );
    }
}
