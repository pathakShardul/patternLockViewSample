package com.example.shardulpathak.mypatternlockverificationsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;

import java.util.ArrayList;
import java.util.List;

public class VerificationActivity extends AppCompatActivity {

    PatternLockView mPatternLockView;
    private static final String TAG = InitialActivity.class.getSimpleName();
    Button mDoneButton;
    List<PatternLockView.Dot> mNewList;
    List<PatternLockView.Dot> mInitialList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        mInitialList = new ArrayList<>();
        mInitialList = getIntent().getParcelableArrayListExtra("list");
        mPatternLockView = findViewById(R.id.final_pattern_lock_view);
        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
        mDoneButton = findViewById(R.id.done_button);
        mDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), InitialActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("closeApp", true);
                startActivity(intent);
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
            mNewList = new ArrayList<>();
            mNewList.addAll(pattern);
        }

        /**
         * Fired when the patten has been cleared from the view
         */
        @Override
        public void onCleared() {
            Log.d(TAG, "Pattern drawing cleared");
            assert mInitialList != null;
            if (!mInitialList.isEmpty()) {
                if (mInitialList.equals(mNewList)) {
                    Toast.makeText(getApplication(), "Pattern matched", Toast.LENGTH_LONG).show();
                } else if (!mInitialList.equals(mNewList)) {
                    Toast.makeText(getApplication(), "Pattern did not match", Toast.LENGTH_LONG).show();
                }
            }
        }
    };
}
