package com.example.saksham.notemakerclipboard.utils;

import android.app.Activity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.saksham.notemakerclipboard.Adapters.NotesAdapter;
import com.example.saksham.notemakerclipboard.Model.NotesPOJO;
import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.Views.Activity.MainActivity;
import com.example.saksham.notemakerclipboard.Views.Fragment.NotesFragment;

import java.util.ArrayList;

/**
 * Created by saksham on 9/24/2017.
 */

public class ToolbarCallbackActionMode implements ActionMode.Callback {

    private Activity activity;
    private NotesAdapter notesAdapter;
    private ArrayList<NotesPOJO> notes;
    private NotesFragment notesFragment;

    public ToolbarCallbackActionMode(MainActivity activity, NotesAdapter notesAdapter, ArrayList<NotesPOJO> notes, NotesFragment notesFragment) {

        this.activity = activity;
        this.notesAdapter = notesAdapter;
        this.notes = notes;
        this.notesFragment = notesFragment;


    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {

        mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        menu.findItem(R.id.menu_del).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_del:
                notesFragment.deleteRows();
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

        notesAdapter.removeSelection(); //remove selection
        notesFragment.setNullToActionMode();

    }
}
