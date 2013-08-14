package com.peshchuk.fias.downloader.struct;

/**
 * @author Ruslan Peshchuk (peshrus@gmail.com)
 */
public class FiasInfo {
	private final int version;
	private final String versionDescription;
	private final String fiasRarUrl;

	public FiasInfo(int version, String versionDescription, String fiasRarUrl) {
		this.version = version;
		this.versionDescription = versionDescription;
		this.fiasRarUrl = fiasRarUrl;
	}

	public int getVersion() {
		return version;
	}

	public String getVersionDescription() {
		return versionDescription;
	}

	public String getFiasRarUrl() {
		return fiasRarUrl;
	}
}