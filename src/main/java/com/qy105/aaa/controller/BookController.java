package com.qy105.aaa.controller;


import com.qy105.aaa.model.Book;
import com.qy105.aaa.service.bookservice.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;
    @RequestMapping("getAllBook")
    public String getAllBook(ModelMap modelMap) {
       List<Book> bookInfos = bookService.getAllBook();
        if (bookInfos == null) {
            modelMap.addAttribute("暂时没有书籍");
        } else {
            modelMap.addAttribute("bookList", bookInfos);
        }
        System.out.println("查询到全部图书");
        return "index";
    }

    @RequestMapping("add")
    public String addBook(Model model, Book book){
        bookService.addBook(book);
        List<Book> books = bookService.getAllBook();
        model.addAttribute("bookList", books);
        return "index";
    }
    @RequestMapping("update")
    public String update(Book book, Model model) {
        bookService.updateBook(book);
        List<Book> books = bookService.getAllBook();
        model.addAttribute("bookList", books);
        return "index";
    }

    @RequestMapping("del")
    public String dell(int id, Model model) {
        bookService.delBook(id);
        List<Book> books = bookService.getAllBook();
        model.addAttribute("bookList", books);
        return "index";
    }
}
