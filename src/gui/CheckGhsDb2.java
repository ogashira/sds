package gui;

import java.util.List;

import csves.DownLoadCsv;
import csves.GetPath;

class CheckGhsDb2 {

	private List<String[]> ghsDb2;
	private String file;
	private String encode;

	CheckGhsDb2() {
		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからghs_db_2.csvのﾊﾟｽを取得する
		file = GetPath.getPath("ghsDb2");
		encode = "utf-8";
		DownLoadCsv dlCsv = new DownLoadCsv(file, encode);
		ghsDb2 = dlCsv.getCsvData();
	}

	//全てのghs_db_2ﾃﾞｰﾀ
	public List<String[]> getGhsDb2() {
		return ghsDb2;
	}

	String[] getExistData(String hinban) {
		/*
		 * SDS表示名、比重、塗料外観をinputFormに表示させる。
		 * 国連ｸﾗｽ、国連番号などは、実際は使っていない。
		 * これらは、引火点などから計算して求める。また、
		 * 単体の場合はUserが入力する。
		 */
		String[] existData = new String[8];
		for (String[] line : ghsDb2) {
			if (line[0].equals(hinban)) {
				existData[0] = line[0]; //品番
				existData[1] = line[1]; //SDS表示名
				existData[2] = line[6]; //比重
				existData[3] = line[4]; //塗料外観
				existData[4] = line[12]; //国連分類ｸﾗｽ3など
				existData[5] = line[13]; //国連番号1263など
				existData[6] = line[15]; //容器等級2,3など
				existData[7] = line[14]; //国連輸送名（品名）
				break;
			}
		}
		return existData;
	}

}
