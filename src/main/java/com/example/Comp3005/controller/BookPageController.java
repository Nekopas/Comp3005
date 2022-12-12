package com.example.Comp3005.controller;

import com.example.Comp3005.entity.BankAccount;
import com.example.Comp3005.entity.Book;
import com.example.Comp3005.entity.Publisher;
import com.example.Comp3005.entity.User;
import com.example.Comp3005.mapper.BankAccountMapper;
import com.example.Comp3005.mapper.BookMapper;
import com.example.Comp3005.mapper.PublisherMapper;
import com.example.Comp3005.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class BookPageController {
    @Autowired
    BookMapper bookMapper;
    @Autowired
    PublisherMapper publisherMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    BankAccountMapper bankAccountMapper;

    @RequestMapping("/book")
    public String BookPage(Long bid, Model model, HttpSession session){
        //read book id and user id from httpsession
        if(bid==null){
            bid= (Long) session.getAttribute("bid");
        }
        session.setAttribute("bid",bid);
        Long uid=(Long)session.getAttribute("uid");
        //pass user object to front
        if(uid!=null){
            ArrayList<User> users = userMapper.findUserById(uid);
            if(users.size()!=0){
                model.addAttribute("user",users.get(0));
            }else{
                model.addAttribute("user",null);
            }
        }else{
            return "redirect:/user/store";
        }
        //pass book object to front
        if(bid!=null){
            ArrayList<Book> books = bookMapper.findBookById(bid);
            if(books.size()!=0){
                Book book=books.get(0);
                ArrayList<Publisher> publishers = publisherMapper.findPublisherById(book.getPublisher_id());
                model.addAttribute("book",book);
                if(publishers.size()==0){
                    model.addAttribute("publisher","null");
                }else{
                    model.addAttribute("publisher",publishers.get(0).getName());
                }
            }else{
                model.addAttribute("msg","No book found");
                model.addAttribute("books",books);
                return "store";
            }
        }
        return "book";
    }

    @RequestMapping("/manager/delete/processing")
    public String DeleteProcessing(Long bid,HttpSession session){
        //remove uid from session
        Long uid =(Long) session.getAttribute("uid");
        if(uid!=null){
            bookMapper.deleteBookById(bid);
        }
        return "redirect:/manager/store";
    }

    @RequestMapping("/manager/add")
    public String AddNewBook(){
        return "book_add";
    }


    @RequestMapping("/manager/add/processing")
    public String AddNewBookProcess(String title,String author,String isbn,String genre,Integer numberOfPage,Integer inStoreNumber,Float price,Float percentage,String publisher_name,HttpSession session){
        //read user id from session
        Long uid = (Long) session.getAttribute("uid");
        if(uid!=null){
            if(userMapper.findUserById(uid).size()==0){
                return "store_manager";
            }
        }
        //check book is existed
        if(bookMapper.findBookByISBN(isbn).size()!=0){
            return "store_manager";
        }
        //initialize a new book
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setGenre(genre);
        book.setNumberOfPage(numberOfPage);
        book.setInStoreNumber(inStoreNumber);
        book.setPreviousSale(0);
        book.setPrice(price);
        book.setPercentage(percentage);
        book.setBookstore_id(0);
        ArrayList<Publisher> publishers = publisherMapper.findPublisherByName(publisher_name);
        if(publishers.size()==0){
            Publisher publisher = new Publisher();
//            System.out.println(publisher_name);
            publisher.setName(publisher_name);
            publisherMapper.insertPublisher(publisher);
            publishers = publisherMapper.findPublisherByName(publisher_name);
            System.out.println(publishers);
            if(publishers.size()==0){
                return "redirect:/manager/add";
            }
            publisher = publishers.get(0);
            book.setPublisher_id(publisher.getId());
            BankAccount ba = new BankAccount();
            ba.setBalance(5000.0f);
            ba.setOwner_id(publisher.getId());
            bankAccountMapper.insertBankAccount(ba);
        }else{
            book.setPublisher_id(publishers.get(0).getId());
        }
        //update to book table
        bookMapper.insertBook(book);
        return "redirect:/manager/store";
    }



}
