package org.weloveiran.ui;

import org.weloveiran.R;
import org.weloveiran.ui.listeners.OnClickCapturePhotoButton;
import org.weloveiran.ui.listeners.OnClickChoosePhotoButton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class HomeActivity extends Activity {

	public static final int CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static final int SELECT_IMAGE_REQUEST_CODE = 99;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.ui_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);


		findViewById(R.id.btnTakePhoto).setOnClickListener(new OnClickCapturePhotoButton(this, CAPTURE_IMAGE_REQUEST_CODE));
		findViewById(R.id.btnChoosePhoto).setOnClickListener(new OnClickChoosePhotoButton(this, SELECT_IMAGE_REQUEST_CODE));
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
		case SELECT_IMAGE_REQUEST_CODE:
			Log.d("Selected Image Path", data.getData().getPath());
			break;
			
		default:
			break;
		}
	}
}