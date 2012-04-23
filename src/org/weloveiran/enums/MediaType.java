package org.weloveiran.enums;

public enum MediaType {
	Image("IMG_", ".jpg"), Video("VID_", ".mp4");
	
	private final String prefix;
	private final String fileExtension;

	MediaType(String prefix, String fileExtension) {
		this.prefix = prefix;
		this.fileExtension = fileExtension;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getFileExtension() {
		return fileExtension;
	}
}