package com.kaihg.comparedemo.reader;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.SpinnerDateModel;

import com.kaihg.comparedemo.MachineModel;

public class CustomReader implements MachineModelReader {

	ReaderParam mParam;

	public CustomReader(ReaderParam param) {
		mParam = param;
	}

	@Override
	public MachineModel convertToModel(String sourceTxt) {
		String[] split = sourceTxt.split("\t(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
		MachineModel model = new MachineModel();
		
		model.countryCode = Integer.parseInt(split[mParam.getMccIndex()]);
		model.localCode = Integer.parseInt(split[mParam.getMncIndex()]);
		if (mParam.getAbreIndex() < split.length && mParam.getAbreIndex() != -1) {
			model.descSimple = split[mParam.getAbreIndex()];
		}
		if (mParam.getPpciIndex() < split.length && mParam.getPpciIndex() != -1) {
			model.descFull = split[mParam.getPpciIndex()];
		}

		model.sourceSplits = new ArrayList<>(Arrays.asList(split));

		return model;
	}

	@Override
	public Charset getEncoding() {
		return StandardCharsets.UTF_8;
	}

}
