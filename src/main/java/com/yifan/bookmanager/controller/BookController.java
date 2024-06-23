package com.yifan.bookmanager.controller;

import com.yifan.bookmanager.common.BaseResponse;
import com.yifan.bookmanager.common.ResultUtils;
import com.yifan.bookmanager.model.entity.Book;
import com.yifan.bookmanager.model.entity.User;
import com.yifan.bookmanager.model.vo.bookVO;
import com.yifan.bookmanager.service.impl.BookServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {
    @Resource
    private BookServiceImpl bookService;

    @GetMapping("/getBorrowBookByUser")
    public BaseResponse<List<bookVO>> getBorrowBookByUser(String userId, HttpServletRequest request) {
        List<Book> books = bookService.QueryBorrowBookByUser(Long.parseLong(userId));
        return getListBaseResponse(books);
    }


    @GetMapping("/getNotBorrowBook")
    public BaseResponse<List<bookVO>> getNotBorrowBook(){
        List<Book> books = bookService.QueryNotBorrowBook();
        for (Book book : books) {
            System.out.println(book);
        }
        return getListBaseResponse(books);

    }

    private BaseResponse<List<bookVO>> getListBaseResponse(List<Book> books) {
        List<bookVO> bookVOS = new ArrayList<>();
        for (Book book : books) {
            bookVO bookVO = new bookVO();
            BeanUtils.copyProperties(book,bookVO);
            bookVOS.add(bookVO);
        }
        return ResultUtils.success(bookVOS);
    }

}
