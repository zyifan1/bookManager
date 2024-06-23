package com.yifan.bookmanager.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yifan.bookmanager.model.entity.Book;
import com.yifan.bookmanager.model.entity.User;

import java.util.List;


public interface BookService extends IService<Book> {


    /**
     * 添加图书
     *
     * @param bookName
     * @param bookInfo
     * @param author
     * @param tags
     * @return
     */
    public long addBook(String bookName, String bookInfo, String author, String tags);


    /**
     * 修改图书信息
     *
     * @param bookName
     * @param newBookInfo
     * @return
     */
    public int updateBookInfo(String bookName, String newBookInfo);


    /**
     * 删除图书（逻辑删除）
     *
     * @param user
     * @param book
     * @return 是否成功  1--成功
     */
    public int deleteUser(User user, Book book);


    /**
     * 查询用户借阅的书籍
     *
     * @param userId
     * @return
     */
    public List<Book> QueryBorrowBookByUser(long userId);


    /**
     * 查询未被借阅的书籍
     * @return
     */
    public List<Book> QueryNotBorrowBook();


}
