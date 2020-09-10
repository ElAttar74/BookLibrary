package com.organization.booklibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcView;
    FloatingActionButton float_btn;
    MyDatabaseHelper myDB;
    ArrayList<String> book_id,book_title,book_author,book_pages;
    CustomAdapter customAdapter;

    ImageView imgEmpty;
    TextView txtEmpty;

    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcView = findViewById(R.id.recyclerView);
        float_btn = (FloatingActionButton) findViewById(R.id.float_btn);
        imgEmpty = (ImageView) findViewById(R.id.imgEmpty);
        txtEmpty = (TextView) findViewById(R.id.txtEmpty);

        float_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });

        myDB = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        storeDataIntoArrays();

        customAdapter = new CustomAdapter(MainActivity.this,MainActivity.this,
                book_id, book_title, book_author, book_pages);
        rcView.setAdapter(customAdapter);
        rcView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            recreate();
        }else{
            Toast.makeText(this, "error ", Toast.LENGTH_SHORT).show();
        }
    }

    void storeDataIntoArrays(){
        Cursor c = myDB.readAllData();
        if (c.getCount() == 0){
            imgEmpty.setVisibility(View.VISIBLE);
            txtEmpty.setVisibility(View.VISIBLE);
        }else {
            while (c.moveToNext()){
                book_id.add(c.getString(0));
                book_title.add(c.getString(1));
                book_author.add(c.getString(2));
                book_pages.add(c.getString(3));
            }
            imgEmpty.setVisibility(View.GONE);
            txtEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_all,menu );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all_books){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All ? ");
        builder.setMessage("Are you sure you want to delete all books ? ");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB.deleteAllData();
                recreate();
            }
        });
        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();

    }


}