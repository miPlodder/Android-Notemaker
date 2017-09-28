package com.example.saksham.notemakerclipboard.Views.Fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
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
import com.example.saksham.notemakerclipboard.Views.Activity.UpdateClipboardNoteActivity;
import com.example.saksham.notemakerclipboard.utils.Constant;

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

        //intialising the REALMDB
        realm = Realm.getDefaultInstance();
        list = new ArrayList<>();
        this.initialiseFromRealm(); //getting info from Realm

        clipboardAdapter = new ClipboardAdapter(getContext(), list,
                new ClipboardAdapter.OnEdit() {
                    @Override
                    public void setOnEditComplete(String text, int position) {

                        Intent i = new Intent(getContext(), UpdateClipboardNoteActivity.class);
                        i.putExtra(Constant.ACTIVITY_INTENT_EXTRA_CLIPBOARD_EDIT, list.get(position).getText());
                        i.putExtra(Constant.ACTIVTY_INTENT_EXTA_CLIPBOARD_POSITION, position);
                        startActivityForResult(i, 007);

                    }
                });
        rvClipoard.setAdapter(clipboardAdapter);

        return view;
    }

    //here we iterate through the realm
    private void initialiseFromRealm() {

        RealmResults<ClipboardPOJO> result = realm.where(ClipboardPOJO.class).findAll();
        Toast.makeText(getContext(), "SIZE" + result.size(), Toast.LENGTH_SHORT).show();
        for (ClipboardPOJO item : result) {

            list.add(item);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 007 && resultCode == Activity.RESULT_OK) {

            Toast.makeText(getContext(), "Successful edit", Toast.LENGTH_SHORT).show();
            updateDB(data.getStringExtra(Constant.ACTIVITY_INTENT_EXTRA_CLIPBOARD_RESPONSE),
                    data.getIntExtra(Constant.ACTIVITY_INTENT_EXTRA_CLIPBOARD_POSITION_RESPONSE, -1));
        }
    }

    private void updateDB(final String clipboardNote, final int position) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                list.get(position).setText(clipboardNote);

                realm.copyToRealmOrUpdate(list.get(position));
                clipboardAdapter.notifyDataSetChanged();
            }
        });

    }
}
