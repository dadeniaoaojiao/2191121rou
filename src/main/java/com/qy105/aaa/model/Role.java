package com.qy105.aaa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class Role implements Serializable {
    private int roleId;
    private String roleName;
}
