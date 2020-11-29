package com.ualr.recyclerviewassignment.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.adapter.AdapterList;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.SharedViewModel;

import java.util.List;

public class InboxListFragment extends Fragment implements AdapterList.OnItemClickListener {
    private static final String TAG = InboxListFragment.class.getSimpleName();
    private static final String FORWARD_TAG = ForwardDialogFragment.class.getSimpleName();
    private static final String FORWARD_KEY = "FORWARD_EMAIL";

    private Context mContext;
    private RecyclerView recyclerView;
    private AdapterList adapter;

    private SharedViewModel model;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inbox_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        List<Inbox> dataSource = DataGenerator.getInboxData(mContext);
        model = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        model.setInboxList(dataSource);

        adapter = new AdapterList(mContext, model.getInboxList().getValue());
        adapter.setOnItemClickListener(this);

        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        model.getInboxList().observe(getViewLifecycleOwner(), new Observer<List<Inbox>>() {
            @Override
            public void onChanged(List<Inbox> inboxList) {
                adapter.updateItems(inboxList);
            }
        });


    }


    @Override
    public void OnItemClick(View view, Inbox obj, int position) {
        List<Inbox> currentData = model.getInboxList().getValue();
        clearAllSelections(currentData);
        currentData.get(position).toggleSelection();
        model.setSelectedIndex(position);
        model.setInboxList(currentData);
    }

    @Override
    public void onIconClick(View view, Inbox obj, int position) {
        List<Inbox> currentData = model.getInboxList().getValue();
        clearAllSelections(currentData);
        currentData.get(position).toggleSelection();
        model.setSelectedIndex(position);
        model.setInboxList(currentData);
    }

    public void addEmail() {
        Inbox newItem = DataGenerator.getRandomInboxItem(mContext);
        List<Inbox> currentData = model.getInboxList().getValue();
        currentData.add(0, newItem);
        model.setInboxList(currentData);
    }

    public boolean deleteEmail() {
        int currentSelection = model.getSelectedIndex().getValue();
        List<Inbox> currentData = model.getInboxList().getValue();

        if (currentSelection != -1 && currentData != null) {
            currentData.remove(currentSelection);
            clearAllSelections(currentData);

            model.setInboxList(currentData);
            model.setSelectedIndex(-1);
            return true;
        }
        return false;
    }

    public int getSelectedEmailPosition() {
        return model.getSelectedIndex().getValue();
    }

    public void clearAllSelections(List<Inbox> inboxList) {
        for (Inbox inbox: inboxList) {
            inbox.setSelected(false);
        }
    }

    public void forwardEmail() {
        ForwardDialogFragment forwardFragment = ForwardDialogFragment.newInstance(getSelectedEmailPosition());
        forwardFragment.show(getParentFragmentManager(), TAG);
    }



}