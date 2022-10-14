package com.c1ph3r.c1ph3rbank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.google.android.material.button.MaterialButton;


// Fragment Method to display the Dashboard layout which contains Options for the user to do.
// Based on the user Selection the layout navigates to the other activity.
public class DashboardLayout extends Fragment {
    UserDataBase userData;
    TextView greetTheUser, ATM_UserName, ATM_AccountNo, ATM_ExpiryDate;
    View view;
    MaterialButton sendMoney, balance, deposit, Transactions;

    // empty constructor for initialization.
    public DashboardLayout(){

    }

    // Constructor with value of the user.
    public DashboardLayout(UserDataBase userData) {
        this.userData = userData;
    }


    // Method will start when this fragment is created.
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dashboard_layout, container, false);
    }


    // Executes when the Fragment is called.
    @SuppressLint("SetTextI18n")
    @Override
    public void onStart() {
        super.onStart();
        view = getView();
        if(view!=null){
            greetTheUser = view.findViewById(R.id.greetTheUser);
            ATM_UserName = view.findViewById(R.id.ATM_UserName);
            ATM_AccountNo = view.findViewById(R.id.ATM_CardNo);
            ATM_ExpiryDate = view.findViewById(R.id.ATM_ExpiryDate);
            if(view != null){
                sendMoney = view.findViewById(R.id.SendMoney);
                balance = view.findViewById(R.id.Balance);
                deposit = view.findViewById(R.id.deposit);
                greetTheUser.setText("");
                greetTheUser.setText("Welcome\n" + userData.getName());
                ATM_UserName.setText( userData.getName());
                ATM_AccountNo.setText(String.valueOf( userData.getAccountNo()));
                ATM_ExpiryDate.setText( userData.getExpiryDate());
                Transactions = view.findViewById(R.id.transactionBtn);

                sendMoney.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), Withdraw.class);
                        intent.putExtra("value", userData);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                balance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), Balance.class);
                        intent.putExtra("value", userData);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                deposit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), Deposit.class);
                        intent.putExtra("value", userData);
                        startActivity(intent);
                        requireActivity().finish();
                    }
                });

                Transactions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent (getActivity(), TransactionsDetails.class);
                        startActivity(intent);
                        requireActivity().finish();
                    }
                });
            }
        }
    }
}