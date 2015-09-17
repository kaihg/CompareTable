package com.kaihg.comparedemo;

import java.nio.file.attribute.AclEntry.Builder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MachineModel {

	public int countryCode;
	public int localCode;
	public String descSimple;
	public String descFull;
	public List<String> sourceSplits;

	public MachineModel() {
		super();
	}

	public MachineModel(int countryCode, int localCode, String descSimple,
			String descFull) {
		super();
		this.countryCode = countryCode;
		this.localCode = localCode;
		this.descSimple = descSimple;
		this.descFull = descFull;
	}

	@Override
	public String toString() {
		return "MachineModel [countryCode=" + countryCode + ", LocalCode="
				+ localCode + ", descSimple=" + descSimple + ", descFull="
				+ descFull + "]";
	}

	

	@Override
	public int hashCode() {
		return countryCode*100+localCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MachineModel other = (MachineModel) obj;
		if (countryCode != other.countryCode)
			return false;
		if (localCode != other.localCode)
			return false;
		return true;
	}

	public String toFullString() {
		StringBuilder builder = new StringBuilder();

		if (sourceSplits != null) {
			for (String source : sourceSplits) {
				builder.append(source).append(",");
			}

		} else {
			builder.append(countryCode).append(",");
			builder.append(String.format("%2d", localCode)).append(",");
			builder.append(descFull).append(",");
			builder.append(descSimple);
		}

		return builder.toString();
	}
	
	public String toSimpleString(){
		StringBuilder builder = new StringBuilder();

		builder.append(countryCode).append("\t");
		builder.append(String.format("%2d", localCode)).append("\t");
		builder.append(descFull).append("\t");
		builder.append(descSimple);

		return builder.toString();
	}
}
