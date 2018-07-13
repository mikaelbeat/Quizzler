package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // Sounds
    MediaPlayer backgroundMusic;
    MediaPlayer correctAnswer;
    MediaPlayer wrongAnswer;
    MediaPlayer buttonSound;

    // TODO: Declare member variables here:
    Button trueButton;
    Button falseButton;
    TextView questionTextView;
    TextView scoreTextView;
    ProgressBar progressBar;

    int startIndex;
    int mQuestion;
    int userScore;

    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
            new TrueFalse(R.string.question_1, true),
            new TrueFalse(R.string.question_2, true),
            new TrueFalse(R.string.question_3, true),
            new TrueFalse(R.string.question_4, true),
            new TrueFalse(R.string.question_5, true),
            new TrueFalse(R.string.question_6, false),
            new TrueFalse(R.string.question_7, true),
            new TrueFalse(R.string.question_8, false),
            new TrueFalse(R.string.question_9, true),
            new TrueFalse(R.string.question_10, true),
            new TrueFalse(R.string.question_11, false),
            new TrueFalse(R.string.question_12, false),
            new TrueFalse(R.string.question_13,true)
    };

    // TODO: Declare constants here
    final int PROGRESS_BAR_INCREMENT =  (int) Math.ceil(100.0 / mQuestionBank.length);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backgroundMusic = MediaPlayer.create(getApplicationContext(), R.raw.fearless);
        correctAnswer = MediaPlayer.create(getApplicationContext(), R.raw.correct);
        wrongAnswer = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
        buttonSound = MediaPlayer.create(getApplicationContext(), R.raw.button);

        if (savedInstanceState != null){
            userScore = savedInstanceState.getInt("ScoreKey");
            startIndex = savedInstanceState.getInt("Question");
        } else{
            userScore = 0;
            startIndex = 0;
        }

        trueButton = (Button) findViewById(R.id.true_button);
        falseButton = (Button) findViewById(R.id.false_button);
        questionTextView = (TextView) findViewById(R.id.question_text_view);
        scoreTextView = (TextView) findViewById(R.id.score);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);


        mQuestion = mQuestionBank[startIndex].getQuestionID();
        questionTextView.setText(mQuestion);
        scoreTextView.setText("Score " + userScore + "/" + mQuestionBank.length);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playButtonSound();
                Log.d("Quizzler", "True pressed!");
                checkAnswer(true);
                updateQuestion();
                Toast trueToast = Toast.makeText(getApplicationContext(), "True pressed!", Toast.LENGTH_SHORT);
                trueToast.show();
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playButtonSound();
                Log.d("Quizzler", "False pressed!");
                checkAnswer(false);
                updateQuestion();
                Toast falseToast = Toast.makeText(getApplicationContext(), "False pressed!", Toast.LENGTH_SHORT);
                falseToast.show();
            }
        });

    }
    
    @Override
    protected void onPause(){
        super.onPause();
        backgroundMusic.pause();
    }
    
    @Override
    protected void onResume(){
        super.onResume();
        backgroundMusic.start();
    }
    
    public void playCorrectAnswerSound(){
        correctAnswer.start();
    }
    
    public void playWrongAnswerSound(){
        wrongAnswer.start();
    }
    
    public void playButtonSound(){
        buttonSound.start();
    }

    private void updateQuestion(){
        startIndex = (startIndex + 1) % mQuestionBank.length;

        if(startIndex == 0){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + userScore + " points!");
            alert.setPositiveButton("Close application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();
        }

        mQuestion = mQuestionBank[startIndex].getQuestionID();
        questionTextView.setText(mQuestion);
        progressBar.incrementProgressBy(PROGRESS_BAR_INCREMENT);
        scoreTextView.setText("Score: " + userScore + "/" +mQuestionBank.length);
    }

    private void checkAnswer(boolean userSelection){
        boolean correctAnswer = mQuestionBank[startIndex].isAnswer();

        if(userSelection == correctAnswer){
            playCorrectAnswerSound();
            Toast.makeText(getApplicationContext(),R.string.correct_toast, Toast.LENGTH_SHORT).show();
            userScore += 1;
        }else{
            playWrongAnswerSound();
            Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", userScore);
        outState.putInt("Question", startIndex);
    }

}