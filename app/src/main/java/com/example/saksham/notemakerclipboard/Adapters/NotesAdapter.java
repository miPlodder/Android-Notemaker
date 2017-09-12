package com.example.saksham.notemakerclipboard.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.saksham.notemakerclipboard.Model.NotesPOJO;
import com.example.saksham.notemakerclipboard.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by saksham on 9/13/2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    ArrayList<NotesPOJO> list;
    Context context;

    public NotesAdapter(ArrayList<NotesPOJO> list, Context context) {

        this.list = list;
        this.context = context;
    }


    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return new NotesViewHolder(inflater.inflate(R.layout.item_notes_recycler_view, parent, false));
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {

        holder.tvNotes.setText(list.get(position).getText());
        holder.tvTimeStamp.setText(list.get(position).getTimeStamp());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //this class is used to assign reference to variables
    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView tvNotes, tvTimeStamp;

        public NotesViewHolder(View itemView) {
            super(itemView);

            tvNotes = (TextView) itemView.findViewById(R.id.tvNotes);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tvTimeStamp);

        }
    }
}

