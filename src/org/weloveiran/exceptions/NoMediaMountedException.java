package org.weloveiran.exceptions;

public class NoMediaMountedException extends Exception {
	public NoMediaMountedException() {
		super("No media mounted. Check if your device has SD card.");
	}
}
