package org.khasanof.product.processor;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.khasanof.product.JsonSerde;
import org.khasanof.product.Order;
import org.khasanof.product.OrderPrice;
import org.khasanof.product.Product;

import java.util.Objects;

/**
 * @author Nurislom
 * @see org.khasanof.product.processor
 * @since 5/28/2024 11:42 AM
 */
public class ProductOrderProcessor {

    public static void main(String[] args) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KTable<String, Product> productKTable = streamsBuilder.table("stored-product-topic", Consumed.with(Serdes.String(), new JsonSerde<>()));

        KStream<String, Order> ordersStream = streamsBuilder.stream("orders", Consumed.with(Serdes.String(), new JsonSerde<>()));

        ordersStream.foreach((key, order) -> {
            productKTable.toStream()
                    .filter((pKey, product) -> {
                        return Objects.equals(product.getName(), order.getProductName());
                    }).map((sKey, product) -> {
                        return KeyValue.pair(sKey, new OrderPrice(product.getPrice(), order.getStock(), order.getProductName()));
                    })
                    .to("order-price-topic");
        });

    }
}
