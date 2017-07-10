package com.zhoukai.service;

import com.zhoukai.dao.IBookDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import com.zhoukai.entity.Book;
import java.util.List;

@Service
public class BookService {
    @Resource
    IBookDao bookDao;

    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    public Book getBook(int id) {
        return bookDao.getBook(id);
    }
}