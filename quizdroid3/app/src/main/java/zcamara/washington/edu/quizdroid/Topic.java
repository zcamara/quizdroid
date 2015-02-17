package zcamara.washington.edu.quizdroid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by zachcamara on 2/15/15.
 */
public class Topic implements Serializable{
    private String title;
    private String shortDesc;
    private String longDesc;
    private Collection<Quiz> questions;
    private int icon;

    public Topic() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    public void addQuestion(Quiz newQuestion) {
        if(questions == null)
            questions = new ArrayList<Quiz>();
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

}
