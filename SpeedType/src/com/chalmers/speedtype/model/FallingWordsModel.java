package com.chalmers.speedtype.model;

import java.util.ArrayList;
import java.util.LinkedList;

import com.chalmers.speedtype.R;
import com.chalmers.speedtype.util.Dictionary;

import android.view.KeyEvent;

public class FallingWordsModel extends Model {

	private static final int LAYOUT_ID = R.layout.falling_words_layout;
	private static final int VIEW_ID = R.id.falling_words_view;
	
	private static final int WORD_FREQUENCY = 3000;
	
	private LinkedList<Word> visibleWords = new LinkedList<Word>();
	private long lastWordTimeMillis;
	
	public FallingWordsModel(){
		super();
		visibleWords.addLast(Dictionary.getNextWord());
		
		lastWordTimeMillis = System.currentTimeMillis();
	}
	
	@Override
	public int getLayoutId() {
		return LAYOUT_ID;
	}

	@Override
	public int getViewId() {
		return VIEW_ID;
	}

	@Override
	public void onInput(KeyEvent event) {
		char inputChar = (char) event.getUnicodeChar();
		if (visibleWords.getFirst().charAt(currentCharPos) == inputChar) {
			incScore(1);
			if (isWordComplete()) {
				updateWord();
			} else {
				incCurrentCharPos();
			}
		}
	}

	@Override
	public boolean isRealTime() {
		return true;
	}
	
	@Override
	protected void updateWord(){
		visibleWords.removeFirst();
		currentCharPos = 0;
	}
	
	@Override
	public void update(){
		if(System.currentTimeMillis() - lastWordTimeMillis > WORD_FREQUENCY){
			Word newWord = Dictionary.getNextWord();
			newWord.setX((int)(Math.random()*400));
			newWord.setY(0);
			visibleWords.addLast(newWord);
			
			lastWordTimeMillis = System.currentTimeMillis();
		}

		updateWordPhysics();
		listener.propertyChange(null);
	}

	@Override
	public Word getActiveWord() {
		return visibleWords.getFirst();
	}
	
	@Override
	protected boolean isWordComplete(){
		return currentCharPos == visibleWords.getFirst().length() -1;
	}
	

	private void updateWordPhysics() {
		for(Word w : visibleWords)
			w.setY(w.getY()+1);
	}
	
	public LinkedList<Word> getVisibleWords(){
		return visibleWords;
	}
}
/*
	private Word currentWord;
	private Word nextWord;
	private Word activeWord;

	private ArrayList<TextView> wordViews;
	private LinkedList<TextView> wordQue = new LinkedList<TextView>();
	
	private TextView currentWordView;
	private TextView activeWordView;
	private TextView scoreView;

	private int durationMillis = 30000;

	public FallingWordsModel(Activity activity,
			final Handler handler) {
		super(activity);
		
		currentWord = Dictionary.getNextWord();
		nextWord = Dictionary.getNextWord();

		//setNewWord();
		//startAnimation(currentWordView);
		
		Runnable runnable = new Runnable() {
			public void run() {
				
				//setNewWord();
				//startAnimation(currentWordView);
				
				while (true) {
					try {
						Thread.sleep(durationMillis/5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					handler.post(new Runnable() {
						public void run() {
							setNewWord();
							startAnimation(currentWordView);
						}
					});
				}
			}
		};
		new Thread(runnable).start();
	}

	private TextView getNextWordView() {
		TextView word = wordViews.get(wordNumber);
		wordQue.add(word);
		wordNumber++;
		wordNumber%=5;
		
		return word;
	}

	@Override
	public void onTextChanged(CharSequence s) {
		
		activeWordView = wordQue.getFirst();
		activeWord = new Word(activeWordView.getText().toString());
		
		System.out.println(activeWord.toString());
		System.out.println(activeWord.charAt(currentChar));
		System.out.println(currentChar);
		if (s.length() > 0 && s.charAt(s.length() - 1) == activeWord.charAt(currentChar)) {
			setTextColors(activeWord, activeWordView);
			currentChar++;
			scoreView.setText("" + ++score);
			if (currentChar == activeWord.length()) {
				wordQue.removeFirst();
				currentChar = 0;
				hideWord(activeWordView);
			}
		}
	}

	private void hideWord(TextView word) {
		word.setAlpha(0);
	}

	private void startAnimation(TextView word) {
		int wordPosX = (int) ((getDisplayWidth() - word.getWidth()) * Math.random());
		int wordPosY = -word.getHeight();
		
		ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) word
				.getLayoutParams();

		mlp.setMargins(wordPosX, wordPosY, 0, 0);
		
		Animation fallAnimation = AnimationUtils.loadAnimation(
				getActivity(), R.anim.fall_animation);

		durationMillis *= 0.99;
		fallAnimation.setDuration(durationMillis);

		word.startAnimation(fallAnimation);
	}

	private void setNewWord() {
		currentWord = nextWord;
		currentWordView = getNextWordView();
		currentWordView.setText(currentWord);
		
		int fontSize = (int)(Math.random()*20 + 30);
		currentWordView.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
		
		nextWord = Dictionary.getNextWord();
	}

	private void setTextColors(Word activeWord, TextView activeWordView) {
		System.out.println(activeWord + "!");
		activeWordView.setText(Html.fromHtml("<font color=#00b4ff><b>"
				+ activeWord.substring(0, currentChar + 1) + "</b></font>"));
		activeWordView.append(activeWord.substring(currentChar + 1,
				activeWord.length()));
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setUpViews(ArrayList<TextView> wordViews, TextView scoreView) {
		this.wordViews = wordViews;
		this.scoreView = scoreView;
		
		scoreView.setText("0");
	}
}*/