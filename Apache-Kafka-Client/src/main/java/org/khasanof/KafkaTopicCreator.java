package org.khasanof;

import org.apache.kafka.clients.admin.Admin;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Objects;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 4/30/2024 6:16 PM
 */
public abstract class KafkaTopicCreator {

    private static final int PARTITIONS = 1;
    private static final short REPLICATION_FACTOR = 1;

    /**
     * @param admin
     * @param topicName
     */
    public static void createTopic(Admin admin, String topicName) {
        NewTopic newTopic = new NewTopic(topicName, PARTITIONS, REPLICATION_FACTOR);
        CreateTopicsResult topicsResult = admin.createTopics(Collections.singletonList(newTopic));
        printTopicResult(topicName, topicsResult);
    }

    private static void printTopicResult(String topicName, CreateTopicsResult topicsResult) {
        topicsResult.values()
                .get(topicName)
                .whenComplete((res, err) -> {
                    if (Objects.isNull(err)) {
                        System.out.println("Topic successfully created!!!");
                    } else {
                        System.out.println("Topic not successfully created!!!");
                    }
                });
    }
}
