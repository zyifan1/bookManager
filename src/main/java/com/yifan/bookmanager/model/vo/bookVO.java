package com.yifan.bookmanager.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class bookVO {
    /**
     * bookId
     */
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
     * 借阅状态
     */
    private String borrowState;

    /**
     * 标签
     */
    private String tags;

    /**
     * 作者
     */
    private String author;

    /**
     * 创建时间
     */
    private Date createTime;
}
