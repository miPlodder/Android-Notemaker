package com.example.saksham.notemakerclipboard.utils;

import android.support.v7.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.example.saksham.notemakerclipboard.Adapters.ClipboardAdapter;
import com.example.saksham.notemakerclipboard.Model.ClipboardPOJO;
import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.Views.Activity.MainActivity;
import com.example.saksham.notemakerclipboard.Views.Fragment.ClipboardFragment;

import java.util.ArrayList;

/**
 * Created by saksham on 10/1/2017.
 */

public class ToolbarCallbackActionModeClipboard implements ActionMode.Callback {

    MainActivity activity ;
    ClipboardAdapter clipboardAdapter;
    ArrayList<ClipboardPOJO> list;
    ClipboardFragment clipboardFragment;

    public ToolbarCallbackActionModeClipboard(MainActivity activity, ClipboardAdapter clipboardAdapter, ArrayList<ClipboardPOJO> list, ClipboardFragment clipboardFragment){

        this.activity = activity;
        this.clipboardAdapter = clipboardAdapter;
        this.list = list;
        this.clipboardFragment = clipboardFragment;

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
                clipboardFragment.deleteRows();
                break;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

        clipboardAdapter.removeSelection(); //remove selection
        clipboardFragment.setNullToActionMode();

    }
}
