package com.c1ph3r.c1ph3rbank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.c1ph3r.c1ph3rbank.controller.Transactions;
import com.c1ph3r.c1ph3rbank.model.TransactionHelper;
import com.c1ph3r.c1ph3rbank.model.UserDataBase;

public class credit_history extends Fragment {
    UserDataBase user;

    // Getting the userData via Constructor.
    public credit_history(UserDataBase userData) {
        this.user = userData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    // Empty Constructor to initiate the fragment.
    public credit_history(){}

    // When view for the fragment is created.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_credit_history, container, false);
    }

    // Executes while the fragment is start.
    public void onStart() {
        super.onStart();
        View view = getView();
        try{
            // If the view is null the code will not execute.
            if(view != null){
                // Printing the Credit transaction to the user.
                TransactionHelper transactionHelper = new TransactionHelper(getActivity(),user.getName()+"Transactions");
                // List view is used to display the the list of transaction.
                ListView creditList = view.findViewById(R.id.credit_List);
                ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_layout_for_list, transactionHelper.getCredit());
                creditList.setAdapter(adapter);
            }
        }catch(Exception e){
            System.out.println(e.toString()+" | Credit History");
        }
    }
}