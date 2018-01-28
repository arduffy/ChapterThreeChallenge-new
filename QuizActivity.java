package com.bignerdranch.android.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String QUESTIONS_ANSWERED_KEY = "questions answered";
    private static final String QUESTION_INDEX_KEY = "index key";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;


    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private boolean[] mQuestionsAnswered = new boolean[mQuestionBank.length];

    private int mCurrentIndex = 0;
    private int correct;
    private int incorrect;

    //trying to commit the whole file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(QUESTION_INDEX_KEY);
            mQuestionsAnswered = savedInstanceState.getBooleanArray(QUESTIONS_ANSWERED_KEY);
        }
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState){
//        super.onSaveInstanceState(outState);
//        Log.d(TAG, "onSaveInstanceState() called");
//        outState.putInt(QUESTION_INDEX_KEY, mCurrentIndex);
//        outState.putBooleanArray(QUESTIONS_ANSWERED_KEY, mQuestionsAnswered);
//    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {
        mTrueButton.setEnabled(!mQuestionsAnswered[mCurrentIndex]);
        mFalseButton.setEnabled(!mQuestionsAnswered[mCurrentIndex]);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            correct = (correct + 1);
        } else {
            messageResId = R.string.incorrect_toast;
            incorrect = (incorrect + 1);
        }

        mQuestionsAnswered[mCurrentIndex] = true;
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();

        checkPercent();
    }

    private void checkPercent() {
        if (mQuestionBank.length == correct + incorrect) {
            int percent_correct = (correct *100) / mQuestionBank.length;
            Toast.makeText(this, this.getString(R.string.score_toast,percent_correct), Toast.LENGTH_LONG).show();
        }
    }

}
