package zcamara.washington.edu.quizdroid;

/**
 * Created by zachcamara on 2/15/15.
 */
import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class myRepo implements TopicRepository {

    private ArrayList<Topic> repo;

    private String[] topics = new String[] {
            "Math",
            "Physics",
            "Marvel Super Heroes"
    };
    private String questions[][]={{"What do you get if you add up the numbers 1-100 consecutively?","If a 10 mile taxi ride costs $12 and a 15 mile ride costs $18, then at the same rate how much would a 24 mile ride cost?","Let's say it takes a bunch of workers 90 days to construct a house. Working at the same rate, how many days would be saved if the number of workers was increased by 25%","Lisa has a collection of 80 CDs. If 40 percent of her CDs are rap,and the rest are hip hop, how many hip hop CDs does she have?","What is the smallest positive number that is divisible by 1, 2, 3, 4, 5, and 6?"},
            {"A man has a mass of 60 kg on Earth. What will his mass be on the Moon?","What will happen to a fresh egg in concentrated saline?","Who is known as \"the father of quantum theory\"?","A man weighs 120 N on Earth. What will he weigh on the Moon?","What is the maximum number of solar eclipses that can be seen from the Earth in one year?","Which of the following is a scalar quantity?"},
            {"Peter Parker works as a photographer for:","S.H.I.E.L.D.'s highest ranking agent is:","Dr. Doom went to the same college as:","Edwin Jarvis is the butler to:","Wyatt Wingfoot was a college roommate of","Peter Parker's parents are named:","Tony Stark's father is named:"}
    };
    private int answers[][] = {{1,0,3,2,0},{2,0,1,0,3,0},{0,1,3,2,0,3,0}
    };
    private String opt[][][]={
            { //Topic 0
                    {"4980","5050","5210","4762"},
                    {"$28.80","$26.70","$25.30","$31.20"},
                    {"22 days","20 days","16 days","18 days"},
                    {"32 CDs","64 CDs","48 CDs","56 CDs"},
                    {"60", "120", "30", "180"}
            },
            {
                    {"90kg","20kg", "60kg", "30kg"},
                    {"It will float","It will break","It will sink","It will sink slightly and remain at the center of the water"},
                    {"Otto Hahn", "Max Planck", "Albert Einstein","James Chadwick"},
                    {"20 N","40 N","60 N","75 N"},
                    {"10","7","8","5"},
                    {"Energy","Weight","Acceleration","Displacement"}
            },
            {
                    {"The Daily Bugle","The New York Times", "The Daily Planet","The Rolling Stone"},
                    {"Steven Rogers","Nick Fury","Natalia Romanova","Reed Richards"},
                    {"Tony Stark","Bruce Banner", "Peter Parker","Reed Richards"},
                    {"Charles Xavier","Nick Fury","Tony Stark","Brian Baddock"},
                    {"Johnny Storm","Reed Richards", "James Howlett","Peter Parker"},
                    {"Martha and Johnathon Parker","William and Jessica Parker","Thomas and Margaret Parker","Richard and Mary Parker"},
                    {"Howard Stark","Tyler Stark", "Joseph Stark","Samuel Stark"}
            }
    };
    private String[] descriptions = {"Love math? Were you that kid in high school that solved math puzzles in your free time? Get an 800 on the math portion of the SAT? If you answered yes to any of these questions, you'll love these simple math questions. No calculators allowed!"
            ,"Physics: the branch of science concerned with the nature and properties of matter and energy. The subject matter of physics, distinguished from that of chemistry and biology, includes mechanics, heat, light and other radiation, sound, electricity, magnetism, and the structure of atoms. Test your random physics knowledge here!"
            ,"Marvel counts among its characters such well-known properties as Spider-Man, Wolverine, Iron Man, the Hulk, Thor, Captain America, the Silver Surfer, Daredevil, and Ghost Rider, such teams as the Avengers, the Fantastic Four, the Guardians of the Galaxy, and X-Men. Most of Marvel's fictional characters operate in a single reality known as the Marvel Universe. How well do you know Marvel characters?"};
    private String[] shortDesc = {"No calculators allowed!"
            ,"Test your random physics knowledge here!"
            ,"How well do you know Marvel heroes?"};

    public void buildRepo() {
        //Create Topic and Quiz objects
        repo = new ArrayList<Topic>();
        for (int topicID = 0; topicID < topics.length; topicID++) {
            //Build all question objects for single topic
            Topic newTopic = new Topic();
            newTopic.setTitle(topics[topicID]);
            newTopic.setShortDesc(shortDesc[topicID]);
            newTopic.setLongDesc(descriptions[topicID]);
            for (int questionID = 0; questionID < questions[topicID].length; questionID++) {
                //String question, String[] answers, int correctAns
                Quiz newQuestion = new Quiz();
                newQuestion.setQuestion(questions[topicID][questionID]);
                newQuestion.setAnswers(opt[topicID][questionID]);
                newQuestion.setCorrectAns(answers[topicID][questionID]);
                newTopic.addQuestion(newQuestion);
            }
            repo.add(newTopic);
        }
    }

    public String[] getTopics(){
        return topics;
    }

    public Collection<Topic> getRepo() {
        return repo;
    }

    public Topic getTopic(String topic) {
        List<String> topicList = Arrays.asList(topics);
        int index = topicList.indexOf(topic);
        return repo.get(index);
    }

}
