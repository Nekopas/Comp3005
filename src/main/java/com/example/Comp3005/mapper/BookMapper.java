package com.example.Comp3005.mapper;

import com.example.Comp3005.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface BookMapper {
    public ArrayList<Book> findAllBook();
    public ArrayList<Book> findBookById(long id);
    public ArrayList<Book> findBookByTitle(String title);
    public ArrayList<Book> findBookByGenre(String genre);
    public ArrayList<Book> findBookByISBN(String isbn);
    public ArrayList<Book> findBookByPublisher(Long publisher);
    public ArrayList<Book> findBookByInput(String input);
    public void insertBook(Book book);
    public void updateBook(Book book);
    public void deleteBookById(long id);
}
