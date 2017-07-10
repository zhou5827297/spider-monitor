package com.zhoukai.controller;


import com.zhoukai.entity.Book;
import com.zhoukai.service.BookService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class BookController {

    @Resource
    BookService service;

    @RequestMapping("/books")
    public List<Book> getBooks() {
        return service.getBooks();
    }

    @RequestMapping("/book/{id}")
    public Book getBook(@PathVariable(name = "id") int id) {
        return service.getBook(id);
    }
}