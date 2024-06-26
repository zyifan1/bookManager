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
     * @param bookName
     * @return
     */
    public boolean deleteBook(String bookName, String userId);

    /**
     * 查询所有书籍
     *
     * @param userId
     * @return
     */
    public List<Book> getAllBook(String userId);

    /**
     * 查询所有正在还没有同意借阅的书籍
     *
     * @param userId
     * @return
     */
    public List<Book> getAllNotAcceptBook(String userId);


    /**
     * 查询所有被借阅的书籍
     *
     * @param userId
     * @return
     */
    public List<Book> QueryAllBorrowBook(String userId);

    /**
     * 查询用户借阅的书籍
     *
     * @param userId
     * @return
     */
    public List<Book> QueryBorrowBookByUser(long userId);


    /**
     * 查询未被借阅的书籍
     *
     * @return
     */
    public List<Book> QueryNotBorrowBook();


    public List<Book> QueryAllNotAcceptBook(String userId);


}
