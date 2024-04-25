package com.example.myapplication;


import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FirebaseDBHelper {
    public ObjectMapper mapper = new ObjectMapper();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public void writeToDbUser(UserModel userModel, OnWriteCompleteListener listener) {
        database.child(userModel.getTCKimlikNo()).setValue(userModel)
                .addOnSuccessListener(aVoid -> {
                    listener.onSuccess();
                })
                .addOnFailureListener(e -> {
                    String errorMessage = e.getMessage();
                    listener.onFailure(errorMessage);
                });
    }
    public void writeToDbDoctor(DoctorModel doctorModel, OnWriteCompleteListener listener) {
        database.child(doctorModel.getTCKimlikNo()).setValue(doctorModel)
                .addOnSuccessListener(aVoid -> {
                    listener.onSuccess();
                })
                .addOnFailureListener(e -> {
                    String errorMessage = e.getMessage();
                    listener.onFailure(errorMessage);
                });
    }


    public boolean readToDbUser(String tc, String sifre, OnReadCompleteListener listener) {
        database.child(tc).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue(Object.class);
                UserModel user = mapper.convertValue((HashMap) value, UserModel.class);
                Log.d("Firebase", "Value is: " + value);
                if (user != null && user.getSifre().equals(sifre)) {
                    listener.onSuccess(true);
                } else {
                    listener.onSuccess(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Firebase", "Failed to read value.", databaseError.toException());
                listener.onFailure(databaseError.getMessage());
            }
        });
        return false;
    }
    public void readToDbDoctor(String tc, String adi, OnReadCompleteListener listener) {
        database.child(tc).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue(Object.class);
                DoctorModel doctorModel = mapper.convertValue((HashMap) value, DoctorModel.class);
                Log.d("Firebase", "Value is: " + value);
                if (doctorModel != null && doctorModel.getAdi().equals(adi)) {
                    listener.onSuccess(true);
                } else {
                    listener.onSuccess(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Firebase", "Failed to read value.", databaseError.toException());
                listener.onFailure(databaseError.getMessage());
            }
        });
    }

    interface OnReadCompleteListener {
        void onSuccess(boolean result);

        void onFailure(String errorMessage);
    }

    interface OnWriteCompleteListener {
        void onSuccess();

        void onFailure(String errorMessage);
    }


}
