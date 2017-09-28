package com.example.saksham.notemakerclipboard.Views.Fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.saksham.notemakerclipboard.Adapters.ClipboardAdapter;
import com.example.saksham.notemakerclipboard.Model.ClipboardPOJO;
import com.example.saksham.notemakerclipboard.R;

import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmResults;

public class ClipboardFragment extends Fragment {

    RecyclerView rvClipoard;
    ClipboardAdapter clipboardAdapter;
    ArrayList<ClipboardPOJO> list;
    Realm realm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_clipboard, container, false);

        rvClipoard = (RecyclerView) view.findViewById(R.id.rvClipboard);
        rvClipoard.setLayoutManager(new LinearLayoutManager(getContext()));

        realm = Realm.getDefaultInstance();
        list = new ArrayList<>();
        this.initialiseFromRealm(); //getting info from Realm

        ClipboardAdapter clipboardAdapter = new ClipboardAdapter(getContext(), list);
        rvClipoard.setAdapter(clipboardAdapter);

        return view;
    }

    //here we iterate through the realm
    private void initialiseFromRealm() {

        RealmResults<ClipboardPOJO> result = realm.where(ClipboardPOJO.class).findAll();
        Toast.makeText(getContext(), "SIZE" + result.size(), Toast.LENGTH_SHORT).show();
        for(ClipboardPOJO item : result){

            list.add(item);
        }

    }
}
