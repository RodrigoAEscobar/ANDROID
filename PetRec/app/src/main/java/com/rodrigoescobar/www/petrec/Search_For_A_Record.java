package com.rodrigoescobar.www.petrec;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 4/24/2016 @ 07:31 pm EST.
 * Assignment # : Final
 * <p/>
 * Updated on 05/1/2016 @ 10:45 pm EST.
 * <p/>
 * This class will search the database for records, based in Date, Pet, Visit Reason, Vet. Name and Cost..
 */

public class Search_For_A_Record extends AppCompatActivity implements View.OnClickListener {

    // Variables
    private static Button button_sbm;
    DB_Helper myDb = new DB_Helper(this);
    Cursor cursor;
    int date, pet, reason, vet, cost;
    Long id;
    EditText search_Date_EditText, search_Pet_EditText, search_Reason_EditText, search_Vet_EditText, search_Cost_EditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_a_record);
        // On Click Listener for button interaction
        OnClickButtonListenerSearch();

        //Initialize EditText Variables
        search_Date_EditText = (EditText) findViewById(R.id.search_date_editText);
        search_Pet_EditText = (EditText) findViewById(R.id.search_pet_editText);
        search_Reason_EditText = (EditText) findViewById(R.id.search_reason_editText);
        search_Vet_EditText = (EditText) findViewById(R.id.search_vet_editText);
        search_Cost_EditText = (EditText) findViewById(R.id.search_cost_editText);

    }

    // Search a Record Listener
    public void OnClickButtonListenerSearch() {
        button_sbm = (Button) findViewById(R.id.search_Search_Button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pet = search_Pet_EditText.getText().toString().length();
                        date = search_Date_EditText.getText().toString().length();
                        reason = search_Reason_EditText.getText().toString().length();
                        vet = search_Vet_EditText.getText().toString().length();
                        cost = search_Cost_EditText.getText().toString().length();
                        String searchValue;
                        Long rowId;

                        // Call of the method Validate to check if EditText are empty
                        if (pet != 0) {
                            searchValue = search_Pet_EditText.getText().toString();
                            rowId = myDb.searchByPet(searchValue);
                            int rowNumber = rowId.intValue() - 1;
                            if (rowNumber >= 0) {
                                Intent intentBundle = new Intent(Search_For_A_Record.this, View_A_Record.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("rowId", rowNumber);
                                intentBundle.putExtras(bundle);
                                startActivity(intentBundle);
                            } else {
                                Toast.makeText(Search_For_A_Record.this, "Pet name not found!", Toast.LENGTH_SHORT).show();
                            }

                        } else if (date != 0) {
                            searchValue = search_Date_EditText.getText().toString();
                            rowId = myDb.searchByDate(searchValue);
                            int rowNumber = rowId.intValue() - 1;
                            if (rowNumber >= 0) {
                                Intent intentBundle = new Intent(Search_For_A_Record.this, View_A_Record.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("rowId", rowNumber);
                                intentBundle.putExtras(bundle);
                                startActivity(intentBundle);
                            } else {
                                Toast.makeText(Search_For_A_Record.this, "Date not found!", Toast.LENGTH_SHORT).show();
                            }

                        } else if (reason != 0) {
                            searchValue = search_Reason_EditText.getText().toString();
                            rowId = myDb.searchByReason(searchValue);
                            int rowNumber = rowId.intValue() - 1;
                            if (rowNumber >= 0) {
                                Intent intentBundle = new Intent(Search_For_A_Record.this, View_A_Record.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("rowId", rowNumber);
                                intentBundle.putExtras(bundle);
                                startActivity(intentBundle);
                            } else {
                                Toast.makeText(Search_For_A_Record.this, "Visit Reason not found!", Toast.LENGTH_SHORT).show();
                            }

                        } else if (vet != 0) {
                            searchValue = search_Vet_EditText.getText().toString();
                            rowId = myDb.searchByVet(searchValue);
                            int rowNumber = rowId.intValue() - 1;
                            if (rowNumber >= 0) {
                                Intent intentBundle = new Intent(Search_For_A_Record.this, View_A_Record.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("rowId", rowNumber);
                                intentBundle.putExtras(bundle);
                                startActivity(intentBundle);
                            } else {
                                Toast.makeText(Search_For_A_Record.this, "Vet. name not found!", Toast.LENGTH_SHORT).show();
                            }

                        } else if (cost != 0) {
                            searchValue = search_Cost_EditText.getText().toString();
                            rowId = myDb.searchByCost(searchValue);
                            int rowNumber = rowId.intValue() - 1;
                            if (rowNumber >= 0) {
                                Intent intentBundle = new Intent(Search_For_A_Record.this, View_A_Record.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("rowId", rowNumber);
                                intentBundle.putExtras(bundle);
                                startActivity(intentBundle);
                            } else {
                                Toast.makeText(Search_For_A_Record.this, "Cost not found!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent("com.rodrigoescobar.www.petrec.Records");
                            startActivity(intent);
                        }
                    } // end onClick
                }
        );

    } // END of OnClickButtonListenerSave

    @Override
    public void onClick(View v) {

    }

    // Date Picker
    public void onStart() {
        super.onStart();
        EditText txtDate = (EditText) findViewById(R.id.search_date_editText);
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
} // END of Search_for_A_Record


