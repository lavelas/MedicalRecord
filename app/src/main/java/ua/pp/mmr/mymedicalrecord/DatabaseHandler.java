package ua.pp.mmr.mymedicalrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Uliana on 30.05.2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "md";

    // Опишем таблицы
    private static final String TABLE_DISEASE = "diseases";
    private static final String TABLE_PILLS = "pills";
    private static final String TABLE_PILL2DISEASE = "pills2diseases";
    private static final String TABLE_TEMP = "temps";
    private static final String TABLE_TEMP2DISEASE = "temps2diseases";
    private static final String TABLE_NOTES = "notes";
    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String TABLE_SEQUENCE = "sequence";

    // Опишем поля таблиц
    private static final String DISEASE_ID = "id";
    private static final String DISEASE_NAME = "name";
    private static final String DISEASE_ANNOTATION = "annotation";
    private static final String DISEASE_START = "start";
    private static final String DISEASE_STATUS = "status";
    private static final String PILLS_ID = "id";
    private static final String PILLS_NAME = "name";
    private static final String PILLS_DOSAGE = "dosage";
    private static final String PILLS_TYPE = "type";
    private static final String PILLS_PERIOD = "period";
    private static final String PILL2DISEASE_DISEASE_ID = "disease_id";
    private static final String PILL2DISEASE_PILL_ID = "pill_id";
    private static final String TEMP_ID = "id";
    private static final String TEMP_TEMP = "temp";
    private static final String TEMP_DATE = "date";
    private static final String TEMP2DISEASE_DISEASE_ID = "disease_id";
    private static final String TEMP2DISEASE_TEMP_ID = "temp_id";
    private static final String NOTES_ID = "id";
    private static final String NOTES_NOTE = "note";
    private static final String NOTES_DATE = "date";
    private static final String SEQUENCE_CURRENT = "current";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_SEQUENCE = "CREATE TABLE " + TABLE_SEQUENCE + "("
                + SEQUENCE_CURRENT + " INTEGER PRIMARY KEY)";

        String CREATE_TABLE_DISEASE = "CREATE TABLE " + TABLE_DISEASE + "("
                + DISEASE_ID + " INTEGER PRIMARY KEY," + DISEASE_NAME + " TEXT," + DISEASE_ANNOTATION + " TEXT,"
                + DISEASE_START + " INTEGER," + DISEASE_STATUS + " INTEGER"+ ")";

        String CREATE_TABLE_PILLS = "CREATE TABLE " + TABLE_PILLS + "("
                + PILLS_ID + " INTEGER PRIMARY KEY," + PILLS_NAME + " TEXT,"
                + PILLS_PERIOD + " INTEGER," + PILLS_DOSAGE + " INTEGER," + PILLS_TYPE + " INTEGER"+ ")";

        String CREATE_TABLE_TEMP2DISEASE = "CREATE TABLE " + TABLE_TEMP2DISEASE + "("
                + TEMP2DISEASE_DISEASE_ID + " INTEGER, " + TEMP2DISEASE_TEMP_ID + "INTEGER)";

        String CREATE_TABLE_PILL2DISEASE = "CREATE TABLE " + TABLE_PILL2DISEASE + "("
                + PILL2DISEASE_DISEASE_ID + " INTEGER, " + PILL2DISEASE_PILL_ID + "INTEGER)";

        String CREATE_TABLE_TEMP = "CREATE TABLE " + TABLE_TEMP + "("
                + TEMP_ID + " INTEGER PRIMARY KEY, " + TEMP_TEMP + " REAL, " + TEMP_DATE + "INTEGER)";

        String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES + "("
                + NOTES_ID + " INTEGER PRIMARY KEY, " + NOTES_NOTE + " TEXT, " + NOTES_DATE + "INTEGER)";

        db.execSQL(CREATE_TABLE_SEQUENCE);
        db.execSQL(CREATE_TABLE_DISEASE);
        db.execSQL(CREATE_TABLE_PILLS);
        db.execSQL(CREATE_TABLE_TEMP2DISEASE);
        db.execSQL(CREATE_TABLE_PILL2DISEASE);
        db.execSQL(CREATE_TABLE_TEMP);
        db.execSQL(CREATE_TABLE_NOTES);
        ContentValues values = new ContentValues();
        values.put(SEQUENCE_CURRENT, 0);
        db.insert(TABLE_SEQUENCE, null, values);
        //db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i2) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISEASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PILL2DISEASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PILLS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEQUENCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEMP2DISEASE);
        this.onCreate(db);
    }

    @Override
    public int addDisease(Disease disease) {
        int diseaseId = nextVal();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DISEASE_ID, diseaseId);
        values.put(DISEASE_NAME, disease.getName());
        values.put(DISEASE_ANNOTATION, disease.getAnnotation());
        values.put(DISEASE_START, disease.getStartDate().getTime());
        values.put(DISEASE_STATUS, disease.getStatus());
        db.insert(TABLE_DISEASE, null, values);
        db.close();
        return diseaseId;
    }

    @Override
    public Disease getDisease(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_DISEASE, null);
        if (cursor != null && cursor.moveToFirst()) {
            System.out.println(DatabaseUtils.dumpCursorToString(cursor));
        }
        return null;
    }

    @Override
    public ArrayList<Disease> getAllDiseases(int status) {
        ArrayList<Disease> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_DISEASE + " WHERE " + DISEASE_STATUS + " = " + status, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Disease disease = new Disease(cursor.getString(cursor.getColumnIndex(DISEASE_NAME)), cursor.getString(cursor.getColumnIndex(DISEASE_ANNOTATION)));
                disease.setStartDate(new java.util.Date((long)cursor.getInt(cursor.getColumnIndex(DISEASE_START))*1000));
                disease.setId(cursor.getInt(cursor.getColumnIndex(DISEASE_ID)));
                disease.setStatus(status);
                result.add(disease);
            } while (cursor.moveToNext());
        }
        return result;
    }

    private int nextVal() {
        int nextVal = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT  * FROM " + TABLE_SEQUENCE, null);
        if (cursor != null && cursor.moveToFirst()){
            nextVal = cursor.getInt(0) + 1;
        } else {
            throw new RuntimeException("Ошибка получения автоинкремента");
        }

        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_SEQUENCE);
        ContentValues values = new ContentValues();
        values.put(SEQUENCE_CURRENT, nextVal);
        db.insert(TABLE_SEQUENCE, null, values);
        db.close();
        return nextVal;
    }
}
