package com.ualr.recyclerviewassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ualr.recyclerviewassignment.adapter.AdapterList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.fragments.ForwardDialogFragment;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.Utils.Tools;
import java.util.List;

import com.ualr.recyclerviewassignment.fragments.InboxListFragment;

//  Implement a new method to delete the corresponding item in the list

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFAB;
    private static final int TOP_POSITION = 0;
    private AdapterList adapter;
    private List<Inbox> dataSource;
    private RecyclerView recyclerView;
    private static final String TAG = MainActivity.class.getSimpleName();
    private InboxListFragment inboxListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_list_multi_selection);
        inboxListFragment = (InboxListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFABClicked();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_multi_selection_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_action:
                onDeleteClicked();
                return true;
            case R.id.forward_action:
                onForwardClicked();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    public void showSnackbar(String view) {
        CoordinatorLayout parentView = findViewById(R.id.lyt_parent);
        String msg = getResources().getString(R.string.snackbar_message);
        int duration = Snackbar.LENGTH_LONG;
        Snackbar snackbar = Snackbar.make(parentView, msg, duration);
        snackbar.setAction(R.string.snackbar_action, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Snackbar action tapped");
            }
        });
        snackbar.show();
    }

    public void onFABClicked() {
        if (inboxListFragment != null && inboxListFragment.isInLayout()) {
            inboxListFragment.addEmail();
        }
    }

    public void onDeleteClicked() {
        if (inboxListFragment != null && inboxListFragment.isInLayout()) {
            boolean emailDeleted = inboxListFragment.deleteEmail();
            if (emailDeleted) {
                String deleteMsg = getResources().getString(R.string.snackbar_message);
                showSnackbar(deleteMsg);
            }
        }
    }

    public void onForwardClicked() {
        if (inboxListFragment != null && inboxListFragment.isInLayout()) {
            inboxListFragment.forwardEmail();
        }
    }
}