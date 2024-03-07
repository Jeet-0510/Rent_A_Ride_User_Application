package com.example.vehicleapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class CarDetail extends AppCompatActivity {

    ImageView Image;
    Button saveButton;
    EditText name,desc,location,Ruppes;
    String url;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        Image=findViewById(R.id.car_image);
        saveButton=findViewById(R.id.car_save);
        name=findViewById(R.id.car_name);
        desc=findViewById(R.id.car_desc);
        location=findViewById(R.id.car_location);
        Ruppes=findViewById(R.id.car_rupees);

        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK)
                        {
                            Intent data=result.getData();
                            uri=data.getData();
                            Image.setImageURI(uri);
                        }
                        else
                        {
                            Toast.makeText(CarDetail.this,"No Image Selected",Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGallary =new Intent(Intent.ACTION_PICK);
                iGallary.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                iGallary.setType("image/*");
                activityResultLauncher.launch(iGallary);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savedata();
            }
        });
    }
    public void savedata()
    {
        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());
        AlertDialog.Builder builder= new AlertDialog.Builder(CarDetail.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage=uriTask.getResult();
                url=urlImage.toString();
                uploadData();
                dialog.dismiss();
                Intent intent=new Intent(CarDetail.this,CarAdmin.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });

    }
    public void uploadData()
    {
        String Cname=name.getText().toString();
        String Cdesc=desc.getText().toString();
        String CLocation=location.getText().toString();
        String Cruppes=Ruppes.getText().toString();
        // String image= url;

        // Dataclass dataclass =new Dataclass(Topic,Desc,Lang,url);
        CarData carData=new CarData(Cname,Cdesc,CLocation,Cruppes,url);
        String currdate= DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("CAR").child(currdate)
                .setValue(carData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(CarDetail.this,"Saved Successful",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CarDetail.this,e.getMessage().toString(),Toast.LENGTH_LONG).show();

                    }
                });
    }
}
