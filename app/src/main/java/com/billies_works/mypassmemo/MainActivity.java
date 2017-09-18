package com.billies_works.mypassmemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // ListItemオブジェクトをつくる
    private ArrayList<ListItem> data;
    MyListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // タイトル名
        setTitle("アカウント一覧");

        loaddata();

        // ListItem配列とレイアウトとの関連付け
        adapter = new MyListAdapter(this, data, R.layout.one_list);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

        // リスト項目をクリックしたときの処理
        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> av,
                                            View view, int position, long id) {
                        // 処理
                        String msg = String.format("id= %d", id);
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

                        // EditPassへ遷移
                        Intent intent = new Intent(MainActivity.this, com.billies_works.mypassmemo.ViewPass.class);
                        intent.putExtra("dbNo", id);
                        startActivityForResult(intent, 2);

                    }

                }
        );
    }

    // インテント先から戻ってきたときの処理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intentdata) {
        super.onActivityResult(requestCode, resultCode, intentdata);
        // リクエストコードと結果コードのチェック
        // リスト項目をクリックして戻ってきたときの処理
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Toast.makeText(this, "ViewPassから戻ったよ", Toast.LENGTH_SHORT).show();
            loaddata();
        }
        // 新規作成で戻ってきたときの処理
        else if (requestCode == 3 && resultCode == RESULT_OK) {
            Toast.makeText(this, "新規作成から戻ったよ", Toast.LENGTH_SHORT).show();
            loaddata();
        }
    }

    private void loaddata() {

        data = new ArrayList<>();

        SimpleDatabaseHelper helper = new SimpleDatabaseHelper(this);

        // データベース取得
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cs = null;
        try {
            // ヘルパーを使ってデータをカーソルに読み込む。
            cs = helper.readAll(db);
            if (cs.moveToFirst()) {
                do {
                    ListItem item = new ListItem();
                    item.setId(cs.getLong(0));
                    item.setLibrary(cs.getString(1));
                    // item.setLoginId(cs.getString(2));
                    // item.setPassword(cs.getString(3));
                    // item.setMemo(cs.getString(4));
                    data.add(item);
                } while (cs.moveToNext());
            }
            Toast.makeText(this, "データを読み込みました。", Toast.LENGTH_SHORT).show();
        }
        catch (ArrayIndexOutOfBoundsException e){
            Toast.makeText(this, "データ読み込みに失敗しました。", Toast.LENGTH_SHORT).show();
        }
        finally {
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
        Intent intent = new Intent(MainActivity.this, com.billies_works.mypassmemo.EditPass.class);
        intent.putExtra("dbNo", 0);
        startActivityForResult(intent, 3);
        return true;
    }
}

