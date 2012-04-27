package org.weloveiran.ui;

import org.weloveiran.R;
import org.weloveiran.tasks.UploadTask;
import org.weloveiran.ui.listeners.OnClickCapturePhotoButton;
import org.weloveiran.ui.listeners.OnClickChoosePhotoButton;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

public class HomeActivity extends Activity {

	public static final int CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static final int SELECT_IMAGE_REQUEST_CODE = 99;

	private ProgressDialog dialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.ui_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

		findViewById(R.id.btnTakePhoto).setOnClickListener(
				new OnClickCapturePhotoButton(this, CAPTURE_IMAGE_REQUEST_CODE));
		findViewById(R.id.btnChoosePhoto).setOnClickListener(
				new OnClickChoosePhotoButton(this, SELECT_IMAGE_REQUEST_CODE));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			Uri selectedImageUri = data.getData();
			String filePath = null;

			try {
				String filemanagerstring = selectedImageUri.getPath();
				String selectedImagePath = getPath(selectedImageUri);

				if (selectedImagePath != null) {
					filePath = selectedImagePath;
				} else if (filemanagerstring != null) {
					filePath = filemanagerstring;
				} else {
					Toast.makeText(getApplicationContext(), "Unknown path", Toast.LENGTH_LONG).show();
				}

				dialog = ProgressDialog.show(HomeActivity.this, "Uploading", "Please wait...", true);
				new UploadTask().execute(dialog, filePath);

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Internal error", Toast.LENGTH_LONG).show();
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}
		}
	}

	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else {
			return null;
		}
	}
}