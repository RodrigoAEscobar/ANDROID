package com.rodrigoescobar.mybooks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Program Name : My Books
 * Created by Rodrigo Escobar on 3/6/2016 @ 07:31 pm EST.
 * Assignment # : MidTerm
 *
 * Updated on 03/28/2016 @ 09:45 pm EST.
 *
 * This class will display the selected book information from the View_My_Books class
 * or the book returned from the Search_For_A_Book class.
 */
public class View_A_Book extends AppCompatActivity {

    // Class Variables
    private static final int ABOUT = Menu.FIRST + 1;
    Cursor booksCursor;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper db = new DatabaseHelper(this);
    int bookID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null) {
            bookID = (extrasBundle.getInt("rowId", View_A_Book.DEFAULT_KEYS_SHORTCUT));
        }
        setContentView(R.layout.activity_view_a_book);
        // Method call for viewing from main menu
        bookInfoField(bookID, booksCursor);

    } // END of onCreate method

    public void bookInfoField(int rowId, Cursor cursor) {

        /*
         * Check if method was called without passing any parameter value.
         * If the view a book button is pressed from main menu,
         * it will show the information of the first book of the database.
         */
        if (rowId == 0) {
            cursor = booksCursor = db.getBookInfo(rowId, sqLiteDatabase);
            booksCursor.moveToFirst();
            String title = cursor.getString(1);
            String author = cursor.getString(2);
            String description = cursor.getString(3);
            String isbn = cursor.getString(4);
            String price = cursor.getString(5);
            String year = cursor.getString(6);

            TextView titleTextElement = (TextView) findViewById(R.id.view_A_Book_Title_EditText);
            titleTextElement.setText(title);

            TextView authorTextElement = (TextView) findViewById(R.id.view_A_Book_Author_EditText);
            authorTextElement.setText(author);

            TextView descriptionTextElement = (TextView) findViewById(R.id.view_A_Book_Description_EditText);
            descriptionTextElement.setText(description);

            TextView isbnTextElement = (TextView) findViewById(R.id.view_A_Book_ISBN_EditText);
            isbnTextElement.setText(isbn);

            TextView priceTextElement = (TextView) findViewById(R.id.view_A_Book_Price_EditText);
            priceTextElement.setText(price);

            TextView yearTextElement = (TextView) findViewById(R.id.view_A_Book_Year_editText);
            yearTextElement.setText(year);
        } else {
            cursor = booksCursor = db.getBookInfo(rowId, sqLiteDatabase);
            booksCursor.moveToPosition(rowId);
            String title = cursor.getString(1);
            String author = cursor.getString(2);
            String description = cursor.getString(3);
            String isbn = cursor.getString(4);
            String price = cursor.getString(5);
            String year = cursor.getString(6);

            TextView titleTextElement = (TextView) findViewById(R.id.view_A_Book_Title_EditText);
            titleTextElement.setText(title);

            TextView authorTextElement = (TextView) findViewById(R.id.view_A_Book_Author_EditText);
            authorTextElement.setText(author);

            TextView descriptionTextElement = (TextView) findViewById(R.id.view_A_Book_Description_EditText);
            descriptionTextElement.setText(description);

            TextView isbnTextElement = (TextView) findViewById(R.id.view_A_Book_ISBN_EditText);
            isbnTextElement.setText(isbn);

            TextView priceTextElement = (TextView) findViewById(R.id.view_A_Book_Price_EditText);
            priceTextElement.setText("$ " + price);

            TextView yearTextElement = (TextView) findViewById(R.id.view_A_Book_Year_editText);
            yearTextElement.setText(year);
        }
    } // END of bookInfoField


    @Override
    public void onDestroy() {
        super.onDestroy();
        booksCursor.close();
        db.close();
    }

    // Options menu to shout about info
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, ABOUT, Menu.NONE, "About")
                .setIcon(R.drawable.add)
                .setAlphabeticShortcut('a');
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ABOUT:
                add();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    private void add() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View addView = inflater.inflate(R.layout.about, null);
        new AlertDialog.Builder(this)
                .setTitle(R.string.about)
                .setView(addView)
                .setNegativeButton(R.string.close,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                // ignore, just dismiss
                            }
                        })
                .show();
    }
} // END of View_A_Class




