package com.example.Comp3005.mapper;

import com.example.Comp3005.entity.BookStore;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface BookStoreMapper {
    public ArrayList<BookStore> findAllBookStore();
    public ArrayList<BookStore> findBookstoreById(long id);
    public ArrayList<BookStore> findBookstoreByName(long name);
    public void insertBookstore(BookStore bs);
    public void updateBookstore(BookStore bs);
    public void deleteBookstoreById(long id);
}
