package com.londonappbrewery.quizzler;

public class TrueFalse {

    private int questionID;
    private boolean answer;

    public TrueFalse(int questionResourceID, boolean trueOrFalse){
        questionID = questionResourceID;
        answer = trueOrFalse;
    }

    public int getQuestionID(){
        return questionID;
    }

    public void setQuestionID(int givenQuestionID){
        questionID = givenQuestionID;
    }

    public boolean isAnswer(){
        return answer;
    }

    public void setAnswer(boolean givenAnswer){
        answer = givenAnswer;
    }
}


// Check the code logic for rounding up questions