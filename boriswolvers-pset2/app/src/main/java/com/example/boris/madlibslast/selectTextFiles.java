package com.example.boris.madlibslast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class selectTextFiles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_text_files);

        // declaring addListener-function
        addListenerOnButton();
    }

    private void addListenerOnButton() {

        // find the radiogroup and listen when button is clicked
        final RadioGroup radiogroupchoosetext = (RadioGroup)findViewById(R.id.radiogroupTextFiles);

        // setting the onClickListener for button
        Button gotoyourtext = (Button)findViewById(R.id.buttongotofillyourtext);
        gotoyourtext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // the id of radioGroup returns an integer
                int chosen_radioGroup = radiogroupchoosetext.getCheckedRadioButtonId();

                // checks which radiobutton is pressed by user
                RadioButton chosen_radioButton = (RadioButton)findViewById(chosen_radioGroup);

                // simple statement to check whether a radiobutton is checked by user
                if (chosen_radioGroup > 0) {
                    // pass the checked button to fillForm Activity
                    Intent goToFormActivity = new Intent(selectTextFiles.this, fillForm.class);
                    goToFormActivity.putExtra("chosen_radiobutton", chosen_radioButton.getText());
                    startActivity(goToFormActivity);
                    finish();
                }
                else {
                    Toast toast = Toast.makeText(selectTextFiles.this, "Pls pick a text!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
