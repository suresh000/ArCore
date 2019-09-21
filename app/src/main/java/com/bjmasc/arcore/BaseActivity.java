package com.bjmasc.arcore;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        finish();
    }
}
