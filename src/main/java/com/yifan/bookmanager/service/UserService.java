package com.yifan.bookmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.bookmanager.model.entity.User;

public interface UserService extends IService<User> {

    /**
     * 注册
     *
     * @param userName
     * @param userAccount
     * @param password
     * @param checkPassword
     * @return 新增用户id
     */
    public long RegisterUser(String userName, String userAccount, String password, String checkPassword);

    /**
     * 登录
     * @param userAccount
     * @param password
     */
    public long login(String userAccount,String password);

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    public User getUserInfo(String userId);

    /**
     * 修改密码
     *
     * @param userId
     * @param oldPassword
     * @param newPassword
     * @param checkPassword
     * @return 是否成功  1--成功
     */
    public int updatePassword(long userId, String oldPassword, String newPassword, String checkPassword);


    /**
     * 删除用户（逻辑删除）
     *
     * @param user
     * @return 是否成功  1--成功
     */
    public int deleteUser(User user, User beOperatorUser);


    /**
     * 借阅书籍
     *
     * @param userId
     * @param bookName
     */
    public Integer borrowBook(String userId, String bookName);

    /**
     * 还书
     * @param userId
     * @param bookName
     * @return
     */
    public Integer returnBook(String userId,String bookName);

}
