package com.chalmers.speedtype;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class GameActivity extends Activity {
	
	private EditText input;
	private TextView wordView;
	private TextView nextWordView;
	
	private Dictionary dictionary;
	private String currentWord;
	private String nextWord;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        
        dictionary = new Dictionary();
        
        setUpViews();
        setUpListeners();
    }

	private void setUpViews() {
		input = (EditText)findViewById(R.id.myEdit);
		wordView = (TextView)findViewById(R.id.word);
		nextWordView = (TextView)findViewById(R.id.next_word);
		
		currentWord = dictionary.getNextWord();
        nextWord = dictionary.getNextWord();
        wordView.setText(currentWord);
        nextWordView.setText(nextWord);
	}
	
	private void setUpListeners() {
		input.addTextChangedListener(new TextWatcher() {
			
			private int currentChar = 0;
			
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if(s.length()>0 && s.charAt(s.length()-1) == currentWord.charAt(currentChar)){
					wordView.setText(Html.fromHtml("<font color=#00ff00>" + currentWord.substring(0, currentChar+1) + "</font>"));
					wordView.append(currentWord.substring(currentChar+1, currentWord.length()));
					currentChar++;
					if(currentChar == currentWord.length()){
						currentWord = nextWord;
						nextWord = dictionary.getNextWord();
						wordView.setText(currentWord);
						nextWordView.setText(nextWord);
						currentChar = 0;
					}
				}
				
			}
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
			public void afterTextChanged(Editable s) { }
		});
	}
}