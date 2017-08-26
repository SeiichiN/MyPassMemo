package com.billies_works.mypassmemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 参考：『Androidアプリ開発・第２版』p.465
 * Created by se-ichi on 17/08/03.
 */

public class SimpleDatabaseHelper extends SQLiteOpenHelper {

    static final private String DB_NAME = "mypass.db";
    static final private String DB_TABLE = "myPass";
    static final private int VERSION = 1;

    /**
     * DBのカラム名
     */
    public final static String COL_ID = "_id";
    public final static String COL_LIBRARY = "library";
    public final static String COL_LOGINID = "loginid";
    public final static String COL_PASSWD = "passwd";
    public final static String COL_MEMO = "memo";


    // コンストラクタ
    public SimpleDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    /**
     * SQLiteOpenHelperは、データベースを開く際にデータベースの有無を調べる。
     * データベースが存在しない場合にデータベースを作成する。
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // テーブルを生成するSQL文の定義 ＊スペースに気をつける
        String createTbl = "CREATE TABLE " + DB_TABLE + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_LIBRARY + " TEXT NOT NULL,"
                + COL_LOGINID + " TEXT NOT NULL,"
                + COL_PASSWD + " TEXT NOT NULL,"
                + COL_MEMO + " TEXT"
                + ");";

        db.execSQL(createTbl);

        String testData1 = "INSERT INTO " + DB_TABLE + "("
                + COL_LIBRARY + "," + COL_LOGINID + "," + COL_PASSWD + "," + COL_MEMO + ")"
                + " VALUES ('中山真一', 'kuon5505@gmail.com', '2w@t0ri3', 'メール用')";

        String testData2 = "INSERT INTO " + DB_TABLE + "("
                + COL_LIBRARY + "," + COL_LOGINID + "," + COL_PASSWD + "," + COL_MEMO + ")"
                + " VALUES ('progate', 'billie175@gmail.com', 'per824Se', 'ユーザー名:billie')";

        String testData3 = "INSERT INTO " + DB_TABLE + "("
                + COL_LIBRARY + "," + COL_LOGINID + "," + COL_PASSWD + "," + COL_MEMO + ")"
                + " VALUES ('プログラミン', 'びりぃ', 'へぬた　ほねく　たはの', '')";

        db.execSQL(testData1);
        db.execSQL(testData2);
        db.execSQL(testData3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE );
        onCreate(db);
    }

    public Cursor readAll(SQLiteDatabase db) {
        return db.query(DB_TABLE, null,null,null,null, null, null);
    }
}
