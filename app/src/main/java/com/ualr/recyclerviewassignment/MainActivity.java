package com.ualr.recyclerviewassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
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
    private SharedViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setContentView(R.layout.activity_list_multi_selection);
    }

    private void initComponent() {
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

    public void showForwardDialog(View view) {
        ForwardDialogFragment dialog = new ForwardDialogFragment();
        dialog.show(getSupportFragmentManager(), FRAGMENT_TAG);
    }
}