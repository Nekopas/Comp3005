package com.example.Comp3005.mapper;

import com.example.Comp3005.entity.Publisher;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface PublisherMapper {
    public ArrayList<Publisher> findPublisherById(long id);
    public ArrayList<Publisher> findPublisherByName(String name);
    public ArrayList<Publisher> findPublisherByPhoneNumber(String phoneNumber);
    public ArrayList<Publisher> findPublisherByEmail(String email);
    public void insertPublisher(Publisher p);
    public void updatePublisher(Publisher p);
    public void deletePublisherById(long id);
}
