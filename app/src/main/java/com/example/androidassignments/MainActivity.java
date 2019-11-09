package com.example.androidassignments;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected static final String ACTIVITY_NAME = "MainActivity";
    String [] cities = {"Abbotsford",
            "Airdrie",
            "Ajax",
            "Aurora",
            "Barrie",
            "Belleville",
            "Blainville",
            "Brampton",
            "Brantford",
            "Brossard",
            "Burlington",
            "Burnaby",
            "Caledon",
            "Calgary",
            "Cambridge",
            "Cape Breton",
            "Chatham-Kent",
            "Chilliwack",
            "Clarington",
            "Coquitlam",
            "Delta",
            "Drummondville",
            "Edmonton",
            "Fredericton",
            "Gatineau",
            "Granby",
            "Grande Prairie",
            "Greater Sudbury",
            "Guelph",
            "Halifax",
            "Halton Hills",
            "Hamilton",
            "Kamloops",
            "Kawartha Lakes",
            "Kelowna",
            "Kingston",
            "Kitchener",
            "Langley",
            "Laval",
            "Lethbridge",
            "London",
            "Longueuil",
            "Maple Ridge",
            "Markham",
            "Medicine Hat",
            "Milton",
            "Mirabel",
            "Mississauga",
            "Moncton",
            "Montreal",
            "Nanaimo",
            "New Westminster",
            "Newmarket",
            "Niagara Falls",
            "Norfolk County",
            "North Bay",
            "North Vancouver",
            "North Vancouver",
            "Oakville",
            "Oshawa",
            "Ottawa",
            "Peterborough",
            "Pickering",
            "Port Coquitlam",
            "Prince George",
            "Quebec City",
            "Red Deer",
            "Regina",
            "Repentigny",
            "Richmond",
            "Richmond Hill",
            "Saanich",
            "Saguenay",
            "Saint John",
            "Saint-Hyacinthe",
            "Saint-Jerome",
            "Saint-Jean-sur-Richelieu",
            "Sarnia",
            "Saskatoon",
            "Sault Ste. Marie",
            "Sherbrooke",
            "St. Albert",
            "St. Catharines",
            "Saint John",
            "St. John",
            "Saint Albert",
            "Saint Catharines",
            "Strathcona County",
            "Surrey",
            "Terrebonne",
            "Thunder Bay",
            "Toronto",
            "Trois-Rivieres",
            "Vancouver",
            "Vaughan",
            "Victoria",
            "Waterloo",
            "Welland",
            "Whitby",
            "Windsor",
            "Winnipeg",
            "Wood Buffalo"};
    String selectedCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(ACTIVITY_NAME, "In onCreate ");
        Button button = (Button) findViewById(R.id.button);
        Button startChat = (Button) findViewById(R.id.button3);
        Button startToolbar = findViewById(R.id.button5);
        Button weatherButton = findViewById(R.id.button6);
        Spinner spinner = findViewById(R.id.spinner);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 10);
            }
        });
        startChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");
                Intent intent = new Intent(MainActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });
        startToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TestToolbar.class);
                startActivity(intent);
            }
        });
        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url ="http://api.openweathermap.org/data/2.5/weather?q="+selectedCity+",ca&APPID=79cecf493cb6e52d25bb7b7050ff723c&mode=xml&units=metric";
                Intent intent = new Intent(MainActivity.this,WeatherForecast.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cities);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        selectedCity = parent.getItemAtPosition(pos).toString();

    }


    public void onNothingSelected(AdapterView<?> parent) {
        selectedCity = parent.getItemAtPosition(0).toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            Log.i(ACTIVITY_NAME, "Returned to MainActivity.onActivityResult ");
        }
        if(resultCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("Response");
            CharSequence text = (CharSequence)("ListItemsActivity passed:" + messagePassed) ;
            Toast toast = Toast.makeText(getApplicationContext(),text, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }
}
