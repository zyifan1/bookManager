package com.yifan.bookmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yifan.bookmanager.model.entity.Book;
import com.yifan.bookmanager.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface bookMapper extends BaseMapper<Book> {

    /**
     * 查询用户借阅的书籍
     *
     * @param userId
     * @return
     */
    @Select("select book.* from borrow_info borrow left join book_info book on borrow.bookId = book.bookId where borrow.userId = #{userId} and (book.borrowState = '已借阅' or book.borrowState = '审批中')")
    public List<Book> getBorrowInfoByUser(@Param("userId") long userId);


    /**
     * 通过userId查询管理员信息
     *
     * @param userId
     * @return
     */
    @Select("select * from user_info where userId = #{userId} and userRole = 'admin'")
    public User getBookAdmin(@Param("userId") long userId);


}
