package org.khasanof.product;

import lombok.*;

/**
 * @author Nurislom
 * @see org.khasanof.processor.product
 * @since 5/28/2024 11:28 AM
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private String productName;
    private Integer stock;
}
