package com.kaihg.comparedemo.reader;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.kaihg.comparedemo.MachineModel;

public class TsCommaReader implements MachineModelReader {
	@Override
	public MachineModel convertToModel(String sourceTxt) {
		String[] split = sourceTxt.split(",");
		MachineModel model = new MachineModel();
		model.countryCode = Integer.parseInt(split[6]);
		model.localCode = Integer.parseInt(split[7]);
		model.descSimple = split[3];
		model.descFull = split[4];
//		model.sourceSplits = split;

		return model;
	}

	@Override
	public Charset getEncoding() {
		return StandardCharsets.UTF_8;
	}
}
