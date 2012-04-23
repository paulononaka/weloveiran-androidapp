package org.weloveiran.ui;

import org.weloveiran.R;
import org.weloveiran.enums.MediaType;
import org.weloveiran.exceptions.MediaStorageException;
import org.weloveiran.exceptions.NoMediaMountedException;
import org.weloveiran.managers.StorageManager;

import roboguice.activity.RoboActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

import com.google.inject.Inject;

public class HomeActivity extends RoboActivity {

	public static final int CAPTURE_IMAGE_REQUEST_CODE = 100;
	
	@Inject
	private StorageManager storageManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.ui_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

		bindOnClickListerToTakePhotoButton();
		bindOnClickListerToChoosePhotoButton();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == Activity.RESULT_OK) {
			handleResult(requestCode, data);
		}
	}
	
	private void handleResult(int requestCode, Intent data) {
		switch (requestCode) {
		case CAPTURE_IMAGE_REQUEST_CODE:
			Log.d("Captured Image Path", data.getData().getPath());
			break;

		default:
			break;
		}
	}

	private void bindOnClickListerToChoosePhotoButton() {
		findViewById(R.id.btnChoosePhoto).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(HomeActivity.this, "Nothing yet! :)", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void bindOnClickListerToTakePhotoButton() {
		findViewById(R.id.btnTakePhoto).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				try {
					Uri fileUri = Uri.fromFile(storageManager.getOutputMediaFile(MediaType.Image));
					Log.d("Storage Dir", fileUri.getPath());
					//intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

				} catch (NoMediaMountedException e) {
					Log.d("Media storage not mounted", "Could not save photo in a external media storage. No media storage was found.");
				} catch (MediaStorageException e) {
					Log.d("Media storage exception", "Could not save photo in a external media storage. Could not create app storage directory.");
				}
				startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
			}
		});
	}
}