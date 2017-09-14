package com.billies_works.mypassmemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.AtomicFile;

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
    public static String COL_ID = "_id";
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
     *
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
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    public Cursor readAll(SQLiteDatabase db) {
        return db.query(DB_TABLE, null, null, null, null, null, null);
    }

    /**
     * searchId -- id値でデータベースを検索する
     *
     * @param db
     * @param args -- 検索するidを配列で収める。
     *             実際にはこの配列にはデータがひとつしかない。
     *             （検索するid値のみ）
     * @return -- そのidのデータがCursorオブジェクトに収められる。
     */
    public Cursor searchId(SQLiteDatabase db, String[] args) {
        String whereid = COL_ID + "= ?";
        return db.query(DB_TABLE, null, whereid, args, null, null, null, null);
    }

    /**
     * save -- データベースにデータをセーブする。
     *
     * @param db   　-- データベースのオブジェクト
     * @param id   -- 保存するid(テーブルのカラムid) long
     * @param args -- 保存するデータの配列
     *             順番は、テーブル作成時に設定したカラムの順番であること。
     * @return -- 成功時(true) 失敗時(false)
     */
    public boolean saveUpdate(SQLiteDatabase db, long id, String[] args) {
        int success = 0;

        ContentValues values = new ContentValues();
        values.put(COL_LIBRARY, args[0]);
        values.put(COL_LOGINID, args[1]);
        values.put(COL_PASSWD, args[2]);
        values.put(COL_MEMO, args[3]);
        String whereClause = COL_ID + " = ?";
        String whereArgs[] = {String.valueOf(id)};

        db.beginTransaction();
        try {
            db.update(DB_TABLE, values, whereClause, whereArgs);
            db.setTransactionSuccessful();
            success = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        /*
        // この方法でやりたかったが、知りたい情報が見つからなかった。
        String textData = "UPDATE " + DB_TABLE + "SET "
                + COL_LIBRARY + " = ?, "
                + COL_LOGINID + " = ?, "
                + COL_PASSWD + " = ?, "
                + COL_MEMO + " = ? "
                +  "WHERE " + COL_ID + " = ?";
        db.execSQL(textData);
        */
        if (success == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * saveNew -- データベースに新規登録する。
     * @param db
     * @param args -- 登録するデータを配列で指定。String[]
     *             配列の順番は、テーブルのカラム順であること。
     * @return -- true or false
     */
    public boolean saveNew(SQLiteDatabase db, String[] args) {
        int success = 0;

        ContentValues values = new ContentValues();
        values.put(COL_LIBRARY, args[0]);
        values.put(COL_LOGINID, args[1]);
        values.put(COL_PASSWD, args[2]);
        values.put(COL_MEMO, args[3]);

        db.beginTransaction();
        try {
            db.insert(DB_TABLE, null, values);
            db.setTransactionSuccessful();
            success = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        if (success == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * delData -- データを削除する。
     * @param db
     * @param id -- 削除データのid (long)
     * @return -- true or false
     */
    public boolean deleteId(SQLiteDatabase db, long id) {
        int success = 0;

        String whereClause = COL_ID + " = ?";
        String whereArgs[] = {String.valueOf(id)};

        db.beginTransaction();
        try {
            db.delete(DB_TABLE, whereClause, whereArgs);
            db.setTransactionSuccessful();
            success = 1;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        if (success == 1) {
            return true;
        } else {
            return false;
        }
    }
}