package com.c1ph3r.c1ph3rbank;

import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.c1ph3r.c1ph3rbank.controller.Transactions;
import com.c1ph3r.c1ph3rbank.model.TransactionHelper;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;


public class debit_history extends Fragment {

    UserDataBase user;
    public debit_history(UserDataBase userData) {
        this.user = userData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_debit_history, container, false);
    }

    public void onStart() {
        super.onStart();
        View view = getView();
        System.out.println(user.getName());
        TransactionHelper transactionHelper = new TransactionHelper(getActivity(),user.getName()+"Transactions");
        ListView debitList = view.findViewById(R.id.Debit_list);
        Transactions transactions = new Transactions();
        System.out.println(transactions.getDebit(transactionHelper).toString());
        ListAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, transactions.getDebit(transactionHelper));
        debitList.setAdapter(adapter);
    }
}