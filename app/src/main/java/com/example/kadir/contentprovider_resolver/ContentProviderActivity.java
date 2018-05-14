package com.example.kadir.contentprovider_resolver;

import android.content.ContentValues;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContentProviderActivity extends AppCompatActivity {


    static final Uri CONTENT_URI = KisiProvider.CONTENT_URI;

    EditText etSilinecek,etGosterilecek,etYeniKisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        init();

    }

    private void init() {

        etGosterilecek = findViewById(R.id.etGosterilecek);
        etSilinecek = findViewById(R.id.etSilinecek);
        etYeniKisi = findViewById(R.id.etYeniKisi);
    }

    public void tumKisileriGoster(View view) {
    }

    public void kisiSil(View view) {
    }

    public void kisiBul(View view) {
    }

    public void yeniKisiEkle(View view) {

        String eklenecekKisi = etYeniKisi.getText().toString();
        ContentValues values = new ContentValues();
        values.put("name",eklenecekKisi);

        Uri _uri = getContentResolver().insert(CONTENT_URI,values);
        Toast.makeText(this, ""+_uri, Toast.LENGTH_SHORT).show();
    }

}
