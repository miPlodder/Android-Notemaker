package com.example.saksham.notemakerclipboard.Views.Fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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

import com.example.saksham.notemakerclipboard.Adapters.ClipboardAdapter;
import com.example.saksham.notemakerclipboard.Model.ClipboardPOJO;
import com.example.saksham.notemakerclipboard.Model.NotesPOJO;
import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.Views.Activity.MainActivity;
import com.example.saksham.notemakerclipboard.Views.Activity.UpdateClipboardNoteActivity;
import com.example.saksham.notemakerclipboard.utils.Constant;
import com.example.saksham.notemakerclipboard.utils.RvItemClickListener;
import com.example.saksham.notemakerclipboard.utils.ToolbarCallbackActionMode;
import com.example.saksham.notemakerclipboard.utils.ToolbarCallbackActionModeClipboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class ClipboardFragment extends Fragment {

    RecyclerView rvClipoard;
    ClipboardAdapter clipboardAdapter;
    ArrayList<ClipboardPOJO> list;
    Realm realm;
    ActionMode mActionMode;
    View view;
    public static final String TAG = "ClipboardFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.initialise(inflater, container);
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
        this.addingRVListener();


        return view;
    }

    public void initialise(LayoutInflater inflater, ViewGroup container) {

        view = inflater.inflate(R.layout.fragment_clipboard, container, false);
        rvClipoard = (RecyclerView) view.findViewById(R.id.rvClipboard);
        rvClipoard.setLayoutManager(new LinearLayoutManager(getContext()));

        //intialising the REALMDB
        realm = Realm.getDefaultInstance();
        list = new ArrayList<>();

        RealmChangeListener realmChangeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object o) {

                Realm realm = (Realm) o;

                RealmResults<ClipboardPOJO> result = realm.where(ClipboardPOJO.class).findAllSorted("timeStamp", Sort.DESCENDING);

                list.clear();
                for(ClipboardPOJO item : result){

                    list.add(item);
                }

                sortTimeStamp();
                Collections.reverse(list);
                clipboardAdapter.notifyDataSetChanged();
            }
        };

        realm.addChangeListener(realmChangeListener);

    }

    //here we iterate through the realm
    private void initialiseFromRealm() {

        RealmResults<ClipboardPOJO> result = realm.where(ClipboardPOJO.class).findAllSorted("timeStamp",Sort.DESCENDING);

        list.clear();
        for (ClipboardPOJO item : result) {
            list.add(item);
        }

        sortTimeStamp();
        Collections.reverse(list);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 007 && resultCode == Activity.RESULT_OK) {

            Toast.makeText(getContext(), "Edited Successfully ", Toast.LENGTH_SHORT).show();
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
                //clipboardAdapter.notifyDataSetChanged();
            }
        });

    }

    //below code is for addition of contextual action bar and deletion of rows of recycler view
    private void addingRVListener() {

        rvClipoard.addOnItemTouchListener(new RvItemClickListener(getContext(), rvClipoard, new RvItemClickListener.OnRvItemClickListener() {
            @Override
            public void setOnItemClick(View v, int position) {

                if (mActionMode != null) {
                    onListItemSelect(position);
                } else {
                    clipboardAdapter.setOnClickListener(position);
                }
            }

            @Override
            public void setOnLongItemClick(View v, int position) {

                onListItemSelect(position);
            }
        }));
    }

    public void sortTimeStamp(){

        for (int i = 0; i < list.size(); i++) {

            for (int j = i + 1; j < list.size(); j++) {

                String date1 = list.get(i).getTimeStamp();
                String date2 = list.get(j).getTimeStamp();

                if (compareDate(date1, date2) == 1) {
                    //date1 is greater //swap
                    ClipboardPOJO temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);

                } else {
                    //date2 is greater
                    //nothing to do
                }
            }
        }


    }

    public int compareDate(String timeStamp1, String timeStamp2) {

        int year1 = Integer.parseInt(timeStamp1.substring(12, 16)), year2 = Integer.parseInt(timeStamp2.substring(12, 16));
        int month1 = Constant.getMonthNumber(timeStamp1.substring(8, 11)), month2 = Constant.getMonthNumber(timeStamp2.substring(8, 11));

        int date1 = Integer.parseInt(timeStamp1.substring(5, 7).trim()), date2 = Integer.parseInt(timeStamp2.substring(5, 7).trim());
        int hour1 = Integer.parseInt(timeStamp1.substring(17, 19)), hour2 = Integer.parseInt(timeStamp2.substring(17, 19));

        int min1 = Integer.parseInt(timeStamp1.substring(20, 22).trim()), min2 = Integer.parseInt(timeStamp2.substring(20, 22).trim());

        if (year1 < year2) {

            //timestamp2 is greater
            return -1;
        } else if (year1 == year2) {

            if (month1 > month2) {

                //timestamp1 is greater
                return 1;

            } else if (month1 == month2) {

                if (date1 > date2) {

                    //timestamp1 is greater
                    return 1;
                } else if (date1 == date2) {

                    if (hour1 > hour2) {

                        return 1;

                    } else if (hour1 == hour2) {

                        if (min1 > min2) {
                            return 1;

                        } else if (min1 == min2) {
                            return 0; //both dates are equal
                        } else {
                            return -1;
                        }

                    } else {
                        return -1;

                    }


                } else {

                    //timestamp2 is greater
                    return -1;
                }

            } else {

                //timestamp2 is greater
                return -1;
            }


        } else {

            //1 means timestamp1 is greater
            return 1;

        }

    }

    private void onListItemSelect(int position) {

        clipboardAdapter.toggleSelection(position);

        boolean hasCheckedItems = clipboardAdapter.getSelectedCount() > 0; //check if any items are already selected

        if (hasCheckedItems && mActionMode == null) {

            //there are some items selected start the action mode
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(new ToolbarCallbackActionModeClipboard((MainActivity) getActivity(), clipboardAdapter, list, this));


        } else if (!hasCheckedItems && mActionMode != null) {
            //there are no items selected finish the action mode
            mActionMode.finish();
        }
        if (mActionMode != null) {

            mActionMode.setTitle(String.valueOf(clipboardAdapter.getSelectedCount()) + " selected");

        }
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
        final SparseBooleanArray selected = clipboardAdapter.getmSelectedItems();

        //loop through selected items
        for (int i = (selected.size() - 1); i >= 0; i--) {

            final int key = selected.keyAt(i);

            if (selected.valueAt(i)) {
                //If the current id is selected remove the item via key
                //deleteFromNoteRealm(selected.keyAt(i));

                final ClipboardPOJO note = list.get(key);

                final RealmResults result = realm.where(NotesPOJO.class).equalTo("index", key).findAll();

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        //Log.d(TAG, "execute: "+result.size());

                        //list.remove(key);
                        //clipboardAdapter.notifyDataSetChanged();
                        note.deleteFromRealm();
                        //Log.d(TAG, "execute: SIZE SIZE SIZE" + list.size());


                        //result.deleteFromRealm(0);;
                    }
                });

                //
                //
            }
        }

        Snackbar snackbar = Snackbar.make(rvClipoard, selected.size() + " Notes deleted", Snackbar.LENGTH_SHORT);
        snackbar.show();
        mActionMode.finish();

    }

}
