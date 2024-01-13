package org.khasanof;

import com.rabbitmq.client.CancelCallback;

import java.io.IOException;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 1/13/2024 4:28 PM
 */
public class DefaultCancelCallback implements CancelCallback {

    @Override
    public void handle(String consumerTag) throws IOException {
        // write some logic!
    }
}
