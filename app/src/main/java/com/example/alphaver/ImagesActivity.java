package com.example.alphaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ImagesActivity extends AppCompatActivity {


    ListView lv;
    FirebaseListAdapter adapter;
    DatabaseReference ref;

    String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        lv = (ListView)findViewById(R.id.lv);
        Query query = FirebaseDatabase.getInstance().getReference().child("Uploads");
        FirebaseListOptions<Upload> options  = new FirebaseListOptions.Builder<Upload>()
                .setLayout(R.layout.information)
                .setLifecycleOwner(ImagesActivity.this)
                .setQuery(query, Upload.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {

                TextView name = v.findViewById(R.id.name_tv);
                ImageView imageView = v.findViewById(R.id.imageViewTV);

                Upload std = (Upload) model;
                name.setText(std.getName());

                if(std.getmImageUrl() != null && Uri.parse(std.getmImageUrl()) != null){
                    Picasso.get().load(Uri.parse(std.getmImageUrl())).into(imageView);
                }else{
                    Picasso.get().load(R.drawable.profile_image).into(imageView);
                }



            }
        };
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(ImagesActivity.this, "TEST onClick works", Toast.LENGTH_SHORT).show();

                Upload s = (Upload) parent.getItemAtPosition(position);

                AlertDialog.Builder adb;
                adb = new AlertDialog.Builder(ImagesActivity.this);

                ref = FirebaseDatabase.getInstance().getReference().child("Uploads").child(s.getName()).child("mImageUrl");

                adb.setTitle("Test");
                adb.setMessage("Delete?");
                adb.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ImagesActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ImagesActivity.this, "Failed To Delete!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog ad = adb.create();
                ad.show();

            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }
}
