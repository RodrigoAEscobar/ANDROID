package com.rodrigoescobar.www.petrec;

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
import android.widget.TextView;

/**
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 4/24/2016 @ 07:31 pm EST.
 * Assignment # : Final
 *
 * Updated on 05/1/2016 @ 10:45 pm EST.
 *
 * This class will display the selected pet information from the View_A_Pet class
 * or the pet returned from the Search_For_A_Pet class.
 */
public class View_A_Pet extends AppCompatActivity {

    // Class Variables
    private static final int ABOUT = Menu.FIRST + 1;
    private static Button button_sbm;
    Cursor petsCursor;
    SQLiteDatabase sqLiteDatabase;
    DB_Helper db = new DB_Helper(this);
    int petID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null) {
            petID = (extrasBundle.getInt("rowId", View_A_Pet.DEFAULT_KEYS_SHORTCUT));
        }
        setContentView(R.layout.activity_view_a_pet);
        // Method call for viewing from main menu
        bookInfoField(petID, petsCursor);
        OnClickButtonListenerCancel();

    } // END of onCreate method

    public void bookInfoField(int rowId, Cursor cursor) {

        /*
         * Check if method was called without passing any parameter value.
         * If the view a book button is pressed from main menu,
         * it will show the information of the first book of the database.
         */
        if (rowId == 0) {
            cursor = petsCursor = db.getPetInfo(rowId, sqLiteDatabase);
            petsCursor.moveToFirst();
            String pet_name = cursor.getString(1);
            String pet_type = cursor.getString(2);
            String pet_dob = cursor.getString(3);
            String pet_sex = cursor.getString(4);
            String owner = cursor.getString(5);
            String address = cursor.getString(6);
            String phone = cursor.getString(7);

            TextView nameTextElement = (TextView) findViewById(R.id.delete_pet_name);
            nameTextElement.setText(pet_name);

            TextView typeTextElement = (TextView) findViewById(R.id.delete_pet_type);
            typeTextElement.setText(pet_type);

            TextView birthdayTextElement = (TextView) findViewById(R.id.delete_pet_birthday);
            birthdayTextElement.setText(pet_dob);

            TextView genderTextElement = (TextView) findViewById(R.id.delete_pet_gender);
            genderTextElement.setText(pet_sex);

            TextView ownerTextElement = (TextView) findViewById(R.id.delete_owner_name);
            ownerTextElement.setText(owner);

            TextView addressTextElement = (TextView) findViewById(R.id.delete_owner_address);
            addressTextElement.setText(address);

            TextView phoneTextElement = (TextView) findViewById(R.id.delete_owner_phone);
            phoneTextElement.setText(phone);
        } else {
            cursor = petsCursor = db.getPetInfo(rowId, sqLiteDatabase);
            petsCursor.moveToPosition(rowId);
            String pet_name = cursor.getString(1);
            String pet_type = cursor.getString(2);
            String pet_dob = cursor.getString(3);
            String pet_sex = cursor.getString(4);
            String owner = cursor.getString(5);
            String address = cursor.getString(6);
            String phone = cursor.getString(7);

            TextView nameTextElement = (TextView) findViewById(R.id.delete_pet_name);
            nameTextElement.setText(pet_name);

            TextView typeTextElement = (TextView) findViewById(R.id.delete_pet_type);
            typeTextElement.setText(pet_type);

            TextView birthdayTextElement = (TextView) findViewById(R.id.delete_pet_birthday);
            birthdayTextElement.setText(pet_dob);

            TextView genderTextElement = (TextView) findViewById(R.id.delete_pet_gender);
            genderTextElement.setText(pet_sex);

            TextView ownerTextElement = (TextView) findViewById(R.id.delete_owner_name);
            ownerTextElement.setText(owner);

            TextView addressTextElement = (TextView) findViewById(R.id.delete_owner_address);
            addressTextElement.setText(address);

            TextView phoneTextElement = (TextView) findViewById(R.id.delete_owner_phone);
            phoneTextElement.setText(phone);
        }
    } // END of recordInfoField

    // Cancel a Book Listener
    public void OnClickButtonListenerCancel() {
        button_sbm = (Button) findViewById(R.id.view_cancel_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Return to Home Class
                        Intent intent = new Intent(View_A_Pet.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }
        );
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        petsCursor.close();
        db.close();
    }

    // Options menu to shout about info
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, ABOUT, Menu.NONE, "About")
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




