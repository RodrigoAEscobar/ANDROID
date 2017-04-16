package com.rodrigoescobar.www.petrec;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 4/24/2016 @ 07:31 pm EST.
 * Assignment # : Final
 * <p/>
 * Updated on 05/1/2016 @ 10:45 pm EST.
 * <p/>
 * This class will display once a pet is registered.
 * This class is the main one and will provide the main interaction with the user.
 * Options menu allow user to add additional pets.
 */

public class MainActivity extends AppCompatActivity {

    private static final int ABOUT = Menu.FIRST + 1;
    private static final int ADD_A_PET = Menu.FIRST + 2;

    // Variables
    private static Button button_sbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // On Click Listener for buttons interaction
        OnClickButtonListenerMyPets();
        OnClickButtonListenerPetRecords();
        OnClickButtonListenerSearch();

    } // END onCreate

    // My Pets Listener
    public void OnClickButtonListenerMyPets() {
        button_sbm = (Button) findViewById(R.id.main_MyPets_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.rodrigoescobar.www.petrec.MyPets");
                        startActivity(intent);
                    }
                }
        );
    } // END OnClickButtonListenerSearch

    // My Pets Listener
    public void OnClickButtonListenerSearch() {
        button_sbm = (Button) findViewById(R.id.main_search_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.rodrigoescobar.www.petrec.Search_For_A_Record");
                        startActivity(intent);
                    }
                }
        );
    } // END OnClickButtonListenerMyPets

    // My Pets Listener
    public void OnClickButtonListenerPetRecords() {
        button_sbm = (Button) findViewById(R.id.main_pet_records_button);
        button_sbm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent("com.rodrigoescobar.www.petrec.Records");
                        startActivity(intent);
                    }
                }
        );
    } // END OnClickButtonListenerMyPets

    // Options menu to shout about info
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, ADD_A_PET, Menu.NONE, "Add A Pet").setAlphabeticShortcut('a');
        menu.add(Menu.NONE, ABOUT, Menu.NONE, "About").setAlphabeticShortcut('?');
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ADD_A_PET:
                add_a_pet();
                return (true);
            case ABOUT:
                about();
                return (true);
        }

        return (super.onOptionsItemSelected(item));
    }

    private void add_a_pet() {
        Intent intent = new Intent("com.rodrigoescobar.www.petrec.RegistrationActivity");
        startActivity(intent);
    }

    private void about() {
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

} // END MainActivity class