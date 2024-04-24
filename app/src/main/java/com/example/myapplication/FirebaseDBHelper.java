package com.example.myapplication;


import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class FirebaseDBHelper {
    public static final String dbName = "IlacTakip.db";
    public static final int databaseVersion = 5;

    public ObjectMapper mapper = new ObjectMapper();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public boolean writeToDb(UserModel userModel){
        Task<Void> voidTask = database.child(userModel.getTCKimlikNo()).setValue(userModel);
        return true;
    }

    public boolean readToDb(String tc, String sifre){
        database.child(tc).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object value = dataSnapshot.getValue(Object.class);
                UserModel user = mapper.convertValue((HashMap) value, UserModel.class);
                Log.d("Firebase", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Firebase", "Failed to read value.", databaseError.toException());
            }
        });

        return true;
    }


}
