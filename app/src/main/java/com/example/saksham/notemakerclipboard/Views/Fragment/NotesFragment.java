package com.example.saksham.notemakerclipboard.Views.Fragment;

import android.content.Intent;
import android.os.Bundle;
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

import io.realm.Realm;
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
    ActionMode mActionMode;
    MainActivity mainActivity;

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

            Toast.makeText(getContext(), "Modified Note", Toast.LENGTH_SHORT).show();

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

                NotesPOJO newNote = new NotesPOJO(note, "");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm aa");
                String format = simpleDateFormat.format(new Date());
                Log.d(TAG, "execute: " + format);

                newNote.setTimeStamp(format);
                newNote.setIndex(notes.size() + 1);

                notes.add(newNote);
                Log.d(TAG, "addition " + newNote.getIndex());
                realm.copyToRealmOrUpdate(notes);

            }
        });

        //refresh the recycler view list
        notesAdapter.notifyDataSetChanged();

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
                realm.copyToRealmOrUpdate(notes.get(position));

            }
        });

        notesAdapter.notifyDataSetChanged();

    }

    private void addingRVListener() {

        rvNotes.addOnItemTouchListener(new RvItemClickListener(getContext(), rvNotes, new RvItemClickListener.OnRvItemClickListener() {
            @Override
            public void setOnItemClick(View v, int position) {
                Toast.makeText(getContext(), "OnItemClick, -->" + notes.get(position), Toast.LENGTH_SHORT).show();
                if (mActionMode != null) {
                    onListItemSelect(position);
                }
            }

            @Override
            public void setOnLongItemClick(View v, int position) {
                Toast.makeText(getContext(), "Long Click", Toast.LENGTH_SHORT).show();
                onListItemSelect(position);
            }
        }));

    }

    private void onListItemSelect(int position) {

        notesAdapter.toggleSelection(position);

        boolean hasCheckedItems = notesAdapter.getSelectedCount() > 0; //check if any items are already selected

        if (hasCheckedItems && mActionMode == null) {
            //there are some items selected start the action mode
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ToolbarCallbackActionMode(getActivity(), notesAdapter, notes, this));

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


                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        NotesPOJO note = notes.get(key);
                        note.deleteFromRealm();


                    }
                });

                notes.remove(key);
                notesAdapter.notifyDataSetChanged();
            }
        }

//        Toast.makeText(getContext(), selected.size() + " items deleted", Toast.LENGTH_SHORT).show();
        Snackbar snackbar = Snackbar.make(rvNotes, selected.size() + " Notes deleted", Snackbar.LENGTH_SHORT);
        snackbar.show();
        mActionMode.finish();

    }

}