package com.rodrigoescobar.www.petrec;

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
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 3/6/2016 @ 07:31 pm EST.
 * Assignment # : MidTerm
 * <p/>
 * Updated on 03/28/2016 @ 09:45 pm EST.
 * <p/>
 * This class will delete from the database the book selected from View_My_Books class.
 */
public class Delete_A_Pet extends AppCompatActivity implements View.OnClickListener {

    // Variables
    private static Button button_sbm;
    SQLiteDatabase sqLiteDatabase;
    DB_Helper db = new DB_Helper(this);
    DB_Helper myDb = new DB_Helper(this);
    Cursor petsCursor;
    int petID;
    Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null) {
            petID = extrasBundle.getInt("rowId", View_A_Pet.DEFAULT_KEYS_SHORTCUT);
        }
        setContentView(R.layout.activity_delete_a_pet);
        // On Click Listener for buttons interaction
        OnClickButtonListenerDelete();
        OnClickButtonListenerCancel();

        // Method call for viewing from main menu
        deleteBook(petID, petsCursor);
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
            cursor = petsCursor = db.getPetInfo(rowId, sqLiteDatabase);
            petsCursor.moveToFirst();
            id = cursor.getLong(0);
            String name = cursor.getString(1);
            String type = cursor.getString(2);
            String birthday = cursor.getString(3);
            String gender = cursor.getString(4);
            String owner = cursor.getString(5);
            String address = cursor.getString(6);
            String phone = cursor.getString(7);

            TextView nameTextElement = (TextView) findViewById(R.id.delete_pet_name);
            nameTextElement.setText(name);

            TextView typeTextElement = (TextView) findViewById(R.id.delete_pet_type);
            typeTextElement.setText(type);

            TextView birthdayTextElement = (TextView) findViewById(R.id.delete_pet_birthday);
            birthdayTextElement.setText(birthday);

            TextView genderTextElement = (TextView) findViewById(R.id.delete_pet_gender);
            genderTextElement.setText(gender);

            TextView ownerTextElement = (TextView) findViewById(R.id.delete_owner_name);
            ownerTextElement.setText(owner);

            TextView addressTextElement = (TextView) findViewById(R.id.delete_owner_address);
            addressTextElement.setText(address);

            TextView phoneTextElement = (TextView) findViewById(R.id.delete_owner_phone);
            phoneTextElement.setText(phone);
        } else {
            cursor = petsCursor = db.getPetInfo(rowId, sqLiteDatabase);
            petsCursor.moveToPosition(rowId);
            id = cursor.getLong(0);
            String name = cursor.getString(1);
            String type = cursor.getString(2);
            String birthday = cursor.getString(3);
            String gender = cursor.getString(4);
            String owner = cursor.getString(5);
            String address = cursor.getString(6);
            String phone = cursor.getString(7);

            TextView nameTextElement = (TextView) findViewById(R.id.delete_pet_name);
            nameTextElement.setText(name);

            TextView typeTextElement = (TextView) findViewById(R.id.delete_pet_type);
            typeTextElement.setText(type);

            TextView birthdayTextElement = (TextView) findViewById(R.id.delete_pet_birthday);
            birthdayTextElement.setText(birthday);

            TextView genderTextElement = (TextView) findViewById(R.id.delete_pet_gender);
            genderTextElement.setText(gender);

            TextView ownerTextElement = (TextView) findViewById(R.id.delete_owner_name);
            ownerTextElement.setText(owner);

            TextView addressTextElement = (TextView) findViewById(R.id.delete_owner_address);
            addressTextElement.setText(address);

            TextView phoneTextElement = (TextView) findViewById(R.id.delete_owner_phone);
            phoneTextElement.setText(phone);
        }
    } // END of recordInfoField


    public void OnClickButtonListenerDelete() {
        button_sbm = (Button) findViewById(R.id.view_cancel_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        userConfirmation((long) petID);
                    }
                }
        );
    }

    // Cancel a Book Listener
    public void OnClickButtonListenerCancel() {
        button_sbm = (Button) findViewById(R.id.delete_cancel_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Return to Home Class
                        Intent intent = new Intent(Delete_A_Pet.this, MainActivity.class);
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
                    .setTitle(R.string.delete_a_pet)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    boolean isDeleted = myDb.deleteData(bookID + 1);
                                    if (isDeleted == true) {
                                        Toast.makeText(Delete_A_Pet.this, "Pet Information Deleted!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(Delete_A_Pet.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(Delete_A_Pet.this, "Book NOT Deleted!", Toast.LENGTH_SHORT).show();
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