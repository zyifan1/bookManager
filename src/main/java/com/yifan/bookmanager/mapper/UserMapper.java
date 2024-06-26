package com.yifan.bookmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yifan.bookmanager.model.entity.Book;
import com.yifan.bookmanager.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 向借阅表中插入借阅记录
     *
     * @param userId
     * @param bookId
     * @return
     */
    @Insert("insert into borrow_info(userId,bookId,borrowState) values(#{userId},#{bookId},'审批中')")
    public int borrowBook(@Param("userId") long userId, @Param("bookId") long bookId);

    /**
     * 将借阅记录中某本书的审批中修改为已借阅
     *
     * @param bookId
     * @return
     */
    @Update("Update borrow_info set borrowState = '已借阅' where bookId = #{bookId} and borrowState = '审批中'")
    public int acceptBorrow(@Param("bookId") long bookId);


    /**
     * 删除借阅表中的某一数据
     *
     * @param userId
     * @param bookId
     * @return
     */
    @Delete("delete from borrow_info where userId=#{userId} and bookId=#{bookId}")
    public int returnBook(@Param("userId") long userId, @Param("bookId") long bookId);

    /**
     * 将某一书籍状态改成已借阅
     *
     * @param bookId
     * @return
     */
    @Update("update book_info set borrowState = '已借阅' where bookId = #{bookId}")
    public int updateBorrowStateInt(@Param("bookId") long bookId);

    /**
     * 将某一书籍状态改成未借阅
     *
     * @param bookId
     * @return
     */
    @Update("update book_info set borrowState = '未借阅' where bookId = #{bookId}")
    public int updateBorrowState(@Param("bookId") long bookId);

    /**
     * 将某一书籍状态改成审批中
     *
     * @param bookId
     * @return
     */
    @Update("update book_info set borrowState = '审批中' where bookId = #{bookId}")
    public int updateBorrowStateToAccept(@Param("bookId") long bookId);


    /**
     * 通过书名查询审批中书籍信息
     *
     * @param bookName
     * @return
     */
    @Select("select * from book_info where bookName = #{bookName} and borrowState = '审批中'")
    public Book getBookByBookName(@Param("bookName") String bookName);

}
