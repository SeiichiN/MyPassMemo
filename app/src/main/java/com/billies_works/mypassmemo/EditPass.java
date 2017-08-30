package com.billies_works.mypassmemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by se-ichi on 17/08/26.
 */

public class EditPass extends AppCompatActivity {

    private SimpleDatabaseHelper helper = null;

    // private ListView mListView02Id;
    private TextView mEditText02Library;
    private TextView mEditText02LoginId;
    private TextView mEditText02Password;
    private TextView mEditText02Memo;
    private ArrayList<String> items;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pass);

        // タイトル
        setTitle(R.string.text02_title);

        Intent i = this.getIntent();
        if (i != null) {
            long listID = (long) i.getLongExtra("dbNo", 0);
            loadData(listID);
        }
        else {
            // init();         // 初期値設定
        }
    }

    private void loadData(long listId){

        // 配列を用意
        items = new ArrayList<>();

        // ListView -- レイアウトファイルと変数を結びつける
        mEditText02Library = (EditText) findViewById(R.id.editText02Library);
        mEditText02LoginId = (EditText) findViewById(R.id.editText02LoginID);
        mEditText02Password = (EditText) findViewById(R.id.editText02PassWD);
        mEditText02Memo = (EditText) findViewById(R.id.editText02Memo);


        if (listId >= 0) {
            Toast.makeText(this, String.format("ID = %d", listId), Toast.LENGTH_SHORT).show();

            helper = new SimpleDatabaseHelper(this);

            // データベース取得
            SQLiteDatabase db = helper.getWritableDatabase();
            Cursor cs = null;
            try {
                Toast.makeText(this, "接続しました。",Toast.LENGTH_SHORT).show();
                // ヘルパーを使ってデータをカーソルに読み込む。
                String[] idNo = {String.valueOf(listId)};
                cs = helper.searchId(db, idNo);

                if (cs.moveToFirst()){
                    do {
                        mEditText02Library.setText(cs.getString(1));
                        mEditText02LoginId.setText(cs.getString(2));
                        mEditText02Password.setText(cs.getString(3));
                        mEditText02Memo.setText(cs.getString(4));
                        Log.d("取得したCursor:", cs.getString(1));
                    } while (cs.moveToNext());
                }
            } finally {
                db.close();
            }

        }
    }

}
