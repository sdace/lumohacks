package com.helladank.lumohacks;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CameraActivity extends AppCompatActivity {
    //public class MainActivity extends ActionBarActivity {

    public static final int REQUEST_CAPTURE = 1;
    ImageView result_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Button click = (Button)findViewById(R.id.Bcapture);
        result_photo = (ImageView)findViewById(R.id.imageView8);
        //maybe imagineview4 is imageview

        //checks to see if a camera even exists on the phone
        if(!hasCamera())
        {
            click.setEnabled(false);
        }
    }

    //boolean functions will only return true or false
    public boolean hasCamera()
    {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
        //if you just want front or back camera u just change the "any" part
    }

    //Actually launch the camera
    public void launchCamera(View v){
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_CAPTURE);
    }

    //gives info on image you just took
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK) {


            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            result_photo.setImageBitmap(photo);
        }
    }




}
