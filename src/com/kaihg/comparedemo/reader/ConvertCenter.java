package com.kaihg.comparedemo.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.kaihg.comparedemo.MachineModel;

public class ConvertCenter {

	private MachineModelReader modelReader;
	private ReaderParam mParam;
	
	public ConvertCenter() {
		super();
	}

	public void init(ReaderParam param) {
		modelReader = new CustomReader(param);
		mParam = param;
	}

	public List<MachineModel> generatorMachineModels(File file)
			throws IOException {
		return readLargerTextFile(file.getAbsolutePath());
	}

	List<MachineModel> readLargerTextFile(String aFileName) throws IOException {
		List<MachineModel> modelList = new ArrayList<>();
		Path path = Paths.get(aFileName);
		try (Scanner scanner = new Scanner(path, modelReader.getEncoding()
				.name())) {
			if (mParam.isHasHeader() && scanner.hasNextLine()) {	// 跳過開頭的文字
				scanner.nextLine();
			}
			
			while (scanner.hasNextLine()) {
				modelList.add(modelReader.convertToModel(scanner.nextLine()));
			}
		}

		return modelList;
	}

}
