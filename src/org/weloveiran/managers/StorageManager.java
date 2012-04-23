package org.weloveiran.managers;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.weloveiran.enums.MediaType;
import org.weloveiran.exceptions.MediaStorageException;
import org.weloveiran.exceptions.NoMediaMountedException;

import android.os.Environment;
import android.util.Log;

public class StorageManager {

	public File getOutputMediaFile(MediaType mediaType) throws NoMediaMountedException, MediaStorageException {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return createFile(mediaType);
		}
		
		throw new NoMediaMountedException();
	}

	public File createFile(MediaType mediaType) throws MediaStorageException {
		File mediaStorageDir = getAppStorageDir();
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());		
		File mediaFile = new File(mediaStorageDir.getPath() + File.separator + mediaType.getPrefix() + timeStamp + mediaType.getFileExtension());

		return mediaFile;
	}
	
	public File getAppStorageDir() throws MediaStorageException {
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "WeLoveIran");
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("WeLoveIran", "failed to create directory");
				throw new MediaStorageException("Could not create app storage folder");
			}
		}
		return mediaStorageDir;
	}
}
