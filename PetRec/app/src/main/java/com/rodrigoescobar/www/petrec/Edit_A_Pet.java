package com.rodrigoescobar.www.petrec;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 4/24/2016 @ 07:31 pm EST.
 * Assignment # : Final
 *
 * Updated on 05/1/2016 @ 10:45 pm EST.
 *
 * This class will display the selected pet information, allow the user to modify any field
 * and save the changes into the database.
 */
public class Edit_A_Pet extends AppCompatActivity implements View.OnClickListener {

    // Variables
    private static final int ABOUT = Menu.FIRST + 1;
    private static ImageButton button_sbm;
    SQLiteDatabase sqLiteDatabase;
    DB_Helper db = new DB_Helper(this);
    DB_Helper myDb = new DB_Helper(this);
    Cursor petsCursor;
    int petID;
    Long id;
    EditText edit_Pet_Name_EditText, edit_Pet_Type_EditText, edit_Pet_Dob_EditText,
            edit_Pet_Gender_EditText, edit_Owner_EditText, edit_Address_EditText, edit_Phone_EditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null) {
            petID = extrasBundle.getInt("rowId", View_A_Pet.DEFAULT_KEYS_SHORTCUT);
        }
        setContentView(R.layout.activity_edit_a_pet);

        // On Click Listener for buttons interaction
        OnClickButtonListenerSave();
        OnClickButtonListenerCancel();

        //Initialize EditText Variables
        edit_Pet_Name_EditText = (EditText) findViewById(R.id.edit_petName_editText);
        edit_Pet_Type_EditText = (EditText) findViewById(R.id.edit_pet_type_editText);
        edit_Pet_Dob_EditText = (EditText) findViewById(R.id.edit_pet_dob_editText);
        edit_Pet_Gender_EditText = (EditText) findViewById(R.id.edit_gender_editText);
        edit_Owner_EditText = (EditText) findViewById(R.id.edit_owner_name_editText);
        edit_Address_EditText = (EditText) findViewById(R.id.edit_address_editText);
        edit_Phone_EditText = (EditText) findViewById(R.id.edit_phone_editText);

        // Method call for viewing from main menu
        bookEditField(petID, petsCursor);
    }

    @Override
    public void onClick(View v) {
    }

    public void bookEditField(int rowId, Cursor cursor) {

        /*
         * Check if method was called without passing any parameter value.
         * If the view a pet info button is pressed from main menu,
         * it will show the information of the database first register pet.
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

            TextView nameTextElement = (TextView) findViewById(R.id.edit_petName_editText);
            nameTextElement.setText(name);

            TextView typeTextElement = (TextView) findViewById(R.id.edit_pet_type_editText);
            typeTextElement.setText(type);

            TextView birthdayTextElement = (TextView) findViewById(R.id.edit_pet_dob_editText);
            birthdayTextElement.setText(birthday);

            TextView genderTextElement = (TextView) findViewById(R.id.edit_gender_editText);
            genderTextElement.setText(gender);

            TextView ownerTextElement = (TextView) findViewById(R.id.edit_owner_name_editText);
            ownerTextElement.setText(owner);

            TextView addressTextElement = (TextView) findViewById(R.id.edit_address_editText);
            addressTextElement.setText(address);

            TextView phoneTextElement = (TextView) findViewById(R.id.edit_phone_editText);
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

            TextView nameTextElement = (TextView) findViewById(R.id.edit_petName_editText);
            nameTextElement.setText(name);

            TextView typeTextElement = (TextView) findViewById(R.id.edit_pet_type_editText);
            typeTextElement.setText(type);

            TextView birthdayTextElement = (TextView) findViewById(R.id.edit_pet_dob_editText);
            birthdayTextElement.setText(birthday);

            TextView genderTextElement = (TextView) findViewById(R.id.edit_gender_editText);
            genderTextElement.setText(gender);

            TextView ownerTextElement = (TextView) findViewById(R.id.edit_owner_name_editText);
            ownerTextElement.setText(owner);

            TextView addressTextElement = (TextView) findViewById(R.id.edit_address_editText);
            addressTextElement.setText(address);

            TextView phoneTextElement = (TextView) findViewById(R.id.edit_phone_editText);
            phoneTextElement.setText(phone);
        }
    } // END of recordInfoField

    // Save a pet Listener
    public void OnClickButtonListenerSave() {
        button_sbm = (ImageButton) findViewById(R.id.edit_save_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Call of the method Validate to check if EditText are empty
                        boolean fieldsOK = validate(new EditText[]{
                                edit_Pet_Name_EditText,
                                edit_Pet_Type_EditText,
                                edit_Pet_Dob_EditText,
                                edit_Pet_Gender_EditText,
                                edit_Owner_EditText,
                                edit_Address_EditText,
                                edit_Phone_EditText,});
                        if (fieldsOK == true) {
                            boolean isUpdated = myDb.updateData(
                                    id,
                                    edit_Pet_Name_EditText.getText().toString(),
                                    edit_Pet_Type_EditText.getText().toString(),
                                    edit_Pet_Dob_EditText.getText().toString(),
                                    edit_Pet_Gender_EditText.getText().toString(),
                                    edit_Owner_EditText.getText().toString(),
                                    edit_Address_EditText.getText().toString(),
                                    edit_Phone_EditText.getText().toString());
                            if (isUpdated == true) {
                                Toast.makeText(Edit_A_Pet.this, "Data Updated!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Edit_A_Pet.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Edit_A_Pet.this, "Data not Updated!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Edit_A_Pet.this, "Empty fields not allowed!", Toast.LENGTH_LONG).show();
                        }
                    }

                }
        );
    }

    // Date Picker
    public void onStart() {
        super.onStart();
        EditText txtDate = (EditText) findViewById(R.id.edit_pet_dob_editText);
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DateDialog dialog = new DateDialog(v);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    dialog.show(ft, "DatePicker");
                }
            }
        });
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

    // Cancel a pet Listener
    public void OnClickButtonListenerCancel() {
        button_sbm = (ImageButton) findViewById(R.id.edit_cancel_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Return to MainActivity Class
                        Intent intent = new Intent(Edit_A_Pet.this, MainActivity.class);
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
