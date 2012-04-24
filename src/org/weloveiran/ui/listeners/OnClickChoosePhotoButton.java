package org.weloveiran.ui.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class OnClickChoosePhotoButton implements OnClickListener {

	private final int selectImageRequestCode;
	private final Activity source;

	public OnClickChoosePhotoButton(Activity source, int selectImageRequestCode) {
		this.source = source;
		this.selectImageRequestCode = selectImageRequestCode;
	}
	
	public void onClick(View v) {
		Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        pickPhoto.setType("image/*");
        source.startActivityForResult(pickPhoto, selectImageRequestCode);
	}
}
