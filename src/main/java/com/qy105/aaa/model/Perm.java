package com.qy105.aaa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class Perm implements Serializable {
    private int perId;
    private String resourcePerm;
}
