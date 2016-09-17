package com.balinasoft.mallione.Ui.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balinasoft.mallione.R;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by Anton on 11.07.2016.
 */
public class DialogFragmentSelectTime extends BaseDialog {
    int year, month, date, maxDay, dayofWeak;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_select_time, null);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        date = calendar.get(Calendar.DATE);
        DateFormat dateFormat = DateFormat.getDateInstance();


        return v;
    }

    String getMonthForInt(int num) {
        String month = "wrong";
        String[] months = dfs.getShortMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

    DateFormatSymbols dfs = new DateFormatSymbols();

    String getWeek(int num) {
        String week = "-";
        String[] weeks = dfs.getShortWeekdays();
        if (num >= 1 && num <= 7) {
            week = weeks[num];
        }
        return week;
    }
}
