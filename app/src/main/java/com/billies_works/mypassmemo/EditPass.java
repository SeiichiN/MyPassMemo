package com.billies_works.mypassmemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private TextView mText02Kome01;
    private TextView mText02Kome02;
    private TextView mText02Kome03;
    private TextView mText02Kome04;

    private Button mButton02Regist;
    private Button mButton02Show;
    private Button mButton02New;
    private Button mButton02Delete;

    private ArrayList<String> items;
    private Long listID;
    private Intent i;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pass);

        // タイトル
        setTitle(R.string.text02_title);

        // 各変数とレイアウトファイルの項目を結びつける
        findview();

        i = this.getIntent();
        if (i != null) {
            listID = (long) i.getLongExtra("dbNo", 0);
            loadData(listID);
        }
        else {
            // init();         // 初期値設定
        }

        // 登録ボタンをクリックした時の処理
        mButton02Regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != null ) {
                    saveData(listID);
                }
            }
        });

        // 一覧に戻るボタンをクリックした時の処理
        mButton02Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 新規作成ボタンをクリックしたときの処理
        mButton02New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newData();
            }
        });


        mButton02Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != null) {
                    delData(listID);
                }
            }
        });

    }

    private void loadData(long listId){

        // 配列を用意
        items = new ArrayList<>();

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

    private void saveData(long idNo) {
        // 各EditTextで入力されたテキストを取得
        String strLibrary = mEditText02Library.getText().toString();
        String strLoginId = mEditText02LoginId.getText().toString();
        String strPassword = mEditText02Password.getText().toString();
        String strMemo = mEditText02Memo.getText().toString();
        String saveDataStr[] = {strLibrary, strLoginId, strPassword, strMemo};

        // データベース取得
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            Toast.makeText(this, "接続しました。", Toast.LENGTH_SHORT).show();
            if (idNo > 0) {
                if (helper.saveUpdate(db, idNo, saveDataStr)) {
                    Toast.makeText(this, "更新しました。", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "更新できませんでした。", Toast.LENGTH_SHORT).show();
                }
            } else if (idNo == 0) {
                if (helper.saveNew(db, saveDataStr)) {
                    Toast.makeText(this, "新規登録しました。", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "新規登録に失敗しました。", Toast.LENGTH_SHORT).show();
                }
            }
        } finally {
            db.close();
        }
    }

    private void newData() {
        init();
        listID = (long) 0;
    }

    // 削除処理
    private void delData(long idNo) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            if (idNo > 0) {
                if (helper.deleteId(db, idNo)) {
                    Toast.makeText(this, "削除しました。", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "削除できませんでした。", Toast.LENGTH_SHORT).show();
                }
            }
        } finally {
            db.close();
        }
    }

    /**
     * 各変数とレイアウトファイルの各項目とを結びつける処理
     */
    private void findview() {

        // ListView -- レイアウトファイルと変数を結びつける
        mEditText02Library = (EditText) findViewById(R.id.editText02Library);
        mEditText02LoginId = (EditText) findViewById(R.id.editText02LoginID);
        mEditText02Password = (EditText) findViewById(R.id.editText02PassWD);
        mEditText02Memo = (EditText) findViewById(R.id.editText02Memo);

        mText02Kome01 = (TextView) findViewById(R.id.text02Kome01);
        mText02Kome02 = (TextView) findViewById(R.id.text02Kome02);
        mText02Kome03 = (TextView) findViewById(R.id.text02Kome03);
        mText02Kome04 = (TextView) findViewById(R.id.text02Kome04);

        mButton02Regist = (Button) findViewById(R.id.button03Edit);
        mButton02Show = (Button) findViewById(R.id.button03Show);
        mButton02New = (Button) findViewById(R.id.button02New);
        mButton02Delete = (Button) findViewById(R.id.button02Delete);
    }

    /**
     * 初期値設定 (EditTextの入力欄は空白、※印は消す)
     * init()
     */
    private void init() {
        mEditText02Library.setText("");
        mEditText02LoginId.setText("");
        mEditText02Password.setText("");
        mEditText02Memo.setText("");

        mText02Kome01.setText("");
        mText02Kome02.setText("");
        mText02Kome03.setText("");
        mText02Kome04.setText("");
        mEditText02Library.requestFocus();      // フォーカスを登録名のEditTextに指定
    }

}
