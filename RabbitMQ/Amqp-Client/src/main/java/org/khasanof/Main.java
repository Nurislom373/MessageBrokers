package org.khasanof;

public class Main {

    private static final DefaultConnectionFactory connectionFactory = new DefaultConnectionFactory();
    private static final DefaultProducer producer = new DefaultProducer(connectionFactory);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            producer.publishQueue();
            Thread.sleep(2000);
        }
    }

}