package com.yifan.bookmanager.controller;


import com.yifan.bookmanager.common.BaseResponse;
import com.yifan.bookmanager.common.ResultUtils;
import com.yifan.bookmanager.model.entity.User;
import com.yifan.bookmanager.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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


    @PostMapping("/adminLogin")
    public BaseResponse<Long> adminLogin(@RequestBody HashMap<String,String> userAccount){
        long loginRes = userService.adminLogin(userAccount.get("userAccount"), userAccount.get("password"));
        System.out.println(loginRes);
        return ResultUtils.success(loginRes);
    }

    @GetMapping("/getAllUser")
    public BaseResponse<List<User>> getAllUser(String userId){
        List<User> users = userService.getAllUser(userId);
        for (User user : users) {
            System.out.println(user);
        }
        return ResultUtils.success(users);

    }

    @PostMapping("/acceptBorrow")
    public BaseResponse<Integer> acceptBorrow(@RequestBody HashMap<String,String> accMap){
        System.out.println(accMap.get("bookName"));
        Integer res = userService.acceptBorrowBook(accMap.get("bookName"));
        return ResultUtils.success(res);
    }


//    @PostMapping("/register")
//    public BaseResponse<Long> userRegister(String ){
//
//    }


}
