package com.organization.booklibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    EditText title_input2, author_input2, pages_input2;
    Button btnUpdate, btnDelete;
    MyDatabaseHelper myDB ;
    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        myDB = new MyDatabaseHelper(UpdateActivity.this);

        title_input2 = (EditText) findViewById(R.id.title_input2);
        author_input2 = (EditText) findViewById(R.id.author_input2);
        pages_input2 = (EditText) findViewById(R.id.pages_input2);

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if ( ab != null){
            ab.setTitle(title);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = title_input2.getText().toString().trim();
                author = author_input2.getText().toString().trim();
                pages = pages_input2.getText().toString().trim();
                myDB.updateData(id, title, author, pages);
                finish();
            }
        });
         btnDelete.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 confirmDialog();

             }
         });





    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
                getIntent().hasExtra("author") && getIntent().hasExtra("pages")){

            //Getting data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

            //Setting intent data
            title_input2.setText(title);
            author_input2.setText(author);
            pages_input2.setText(pages);


        }else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + " ? ");
        builder.setMessage("Are you sure you want to delete " + title +" ? ");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }


}