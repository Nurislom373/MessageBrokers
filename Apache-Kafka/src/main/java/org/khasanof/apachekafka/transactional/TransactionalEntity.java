package org.khasanof.apachekafka.transactional;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionalEntity {
    private Integer id;
    private String user;
    private BigInteger amount;
}
