package com.kaihg.comparedemo.reader;

public class ReaderParam {

	private boolean hasHeader;
	private String fileName;
	private int mccIndex ;
	private int mncIndex ;
	private int ppciIndex;
	private int abreIndex;

	public boolean isHasHeader() {
		return hasHeader;
	}

	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	public int getMccIndex() {
		return mccIndex;
	}

	public void setMccIndex(int mccIndex) {
		this.mccIndex = mccIndex;
	}

	public int getMncIndex() {
		return mncIndex;
	}

	public void setMncIndex(int mncIndex) {
		this.mncIndex = mncIndex;
	}

	public int getPpciIndex() {
		return ppciIndex;
	}

	public void setPpciIndex(int ppciIndex) {
		this.ppciIndex = ppciIndex;
	}

	public int getAbreIndex() {
		return abreIndex;
	}

	public void setAbreIndex(int abreIndex) {
		this.abreIndex = abreIndex;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "ReaderParam [hasHeader=" + hasHeader + ", fileName=" + fileName
				+ ", mccIndex=" + mccIndex + ", mncIndex=" + mncIndex
				+ ", ppciIndex=" + ppciIndex + ", abreIndex=" + abreIndex + "]";
	}

}
