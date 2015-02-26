package zcamara.washington.edu.quizdroid;

import java.io.Serializable;

/**
 * Created by zachcamara on 2/15/15.
 */
public class Quiz implements Serializable{
    private String question;
    private String[] answers;
    private int correctAns;

    public Quiz() {
    }

    public void setQuestion (String question) {
        this.question = question;
    }

    public void setAnswers (String[] answers) {
        this.answers = answers;
    }

    public void setCorrectAns (int correctAns) {
        this.correctAns = correctAns;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String getAnswer() {
        return answers[correctAns];
    }

}
