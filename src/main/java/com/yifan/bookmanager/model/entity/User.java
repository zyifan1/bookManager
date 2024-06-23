package com.yifan.bookmanager.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class User {

    /**
     * userId
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;

    /**
     * 昵称
     */
    private String userName;


    /**
     * 用户角色 -- user/admin
     */
    private String userRole;

    /**
     * 用户名
     */
    private String userAccount;

    /**
     * 密码
     */
    private String password;

//    /**
//     * 校验密码
//     */
//    private String checkPassword;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableLogic
    private Integer isDelete;
}
