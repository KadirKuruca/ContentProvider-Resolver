package com.example.kadir.contentprovider_resolver;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContentProviderActivity extends AppCompatActivity {


    static final Uri CONTENT_URI = KisiProvider.CONTENT_URI;

    EditText etSilinecek,etGosterilecek,etYeniKisi;
    ListView lvKisiListesi;

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
        lvKisiListesi = findViewById(R.id.lvKisiListesi);
    }

    public void tumKisileriGoster(View view) {

        tumRehberiGosterFonk();
    }

    public void kisiSil(View view) {

        if(!etSilinecek.getText().toString().trim().equals("")){
            int sayi = 0;
            String silinecekID = etSilinecek.getText().toString();
            sayi = getContentResolver().delete(CONTENT_URI,"id = ?",new String[]{silinecekID});
            if(sayi != 0){
                Toast.makeText(this, "Kişi Silindi!", Toast.LENGTH_SHORT).show();
                tumRehberiGosterFonk();
            }
        }
        else
            Toast.makeText(this, "Silme İşlemi İçin Gerekli Alanı Doldurunuz!", Toast.LENGTH_SHORT).show();
    }

    public void kisiBul(View view) {

        if(!etGosterilecek.getText().toString().trim().equals("")) {
            String[] projection = {"id, name"};
            String selection = etGosterilecek.getText().toString();
            Cursor c = getContentResolver().query(CONTENT_URI, projection, "id = ?", new String[]{selection}, null);

            ArrayList<String> kisiListesi = new ArrayList<>();

            if (c.moveToFirst()) {
                do {
                    String id = c.getString(c.getColumnIndex("id"));
                    String name = c.getString(c.getColumnIndex("name"));
                    kisiListesi.add(id + " - " + name);

                } while (c.moveToNext());
            }
            else{
                Toast.makeText(this, "Kullanıcı Bulunamadı.", Toast.LENGTH_SHORT).show();
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, kisiListesi);
            lvKisiListesi.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(this, "Gösterilecek Kişinin ID sini Yazınız!", Toast.LENGTH_SHORT).show();
        }

    }

    public void yeniKisiEkle(View view) {

        if(!etYeniKisi.getText().toString().trim().equals("")) {
            Uri _uri = null;
            String eklenecekKisi = etYeniKisi.getText().toString();
            ContentValues values = new ContentValues();
            values.put("name", eklenecekKisi);
            _uri = getContentResolver().insert(CONTENT_URI,values);
            if(_uri != null) {
                Toast.makeText(this, "Kişi Eklendi." + _uri, Toast.LENGTH_SHORT).show();
                tumRehberiGosterFonk();
            }
        }
        else{
            Toast.makeText(this, "Kişi İsmini Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
        }
    }

    public void tumRehberiGosterFonk(){

        String[] projection = {"id, name"};
        Cursor c = getContentResolver().query(CONTENT_URI,projection,null,null,null);

        ArrayList<String> kisiListesi = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                String id = c.getString(c.getColumnIndex("id"));
                String name = c.getString(c.getColumnIndex("name"));
                kisiListesi.add(id+" - "+name);

            }while(c.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,kisiListesi);
        lvKisiListesi.setAdapter(adapter);
    }

}
