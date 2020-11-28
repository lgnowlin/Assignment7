package com.ualr.recyclerviewassignment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.ViewModelProvider;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.SharedViewModel;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.adapter.AdapterList;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

public class InboxListFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterList adapter;
    private List<Inbox> dataSource;
    private SharedViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inbox_list_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        model = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
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

        //Button button = view.findViewById(R.id.button);
    }
}
