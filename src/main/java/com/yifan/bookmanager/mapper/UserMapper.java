package com.yifan.bookmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yifan.bookmanager.model.entity.Book;
import com.yifan.bookmanager.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Insert("insert into borrow_info(userId,bookId) values(#{userId},#{bookId})")
    public int borrowBook(@Param("userId") long userId,@Param("bookId") long bookId);


    @Delete("delete from borrow_info where userId=#{userId} and bookId=#{bookId}")
    public int returnBook(@Param("userId")long userId,@Param("bookId")long bookId);

//    @Update("update ")
//    public int borrowBook(@Param("userId") long userId,@Param("bookId") long bookId);

    @Update("update book_info set borrowState = '已借阅' where bookId = #{bookId}")
    public int updateBorrowStateInt(@Param("bookId")long bookId);


    @Update("update book_info set borrowState = '未借阅' where bookId = #{bookId}")
    public int updateBorrowState(@Param("bookId")long bookId);

}
