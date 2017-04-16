package com.rodrigoescobar.www.petrec;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 4/24/2016 @ 07:31 pm EST.
 * Assignment # : Final
 * <p/>
 * Updated on 05/1/2016 @ 10:45 pm EST.
 * <p/>
 * This class will display the pet registration form if the pet_data_table is empty or
 * when called from the options menu in the main activity.
 */

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    // Variables
    private static ImageButton button_sbm;
    DB_Helper myDb = new DB_Helper(this);
    EditText pet_name_EditText, pet_type__EditText, pet_dob__EditText,
            owner_name_EditText, owner_address_EditText, owner_phone_EditText;
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // On Click Listener for buttons interaction
        OnClickButtonListenerSave();
        OnClickButtonListenerCancel();

        //Initialize EditText Variables
        pet_name_EditText = (EditText) findViewById(R.id.edit_petName_editText);
        pet_type__EditText = (EditText) findViewById(R.id.edit_pet_type_editText);
        pet_dob__EditText = (EditText) findViewById(R.id.edit_pet_dob_editText);
        owner_name_EditText = (EditText) findViewById(R.id.edit_owner_name_editText);
        owner_address_EditText = (EditText) findViewById(R.id.edit_address_editText);
        owner_phone_EditText = (EditText) findViewById(R.id.edit_phone_editText);

    } // END onCreate

    // Gender Selector
    public void RadioButtonClicked(View view) {
        // Check that the button is  now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonFemale:
                if (checked)
                    gender = "Female";
                break;
            case R.id.radioButtonMale:
                if (checked)
                    gender = "Male";
                break;
        }
    }


    // Save Button Listener
    public void OnClickButtonListenerSave() {
        button_sbm = (ImageButton) findViewById(R.id.edit_save_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Call of the method Validate to check if EditText fields are empty
                        boolean fieldsOK = validate(new EditText[]{
                                pet_name_EditText,
                                pet_type__EditText,
                                pet_dob__EditText,
                                owner_name_EditText,
                                owner_address_EditText,
                                owner_phone_EditText
                        });

                        if (fieldsOK == true) {

                            boolean isInserted = myDb.insertData(
                                    pet_name_EditText.getText().toString(),
                                    pet_type__EditText.getText().toString(),
                                    pet_dob__EditText.getText().toString(),
                                    gender,
                                    owner_name_EditText.getText().toString(),
                                    owner_address_EditText.getText().toString(),
                                    owner_phone_EditText.getText().toString()
                            );

                            if (isInserted == true) {
                                Toast.makeText(RegistrationActivity.this, "Pet Data Inserted!", Toast.LENGTH_SHORT).show();
                                // Reset fields
                                pet_name_EditText.setText("");
                                pet_type__EditText.setText("");
                                pet_dob__EditText.setText("   /   /   ");
                                owner_name_EditText.setText("");
                                owner_address_EditText.setText("");
                                owner_phone_EditText.setText("");

                                // Return to MainActivity Class
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistrationActivity.this, "Data not Inserted!", Toast.LENGTH_SHORT).show();
                            } // close if loop
                        } else {
                            Toast.makeText(RegistrationActivity.this, "Please enter the missing information", Toast.LENGTH_LONG).show();
                        }
                    } // end onClick
                }
        );
    } // END of OnClickButtonListenerSave


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

    // Cancel Button Listener
    public void OnClickButtonListenerCancel() {
        button_sbm = (ImageButton) findViewById(R.id.registration_cancel_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Return to MainActivity Class
                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
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


    @Override
    public void onClick(View v) {

    }
} // END class