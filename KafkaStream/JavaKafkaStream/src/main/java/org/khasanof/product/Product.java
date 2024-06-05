package org.khasanof.product;

import lombok.*;

/**
 * @author Nurislom
 * @see org.khasanof.processor.product
 * @since 5/28/2024 11:26 AM
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String name;
    private Integer price;
}
