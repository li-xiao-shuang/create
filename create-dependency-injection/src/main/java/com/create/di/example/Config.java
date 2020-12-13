package com.create.di.example;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Config {

    private String ip;
    private String userName;
    private String password;

    public Config(String ip, String userName, String password) {
        this.ip = ip;
        this.userName = userName;
        this.password = password;
    }
}
