package com.kaihg.comparedemo;

import java.util.List;

import com.kaihg.comparedemo.reader.ReaderParam;

public class ParamParser {

	public static ReaderParam parseParam(List<String> settings) {
		ReaderParam param = new ReaderParam();
		for (String set : settings) {
			int equalPosition = set.indexOf("=");
			if (equalPosition == -1) {
				continue;
			}

			String leftToEqual = set.substring(0, equalPosition - 1).trim();
			String rightToEqual = set.substring(equalPosition + 1).trim();
//			System.out.println(leftToEqual);
//			System.out.println(rightToEqual);

			switch (leftToEqual) {
			case "filename":
				param.setFileName(rightToEqual);
				break;
			case "hasHeader":
				param.setHasHeader(Boolean.parseBoolean(rightToEqual));
				break;
			case "MccPosition":
				param.setMccIndex(Integer.parseInt(rightToEqual));
				break;
			case "MncPosition":
				param.setMncIndex(Integer.parseInt(rightToEqual));
				break;
			case "PpciPosition":
				param.setPpciIndex(Integer.parseInt(rightToEqual));
				break;
			case "AbrevPosition":
				param.setAbreIndex(Integer.parseInt(rightToEqual));
				break;

			default:
				break;
			}

		}

		return param;
	}

}
