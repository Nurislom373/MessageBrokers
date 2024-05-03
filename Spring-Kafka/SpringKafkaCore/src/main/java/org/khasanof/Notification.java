package org.khasanof;

import lombok.*;

import java.io.Serializable;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 5/2/2024 9:17 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {

    private String to;
    private String from;
    private String message;

}
