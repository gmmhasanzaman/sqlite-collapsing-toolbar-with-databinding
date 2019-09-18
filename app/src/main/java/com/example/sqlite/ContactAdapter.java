package com.example.sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.sqlite.databinding.ContactItemBinding;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private Context context;
    private List<Contact> contactList;


    public ContactAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactItemBinding binding;
        LayoutInflater inflater = LayoutInflater.from(context);

        binding = DataBindingUtil.inflate(inflater,R.layout.contact_item,parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Contact contact = contactList.get(position);
        holder.binding.nameTV.setText(contact.getName());
        holder.binding.phoneTV.setText(contact.getPhone());

        String firstLetter = String.valueOf(contact.getName().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(position);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter,color);

        holder.binding.contactIV.setImageDrawable(drawable);



    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ContactItemBinding binding;
        public ViewHolder(@NonNull ContactItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
