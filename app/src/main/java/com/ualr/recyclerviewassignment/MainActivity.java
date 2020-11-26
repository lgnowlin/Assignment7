package com.ualr.recyclerviewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import com.ualr.recyclerviewassignment.adapter.AdapterList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.Utils.Tools;
import java.util.List;

// TODO 07. Detect click events on the thumbnail located on the left of every list row when the corresponding item is selected.
//  Implement a new method to delete the corresponding item in the list

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFAB;
    private static final int TOP_POSITION = 0;
    private AdapterList adapter;
    private List<Inbox> dataSource;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_multi_selection);
        initComponent();

    }

    private void initComponent() {
        dataSource = DataGenerator.getInboxData(this);
        dataSource.addAll(DataGenerator.getInboxData(this));

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AdapterList(this, dataSource);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterList.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Inbox obj, int position) {
                adapter.toggleItemState(position);
            }
        });

        adapter.setOnThumbnailClickListener(new AdapterList.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Inbox obj, int position) {
                adapter.deleteItem(position);
            }
        });

        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.addItem(TOP_POSITION, dataSource.get(Tools.getRandomNumberInRange(TOP_POSITION,dataSource.size()-1)));
                recyclerView.scrollToPosition(TOP_POSITION);
            }
        });


    }

}