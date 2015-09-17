package com.kaihg.comparedemo.reader;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import com.kaihg.comparedemo.MachineModel;

public class MachineCommaReader implements MachineModelReader{

	@Override
	public MachineModel convertToModel(String sourceTxt) {
		String[] split = sourceTxt.split(",");
		MachineModel model = new MachineModel();
		model.countryCode = Integer.parseInt(split[0]);
		model.localCode = Integer.parseInt(split[1]);
		model.descSimple = split[2];
		model.descFull = split[3];
//		model.sourceSplits=split;
		
		return model;
	}

	@Override
	public Charset getEncoding() {
		return StandardCharsets.UTF_8;
	}

}
