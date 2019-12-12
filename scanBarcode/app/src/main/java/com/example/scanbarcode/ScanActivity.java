package com.example.scanbarcode;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import info.androidhive.barcode.BarcodeReader;

public class ScanActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener{

    BarcodeReader barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        getSupportActionBar().hide();
        // get the barcode reader instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
    }

    @Override
    public void onScanned(Barcode barcode) {

        // playing barcode reader beep sound
        barcodeReader.playBeep();

        Intent intentIP =getIntent();

        Intent intent = new Intent(ScanActivity.this, ResultActivity.class);
        intent.putExtra("ipAddress",intentIP.getStringExtra("ipAddress"));
        intent.putExtra("code", barcode.displayValue);
        startActivity(intent);


    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String s) {
        Toast.makeText(getApplicationContext(), "Error occurred while scanning " + s, Toast.LENGTH_SHORT).show();
    }
}
