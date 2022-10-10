package com.c1ph3r.c1ph3rbank;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c1ph3r.c1ph3rbank.model.UserDataBase;
import com.google.android.material.button.MaterialButton;

public class ConfirmationForPayment extends Fragment {
    View view;
    private UserDataBase userData;

    public ConfirmationForPayment() {
    }

    public ConfirmationForPayment(UserDataBase userData) {
        this.userData = userData;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_confirmation_for_payment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        view = getView();
        MaterialButton backBtn;
        backBtn = view.findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),DashBoard.class);
                intent.putExtra("value", userData);
                System.out.println(userData.getBalance());
                startActivity(intent);
               getActivity().finish();
            }
        });
    }

    public void onClickBackBtn(View view) {

    }
}