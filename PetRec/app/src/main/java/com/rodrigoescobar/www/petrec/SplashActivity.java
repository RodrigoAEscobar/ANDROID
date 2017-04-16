package com.rodrigoescobar.www.petrec;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 4/24/2016 @ 07:31 pm EST.
 * Assignment # : Final
 *
 * Updated on 05/1/2016 @ 10:45 pm EST.
 *
 * This class will display the Splash Activity for 3 seconds.
 * Calls the DB_Helper class to create an empty database with two tables.
 * Verify if pet_data_table is empty or not, if it is empty will display the registration activity,
 * if not will display the main activity.
 */


public class SplashActivity extends AppCompatActivity {

    DB_Helper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        myDB = new DB_Helper(this);
        boolean check = myDB.isEmpty();

        if (check == true) {
            Thread myThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                        Intent startMainActivity = new Intent(getApplicationContext(), RegistrationActivity.class);
                        startActivity(startMainActivity);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();
        } else {
            Thread myThread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                        Intent startMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(startMainActivity);
                        finish();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            myThread.start();
        }
    }
}
