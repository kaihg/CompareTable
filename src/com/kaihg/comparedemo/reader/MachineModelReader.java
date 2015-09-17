package com.kaihg.comparedemo.reader;

import java.nio.charset.Charset;

import com.kaihg.comparedemo.MachineModel;

public interface MachineModelReader {

	public MachineModel convertToModel(String sourceTxt);
	public Charset getEncoding();
}
