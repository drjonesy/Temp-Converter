package com.rjones.tempconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class TempConverterActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    DecimalFormat threeDec = new DecimalFormat("##.###");

    private EditText fahrenheitEditText;
    private TextView celsiusTextView;

    private String fahrenheitString;

    private SharedPreferences savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        fahrenheitEditText = (EditText) findViewById(R.id.fahrenheitEditText);
        celsiusTextView = (TextView) findViewById(R.id.celsiusTextView);

        //set listener (instead of button)
        fahrenheitEditText.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public void onPause(){
        Editor editor = savedValues.edit();
        editor.putString("fahrenheitString", fahrenheitString);
        editor.apply();

        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();

        fahrenheitString = savedValues.getString("fahrenheitString", "");
        celsiusTextView.setText(fahrenheitString);
        calculateAndDisplay();
    }

    private void calculateAndDisplay(){
        //get Fahrenheit
        fahrenheitString = fahrenheitEditText.getText().toString();
        //set fahrenheit value
        float fahrenheit;
        if(fahrenheitString.equals("")){
            fahrenheit = 32;
        }else {
            fahrenheit = Float.parseFloat(fahrenheitString);
        }
        // calculate celsius
        float celsius = (fahrenheit - 32) * 5/9;

        //display celsiusString
        celsiusTextView.setText(threeDec.format(celsius));
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
        calculateAndDisplay();
        //hide soft keyboard
        return false;
    }
}
