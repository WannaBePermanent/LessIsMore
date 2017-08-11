package sechan.intern.lessismore.model.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    // private final Post instance;
    private static DBHelper instance = null;

    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


 public static DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context,"lessismore.db",null,1);
        }
        return instance;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table LIMDATA(id integer PRIMARY KEY autoincrement, JSONDATA text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스의 버전이 바뀌었을 때 호출되는 콜백 메서드
        // 버전 바뀌었을 때 기존데이터베이스를 어떻게 변경할 것인지 작성한다
        // 각 버전의 변경 내용들을 버전마다 작성해야함
        String sql = "drop table item;"; // 테이블 드랍
        db.execSQL(sql);
        onCreate(db); // 다시 테이블 생성
    }

    public void insertArticle(String jsonData) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO LIMDATA VALUES(null, '" + jsonData + "');");
        db.close();
    }


    public ArrayList<DBData> getArticles() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<DBData> dbData = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM LIMDATA", null);
        while (cursor.moveToNext()) {
            /*result += "번호 : " + cursor.getString(0)
                    + "\n데이터 : "
                    + cursor.getString(1)+"\n";*/
            dbData.add(0,new DBData(cursor.getInt(0),cursor.getString(1)));
        }

        return dbData;
    }


    public void removeArticle(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM LIMDATA WHERE ID=" + Integer.toString(id));
        db.close();
    }

}
