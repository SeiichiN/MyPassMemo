package com.billies_works.mypassmemo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by se-ichi on 17/09/16.
 */

public class ViewPass extends AppCompatActivity {

    TextView mTextView03Library;
    TextView mTextView03LoginId;
    TextView mTextView03Password;
    TextView mTextView03Memo;

    Button mButton03Edit;
    Button mButton03Show;
    Button mButton03Delete;

    private Intent i;
    private long listID;
    private ArrayList<String> items;

    private SimpleDatabaseHelper helper = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pass);

        setTitle(R.string.text03_title);

        findview();

        i = this.getIntent();
        if (i != null) {
            listID = (long) i.getLongExtra("dbNo", 0);
            // Toast.makeText(this, String.format("listId= %d", listID), Toast.LENGTH_SHORT).show();
            loadData(listID);
        }
        else {
            // init();         // 初期値設定
        }

        // 編集ボタンをクリックした時の処理
        mButton03Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != null ) {
                    // EditPassへintentする
                    Intent intent = new Intent(ViewPass.this, com.billies_works.mypassmemo.EditPass.class);
                    intent.putExtra("dbNo", listID);
                    startActivity(intent);
                }
            }
        });

        // 一覧に戻るボタンをクリックした時の処理
        mButton03Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // 新規作成ボタンをクリックしたときの処理
        /* mButton02New.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newData();
            }
        });
        */

        // 削除ボタンを押したときの処理
        mButton03Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i != null) {
                    delData(listID);
                }
            }
        });
    }


    /**
     * 各変数とレイアウトファイルの各項目とを結びつける処理
     */
    private void findview() {

        // ListView -- レイアウトファイルと変数を結びつける
        mTextView03Library = (TextView) findViewById(R.id.textView03Library);
        mTextView03LoginId = (TextView) findViewById(R.id.textView03LoginID);
        mTextView03Password = (TextView) findViewById(R.id.textView03PassWD);
        mTextView03Memo = (TextView) findViewById(R.id.textView03Memo);

        mButton03Edit = (Button) findViewById(R.id.button03Edit);
        mButton03Show = (Button) findViewById(R.id.button03Show);
        mButton03Delete = (Button) findViewById(R.id.button03Delete);
    }

    private void loadData(long listId){

        // 配列を用意
        items = new ArrayList<>();

        if (listId >= 0) {
            Toast.makeText(this, String.format("ID = %d", listId), Toast.LENGTH_SHORT).show();

            helper = new SimpleDatabaseHelper(this);

            // データベース取得
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cs = null;
            try {
                Toast.makeText(this, "接続しました。",Toast.LENGTH_SHORT).show();
                // ヘルパーを使ってデータをカーソルに読み込む。
                String[] idNo = {String.valueOf(listId)};
                cs = helper.searchId(db, idNo);

                if (cs.moveToFirst()){
                    do {
                        mTextView03Library.setText(cs.getString(1));
                        mTextView03LoginId.setText(cs.getString(2));
                        mTextView03Password.setText(cs.getString(3));
                        mTextView03Memo.setText(cs.getString(4));
                        Log.d("取得したCursor:", cs.getString(1));
                    } while (cs.moveToNext());
                }
            } finally {
                db.close();
            }

        }
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


    // メニュー定義ファイルをもとにオプションメニューを生成
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Toast toast = Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT);
        // toast.show();
        Intent intent = new Intent(ViewPass.this, com.billies_works.mypassmemo.EditPass.class);
        intent.putExtra("dbNo", 0);
        startActivity(intent);
        return true;
    }

}
