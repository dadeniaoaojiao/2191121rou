package com.qy105.aaa.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString
public class Book implements Serializable {
    private int BookId;
    private String BookName;
    private int BookStore;
    private Double BookPrice;
}
