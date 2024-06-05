package org.khasanof.product.consumer;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.khasanof.product.JsonSerde;
import org.khasanof.product.Product;

/**
 * @author Nurislom
 * @see org.khasanof.product.consumer
 * @since 5/28/2024 11:30 AM
 */
public class KProductTable {

    private final KTable<String, Product> productKTable;

    public KProductTable(StreamsBuilder streamsBuilder) {
        this.productKTable = streamsBuilder.table("stored-product-topic", Consumed.with(Serdes.String(), new JsonSerde<>()));
    }

    /**
     *
     * @return
     */
    public KTable<String, Product> getProductKTable() {
        return this.productKTable;
    }
}
