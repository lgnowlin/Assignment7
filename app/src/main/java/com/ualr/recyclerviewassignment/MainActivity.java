package com.ualr.recyclerviewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
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
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_list_multi_selection);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, new InboxListFragment());
        initComponent();
        ft.commit();
    }

    private void initComponent() {
        dataSource = DataGenerator.getInboxData(this);
        dataSource.addAll(DataGenerator.getInboxData(this));

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
    public void showSnackbar(View view) {
        // TODO 02. Get the coordinator layout, parent of the snackbar
        CoordinatorLayout parentView = findViewById(R.id.lyt_parent);
        // TODO 03. Create a snackbar object by calling the static make method
        String msg = getResources().getString(R.string.snackbar_message);
        int duration = Snackbar.LENGTH_LONG;
        Snackbar snackbar = Snackbar.make(parentView, msg, duration);
        // TODO 04. Add an action to the snackbar message
        snackbar.setAction(R.string.snackbar_action, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Snackbar action tapped");
            }
        });
        // TODO 05. Show the message to the user
        snackbar.show();
    }
}