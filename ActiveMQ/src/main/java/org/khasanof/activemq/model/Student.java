package org.khasanof.activemq.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student implements Serializable {
    private Integer id;
    private String name;
    private String groupName;
}
