package com.billies_works.mypassmemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by se-ichi on 17/08/26.
 */

public class EditPass extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pass);

        // タイトル
        setTitle("@string/text02_title");
    }
}
