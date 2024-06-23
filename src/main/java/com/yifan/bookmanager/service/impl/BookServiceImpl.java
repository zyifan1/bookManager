package com.yifan.bookmanager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yifan.bookmanager.common.ErrorCode;
import com.yifan.bookmanager.exception.BusinessException;
import com.yifan.bookmanager.mapper.bookMapper;
import com.yifan.bookmanager.model.entity.Book;
import com.yifan.bookmanager.model.entity.User;
import com.yifan.bookmanager.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<bookMapper, Book> implements BookService {
    @Override
    public long addBook(String bookName, String bookInfo, String author, String tags) {
        //校验
        if (StrUtil.hasBlank(bookName, bookInfo)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //查询校验
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq("bookName", bookName);
        Long resCount = this.baseMapper.selectCount(bookQueryWrapper);
        if (resCount > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "图书已存在");
        }
        //添加
        Book book = new Book();
        book.setBookName(bookName);
        book.setBookInfo(bookInfo);
        book.setAuthor(author);
        book.setTags(tags);
        boolean saveRes = this.save(book);
        if (!saveRes) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "添加失败");
        }
        return book.getBookId();
    }


    @Override
    public int updateBookInfo(String bookName, String newBookInfo) {
        //校验
        if (StrUtil.hasBlank(bookName, newBookInfo)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        boolean res = queryBook("bookName", bookName);
        if (!res) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该图书不存在");
        }
        UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<>();
        bookUpdateWrapper.eq("bookName", bookName);
        bookUpdateWrapper.setSql("bookInfo", newBookInfo);
        return this.baseMapper.update(bookUpdateWrapper);
    }

    @Override
    public int deleteUser(User user, Book book) {
        return 0;
    }

    @Override
    public List<Book> QueryBorrowBookByUser(long userId) {
        if (userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数错误");
        }
        System.out.println(userId);
        List<Book> bookList = this.baseMapper.getBorrowInfoByUser(userId);
        for (Book book : bookList) {
            System.out.println(book);
        }
        return bookList;
    }

    @Override
    public List<Book> QueryNotBorrowBook() {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq("borrowState","未借阅");
        return this.baseMapper.selectList(bookQueryWrapper);
    }



    /**
     * 查询
     *
     * @param field     需查询字段
     * @param queryInfo 查询字段的值
     * @return
     */
    public boolean queryBook(String field, Object queryInfo) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq(field, queryInfo);
        Long resCount = this.baseMapper.selectCount(bookQueryWrapper);
        return resCount > 0;
    }

    public Book queryBooks(String field, Object queryInfo) {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.eq(field, queryInfo);
        return this.baseMapper.selectOne(bookQueryWrapper);
    }
}
