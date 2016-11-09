package com.example.boris.madlibslast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class fillForm extends AppCompatActivity {

    // declaring the story object (from Story.java)
    private Story the_story;

    // stream object to read a text file
    private InputStream stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_form);

        // get extras from selectTextFiles activity and render the text file to be filled
        Bundle extras = getIntent().getExtras();
        String chosen_radioButton = extras.getString("chosen_radiobutton");

        // determine which text file is chosen by the user
        if (chosen_radioButton.equals("Simple")) {
            stream = this.getResources().openRawResource(R.raw.madlib0_simple);
        }
        else if (chosen_radioButton.equals("Tarzan")) {
            stream = this.getResources().openRawResource(R.raw.madlib1_tarzan);
        }
        else if (chosen_radioButton.equals("University")) {
            stream = this.getResources().openRawResource(R.raw.madlib2_university);
        }
        else if (chosen_radioButton.equals("Clothes")) {
            stream = this.getResources().openRawResource(R.raw.madlib3_clothes);
        }
        else if (chosen_radioButton.equals("Dance")) {
            stream = this.getResources().openRawResource(R.raw.madlib4_dance);
        }

        // creating new story object
        the_story = new Story(stream);

        // print remaining words to screen for the user
        int remaining_words = the_story.getPlaceholderRemainingCount();
        TextView word_left = (TextView)findViewById(R.id.textViewwordsleft);
        word_left.setText("You have " + Integer.toString(remaining_words) + " word(s) left!");

        // a suggestion for a word for the user to fill in
        String suggestion = the_story.getNextPlaceholder();
        EditText filledWord = (EditText)findViewById(R.id.editText);
        filledWord.setHint(suggestion);
        TextView fill_suggestion = (TextView)findViewById(R.id.textViewfillsuggestion);
        fill_suggestion.setText("Fill in a: " + suggestion + "...");
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        int remaining_words = the_story.getPlaceholderRemainingCount();
        String suggestion = the_story.getNextPlaceholder();

        // saving the current story, and some variables if screen is rotated
        outState.putSerializable("the_story", the_story);
        outState.putInt("remaining_words", remaining_words);
        outState.putString("suggestion", suggestion);
    }

    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        // setting the saved story and variables if screen is rotated
        the_story = (Story)inState.getSerializable("the_story");

        // obtain and set suggestion of EditText
        String suggestion = inState.getString("suggestion");
        EditText filledWord = (EditText)findViewById(R.id.editText);
        filledWord.setHint(suggestion);
        TextView fill_suggestion = (TextView)findViewById(R.id.textViewfillsuggestion);
        fill_suggestion.setText("Fill in a: " + suggestion + "...");

        // obtain and set the remaining words
        int remaining_words = inState.getInt("remaining_words");
        TextView word_left = (TextView)findViewById(R.id.textViewwordsleft);
        word_left.setText("You have " + Integer.toString(remaining_words) + " word(s) left!");
    }


    public void putTheWord(View view) {
        // obtain filled text by user
        EditText filledWord = (EditText)findViewById(R.id.editText);
        String filledWordString = filledWord.getText().toString();

        // check if user filled in something
        if (!(filledWordString.length() == 0)) {

            // fill word in text
            the_story.fillInPlaceholder(filledWordString);

        }
        else {
            Toast toast = Toast.makeText(this, "You did not fill in a word!", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (the_story.isFilledIn())
        {
            Intent goToFilledText = new Intent(this, filledText.class);
            goToFilledText.putExtra("the_story", the_story.toString());
            startActivity(goToFilledText);
            finish();
        }

        int remaining_words = the_story.getPlaceholderRemainingCount();

        // if-statement to prevent Toast messages etc if the user filled in the last word
        if (remaining_words > 0) {

            TextView word_left = (TextView) findViewById(R.id.textViewwordsleft);
            word_left.setText("You have " + Integer.toString(remaining_words) + " word(s) left!");

            // clear the editText bar
            filledWord.setText("");

            // put the new suggestion into editText bar
            String suggestion = the_story.getNextPlaceholder();
            EditText filledWord2 = (EditText) findViewById(R.id.editText);
            filledWord2.setHint(suggestion);
            TextView fill_suggestion = (TextView) findViewById(R.id.textViewfillsuggestion);
            fill_suggestion.setText("Fill in a: " + suggestion + "...");

            Toast toast = Toast.makeText(this, "Continue with filling!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
