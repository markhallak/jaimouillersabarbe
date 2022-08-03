package com.example.jaimouillersabarbe;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaimouillersabarbe.databinding.ActivityMainBinding;
import com.example.jaimouillersabarbe.models.Report;
import com.example.jaimouillersabarbe.models.User;
import com.example.jaimouillersabarbe.util.Adapter;
import com.example.jaimouillersabarbe.util.FirebaseUtil;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button reportBtn, addDataBtn;
    FirebaseUtil db;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    List<Object> retrievedData;
    List<Report> readyData;
    RecyclerView recyclerView;
    Adapter adapter;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new FirebaseUtil();

        retrieveData();

        initializeViews();
        initializeListeners();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void retrieveData() {
        retrievedData = db.getData(db.getCollection("reports"));
        readyData = convertData(retrievedData);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    List<Report> convertData(List<Object> list) {
        List<Report> reportList = new ArrayList<>();

        if(list.size() == 0){
            return reportList;
        }

        for (Object e : list) {
            Map map = (Map) e;
            reportList.add(Report.fromHashMap(map));
        }

        return reportList;
    }

    private void initializeListeners() {

//
//        readDataBtn.setOnClickListener(view -> {
//            CollectionReference ref = db.getCollection("users");
//            ref.orderBy("first", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        Log.d("TAG", document.getId() + " => " + document.getData());
//                    }
//                } else {
//                    Log.d("TAG", "Error getting documents: ", task.getException());
//                }
//            });
//        });

        reportBtn.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ReportActivity.class));
        });

        addDataBtn.setOnClickListener(view -> {
            Map<String, Object> report = new HashMap<>();
            report.put("type", Long.valueOf(1));
            report.put("area", "LA");
            report.put("city", "Cali");
            report.put("date", "1/11/2022");
            report.put("amount", Long.valueOf(2500));
            report.put("title", "I got bribed");
            report.put("description", "I got bribed in LA, Cali while I was in the police department");

            db.addData(db.getCollection("reports"), report);
        });
    }

    private void initializeViews() {
        reportBtn = findViewById(R.id.reportBtn);
        reportBtn.setBackgroundDrawable(null);

        addDataBtn = findViewById(R.id.addDataBtn);

        recyclerView = findViewById(R.id.reportsRecyclerView);
        adapter = new Adapter(this, readyData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        db.getCollection("reports").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Log.d("ERROR: ", error.toString());
                    return;
                }

                for (DocumentChange e : value.getDocumentChanges()) {
                    DocumentSnapshot snapshot = e.getDocument();

                    switch (e.getType()) {
                        case ADDED:
                            onDocumentAdded(e); // Add this line
                            break;
                        case MODIFIED:
                            onDocumentModified(e); // Add this line
                            break;
                        case REMOVED:
                            onDocumentRemoved(e); // Add this line
                            break;

                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onDocumentAdded(DocumentChange change) {
        Map map = change.getDocument().getData();
        if(map.size() != 7){
            return;
        }

        readyData.add(change.getNewIndex(), Report.fromHashMap(map));
        adapter.notifyItemInserted(change.getNewIndex());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onDocumentModified(DocumentChange change) {
        Map map = change.getDocument().getData();

        if(map.size() != 7){
            return;
        }

        if (change.getOldIndex() == change.getNewIndex()) {
            // Item changed but remained in same position
            readyData.set(change.getOldIndex(), Report.fromHashMap(map));
            adapter.notifyItemChanged(change.getOldIndex());
        } else {
            // Item changed and changed position
            readyData.remove(change.getOldIndex());
            readyData.add(change.getNewIndex(), Report.fromHashMap(map));
            adapter.notifyItemMoved(change.getOldIndex(), change.getNewIndex());
        }
    }

    protected void onDocumentRemoved(DocumentChange change) {
        readyData.remove(change.getOldIndex());
        adapter.notifyItemRemoved(change.getOldIndex());
    }
}