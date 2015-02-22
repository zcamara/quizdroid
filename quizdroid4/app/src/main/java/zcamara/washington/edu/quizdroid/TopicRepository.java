package zcamara.washington.edu.quizdroid;

import java.util.Collection;

/**
 * Created by zachcamara on 2/15/15.
 */
public interface TopicRepository {

    void buildRepo();

    String[] getTopics();

    Collection<Topic> getRepo();

    Topic getTopic(String topic);

}
