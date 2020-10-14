package com.teamx.zapmeal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Welcome extends AppCompatActivity {
    ImageView imageView;
    EditText editText, editText1;
    Button button;
    UserDB userDB;
    private Bitmap imageToStore;
    private static final int PICK_IMAGE_REQUEST = 100;
    private Uri imageFilePath;
    ModelClass modelClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        editText = findViewById(R.id.editTextTextPersonName);
        editText1 = findViewById(R.id.editTextTextPersonName2);
        button = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView4);
        userDB = new UserDB(this);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent objectIntent = new Intent();
                    objectIntent.setType("image/*");

                    objectIntent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(objectIntent, PICK_IMAGE_REQUEST);
                } catch (Exception e) {
                    Toast.makeText(Welcome.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   modelClass = new ModelClass(editText.getText().toString(), editText1.getText().toString(),  editText1.getText().toString());
             //   userDB.putData(modelClass);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imageFilePath = data.getData();
            //imageToStore = (Bitmap) data.getExtras().get("data"); data.getData();
            try {
                imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);
                imageView.setImageBitmap(imageToStore);
            } catch (IOException e) {
                Toast.makeText(Welcome.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }
    }
}