package com.example.kadir.contentprovider_resolver;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.SortedMap;
import java.util.SortedSet;

public class MainActivity extends AppCompatActivity {

    ListView lvKisiler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvKisiler = findViewById(R.id.lvKisiler);
    }

    public void kisileriGor(View view) {

        ArrayList<String> tumKisiler = new ArrayList<>();

        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection = null;
        String[] args = null;
        String sortOrder = null;

        Cursor c = resolver.query(uri,projection, selection,null,sortOrder);

        if(c != null && c.getCount()>0){
            while(c.moveToNext()) {
                String name = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                tumKisiler.add(name+ " - "+number);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tumKisiler);
            lvKisiler.setAdapter(adapter);
        }
    }

    public void openContentProviderActivity(View view) {

        Intent intent = new Intent(this,ContentProviderActivity.class);
        startActivity(intent);

    }
}
