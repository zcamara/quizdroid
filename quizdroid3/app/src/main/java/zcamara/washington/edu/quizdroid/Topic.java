package zcamara.washington.edu.quizdroid;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by zachcamara on 2/15/15.
 */
public class Topic {
    private String title;
    private String shortDesc;
    private String longDesc;
    private Collection<Quiz> questions;

    public Topic(String title, String shortDesc, String longDesc) {
        this.title = title;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        questions = new ArrayList<Quiz>();
    }

    public void addQuestion(Quiz newQuestion) {
        questions.add(newQuestion);
    }

    public Collection<Quiz> getQuestions() {
        return questions;
    }

    public String getTitle() {
        return title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

}
