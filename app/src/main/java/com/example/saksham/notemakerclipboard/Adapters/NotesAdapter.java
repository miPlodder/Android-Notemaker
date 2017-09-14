package com.example.saksham.notemakerclipboard.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;

import com.example.saksham.notemakerclipboard.Model.NotesPOJO;
import com.example.saksham.notemakerclipboard.R;
import com.example.saksham.notemakerclipboard.Views.Activity.EditNoteActivity;
import com.example.saksham.notemakerclipboard.utils.Constant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by saksham on 9/13/2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private ArrayList<NotesPOJO> list;
    private Context context;
    private OnEdit onEdit;

    public interface OnEdit {

        void doAfterOnEdit(int position, String note);
    }

    public NotesAdapter(ArrayList<NotesPOJO> list, Context context, OnEdit onEdit) {

        this.list = list;
        this.context = context;
        this.onEdit = onEdit;
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

                onEdit.doAfterOnEdit(
                        position,
                        holder.tvNotes.getText().toString()
                );

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //this class is used to assign reference to variables
    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView tvNotes, tvTimeStamp;
        CardView cvNote;

        public NotesViewHolder(View itemView) {
            super(itemView);

            tvNotes = (TextView) itemView.findViewById(R.id.tvNotes);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            cvNote = (CardView) itemView.findViewById(R.id.cvNote);

        }
    }
}

