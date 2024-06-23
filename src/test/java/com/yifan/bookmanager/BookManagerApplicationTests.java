package com.yifan.bookmanager;

import com.yifan.bookmanager.model.entity.Book;
import com.yifan.bookmanager.service.BookService;
import com.yifan.bookmanager.service.impl.BookServiceImpl;
import com.yifan.bookmanager.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookManagerApplicationTests {
    @Resource
    UserServiceImpl userService;

    @Resource
    BookServiceImpl bookService;

//    @Test
//    void testRegister() {
//        userService.RegisterUser("zyf","yifan","518420zyf","518420zyf");
//    }

    @Test
    void testAddBook() {
        bookService.addBook("三国演义","群雄逐鹿","罗贯中","[历史,小说]");
    }


    @Test
    void testBookByUser(){
        List<Book> books = bookService.QueryBorrowBookByUser(Long.valueOf("1804377759914991617"));
        for (Book book : books) {
            System.out.println(book);
        }
    }


    @Test
    void modifyPassword(){
        userService.updatePassword(1804377759914991L,"518420zyf","admin","admin");
    }


}
