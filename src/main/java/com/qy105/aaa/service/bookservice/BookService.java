package com.qy105.aaa.service.bookservice;


import com.qy105.aaa.model.Book;

import java.util.List;

public interface BookService {
    /**
     * 查询全部图书
     * @return
     */
    List<Book> getAllBook();

    /**
     * 添加一本图书
     * @param book
     */

    void addBook(Book book);

    /**
     * 修改图书信息
     * @param book
     */
    void updateBook(Book book);

    /**
     * 删除图书
     * @param id
     */
    void delBook(int id);
}
