package com.billies_works.mypassmemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by se-ichi on 17/08/26.
 */

public class EditPass extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pass);

        // タイトル
        setTitle(R.string.text02_title);

        Intent i = this.getIntent();
        if (i != null) {
            int listID = (int) i.getIntExtra("dbNo", 0);
            String msg = String.format("ID = %d", listID);
            Log.d("取得したlistID: ", msg);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            // loadData(listID);
        }
        else {
            // init();         // 初期値設定
        }
    }
}
