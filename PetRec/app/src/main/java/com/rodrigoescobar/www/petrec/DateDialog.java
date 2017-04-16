package com.rodrigoescobar.www.petrec;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Program Name : Petrec
 * Created by Rodrigo Escobar on 4/24/2016 @ 07:31 pm EST.
 * Assignment # : Final
 *
 * Updated on 05/1/2016 @ 10:45 pm EST.
 *
 * This class is created to handle a better formated date picker.
 */


public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {


    EditText txtDate;
    String date;

    public DateDialog(View view) {
        txtDate = (EditText) view;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String date = (day + " / " + (month + 1) + " / " + year);
        txtDate.setText(date);
    }
    


}
