package com.example.vehicleapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragmentUser extends Fragment {

    ImageView imageView;
    FloatingActionButton floatingActionButton;
    Uri uri = null;

    public ProfileFragmentUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);

        imageView = view.findViewById(R.id.image_view);
        floatingActionButton = view.findViewById(R.id.floating_action);

        floatingActionButton.setOnClickListener(v -> ImagePicker.with(ProfileFragmentUser.this).crop().compress(1024).maxResultSize(1080, 1080).start());

        imageView.setOnClickListener(v -> {
            // Create a dialog to show the enlarged image
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            // Create a parent layout to hold the ImageView
            LinearLayout parentLayout = new LinearLayout(getContext());
            parentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            parentLayout.setGravity(Gravity.CENTER);

            // Create the ImageView and set its image resource or URI
            ImageView enlargedImageView = new ImageView(getContext());
            enlargedImageView.setLayoutParams(new LinearLayout.LayoutParams(
                    1000, // Set width in pixels
                    1000  // Set height in pixels
            ));
            if (uri == null) {
                enlargedImageView.setImageResource(R.drawable.person1);
                //imageView.setImageResource(R.drawable.person1);
            } else {
                enlargedImageView.setImageURI(uri);
            }

            // Add the ImageView to the parent layout
            parentLayout.addView(enlargedImageView);

            // Set the parent layout as the view for the dialog
            builder.setView(parentLayout);

            AlertDialog dialog = builder.create();
            dialog.show();

        });
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uri = data.getData();
        if (uri!=null) {
            imageView.setImageURI(uri);
        }
    }
}