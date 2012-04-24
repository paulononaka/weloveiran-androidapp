package org.weloveiran.ui.listeners;

import org.weloveiran.enums.MediaType;
import org.weloveiran.exceptions.MediaStorageException;
import org.weloveiran.exceptions.NoMediaMountedException;
import org.weloveiran.managers.StorageManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class OnClickCapturePhotoButton implements OnClickListener {

	private final Activity source;
	private final int capturePhotoRequestCode;
	private final StorageManager storageManager;

	public OnClickCapturePhotoButton(Activity source, int capturePhotoRequestCode) {
		this.source = source;
		this.capturePhotoRequestCode = capturePhotoRequestCode;
		this.storageManager = new StorageManager(); // could be injected for test purposes
	}
	
	public void onClick(View v) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		try {
			Uri fileUri = Uri.fromFile(storageManager.getOutputMediaFile(MediaType.Image));
			Log.d("Storage Dir", fileUri.getPath());
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		} catch (NoMediaMountedException e) {
			Log.d("Media storage not mounted", "Could not save photo in a external media storage. No media storage was found.");
		} catch (MediaStorageException e) {
			Log.d("Media storage exception", "Could not save photo in a external media storage. Could not create app storage directory.");
		}
		source.startActivityForResult(intent, capturePhotoRequestCode);
	}
}
