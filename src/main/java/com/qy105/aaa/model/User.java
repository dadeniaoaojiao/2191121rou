package com.qy105.aaa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Getter
@Setter
@ToString
public class User implements Serializable {
    private int userId;
    private String userName;
    private String userPassword;
    private int userStatus;
}
