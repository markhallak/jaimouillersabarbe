package com.example.jaimouillersabarbe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

enum TypeOfReport {
    PAIDBRIBE(1), BRIBEFIGHTER(2), METHONESTOFFICER(3);

    TypeOfReport(int i) {
    }
}

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
    }
}