package com.example.saksham.notemakerclipboard.Views.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.saksham.notemakerclipboard.Adapters.NotesAdapter;
import com.example.saksham.notemakerclipboard.Model.NotesPOJO;
import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.Views.Activity.AddNoteActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static android.app.Activity.RESULT_OK;


public class NotesFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "NotesFragment";
    FloatingActionButton fabNotes;
    RecyclerView rvNotes;
    ArrayList<NotesPOJO> notes;
    NotesAdapter notesAdapter;
    Realm realm;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        this.initialise(root);



        notes = new ArrayList<>();
        this.getSavedTextFromDB();

        notesAdapter = new NotesAdapter(notes, getActivity());
        rvNotes.setAdapter(notesAdapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));


        //TODO check what below statement does
        //rvNotes.setItemAnimator(new DefaultItemAnimator());


        return root;
    }

    private void initialise(View root) {

        fabNotes = (FloatingActionButton) root.findViewById(R.id.fabNotes);
        fabNotes.setOnClickListener(this);
        rvNotes = (RecyclerView) root.findViewById(R.id.rvNotes);

        realm = Realm.getDefaultInstance();
        Log.d(TAG, "initialise: "+Realm.getDefaultInstance());

    }

    //on start get data from Realm Dataset
    private void getSavedTextFromDB() {

        RealmQuery<NotesPOJO> query = realm.where(NotesPOJO.class);
        RealmResults<NotesPOJO> results = query.findAll();

        for (NotesPOJO item : results) {

            notes.add(item);

        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fabNotes:

                Log.d(TAG, "onClick: ");
                startActivityForResult(
                        new Intent(getContext(), AddNoteActivity.class),
                        101
                );
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 101 && resultCode == RESULT_OK) {

            //adding note to realm database
            addNote(data.getStringExtra("note"));
        }
    }

    //Add/write new Entry to DB
    public void addNote(final String note) {


        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                NotesPOJO newNote = new NotesPOJO(note,"");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm aa");
                String format = simpleDateFormat.format(new Date());
                Log.d(TAG, "execute: "+format);

                newNote.setTimeStamp(format);

                notes.add(newNote);
                realm.copyToRealmOrUpdate(notes);

            }
        });

        //refresh the recycler view list
        notesAdapter.notifyDataSetChanged();

    }

}