package com.yifan.bookmanager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yifan.bookmanager.common.ErrorCode;
import com.yifan.bookmanager.exception.BusinessException;
import com.yifan.bookmanager.mapper.UserMapper;
import com.yifan.bookmanager.model.entity.Book;
import com.yifan.bookmanager.model.entity.User;
import com.yifan.bookmanager.service.UserService;
import com.yifan.bookmanager.utils.Secure;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private static final String SALT = "Exchange the rest of your life";

    @Resource
    private BookServiceImpl bookService;
    @Autowired
    private com.yifan.bookmanager.mapper.bookMapper bookMapper;

    @Override
    public long RegisterUser(String userName, String userAccount, String password, String checkPassword) {
        //校验
        if (StrUtil.hasBlank(userName, userAccount, password, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名过短");
        }
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不一致");
        }
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码过短");
        }
        //校验重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount);
        Long count = this.baseMapper.selectCount(userQueryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名重复");
        }
        //加密
        String encryptPassword = Secure.md5Encryption(password + SALT);
        //添加
        User user = new User();
        user.setUserName(userName);
        user.setUserAccount(userAccount);
        user.setPassword(encryptPassword);
        user.setUserRole("user");
        boolean saveRes = this.save(user);
        if (!saveRes) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "注册失败");
        }
        return user.getUserId();

    }

    @Override
    public long login(String userAccount, String password) {
        if (StrUtil.hasBlank(userAccount, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        //加密
        String encryptPassword = Secure.md5Encryption(password + SALT);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount)
                .eq("password", encryptPassword)
                .eq("userRole", "user");
        User user = this.baseMapper.selectOne(userQueryWrapper);
        if (BeanUtil.isEmpty(user)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return user.getUserId();
    }

    @Override
    public long adminLogin(String userAccount, String password) {
        if (StrUtil.hasBlank(userAccount, password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        //加密
        String encryptPassword = Secure.md5Encryption(password + SALT);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userAccount", userAccount)
                .eq("password", encryptPassword)
                .eq("userRole", "admin");
        User user = this.baseMapper.selectOne(userQueryWrapper);
        if (BeanUtil.isEmpty(user)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return user.getUserId();
    }


    @Override
    public User getUserInfo(String userId) {
        if (StrUtil.hasBlank(userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userId", userId);
        return this.baseMapper.selectOne(userQueryWrapper);
    }

    @Override
    public List<User> getAllUser(String userId) {
        if (StrUtil.hasBlank(userId)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        isAdmin(userId);

        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        return this.baseMapper.selectList(userQueryWrapper);
    }

    @Override
    public int updatePassword(long userId, String oldPassword, String newPassword, String checkPassword) {
        //校验
        if (StrUtil.hasBlank(oldPassword, newPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userId", userId)
                .eq("password", Secure.md5Encryption(oldPassword + SALT));
        Long resCount = this.baseMapper.selectCount(userQueryWrapper);
        if (resCount <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
//        if (newPassword.equals(checkPassword)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不一致");
//        }
        //修改
        String s = Secure.md5Encryption(newPassword + SALT);
        System.out.println(s);
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("userId", userId);
        userUpdateWrapper.setSql("password = " + Secure.md5Encryption(newPassword + SALT));
        return this.baseMapper.update(userUpdateWrapper);
    }


    @Override
    public int deleteUser(User user, User beOperatorUser) {
        //校验操作者
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userRole", "admin")
                .eq("userId", user.getUserId());
        Long selRes = this.baseMapper.selectCount(userQueryWrapper);
        if (selRes <= 0) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //修改
        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("userId", beOperatorUser.getUserId());
        userUpdateWrapper.setSql("isDelete = 1");
        return this.baseMapper.update(userUpdateWrapper);
    }

    @Override
    public Integer borrowBook(String userId, String bookName) {
        //校验
        if (StrUtil.hasBlank(userId, bookName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数未空");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userId", Long.parseLong(userId));
        Long userQueryRes = this.baseMapper.selectCount(userQueryWrapper);
        if (userQueryRes <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户不存在");
        }
        Book book = bookService.queryBooks("bookName", bookName);
        if (BeanUtil.isEmpty(book)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "图书不存在");
        }

        //逻辑
        Long bookId = book.getBookId();
        //向借阅表中插入记录，并将状态修改成审批中
        int insertRes = this.baseMapper.borrowBook(Long.parseLong(userId), bookId);
        if (insertRes <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "借阅失败");
        }
        //将图书表中的该图书的状态修改成审批中
        int updateRes = this.baseMapper.updateBorrowStateToAccept(bookId);
        if (updateRes <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "借阅失败");
        }

        return updateRes;
//        return this.baseMapper.updateBorrowStateInt(bookId);
    }

    @Override
    public Integer acceptBorrowBook(String bookName) {
        if (StrUtil.isBlank(bookName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        System.out.println("the bookName is " + bookName);
        //通过书名获取图书
        Book book = this.baseMapper.getBookByBookName(bookName);
//        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
//        bookQueryWrapper.eq("bookName",bookName);
//        Book book = bookMapper.selectOne(bookQueryWrapper);
        System.out.println(book);
        if (book.getBookId() <= 0) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "该图书不存在");
        }
        //将借阅表中该图书的状态改成已借阅
        this.baseMapper.acceptBorrow(book.getBookId());
        //将图书表中的该书的状态改成已借阅
        return this.baseMapper.updateBorrowStateInt(book.getBookId());
    }

    @Override
    public Integer returnBook(String userId, String bookName) {
        //校验
        if (StrUtil.hasBlank(userId, bookName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数未空");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userId", Long.parseLong(userId));
        Long userQueryRes = this.baseMapper.selectCount(userQueryWrapper);
        if (userQueryRes <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户不存在");
        }
        Book book = bookService.queryBooks("bookName", bookName);
        if (BeanUtil.isEmpty(book)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "图书不存在");
        }

        Long bookId = book.getBookId();
//        int insertRes = this.baseMapper.borrowBook(Long.parseLong(userId), bookId);
        int insertRes = this.baseMapper.returnBook(Long.parseLong(userId), bookId);


        if (insertRes <= 0) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "还书失败");
        }

        return this.baseMapper.updateBorrowState(bookId);
    }


    public boolean isAdmin(String userId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userId", userId);
        List<User> users = this.baseMapper.selectList(userQueryWrapper);
        if (users.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        return true;
    }


}
