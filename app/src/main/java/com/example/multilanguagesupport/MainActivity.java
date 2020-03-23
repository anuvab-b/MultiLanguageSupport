package com.example.multilanguagesupport;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        //change Actionbar Title,if you dont change it will be according to your System default language
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        Button changeLang = findViewById(R.id.changeMyLang);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show AlertDialog to display list of languages
                //One can be selected
                showChangeLanguageDialog();
            }
        });
    }

    //Create separate strings.xml for each language
    private void showChangeLanguageDialog() {
        //Language options array
        final String[] listItems = {"French", "हिंदी", "বাংলা", "English"};

        AlertDialog.Builder nBuilder = new AlertDialog.Builder(MainActivity.this);
        nBuilder.setTitle("Choose Language...");
        nBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    //French
                    setLocale("fr");
                    recreate();
                } else if (which == 1) {
                    //Hindi
                    setLocale("hi");
                    recreate();
                } else if (which == 2) {
                    //Bengali
                    setLocale("bn");
                    recreate();
                } else if (which == 3) {
                    //English
                    setLocale("en");
                    recreate();
                }

                //dismiss AlertDialog once language is chosen
                dialog.dismiss();
            }
        });

        AlertDialog mDialog = nBuilder.create();
        //show alertDialog
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics());
        //Save Data to Shared Preferences
        SharedPreferences.Editor editor = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My_Lang",lang);
        editor.apply();
    }

    //Load Language saved in Shared Preferences
    public void loadLocale(){
        SharedPreferences preferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = preferences.getString("My_Lang","");
        setLocale(language);
    }
}
