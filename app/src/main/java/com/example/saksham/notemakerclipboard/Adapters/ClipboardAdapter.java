package com.example.saksham.notemakerclipboard.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.saksham.notemakerclipboard.Model.ClipboardPOJO;
import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.Views.Activity.UpdateClipboardNoteActivity;
import com.example.saksham.notemakerclipboard.utils.Constant;

import java.util.ArrayList;

/**
 * Created by saksham on 9/28/2017.
 */


public class ClipboardAdapter extends RecyclerView.Adapter<ClipboardAdapter.ClipboardViewHolder> {

    Context context;
    ArrayList<ClipboardPOJO> clipboardList;
    OnEdit onEdit;
    public static final String TAG = "ClipboardAdapter";
    private SparseBooleanArray mSelectedItems;

    public interface OnEdit {

        void setOnEditComplete(String text, int position);
    }

    public ClipboardAdapter(Context context, ArrayList<ClipboardPOJO> clipboardList, OnEdit onEdit) {

        this.context = context;
        this.clipboardList = clipboardList;
        this.onEdit = onEdit;
        mSelectedItems = new SparseBooleanArray();

    }

    @Override
    public ClipboardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return new ClipboardViewHolder(inflater.inflate(R.layout.item_notes_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(final ClipboardViewHolder holder, final int position) {

        holder.tvNotes.setText(clipboardList.get(position).getText());
        holder.tvTimestamp.setText(clipboardList.get(position).getTimeStamp());

        holder.itemView.setBackgroundColor(mSelectedItems.get(position) ? context.getResources().getColor(R.color.colorPrimary): Color.TRANSPARENT);

    }

    @Override
    public int getItemCount() {

        return clipboardList.size();
    }

    public void setOnClickListener(int position) {

        onEdit.setOnEditComplete(clipboardList.get(position).getText(),
                position);
    }

    /*
        below code is added for contexual action bar
     */


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

    public void deleteItems() {

    }

    //
    public void selectView(int position, boolean value) {

        if (value) {
            mSelectedItems.put(position, value);

            Log.d(TAG, "addingView: " + mSelectedItems.get(position));
        } else {

            Log.d(TAG, "deletingView: " + mSelectedItems.get(position));
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
        Log.d(TAG, "getmSelectedItems: " + mSelectedItems);
        return mSelectedItems;
    }


    static class ClipboardViewHolder extends RecyclerView.ViewHolder {

        TextView tvNotes, tvTimestamp;
        CardView cvNote;
        LinearLayout llRv;

        public ClipboardViewHolder(View itemView) {

            super(itemView);

            tvNotes = (TextView) itemView.findViewById(R.id.tvNotes);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            cvNote = (CardView) itemView.findViewById(R.id.cvNote);
            llRv = (LinearLayout) itemView.findViewById(R.id.llRv);

        }
    }
}

