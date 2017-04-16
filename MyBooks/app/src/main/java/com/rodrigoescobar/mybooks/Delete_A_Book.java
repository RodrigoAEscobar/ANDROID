package com.rodrigoescobar.mybooks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Program Name : My Books
 * Created by Rodrigo Escobar on 3/6/2016 @ 07:31 pm EST.
 * Assignment # : MidTerm
 *
 * Updated on 03/28/2016 @ 09:45 pm EST.
 *
 * This class will delete from the database the book selected from View_My_Books class.
 */
public class Delete_A_Book extends AppCompatActivity implements View.OnClickListener {

    // Variables
    private static Button button_sbm;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper db = new DatabaseHelper(this);
    DatabaseHelper myDb = new DatabaseHelper(this);
    Cursor booksCursor;
    int bookID;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null) {
            bookID = extrasBundle.getInt("rowId", View_A_Book.DEFAULT_KEYS_SHORTCUT);
        }
        setContentView(R.layout.activity_delete_a_book);
        // On Click Listener for buttons interaction
        OnClickButtonListenerDelete();
        OnClickButtonListenerCancel();

        // Method call for viewing from main menu
        deleteBook(bookID, booksCursor);
    }

    @Override
    public void onClick(View v) {

    }

    public void deleteBook(int rowId, Cursor cursor) {

        /*
         * Check if method was called without passing any parameter value.
         * If the delete a book button is pressed from main menu,
         * the screen will display the first book of the database
         */
        if (rowId == 0 && cursor == null) {
            cursor = booksCursor = db.getBookInfo(rowId, sqLiteDatabase);
            booksCursor.moveToFirst();
            id = cursor.getLong(0);
            String title = cursor.getString(1);
            String author = cursor.getString(2);
            String description = cursor.getString(3);
            String isbn = cursor.getString(4);
            String price = cursor.getString(5);
            String year = cursor.getString(6);

            TextView titleTextElement = (TextView) findViewById(R.id.delete_Title_TextView);
            titleTextElement.setText(title);

            TextView authorTextElement = (TextView) findViewById(R.id.delete_Author_TextView);
            authorTextElement.setText(author);

            TextView descriptionTextElement = (TextView) findViewById(R.id.delete_Description_TextView);
            descriptionTextElement.setText(description);

            TextView isbnTextElement = (TextView) findViewById(R.id.delete_ISBN_TextView);
            isbnTextElement.setText(isbn);

            TextView priceTextElement = (TextView) findViewById(R.id.delete_Price_TextView);
            priceTextElement.setText(price);

            TextView yearTextElement = (TextView) findViewById(R.id.delete_Year_TextView);
            yearTextElement.setText(year);
        } else {
            cursor = booksCursor = db.getBookInfo(rowId, sqLiteDatabase);
            booksCursor.moveToPosition(rowId);
            id = cursor.getLong(0);
            String title = cursor.getString(1);
            String author = cursor.getString(2);
            String description = cursor.getString(3);
            String isbn = cursor.getString(4);
            String price = cursor.getString(5);
            String year = cursor.getString(6);

            TextView titleTextElement = (TextView) findViewById(R.id.delete_Title_TextView);
            titleTextElement.setText(title);

            TextView authorTextElement = (TextView) findViewById(R.id.delete_Author_TextView);
            authorTextElement.setText(author);

            TextView descriptionTextElement = (TextView) findViewById(R.id.delete_Description_TextView);
            descriptionTextElement.setText(description);

            TextView isbnTextElement = (TextView) findViewById(R.id.delete_ISBN_TextView);
            isbnTextElement.setText(isbn);

            TextView priceTextElement = (TextView) findViewById(R.id.delete_Price_TextView);
            priceTextElement.setText(price);

            TextView yearTextElement = (TextView) findViewById(R.id.delete_Year_TextView);
            yearTextElement.setText(year);
        }
    } // END of bookInfoField


    public void OnClickButtonListenerDelete() {
        button_sbm = (Button) findViewById(R.id.delete_Delete_Button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userConfirmation((long) bookID);
                    }
                }
        );
    }

    // Cancel a Book Listener
    public void OnClickButtonListenerCancel() {
        button_sbm = (Button) findViewById(R.id.delete_Cancel_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Return to Home Class
                        Intent intent = new Intent(Delete_A_Book.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        );
    }

    // Display a dialog to obtain user confirmation before deleting the book
    public void userConfirmation(final Long bookID) {
        if (bookID > 0) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.delete_a_Book)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    boolean isDeleted = myDb.deleteData(bookID + 1);
                                    if (isDeleted == true) {
                                        Toast.makeText(Delete_A_Book.this, "Book Deleted!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Delete_A_Book.this, Home.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Delete_A_Book.this, "Book NOT Deleted!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    // ignore, just dismiss
                                }
                            })
                    .show();
        }

    }
}