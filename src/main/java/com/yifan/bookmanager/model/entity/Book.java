package com.yifan.bookmanager.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("book_info")
public class Book {

    /**
     * bookId
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long bookId;

    /**
     * 书名
     */
    private String bookName;

    /**
     * 信息
     */
    private String bookInfo;

    /**
     * 作者
     */
    private String author;


    /**
     * 标签
     */
    private String tags;

    /**
     * 借阅状态
     */
    private String borrowState;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableLogic
    private Integer isDelete;
}
