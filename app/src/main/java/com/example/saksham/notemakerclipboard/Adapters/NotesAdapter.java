package com.example.saksham.notemakerclipboard.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import com.example.saksham.notemakerclipboard.Model.NotesPOJO;
import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.Views.Activity.EditNoteActivity;
import com.example.saksham.notemakerclipboard.Views.Activity.MainActivity;
import com.example.saksham.notemakerclipboard.utils.Constant;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by saksham on 9/13/2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {


    public static final String TAG = "NotesAdapter";
    private ArrayList<NotesPOJO> list;
    private Context context;
    private OnEdit onEdit;
    private MainActivity mainActivity;
    private SparseBooleanArray mSelectedItems;

    public interface OnEdit {

        void doAfterOnEdit(int position, String note);
    }

    public NotesAdapter(ArrayList<NotesPOJO> list, Context context, OnEdit onEdit) {

        this.list = list;
        this.context = context;
        this.onEdit = onEdit;

        //for long click action_mode purpose
        this.mainActivity = (MainActivity) context;
        mSelectedItems = new SparseBooleanArray();
    }


    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return new NotesViewHolder(inflater.inflate(R.layout.item_notes_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final NotesViewHolder holder, final int position) {

        holder.tvNotes.setText(list.get(position).getText());
        holder.tvTimeStamp.setText(list.get(position).getTimeStamp());

        holder.cvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, "inside REAL", Toast.LENGTH_SHORT).show();


            }
        });


        /*holder.cvNote.setElevation();*/

        holder.itemView.setBackgroundColor(mSelectedItems.get(position)? context.getResources().getColor(R.color.colorPrimary): Color.TRANSPARENT);


    }

    //this method is used to add only one on click listener
    public void setOnClickListener(int position){

        Toast.makeText(context, "inside adapter", Toast.LENGTH_SHORT).show();
        onEdit.doAfterOnEdit(
                position,
                list.get(position).getText()
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //this class is used to assign reference to variables
    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView tvNotes, tvTimeStamp;
        CardView cvNote;
        LinearLayout llRv;

        public NotesViewHolder(View itemView) {
            super(itemView);

            tvNotes = (TextView) itemView.findViewById(R.id.tvNotes);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            cvNote = (CardView) itemView.findViewById(R.id.cvNote);
            llRv = (LinearLayout) itemView.findViewById(R.id.llRv);

        }

    }

    //methods required to do selections in android
    //toggle state from selected or deselected
    public void toggleSelection(int position) {
        selectView(position, !mSelectedItems.get(position));
    }

    //remove selected items
    public void removeSelection() {
        mSelectedItems = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void deleteItems(){

    }
    //
    public void selectView(int position, boolean value) {

        if (value) {
            mSelectedItems.put(position, value);

            Log.d(TAG, "addingView: "+mSelectedItems.get(position));
        }
        else {

            Log.d(TAG, "deletingView: "+mSelectedItems.get(position));
            mSelectedItems.delete(position);
        }
        notifyDataSetChanged();
    }

    //get total selected items
    public int getSelectedCount() {
        return mSelectedItems.size();
    }

    //return all selected ids
    public SparseBooleanArray getmSelectedItems() {
        Log.d(TAG, "getmSelectedItems: "+mSelectedItems);
        return mSelectedItems;
    }


}