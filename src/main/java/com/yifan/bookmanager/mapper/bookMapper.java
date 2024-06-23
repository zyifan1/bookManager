package com.yifan.bookmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yifan.bookmanager.model.entity.Book;
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
    @Select("select book.* from borrow_info borrow left join book_info book on borrow.bookId = book.bookId where borrow.userId = #{userId} and book.borrowState = '已借阅'")
    public List<Book> getBorrowInfoByUser(@Param("userId") long userId);

//    @Update("update book_info set borrowState = '已借阅' where bookId = #{bookId}")
//    public int updateBorrowStateInt(@Param("bookId")long bookId);


}
