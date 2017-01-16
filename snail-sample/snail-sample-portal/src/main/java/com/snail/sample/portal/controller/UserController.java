package com.snail.sample.portal.controller;

import com.snail.domain.dto.BaseResult;
import com.snail.sample.domain.po.User;
import com.snail.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhaoxiaogang on 2017/1/14.
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public BaseResult<User> info(@PathVariable("id") int id) {
        User user = (User) userService.selectByPk(15);
        return new BaseResult<User>(true, user);
    }

}
