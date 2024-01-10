package org.khasanof;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 1/11/2024 12:53 AM
 */
public class ConsumeMain {

    private static final DefaultConnectionFactory connectionFactory = new DefaultConnectionFactory();
    private static final DefaultConsumer consumer = new DefaultConsumer(connectionFactory);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            consumer.consumeQueue();
            Thread.sleep(1000);
        }
    }

}
