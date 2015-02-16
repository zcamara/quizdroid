package zcamara.washington.edu.quizdroid;

/**
 * Created by zachcamara on 2/15/15.
 */
public class Quiz {
    private String question;
    private String[] answers;
    private int correctAns;

    public Quiz(String question, String[] answers, int correctAns) {
        this.question = question;
        this.answers = answers;
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
