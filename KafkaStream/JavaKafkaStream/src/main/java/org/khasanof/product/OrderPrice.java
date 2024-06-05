package org.khasanof.product;

import lombok.*;

/**
 * @author Nurislom
 * @see org.khasanof.product
 * @since 5/28/2024 11:51 AM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderPrice {

    private Integer price;
    private Integer stock;
    private String productName;
}
