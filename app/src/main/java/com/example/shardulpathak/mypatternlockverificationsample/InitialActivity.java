package com.example.shardulpathak.mypatternlockverificationsample;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;

import java.util.ArrayList;
import java.util.List;

public class InitialActivity extends AppCompatActivity {

    PatternLockView mPatternLockView;
    Button mNextButton;
    private static final String TAG = InitialActivity.class.getSimpleName();
    List<PatternLockView.Dot> mLocalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean closeApp = getIntent().getBooleanExtra("closeApp", false);
        if (closeApp) {
            finish();
        }
        mPatternLockView = findViewById(R.id.initial_pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent patternIntent = new Intent(getApplication(), VerificationActivity.class);
                patternIntent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) mLocalList);
                startActivity(patternIntent);
            }
        });
    }

    private PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
        /**
         * Fired when the pattern drawing has just started
         */
        @Override
        public void onStarted() {
            Log.d(TAG, "Pattern drawing started");
        }

        /**
         * Fired when the pattern is still being drawn and progressed to
         * one more {@link PatternLockView.Dot}
         *
         * @param progressPattern
         */
        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(TAG, "Pattern drawing in progress");
        }

        /**
         * Fired when the user has completed drawing the pattern and has moved their finger away
         * from the view
         *
         * @param pattern
         */
        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.d(TAG, "Pattern drawing complete");
            mLocalList = new ArrayList<>();
            mLocalList.addAll(pattern);
            Log.d(TAG, "list is:" + mLocalList);
        }

        /**
         * Fired when the patten has been cleared from the view
         */
        @Override
        public void onCleared() {
            Log.d(TAG, "Pattern drawing cleared");
            Toast.makeText(getApplication(), "Pattern Saved", Toast.LENGTH_LONG).show();
        }
    };
}
