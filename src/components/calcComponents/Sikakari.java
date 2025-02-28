package components.calcComponents;

import java.util.List;

import csves.DownLoadCsv;
import csves.GetPath;

public class Sikakari {

	private List<String[]> sikakariData;
	private String file;
	private String encode;
	private String hinban;
	private String sikakariHb;

	public Sikakari(String hinban) {
		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからflashpoint.csvのﾊﾟｽを取得する
		file = GetPath.getPath("sikakari");
		encode = "shift-JIS";
		this.hinban = hinban;
		DownLoadCsv dlCsv = new DownLoadCsv(file, encode);
		sikakariData = dlCsv.getCsvData();
	}

	public List<String[]> getSikakariData() {
		return sikakariData;
	}

	public String getSikakariHb() {
		Boolean isMatch = false;
		for (String[] line : sikakariData) {
			if (line[0].equals(this.hinban)) {
				sikakariHb = line[1];
				isMatch = true;
				break;
			}
		}
		if (isMatch == false) {
			sikakariHb = "noData";
			System.out.println("仕掛品が見つかりません。プログラム終了します  (-_-;)");
			System.exit(0);
		}
		return sikakariHb;
	}

}
