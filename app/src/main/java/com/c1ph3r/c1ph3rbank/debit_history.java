package com.c1ph3r.c1ph3rbank;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.c1ph3r.c1ph3rbank.DBHelper.TransactionHelper;
import com.c1ph3r.c1ph3rbank.Model.UserDataBase;


public class debit_history extends Fragment {

    UserDataBase user;
    public debit_history(UserDataBase userData) {
        this.user = userData;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    public debit_history(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_debit_history, container, false);
    }

    // Method to display the array list to the listview.
    public void onStart() {
        super.onStart();
        View view = getView();
        try{
            if(view != null){
                // Set the debit history to the list view adapter.
                TransactionHelper transactionHelper = new TransactionHelper(getActivity(),user.getName()+"Transactions");
                ListView debitList = view.findViewById(R.id.Debit_list);
                ListAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_layout_for_list, transactionHelper.getDebit());
                debitList.setAdapter(adapter);
            }
        }catch(Exception e){
            System.out.println(e.toString()+"");
        }
    }
}