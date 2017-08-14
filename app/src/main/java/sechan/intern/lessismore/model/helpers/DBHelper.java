package sechan.intern.lessismore.model.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance = null;

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    // 규칙 4. private 생성자

    public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context, "lessismore.db", null, 1);
        }
        return instance;
    }

    // 규칙 1,3 정적팩터리 메서드와 싱글턴 패턴

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table LIMDATA(id integer PRIMARY KEY autoincrement, JSONDATA text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table item;"; // 테이블 드랍
        db.execSQL(sql);
        onCreate(db); // 다시 테이블 생성
    }

    public void insertArticle(String jsonData) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO LIMDATA VALUES(null, '" + jsonData + "');");
        db.close();
    }


    public ArrayList<LimDBArticle> getArticles() {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<LimDBArticle> limDbArticle = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM LIMDATA", null);
        while (cursor.moveToNext()) {
            /*result += "번호 : " + cursor.getString(0)
                    + "\n데이터 : "
                    + cursor.getString(1)+"\n";*/
            limDbArticle.add(0, new LimDBArticle(cursor.getInt(0), cursor.getString(1)));
        }

        return limDbArticle;
    }


    public void removeArticle(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM LIMDATA WHERE ID=" + Integer.toString(id));
        db.close();
    }

}
