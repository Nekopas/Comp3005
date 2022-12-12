package com.example.Comp3005.mapper;

import com.example.Comp3005.entity.BankAccount;
import com.example.Comp3005.entity.BookStore;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface BankAccountMapper {
    public ArrayList<BankAccount> findBankAccountById(long id);
    public ArrayList<BankAccount> findBankAccountByOwnerId(long owner_id);
    public void insertBankAccount(BankAccount bankAccount);
    public void updateBankAccount(BankAccount bankAccount);
    public void deleteBankAccountById(long id);
}
