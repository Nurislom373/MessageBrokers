package org.khasanof.product;

import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * @author Nurislom
 * @see org.khasanof.product
 * @since 5/28/2024 11:33 AM
 */
public class JsonSerde<T> extends Serdes.WrapperSerde<T> {

    /**
     *
     */
    public JsonSerde() {
        super(new JsonSerializer<>(), new JsonDeserializer<>());
    }
}
