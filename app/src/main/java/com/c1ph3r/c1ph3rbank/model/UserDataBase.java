package com.c1ph3r.c1ph3rbank.model;

import android.app.Activity;

import com.c1ph3r.c1ph3rbank.controller.UserDetail;

import java.io.Serializable;

//Pojo Class to store the user details.
public class UserDataBase implements Serializable {


    //Setting these values as private so it cannot be accessed outside the class.
    private int pin;
    private int balance;


    private  int accountNo;
    private  String accountType;
    private   String name;
    private boolean isLoggedIn;


    public String getExpiryDate() {
        return expiryDate;
    }

    private String expiryDate;
    //Creating the constructor for getting the input from the user.
    public UserDataBase(String name, int accountNo, int pin, String accountType, int balance, String expiryDate, boolean loggedIn) {
        //setting the constructor values to the default values of the class.
        this.pin = pin;
        this.balance = balance;
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.name = name;
        this.isLoggedIn = loggedIn;
        this.expiryDate = expiryDate;
    }

    //Getter and setters for the private values.
    public int getPin() {
        return pin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
    public String getName() {
        return name;
    }

    public int getAccountNo() {
        return accountNo;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }
}