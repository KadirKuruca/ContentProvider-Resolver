package com.example.kadir.contentprovider_resolver;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;



public class KisiProvider extends ContentProvider {

    //Database Sabitleri
    SQLiteDatabase db;

    private final static String DATABASE_NAME = "kisiler.db";
    private final static int DATABASE_VERSION = 1;
    private final static String KISILER_TABLE_NAME = "kisiler";

    private final static String CREATE_KISILER_TABLE = "CREATE TABLE "+ KISILER_TABLE_NAME
            + " (id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + " name TEXT NOT NULL);";

    //Database Sabitleri

    //ContentProvider

    static final String CONTENT_AUTHORITY = "com.example.kadir.contentprovider_resolver.kisiprovider";
    static final String PATH_KISILER = "kisiler";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    //static final Uri CONTENT_URI = Uri.parse("com.example.kadir.contentprovider_resolver.kisiprovider/kisiler");
    static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_KISILER); // Yukarıdakinin daha düzenli hali

    static final UriMatcher matcher;

    static{ //Bu sınıftan herhangi bir öğeye erişildiğinde çalışacak metod. Burada matcher a ilk değeri atanıyor.
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(CONTENT_AUTHORITY,PATH_KISILER, 1);
    }

    public static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_KISILER_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + KISILER_TABLE_NAME);
            onCreate(db);
        }
    }

    //ContentProvider


    @Override
    public boolean onCreate() {

        DatabaseHelper helper = new DatabaseHelper(getContext());
        db = helper.getWritableDatabase();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        switch (matcher.match(uri)){

            case 1:
                long eklenenSatirID = db.insert(KISILER_TABLE_NAME,null,values);
                if(eklenenSatirID > 0){
                    Uri _uri = ContentUris.withAppendedId(CONTENT_URI,eklenenSatirID);
                    return _uri;
                }
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
