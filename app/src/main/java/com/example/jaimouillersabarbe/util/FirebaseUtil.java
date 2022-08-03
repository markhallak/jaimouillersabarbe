package com.example.jaimouillersabarbe.util;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUtil {

    FirebaseFirestore db;

    public FirebaseUtil(){
        if (db == null) {
            db = FirebaseFirestore.getInstance();
        }
    }

    public CollectionReference getCollection(String collectionName) {
        return db.collection(collectionName);
    }

    public void addData(CollectionReference collectionReference, Object obj) {

        db.collection(collectionReference.getPath())
                .add(obj)
                .addOnSuccessListener(documentReference -> {
                    Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    Log.d("Success", "TRUE");
                })
                .addOnFailureListener(e -> {
                    Log.w("ERROR", "Error adding document", e);
                    Log.d("Success", "FALSE");
                });
    }

    public List<Object> getData(CollectionReference collectionReference){
        List<Object> list = new ArrayList<>();

        db.collection(collectionReference.getPath())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getData());
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("TAG", "Error getting documents.", task.getException());
                        }
                    }
                });

        return list;
    }
}
