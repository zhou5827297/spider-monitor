package com.zhoukai.dao;

import org.springframework.stereotype.Repository;

import com.zhoukai.entity.Book;

import java.util.List;

@Repository
public interface IBookDao {
    List<Book> getBooks();

    Book getBook(int id);
}
