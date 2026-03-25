package com.example.projectihm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectihm.users.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UsersRecyclerViewAdapter extends RecyclerView.Adapter<UsersRecyclerViewAdapter.UsersViewHolder> {
    private List<Users> usersList;

    public UsersRecyclerViewAdapter(List<Users> usersList) {
        this.usersList = new ArrayList<>(usersList);
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_user_row, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        Users p  = usersList.get(position);
        holder.updateData(p);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        private TextView lblUID;
        private TextView lblFirstname;
        private TextView lblLastname;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            lblUID = itemView.findViewById(R.id.lblUserId);
            lblFirstname = itemView.findViewById(R.id.editItemFirstname);
            lblLastname = itemView.findViewById(R.id.editItemLastname);
        }

        public void updateData(Users p) {
            lblUID.setText(String.format(Locale.getDefault(),"%d",p.getUid()));
            lblFirstname.setText(p.getFirstName());
            lblLastname.setText(p.getLastName());
        }
    }

}
