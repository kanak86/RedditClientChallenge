package reddit.challenge.client.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import reddit.challenge.client.R;

/**
 * Created by apoorvakanaksiwach on 2/4/18.
 */

public class ImageDialogFragment extends DialogFragment implements View.OnClickListener{

    ImageView fullSizeImage;
    Button saveButton;
    int counter = 100;
    private static final int WRITE_PERMISSION_REQUEST_CODE = 1;

    public static ImageDialogFragment newInstance(String title, String URL) {
        ImageDialogFragment frag = new ImageDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("URL", URL);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.image_dialog_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        String title = getArguments().getString("title", "title");
        getDialog().setTitle(title);
        String URL = getArguments().getString("URL", "No Image");
        fullSizeImage = view.findViewById(R.id.full_image);
        saveButton = view.findViewById(R.id.save_image);
        saveButton.setOnClickListener(this);

        Glide.with(getActivity()).load(URL).into(fullSizeImage);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onClick(View view) {
        askPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_PERMISSION_REQUEST_CODE);
    }

    private void askPermission(String permission, int requestCode) {
        if(ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {permission}, requestCode);
        } else {
            saveImage();
        }
    }

    private void saveImage() {
        Bitmap bitmap = ((BitmapDrawable) fullSizeImage.getDrawable()).getBitmap();
        File filePath = Environment.getExternalStorageDirectory();

        File dir = new File(filePath.getAbsolutePath()+"/RingCodeChallenge/");
        dir.mkdirs();

        File file = new File(dir, "ring_code_"+counter);
        counter++;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        try {
            OutputStream output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();
            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_LONG).show();
        } catch(FileNotFoundException fileNotFound) {
            Log.e("Kanak FOF Exception", "Kanak FOF Exception");
        } catch (IOException io) {
            Log.e("Kanak io Exception", "Kanak io Exception");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_PERMISSION_REQUEST_CODE :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                }
                break;
        }
    }
}
