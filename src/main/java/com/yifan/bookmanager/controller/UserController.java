package com.yifan.bookmanager.controller;


import com.yifan.bookmanager.common.BaseResponse;
import com.yifan.bookmanager.common.ResultUtils;
import com.yifan.bookmanager.model.entity.User;
import com.yifan.bookmanager.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {


    @Resource
    UserServiceImpl userService;

    @PostMapping("/borrowBook")
    public BaseResponse<Integer> borrowBook(@RequestBody HashMap<String,String> borrowMap){
        Integer res = userService.borrowBook(borrowMap.get("userId"), borrowMap.get("bookName"));
        return ResultUtils.success(res);
    }

    @PostMapping("/returnBook")
    public BaseResponse<Integer> returnBook(@RequestBody HashMap<String,String> borrowMap){
        Integer res = userService.returnBook(borrowMap.get("userId"), borrowMap.get("bookName"));
        return ResultUtils.success(res);
    }

    @GetMapping("/getUserInfo")
    public BaseResponse<User> getUserInfo(String userId){
        System.out.println(userId);
        User userInfo = userService.getUserInfo(userId);
        return ResultUtils.success(userInfo);
    }

    @PostMapping("/login")
    public BaseResponse<Long> userLogin(@RequestBody HashMap<String,String> userAccount){
        long loginRes = userService.login(userAccount.get("userAccount"), userAccount.get("password"));
        System.out.println(loginRes);
        return ResultUtils.success(loginRes);
    }

//    @PostMapping("/register")
//    public BaseResponse<Long> userRegister(String ){
//
//    }


}
