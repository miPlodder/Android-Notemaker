package com.example.saksham.notemakerclipboard.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
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

    public interface OnEdit {

        void setOnEditComplete(String text, int position);
    }

    public ClipboardAdapter(Context context, ArrayList<ClipboardPOJO> clipboardList, OnEdit onEdit) {

        this.context = context;
        this.clipboardList = clipboardList;
        this.onEdit = onEdit;
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

        holder.cvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onEdit.setOnEditComplete(clipboardList.get(position).getText(),
                        position);

            }
        });

    }

    @Override
    public int getItemCount() {

        return clipboardList.size();
    }

    static class ClipboardViewHolder extends RecyclerView.ViewHolder {

        TextView tvNotes, tvTimestamp;
        CardView cvNote;

        public ClipboardViewHolder(View itemView) {

            super(itemView);

            tvNotes = (TextView) itemView.findViewById(R.id.tvNotes);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);
            cvNote = (CardView) itemView.findViewById(R.id.cvNote);

        }
    }
}

