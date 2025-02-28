package components.calcComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

public class GenryouBunkai {

	private List<String[]> genryouBunkaiData;
	private String file;
	private String encode;
	private final String[] TMBZ = { "G-123TMBZ", "G-124TMBZ", "G-135TMBZ",
			"G-TMBZ", "G-ALL-TMBZ", "G-SONOTA" };

	public GenryouBunkai() {
		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙから原料分解表のpathを取得する。
		//GetPathｸﾗｽのstaticﾒｿｯﾄﾞgetPath()を使う。
		file = GetPath.getPath("genryouBunkai");
		encode = "shift-JIS";
		DownLoadCsv dlCsv = new DownLoadCsv(file, encode);
		genryouBunkaiData = dlCsv.getCsvData();
	}

	public List<String[]> getGenryouBunkaiData() {
		return genryouBunkaiData;
	}

	public Map<String, Float> getListGenryouBunkai(List<String[]> psPurpose) {
		//psPurposeの原料をgenryouBunkaiDataを使って分解する。
		//同時に配合量の計算もする。genryouBunkaiDataのﾄｰﾀﾙは全て
		//１になっているので、そのまま掛ける。

		List<String[]> listGenryouBunkai = new ArrayList<>();
		for (String[] line : psPurpose) {
			String genryou = line[1];
			float total = Float.parseFloat(line[3]);
			try {
				for (String[] bunkaiLine : genryouBunkaiData) {
					if (genryou.equals(bunkaiLine[0])) {
						bunkaiLine[2] = Float.toString(
								Float.parseFloat(bunkaiLine[2]) * total);
						listGenryouBunkai.add(bunkaiLine);
					}
				}
			} catch (Exception e) {
				System.out.println("原料分解できません");
				System.out.println(e.getMessage());
				System.exit(1);
			}
		}



		Map<String, Float> dupliBunkai = this.getDupliBunkai(
				listGenryouBunkai);
		//その他添加剤有機溶剤も追加しておく
		for (String addHinban : TMBZ) {
			if (!dupliBunkai.containsKey(addHinban)) {
				dupliBunkai.put(addHinban, (float) 0);
			}
		}


		return dupliBunkai;
	}

	private Map<String, Float> getDupliBunkai(List<String[]> listGenryouBunkai) {
		//listGenryouBunkaiを受け取って、HashMapにして重複する原料の配合量を合算する

		Map<String, Float> dupliBunkai = new HashMap<>();
		for (String[] line : listGenryouBunkai) {
			if (dupliBunkai.containsKey(line[1])) {
				dupliBunkai.put(line[1], dupliBunkai.get(line[1]) +
						Float.parseFloat(line[2]));
			} else {
				dupliBunkai.put(line[1], Float.parseFloat(line[2]));
			}
		}
		return dupliBunkai;
	}
}
