package com.teamx.zapmeal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Welcome extends AppCompatActivity {
    private RecyclerView recyclerView;
    GuestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        recyclerView = findViewById(R.id.rv_welcome);

        String[] prices = {"800", "1200", "800", "1200", "1250", "500", "1200", "1000", "600", "800", "1000", "500", "800", "1000"};
        String[] names = {"Amala and Ewedu", "Fried Rice and Grilled Fish", "Fufu and Vegetables", "Jollof Rice and Beef",
                "Jollof Rice and Chicken", "Moimoi", "Noodels and Plantain", "Pounded Yam and Vegetables",
                "Plantain and Eggs", "Rice and Grilled Fish", "Suya", "Yam Porridge", "Rice and Stew", "Fried Rice"};
        int[] flags = {R.drawable.amalaandewedu, R.drawable.friceandfish, R.drawable.fufuandveg, R.drawable.jollofrice, R.drawable.jf,
                R.drawable.moimoi, R.drawable.noodelsandplantain, R.drawable.p_yamandveg, R.drawable.plantainandegg, R.drawable.riceandfish,
                R.drawable.suya, R.drawable.yamporridge, R.drawable.riceandstew, R.drawable.download};
        adapter = new GuestAdapter(getApplicationContext(), flags, names, prices);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }
}