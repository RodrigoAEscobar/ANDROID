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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Program Name : My Books
 * Created by Rodrigo Escobar on 3/6/2016 @ 07:31 pm EST.
 * Assignment # : MidTerm
 *
 * Updated on 03/28/2016 @ 09:45 pm EST.
 *
 *
 * This class is to edit any book field and update the database.
 */
public class Edit_A_Book extends AppCompatActivity implements View.OnClickListener {

    // Variables
    private static final int ABOUT = Menu.FIRST + 1;
    private static Button button_sbm;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper db = new DatabaseHelper(this);
    DatabaseHelper myDb = new DatabaseHelper(this);
    Cursor booksCursor;
    int bookID;
    Long id;
    EditText edit_Title_EditText, edit_Author_EditText, edit_Description_EditText,
            edit_Isbn_EditText, edit_Price_EditText, edit_Year_EditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null) {
            bookID = extrasBundle.getInt("rowId", View_A_Book.DEFAULT_KEYS_SHORTCUT);
        }
        setContentView(R.layout.activity_edit_a_book);

        // On Click Listener for buttons interaction
        OnClickButtonListenerSave();
        OnClickButtonListenerCancel();

        //Initialize EditText Variables
        edit_Title_EditText = (EditText) findViewById(R.id.edit_Title_EditText);
        edit_Author_EditText = (EditText) findViewById(R.id.edit_Author_EditText);
        edit_Description_EditText = (EditText) findViewById(R.id.edit_Description_EditText);
        edit_Isbn_EditText = (EditText) findViewById(R.id.edit_ISBN_EditText);
        edit_Price_EditText = (EditText) findViewById(R.id.edit_Price_EditText);
        edit_Year_EditText = (EditText) findViewById(R.id.edit_Year_EditText);

        // Method call for viewing from main menu
        bookEditField(bookID, booksCursor);
    }

    @Override
    public void onClick(View v) {
    }

    public void bookEditField(int rowId, Cursor cursor) {

        /*
         * Check if method was called without passing any parameter value.
         * If the view a book button is pressed from main menu,
         * it will show the information of the database first book.
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

            TextView titleTextElement = (TextView) findViewById(R.id.edit_Title_EditText);
            titleTextElement.setText(title);

            TextView authorTextElement = (TextView) findViewById(R.id.edit_Author_EditText);
            authorTextElement.setText(author);

            TextView descriptionTextElement = (TextView) findViewById(R.id.edit_Description_EditText);
            descriptionTextElement.setText(description);

            TextView isbnTextElement = (TextView) findViewById(R.id.edit_ISBN_EditText);
            isbnTextElement.setText(isbn);

            TextView priceTextElement = (TextView) findViewById(R.id.edit_Price_EditText);
            priceTextElement.setText("$ " + price);

            TextView yearTextElement = (TextView) findViewById(R.id.edit_Year_EditText);
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

            TextView titleTextElement = (TextView) findViewById(R.id.edit_Title_EditText);
            titleTextElement.setText(title);

            TextView authorTextElement = (TextView) findViewById(R.id.edit_Author_EditText);
            authorTextElement.setText(author);

            TextView descriptionTextElement = (TextView) findViewById(R.id.edit_Description_EditText);
            descriptionTextElement.setText(description);

            TextView isbnTextElement = (TextView) findViewById(R.id.edit_ISBN_EditText);
            isbnTextElement.setText(isbn);

            TextView priceTextElement = (TextView) findViewById(R.id.edit_Price_EditText);
            priceTextElement.setText("$ " + price);

            TextView yearTextElement = (TextView) findViewById(R.id.edit_Year_EditText);
            yearTextElement.setText(year);
        }
    } // END of bookInfoField

    // Save a Book Listener
    public void OnClickButtonListenerSave() {
        button_sbm = (Button) findViewById(R.id.save_Edit_Button_Button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Call of the method Validate to check if EditText are empty
                        boolean fieldsOK = validate(new EditText[]{
                                edit_Title_EditText,
                                edit_Author_EditText,
                                edit_Description_EditText,
                                edit_Isbn_EditText,
                                edit_Price_EditText,
                                edit_Year_EditText});
                        if (fieldsOK == true) {
                            boolean isUpdated = myDb.updateData(
                                    id,
                                    edit_Title_EditText.getText().toString(),
                                    edit_Author_EditText.getText().toString(),
                                    edit_Description_EditText.getText().toString(),
                                    edit_Isbn_EditText.getText().toString(),
                                    edit_Price_EditText.getText().toString(),
                                    edit_Year_EditText.getText().toString());
                            if (isUpdated == true) {
                                Toast.makeText(Edit_A_Book.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Edit_A_Book.this, Home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Edit_A_Book.this, "Data not Updated!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Edit_A_Book.this, "Empty fields not allowed!", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }

    // For loop to validate EditText Fields are not empty
    private boolean validate(EditText[] fields) {
        for (int i = 0; i < fields.length; i++) {
            EditText currentField = fields[i];
            if (currentField.getText().toString().length() <= 0) {
                return false;
            }
        }
        return true;
    }

    // Cancel a Book Listener
    public void OnClickButtonListenerCancel() {
        button_sbm = (Button) findViewById(R.id.edit_Cancel_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Return to Home Class
                        Intent intent = new Intent(Edit_A_Book.this, Home.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }
        );
    }

    public void setId(Long id) {
        this.id = id;
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
}
