package com.c1ph3r.c1ph3rbank.model;

//Pojo Class to store the user details.
public class UserDataBase {
    //Setting these values as private so it cannot be accessed outside the class.
    private int pin;
    private int balance;
    private final int accountNo;
    private final String accountType;
    public final String name;
    //Creating the constructor for getting the input from the user.
    public UserDataBase(String name, int accountNo, int pin, String accountType, int balance) {
        //setting the constructor values to the default values of the class.
        this.pin = pin;
        this.balance = balance;
        this.accountNo = accountNo;
        this.accountType = accountType;
        this.name = name;
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