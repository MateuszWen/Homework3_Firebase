package com.mw.homework;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.mw.homework.tasks.TaskFragment;
import com.mw.homework.tasks.TaskListContent;

import java.io.Serializable;

public class ModifyData extends AppCompatActivity {

    private TaskListContent.Book book;

    public ModifyData(){
    }

    public ModifyData(TaskListContent.Book book) {
        this.book = book;
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
    }

    public void onClickButtonAdd(View view) {

        String bookId = getIntent().getStringExtra("EXTRA_BOOK");

        EditText author_name = findViewById(R.id.edittext_author_name);
        EditText book_title = findViewById(R.id.edittext_book_title);
        String author_nameString = author_name.getText().toString();
        String book_titleString = book_title.getText().toString();

        if(!author_nameString.isEmpty()){
            MainActivity.ref.child(bookId).child("authorName").setValue(author_nameString);
            TaskListContent.refreshListAndUpdateFirebase();
        }
        if(!book_titleString.isEmpty()){
            MainActivity.ref.child(bookId).child("bookTitle").setValue(book_titleString);
            TaskListContent.refreshListAndUpdateFirebase();
        }

        //Automatically hide the keyboard after AddButton press
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        finish();
    }

    @Override
    public void finish(){
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent); //By not passing the intent in the result, the calling activity will get null data.
        super.finish();
    }

    public static ModifyData newInstance(TaskListContent.Book book){
        return new ModifyData(book);
    }
    
}
