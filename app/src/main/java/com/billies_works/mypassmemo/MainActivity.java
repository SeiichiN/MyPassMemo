package com.billies_works.mypassmemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SimpleDatabaseHelper helper = null;
    private ListView mListView01Library;
    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // タイトル名
        setTitle("アカウント一覧");

        // 配列を用意
        items = new ArrayList<>();

        // ListView -- レイアウトファイルと変数を結びつける
        mListView01Library = (ListView) findViewById(R.id.listView01Library);

        helper = new SimpleDatabaseHelper(this);

        // データベース取得
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs = null;
        try {
            Toast.makeText(this, "接続しました。",Toast.LENGTH_SHORT).show();
            // ヘルパーを使ってデータをカーソルに読み込む。
            cs = helper.readAll(db);
            if (cs.moveToFirst()){
                do {
                    items.add(cs.getString(1));
                    Log.d("取得したCursor:", cs.getString(1));
                } while (cs.moveToNext());
            }
        } finally {
            db.close();
        }

        // ArrayAdapterのコンストラクタ
        // 第1引数 -- Context
        // 第2引数 -- リソースとして登録されたTextViewに対するリソースID
        //          今回はもともと用意されている定義済みのレイアウトファイルのID
        // 第3引数 -- 一覧させたいデータの配列
        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, items);

        // ListViewにアダプラーをセット（＝表示）
        mListView01Library.setAdapter(adapter);

        // ArrayAdapterに対してListViewのリスト(items)の更新
        adapter.notifyDataSetChanged();

        // リスト項目をクリックしたときの処理
        mListView01Library.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> av,
                                            View view, int position, long id) {
                        // 処理
                        String msg = String.format("id= %d", id);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        // activity_mainへ遷移
                        Intent intent = new Intent(MainActivity.this, com.billies_works.mypassmemo.EditPass.class);
                        intent.putExtra("dbNo", id);
                        startActivity(intent);

                    }

                }

        );

    }
}

