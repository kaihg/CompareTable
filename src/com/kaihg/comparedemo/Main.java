package com.kaihg.comparedemo;

import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import com.kaihg.comparedemo.reader.ConvertCenter;
import com.kaihg.comparedemo.reader.ReaderParam;

public class Main {

	private final static String MAIN_TABLE = "ts25.txt";
	private final static String MACHINE_TABLE = "machine.txt";

	public static void main(String[] args) {
		Main command = new Main();
		command.init();
		command.compare2Table();
	}

	private ConvertCenter mConvertCenter;
	private List<MachineModel> tableTs25, tableMachine;

	private ReaderParam mainParam, machineParam;

	public Main() {
		createReaderParam();
		mConvertCenter = new ConvertCenter();
	}

	private void createReaderParam() {
		try {
			File file = new File("setting.txt");
			Path path = Paths.get(file.getAbsolutePath());
			Scanner scanner = new Scanner(path);
			List<String> settings = new ArrayList<>();
			while (scanner.hasNextLine()) {
				settings.add(scanner.nextLine());
			}
			mainParam = ParamParser.parseParam(settings.subList(3, 3 + 6));
			machineParam = ParamParser.parseParam(settings.subList(13, 13 + 6));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	private void init() {
		System.out.println("開始讀取 " + mainParam.getFileName() + " 跟 "
				+ machineParam.getFileName());
		File aFile = new File(mainParam.getFileName());
		try {
			mConvertCenter.init(mainParam);
			tableTs25 = mConvertCenter.generatorMachineModels(aFile);
		} catch (IOException e) {
			System.out.println("找不到 " + mainParam.getFileName());
			e.printStackTrace();
		}

		File bFile = new File(machineParam.getFileName());
		try {
			mConvertCenter.init(machineParam);
			tableMachine = mConvertCenter.generatorMachineModels(bFile);
		} catch (IOException e) {
			System.out.println("找不到 " + machineParam.getFileName());
			e.printStackTrace();
		}
	}

	private boolean hasSameDesc(MachineModel model1, MachineModel model2) {
//		if ((model1.descFull == null) ^ (model2.descFull == null)
//				|| (model1.descSimple == null) ^ (model2.descSimple == null)) {
//			return false;
//		}
//
//		return model1.descFull.equals(model2.descFull)
//				&& model1.descSimple.equals(model2.descSimple);
		boolean sameDesc = true;
		if (model1.descFull!=null && model2.descFull!=null) {
			sameDesc = model1.descFull.equals(model2.descFull);
		}
		if (model1.descSimple!=null && model2.descSimple!=null) {
			sameDesc &= model1.descSimple.equals(model2.descSimple);
		}
		
		return sameDesc;
	}

	private void compare2Table() {

		Map<Integer, MachineModel> notEqualMap = new HashMap<>();
		for (int i = 0, size = tableTs25.size(); i < size; i++) {
			MachineModel model = tableTs25.get(i);
			int index = tableMachine.indexOf(model);

			if (index != -1) {
				MachineModel machineModel = tableMachine.get(index);

				if (!hasSameDesc(model, machineModel)) {
					model.sourceSplits.add(machineModel.descFull);
					model.sourceSplits.add(machineModel.descSimple);
					notEqualMap.put(codeHashCode(model), model);
				}
				tableMachine.remove(index);
			} else {
				// System.out.println("index=-1");
				// System.out.println("model="+model.toString());
				if (!notEqualMap.containsKey(codeHashCode(model))) {
					notEqualMap.put(codeHashCode(model), model);
				}
			}

			// if (!tableMachine.remove(model)) { //
			// 移除失敗，代表table2裡面沒有model，或資料不符
			// // notEuqalList.add(model);
			// notEqualMap.put(codeHashCode(model), model);
			// }
		}
		// notEuqalList.addAll(table2); //剩下的就是table25沒有的值
		for (MachineModel machineModel : tableMachine) {
			notEqualMap.put(codeHashCode(machineModel), machineModel);
		}

		// for (MachineModel model : notEqualMap.values()) {
		// System.out.println(model.toString());
		// }

		outputCompareResult(new ArrayList<MachineModel>(notEqualMap.values()),"result");
		
		// 產生"相同"的list，將不同的從ts25裡拔掉後再輸出一次
		Map<Integer, MachineModel> equalMap = new HashMap<>();
		for (int i = 0; i < tableTs25.size(); i++) {
			MachineModel model = tableTs25.get(i);
			int code = codeHashCode(model);
			if (notEqualMap.get(code)==null) {
				equalMap.put(code, model);
			}
		}
		outputCompareResult(new ArrayList<MachineModel>(equalMap.values()),"same-result");
	}

	private void sortByCode(List<MachineModel> collections) {
		Comparator<MachineModel> comparator = new Comparator<MachineModel>() {

			@Override
			public int compare(MachineModel o1, MachineModel o2) {
				if (o1.countryCode == o2.countryCode) {
					return o1.localCode - o2.localCode;
				}
				return o1.countryCode - o2.countryCode;
			}

		};

		Collections.sort(collections, comparator);
	}

	private void outputCompareResult(List<MachineModel> modelList,String fileName) {
		sortByCode(modelList);

		File file1 = new File(fileName+"-full.txt");
		Path path1 = Paths.get(file1.getAbsolutePath());
		File file2 = new File(fileName+"-simple.txt");
		Path path2 = Paths.get(file2.getAbsolutePath());
		BufferedWriter writer = null;
		try {
			writer = Files.newBufferedWriter(path1, StandardCharsets.UTF_8);
			for (MachineModel model : modelList) {
				writer.write(model.toFullString());
				writer.newLine();
			}
			
			writer = Files.newBufferedWriter(path2, StandardCharsets.UTF_8);
			for (MachineModel model : modelList) {
				writer.write(model.toSimpleString());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("比對完成，共有" + modelList.size() + "筆資料");
	}

	private int codeHashCode(MachineModel model) {
		final int prime = 31;
		int result = 1;
		result = prime * result + model.countryCode;
		result = prime * result + model.localCode;
		return result;
	}

}
