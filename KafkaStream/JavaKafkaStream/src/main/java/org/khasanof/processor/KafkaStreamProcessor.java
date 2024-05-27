package org.khasanof.processor;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;

import java.util.concurrent.CountDownLatch;

import static org.khasanof.config.KafkaStreamConfiguration.getProperties;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 5/27/2024 11:14 AM
 */
public class KafkaStreamProcessor {

    public static void main(String[] args) {
        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Long> stream = builder.stream("word-input-topic-v2", Consumed.with(Serdes.String(), Serdes.Long()));
        stream.foreach((key, value) -> System.out.println("key: " + key + ", value: " + value));

        final Topology topology = builder.build();

        final KafkaStreams streams = new KafkaStreams(topology, getProperties());
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
            streams.close();
        }
        System.exit(0);
    }
}
