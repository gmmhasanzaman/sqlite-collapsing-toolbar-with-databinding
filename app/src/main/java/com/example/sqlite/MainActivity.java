package com.example.sqlite;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlite.databinding.ActivityMainBinding;
import com.example.sqlite.databinding.AddContactLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AlertDialog alertDialog;
    private DatabaseHelper helper;
    private AddContactLayoutBinding layoutBinding;
    private ContactAdapter adapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.toolbar.setTitle("Contact");


        init();

        getContact();

        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);*/
    }



    private void init() {

        helper = new DatabaseHelper(MainActivity.this);

        contactList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactAdapter(this,contactList);
        binding.recyclerView.setAdapter(adapter);


        binding.addContactFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBox();
            }
        });
    }

    private void getContact() {

        contactList.clear();
        Cursor cursor = helper.getContact();
        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex(helper.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(helper.COL_NAME));
            String phone = cursor.getString(cursor.getColumnIndex(helper.COL_PHONE));

            contactList.add(new Contact(id,name,phone));
            adapter.notifyDataSetChanged();

        }
    }

    private void dialogBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        layoutBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.add_contact_layout, null, false);

        layoutBinding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact();
            }
        });
        layoutBinding.CancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //builder.alertDialog.dismiss();
                alertDialog.dismiss();
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });


        builder.setView(layoutBinding.getRoot());
        alertDialog = builder.create();
        alertDialog = builder.show();
    }

    private void addContact() {
        //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
        String name = layoutBinding.nameET.getText().toString().trim();
        String phone = layoutBinding.phoneET.getText().toString().trim();
        String email = layoutBinding.emailET.getText().toString().trim();

        long id = helper.insertData(name,phone,email);
        getContact();
        alertDialog.dismiss();
        Toast.makeText(this, "id = "+id, Toast.LENGTH_SHORT).show();
    }

}
