package com.a65apps.architecturecomponents.presentation.contacts;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a65apps.architecturecomponents.R;
import com.a65apps.architecturecomponents.domain.contacts.ContactState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    @NonNull
    private final List<ContactState> items;

    ContactsAdapter(@NonNull List<ContactState> items) {
        this.items = new ArrayList<>(items);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    void updateContacts(@NonNull List<ContactState> items) {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.name)
        TextView nameView;

        ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull ContactState item) {
            nameView.setText(item.name());
        }
    }
}
