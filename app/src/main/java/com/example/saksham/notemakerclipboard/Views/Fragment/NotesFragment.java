package com.example.saksham.notemakerclipboard.Views.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.saksham.notemakerclipboard.Adapters.NotesAdapter;
import com.example.saksham.notemakerclipboard.Model.NotesPOJO;
import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.Views.Activity.AddNoteActivity;
import com.example.saksham.notemakerclipboard.Views.Activity.EditNoteActivity;
import com.example.saksham.notemakerclipboard.Views.Activity.MainActivity;
import com.example.saksham.notemakerclipboard.utils.Constant;
import com.example.saksham.notemakerclipboard.utils.RvItemClickListener;
import com.example.saksham.notemakerclipboard.utils.ToolbarCallbackActionMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

import static android.app.Activity.RESULT_OK;


public class NotesFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "NotesFragment";
    FloatingActionButton fabNotes;
    RecyclerView rvNotes;
    ArrayList<NotesPOJO> notes;
    NotesAdapter notesAdapter;
    Realm realm;
    ActionMode mActionMode;

    public NotesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_notes, container, false);
        this.initialise(root);

        notes = new ArrayList<>();
        this.getSavedTextFromDB();

        notesAdapter = new NotesAdapter(notes, getActivity(), new NotesAdapter.OnEdit() {

            @Override
            public void doAfterOnEdit(int position, String note) {

                Intent i = new Intent(getContext(), EditNoteActivity.class);
                i.putExtra(Constant.ACTIVITY_INTENT_KEY_POSITION,
                        position);

                i.putExtra(Constant.ACTIVITY_INTENT_KEY_EDIT,
                        note);

                startActivityForResult(i, Constant.ACTIVITY_EDIT_NOTE_CODE);
            }
        });
        rvNotes.setAdapter(notesAdapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));

        //adding listener for item click and long clicks
        this.addingRVListener();

        //TODO check what below statement does
        //rvNotes.setItemAnimator(new DefaultItemAnimator());


        return root;
    }

    private void initialise(View root) {

        fabNotes = (FloatingActionButton) root.findViewById(R.id.fabNotes);
        fabNotes.setOnClickListener(this);
        rvNotes = (RecyclerView) root.findViewById(R.id.rvNotes);

        realm = Realm.getDefaultInstance();

        RealmChangeListener realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {

                Realm realm = (Realm) o;
                RealmResults<NotesPOJO> result = realm.where(NotesPOJO.class).findAllSorted("timeStamp",Sort.DESCENDING);

                notes.clear();

                //making the list empty
                for(NotesPOJO item : result){

                    notes.add(item);
                }
                Toast.makeText(getContext(), "EMPTY AND FILLING AGAIN LIST", Toast.LENGTH_SHORT).show();
                notesAdapter.notifyDataSetChanged();
            }
        };

        realm.addChangeListener(realmChangeListener);

        /*
        This is how we delete all rows from REALM DATABASE
        */
        /*realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                realm.deleteAll();
            }
        });*/


    }

    //on start get data from Realm Dataset
    private void getSavedTextFromDB() {

        Log.d(TAG, "getting data after notify data set ");
        RealmQuery<NotesPOJO> query = realm.where(NotesPOJO.class);
        RealmResults<NotesPOJO> results = query.findAllSorted("timeStamp", Sort.DESCENDING);

        for (NotesPOJO item : results) {

            notes.add(item);

        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fabNotes:

                startActivityForResult(
                        new Intent(getContext(), AddNoteActivity.class),
                        Constant.ACTIVITY_ADD_NOTE_CODE
                );
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == Constant.ACTIVITY_ADD_NOTE_CODE && resultCode == RESULT_OK) {

            //adding note to realm database
            addNote(data.getStringExtra(Constant.ACTIVITY_INTENT_KEY_ADD));
        }

        if (requestCode == Constant.ACTIVITY_EDIT_NOTE_CODE && resultCode == RESULT_OK) {

            Toast.makeText(getContext(), "Edited Successfully", Toast.LENGTH_SHORT).show();

            //use intent to retriece the data
            updateNote(data);
        }
    }


    //Add/write new Entry to DB
    public void addNote(final String note) {

        //here no need of beginning or committing a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {


                //used like hashing to generate a different IDS for all elements
                Random id = new Random();

                NotesPOJO newNote = realm.createObject(NotesPOJO.class, id.nextInt(Integer.MAX_VALUE));

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm aa");
                String format = simpleDateFormat.format(new Date());

                newNote.setTimeStamp(format);
                newNote.setText(note);

                //notes.add(newNote);
                realm.copyToRealmOrUpdate(notes);


            }
        });

        //refresh the recycler view list
        //notesAdapter.notifyDataSetChanged();

    }

    //edit the notes here to data and then notify the Realm Database
    private void updateNote(Intent i) {

        final String note = i.getStringExtra(Constant.ACTIVITY_INTENT_KEY_EDIT);
        final int position = i.getIntExtra(Constant.ACTIVITY_INTENT_KEY_POSITION, -1);

        //here no need of beginning or committing a transaction
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                notes.get(position).setText(note);
                //Log.d(TAG, "editing "+notes.get(position).getIndex());

                //here not adding a new object to the REALM DATABASE
                realm.copyToRealmOrUpdate(notes.get(position));

            }
        });

        //notesAdapter.notifyDataSetChanged();

    }

    private void addingRVListener() {

        rvNotes.addOnItemTouchListener(new RvItemClickListener(getContext(), rvNotes, new RvItemClickListener.OnRvItemClickListener() {
            @Override
            public void setOnItemClick(View v, int position) {

                if (mActionMode != null) {
                    onListItemSelect(position);
                }else{
                    notesAdapter.setOnClickListener(position);
                }
            }

            @Override
            public void setOnLongItemClick(View v, int position) {

                onListItemSelect(position);
            }
        }));
    }

    private void onListItemSelect(int position) {

        notesAdapter.toggleSelection(position);

        boolean hasCheckedItems = notesAdapter.getSelectedCount() > 0; //check if any items are already selected

        if (hasCheckedItems && mActionMode == null) {

            //there are some items selected start the action mode
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ToolbarCallbackActionMode((MainActivity) getActivity(), notesAdapter, notes, this));


        } else if (!hasCheckedItems && mActionMode != null) {
            //there are no items selected finish the action mode
            mActionMode.finish();
        }
        if (mActionMode != null)
            mActionMode.setTitle(String.valueOf(notesAdapter.getSelectedCount()) + " selected");
    }

    //set Action mode null after use
    public void setNullToActionMode() {

        if (mActionMode != null) {

            mActionMode = null;

        }
    }

    //Delete selected rows
    public void deleteRows() {
        //get Selected items
        final SparseBooleanArray selected = notesAdapter.getmSelectedItems();

        //loop through selected items
        for (int i = (selected.size() - 1); i >= 0; i--) {

            final int key = selected.keyAt(i);

            if (selected.valueAt(i)) {
                //If the current id is selected remove the item via key
                //deleteFromNoteRealm(selected.keyAt(i));

                final NotesPOJO note = notes.get(key);
                Log.d(TAG, "INDEX INDEX INDEX" + note.getIndex());

                final RealmResults result = realm.where(NotesPOJO.class).equalTo("index", key).findAll();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        //Log.d(TAG, "execute: "+result.size());

                        //notes.remove(key);
                        //notesAdapter.notifyDataSetChanged();
                        note.deleteFromRealm();
                        Log.d(TAG, "execute: SIZE SIZE SIZE"+notes.size());


                        //result.deleteFromRealm(0);;
                    }
                });
            }
        }

        Snackbar snackbar = Snackbar.make(rvNotes, selected.size() + " Notes deleted", Snackbar.LENGTH_SHORT);
        snackbar.show();
        mActionMode.finish();

    }
}