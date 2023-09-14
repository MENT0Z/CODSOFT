package com.mruraza.kuuizz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class bookmarkactivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;

    private static final String FILE_NAME = "KUIZZ";
    private static final String KEY_NAME = "QUESTIONS";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Gson gson;
    List<modelquestion>bookmarlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarkactivity);

        toolbar = findViewById(R.id.toolbarinbookmark);
        recyclerView = findViewById(R.id.recyclerview_bookmark);


        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
        getBookmarks();



        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Bookmarks");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        bookmarkadaptor bbb = new bookmarkadaptor(bookmarlist);
        recyclerView.setAdapter(bbb);




    }
    @Override
    protected void onPause() {
        super.onPause();
        storebookmarks();
    }

    void storebookmarks(){

        String json = gson.toJson(bookmarlist);
        editor.putString(KEY_NAME,json);
        editor.commit();
    }

    void getBookmarks()
    {
        String json = preferences.getString(KEY_NAME,"");
        Type type = new TypeToken<List<modelquestion>>(){}.getType();
        bookmarlist = gson.fromJson(json,type);
        if(bookmarlist == null)
        {
            bookmarlist = new ArrayList<>();
        }
    }

}