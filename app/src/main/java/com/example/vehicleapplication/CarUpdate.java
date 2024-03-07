package com.example.vehicleapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CarUpdate extends AppCompatActivity {
    ImageView updateCarImage;
    Button updateCarButton;
    EditText updateCarDesc, updateCarName, updateCarLocation,updateCarPrice;
    String CarName,CarDesc,CarLocation,CarPrice;
    String imageUrl;
    String CarKey, oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_update);
        updateCarImage=findViewById(R.id.carupdate_image);
        updateCarButton=findViewById(R.id.carupdate_btn);
        updateCarDesc=findViewById(R.id.carupdate_desc);
        updateCarName=findViewById(R.id.carupdate_name);
        updateCarLocation=findViewById(R.id.carupdate_location);
        updateCarPrice=findViewById(R.id.carupdate_rupees);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            updateCarImage.setImageURI(uri);
                        } else {
                            Toast.makeText(CarUpdate.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            Glide.with(this).load(bundle.getString("Car_Image")).into(updateCarImage);
            updateCarName.setText(bundle.getString("Car_Name"));
            updateCarDesc.setText(bundle.getString("Car_Desc"));
            updateCarLocation.setText(bundle.getString("Car_Location"));
            updateCarPrice.setText(bundle.getString("Car_Price"));
            CarKey=bundle.getString("Car_Key");
            oldImageURL=bundle.getString("Car_Image");
            // carView_name.setText(bundle.getString("Car_Name"));

        }
        databaseReference = FirebaseDatabase.getInstance().getReference("CAR").child(CarKey);
        updateCarImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        updateCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(CarUpdate.this, CarAdmin.class);
                startActivity(intent);
            }
        });
    }
    public void saveData(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Android Images").child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(CarUpdate.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void updateData(){
        CarName=updateCarName.getText().toString().trim();
        CarDesc=updateCarDesc.getText().toString().trim();
        CarPrice=updateCarPrice.getText().toString().trim();
        CarLocation=updateCarLocation.getText().toString().trim();
        CarData dataClass = new CarData(CarName,CarDesc,CarLocation,CarPrice, imageUrl);
        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                    reference.delete();
                    Toast.makeText(CarUpdate.this, "Updated", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CarUpdate.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
