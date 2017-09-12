package com.example.saksham.notemakerclipboard.Views.Fragment;

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

import com.example.saksham.notemakerclipboard.Adapters.NotesAdapter;
import com.example.saksham.notemakerclipboard.Model.NotesPOJO;
import com.example.saksham.notemakerclipboard.R;

import java.util.ArrayList;


public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";
    FloatingActionButton fabNotes;
    RecyclerView rvNotes;
    ArrayList<NotesPOJO> notes ;
    NotesAdapter notesAdapter;

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

        //fake text
        notes.add(new NotesPOJO("text","1/2/3"));
        notes.add(new NotesPOJO("text","1/2/3"));
        notes.add(new NotesPOJO("text","1/2/3"));

        notesAdapter = new NotesAdapter(notes, getActivity());
        rvNotes.setAdapter(notesAdapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));

        //TODO check what below statement does
        rvNotes.setItemAnimator(new DefaultItemAnimator());


        return root;
    }

    private void initialise(View root){

        fabNotes = (FloatingActionButton) root.findViewById(R.id.fabNotes);
        rvNotes = (RecyclerView) root.findViewById(R.id.rvNotes);

    }
}
