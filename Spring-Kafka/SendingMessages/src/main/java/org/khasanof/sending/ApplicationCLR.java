package org.khasanof.sending;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Nurislom
 * @see org.khasanof.sending
 * @since 5/18/2024 5:34 AM
 */
@Component
public class ApplicationCLR implements CommandLineRunner {

    private final ProduceReplyingMessages produceReplyingMessages;

    public ApplicationCLR(ProduceReplyingMessages produceReplyingMessages) {
        this.produceReplyingMessages = produceReplyingMessages;
    }

    @Override
    public void run(String... args) {
        produceReplyingMessages.run();
    }
}
