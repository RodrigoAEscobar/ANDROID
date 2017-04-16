package com.rodrigoescobar.www.petrec;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Add_A_Record extends AppCompatActivity implements View.OnClickListener {

    // Variables
    private static ImageButton button_sbm;
    DB_Helper myDb = new DB_Helper(this);
    String pet_Name;
    EditText visit_reason;
    EditText date;
    EditText diagnostic;
    EditText cost;
    EditText vet_name;
    int Pet_ID;
    Long id;
    private Cursor petsCursor = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        // On Click Listener for buttons interaction
        OnClickButtonListenerSave();
        OnClickButtonListenerCancel();


        //Initialize EditText Variables
        visit_reason = (EditText) findViewById(R.id.delete_record_reason_editText);
        date = (EditText) findViewById(R.id.add_record_date_editText);
        diagnostic = (EditText) findViewById(R.id.add_record_diagnostic_editText);
        cost = (EditText) findViewById(R.id.add_record_cost_editText);
        vet_name = (EditText) findViewById(R.id.delete_record_vet_name_editText);

        Intent intentExtras = getIntent();
        Bundle extrasBundle = intentExtras.getExtras();
        if (extrasBundle != null) {
            Pet_ID = extrasBundle.getInt("rowId", Add_A_Record.DEFAULT_KEYS_SHORTCUT);
            Pet_ID = Pet_ID + 1;
        }
        // Assign PetName field data from Database pet_info_table to the cursor and assign the String value to pet_Name.
        petsCursor = myDb.getReadableDatabase()
                .rawQuery("SELECT _ID as _id, Pet_Name FROM pet_info_table WHERE _ID == " + Pet_ID + " ORDER BY _ID", null);
        petsCursor.moveToFirst();
        pet_Name = petsCursor.getString(1);


    } // END onCreate

    // Save Button Listener
    public void OnClickButtonListenerSave() {
        button_sbm = (ImageButton) findViewById(R.id.add_record_save_imageButton);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Call of the method Validate to check if EditText are empty
                        boolean fieldsOK = validate(new EditText[]{
                                visit_reason,
                                date,
                                diagnostic,
                                cost,
                                vet_name,
                        });

                        if (fieldsOK == true) {

                            boolean isInserted = myDb.insertRecord(
                                    pet_Name,
//                                    String.valueOf(Pet_ID),
                                    visit_reason.getText().toString(),
                                    date.getText().toString(),
                                    diagnostic.getText().toString(),
                                    cost.getText().toString(),
                                    vet_name.getText().toString()
                            );

                            if (isInserted == true) {
                                Toast.makeText(Add_A_Record.this, "Record Inserted!", Toast.LENGTH_SHORT).show();
                                // Reset fields
                                visit_reason.setText("");
                                date.setText("");
                                diagnostic.setText("   /   /   ");
                                cost.setText("");
                                vet_name.setText("");

                                // Return to Home Class
                                Intent intent = new Intent(Add_A_Record.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Add_A_Record.this, "Record not Inserted!", Toast.LENGTH_SHORT).show();
                            } // close if loop
                        } else {
                            Toast.makeText(Add_A_Record.this, "Please enter the missing information", Toast.LENGTH_LONG).show();
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
        button_sbm = (ImageButton) findViewById(R.id.add_record_cancel_imageButton);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Return to Home Class
                        Intent intent = new Intent(Add_A_Record.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
        );
    }

    // Date Picker
    public void onStart() {
        super.onStart();
        EditText txtDate = (EditText) findViewById(R.id.add_record_date_editText);
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