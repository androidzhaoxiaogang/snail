package com.snail.sample.domain.po;

import com.snail.domain.po.BasePo;

import javax.persistence.Id;

/**
 * Created by zhaoxiaogang on 2017/1/14.
 */

public class User {
    @Id
    private Integer id;

    private String username;

    private String password;

    private String image;

    private String enabled;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
