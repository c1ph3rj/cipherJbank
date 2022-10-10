package com.c1ph3r.c1ph3rbank.model;

import java.io.Serializable;

//Pojo Class to store the user details.
public class UserDataBase implements Serializable {
    //Setting these values as private so it cannot be accessed outside the class.
    private int pin;
    private int balance;
    private final int accountNo;
    private final String accountType;
    public final String name;

    public String getExpiryDate() {
        return expiryDate;
    }

    private String expiryDate;
    //Creating the constructor for getting the input from the user.
    public UserDataBase(String name, int accountNo, int pin, String accountType, int balance, String expiryDate) {
        //setting the constructor values to the default values of the class.
        this.pin = pin;
        this.balance = balance;
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public int getAccountNo() {
        return accountNo;
    }
}