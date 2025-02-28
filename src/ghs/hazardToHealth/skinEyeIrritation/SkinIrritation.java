package ghs.hazardToHealth.skinEyeIrritation;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;


class SkinIrritation {

	private List<String[]> listSkinIrritation;
	private List<String[]> listFilterdGmiccs;
	private float nonListRatio;
	private Map<String, Float> mapSkinIrritationRatio;
	private Map<String, List<String>> mapSkinIrritationCas;
	private String[] skinIrritationRowCol;

	SkinIrritation(List<String[]> listFilterdGmiccs, float nonListRatio) {
		String filePath = GetPath.getPath("skinIrritation");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listSkinIrritation = dl.getCsvData();

		this.listFilterdGmiccs = listFilterdGmiccs;
		this.nonListRatio = nonListRatio;
		mapSkinIrritationRatio = this.getMapSkinIrritationRatio();
		mapSkinIrritationCas = this.getMapSkinIrritationCas();
		Map<String, Float> mapAllCasesRatio = this.getMapAllCasesRatio();
		skinIrritationRowCol = this.getSkinIrritationRowCol(mapAllCasesRatio);
	}

	List<String[]> getListSkinIrritation() {
		return listSkinIrritation;
	}

	String[] returnSkinIrritationRowCol() {
		return skinIrritationRowCol;
	}

	Map<String, List<String>> returnMapSkinIrritationCas() {
		return mapSkinIrritationCas;
	}

	Map<String, Float> returnMapSkinIrritationRatio() {
		return mapSkinIrritationRatio;
	}

	/*
	 * mainのメソッドこれを求めるためにある。
	 */
	Map<String, String> getMapSkinIrritation() {

		Map<String, String> mapSkinIrritation = new HashMap<>();

		//unknownRatioを求めておく。
		//unknownRatio = nonListRatio + 「分類できない」のratio
		float unknownRatio = this.getUnkownRatio();

		//全ての成分にデータが全くなく、または評価するに不十分な場合は、
		//「分類できない」として処理を行わない。
		if (unknownRatio >= 1) {
			mapSkinIrritation.put("kubun", "分類できない");
			mapSkinIrritation.put("pictogram", "-");
			mapSkinIrritation.put("signalWord", "-");
			mapSkinIrritation.put("hazardInfo", "-");
			return mapSkinIrritation;
		}

		//分類結果が区分３になった場合で、選択した分類がJISであれば、区分３を
		//採用していないので、「区分に該当しない」に分類する。
		int row = Integer.parseInt(skinIrritationRowCol[0]);
		if (row == 3) {
			row = 4;
		}
		mapSkinIrritation.put("kubun", listSkinIrritation.get(row)[0]);
		mapSkinIrritation.put("pictogram", listSkinIrritation.get(row)[1]);
		mapSkinIrritation.put("signalWord", listSkinIrritation.get(row)[2]);
		mapSkinIrritation.put("hazardInfo", listSkinIrritation.get(row)[3]);

		//分類処理をした結果、「区分に該当しない」となった際、未知の成分合計
		//濃度が考慮濃度の最小値(1%)以上含有している場合は「分類できない」と
		//する
		if (unknownRatio >= 0.01 && mapSkinIrritation.get("kubun").equals(
				"区分に該当しない")) {
			mapSkinIrritation.put("kubun", "分類できない");
		}

		return mapSkinIrritation;
	}

	private float getUnkownRatio() {

		if (mapSkinIrritationRatio.get("分類できない") == null) {
			return nonListRatio;
		}

		return nonListRatio + mapSkinIrritationRatio.get("分類できない");
	}

	/*
	 * mainのメソッド。これを求めるためにある。
	 */
	List<String> getSkinCas() {
		List<String> skinCas = new ArrayList<>();
		String skinCase = skinIrritationRowCol[1];

		if (skinCase.equals("case1")) {
			skinCas = mapSkinIrritationCas.get("区分1");
			return skinCas;
		}
		if (skinCase.equals("case2")) {
			skinCas = mapSkinIrritationCas.get("区分2");
			return skinCas;
		}
		if (skinCase.equals("case3")) {
			skinCas = mapSkinIrritationCas.get("区分3");
			return skinCas;
		}
		if (skinCase.equals("case12")) {
			if (mapSkinIrritationCas.containsKey("区分1")) {
				skinCas = mapSkinIrritationCas.get("区分1");
			}
			if (mapSkinIrritationCas.containsKey("区分2")) {
				skinCas.addAll(mapSkinIrritationCas.get("区分2"));
			}
			return skinCas;
		}
		if (skinCase.equals("case123")) {
			if (mapSkinIrritationCas.containsKey("区分1")) {
				skinCas = mapSkinIrritationCas.get("区分1");
			}
			if (mapSkinIrritationCas.containsKey("区分2")) {
				skinCas.addAll(mapSkinIrritationCas.get("区分2"));
			}
			if (mapSkinIrritationCas.containsKey("区分3")) {
				skinCas.addAll(mapSkinIrritationCas.get("区分3"));
			}
			return skinCas;
		}

		return skinCas;
	}

	private Map<String, Float> getMapSkinIrritationRatio() {
		/*
		 * Map<String, Float>
		 * mapSkinIrritationRatio = {"区分1": o%, "区分2":o%, "区分3":o%, "分類できない":o%,
		 * "区分に該当しない":o%, "区分に該当しない(分類対象外):o%} これらの内の存在する
		 * キー:バリューのみ。存在しないキーはHashMapには入っていない。
		 * 区分1A,1B,1Cは全て区分1とする。空白は「分類できない」にする。
		 */

		Map<String, Float> mapSkinIrritationRatio = new HashMap<>();
		//gmiccsの皮膚刺激性の列
		int retuban = listSkinIrritation.get(0).length - 1;
		int kubunCol = Integer.parseInt(listSkinIrritation.get(1)[retuban]) - 1;
		//HashMapのキーがnullだったら、valueにratioを入力
		//キーが既に存在していたら、valueのratioに加算する。
		float ratio = 0;
		for (String[] line : listFilterdGmiccs) {
			//文字に含まれる数字を半角にする。
			//区分１-> 区分1 にする。
			//区分1A,1B,1C -> 区分1にする
			String kubun = Normalizer.normalize(line[kubunCol], Normalizer.Form.NFKC);
			if (kubun.contains("区分1")) {
				kubun = "区分1";
			}
			if (kubun.equals("") || kubun == null) {
				kubun = "分類できない";
			}
			if (mapSkinIrritationRatio.get(line[kubunCol]) == null) {
				mapSkinIrritationRatio.put(kubun, Float.parseFloat(
						line[line.length - 1]));
				//lineの最終列がratioだから
			} else {
				ratio = mapSkinIrritationRatio.get(line[kubunCol]);
				mapSkinIrritationRatio.put(kubun, ratio += Float.parseFloat(line[line.length - 1]));
			}
		}

		//mapSkinIrritationRatioの区分1,2,3がnullだったら0.0にしておく
		if (mapSkinIrritationRatio.get("区分1") == null) {
			mapSkinIrritationRatio.put("区分1", 0f);
		}
		if (mapSkinIrritationRatio.get("区分2") == null) {
			mapSkinIrritationRatio.put("区分2", 0f);
		}
		if (mapSkinIrritationRatio.get("区分3") == null) {
			mapSkinIrritationRatio.put("区分3", 0f);
		}

		return mapSkinIrritationRatio;
	}

	private Map<String, List<String>> getMapSkinIrritationCas() {
		/*
		 * Map<String, List<String>>
		 * mapSkinIrritationCas = {"区分1":[108-88-3, 1330-20-4,......], "区分2":[....]...}
		 */

		Map<String, List<String>> mapSkinIrritationCas = new HashMap<>();

		//gmiccsの皮膚刺激性の列
		int retuban = listSkinIrritation.get(0).length -1;
		int kubunCol = Integer.parseInt(listSkinIrritation.get(1)[retuban]) - 1;
		//HashMapのキーがnullだったら、valueにcasNoのListを入力
		//キーが既に存在していたら、valueのListにcasNoをaddする。
		for (String[] line : listFilterdGmiccs) {
			//区分１の数値を小文字にしておく
			String kubun = Normalizer.normalize(line[kubunCol], Normalizer.Form.NFKC);
			if (mapSkinIrritationCas.get(line[kubunCol]) == null) {
				List<String> newCasNos = new ArrayList<>();
				newCasNos.add(line[2]);
				mapSkinIrritationCas.put(kubun, newCasNos);
			} else {
				List<String> casNos = mapSkinIrritationCas.get(line[kubunCol]);
				casNos.add(line[2]);
				mapSkinIrritationCas.put(kubun, casNos);
			}
		}

		return mapSkinIrritationCas;
	}

	/*
	Map<String, String> getMapSkinIrritation(){
		Map<String, String> mapSkinIrritation = new HashMap<>();

		int rowCnt;
		int colCase;
		//colCase=1:case1, colCase=2:case2, colCase=3:case3, colCase=4:case12,
		//colCase=5:case123 caseもintで表して、for文で回す。
		for(rowCnt = 1; rowCnt < listSkinIrritation.size(); rowCnt++) {
			for(colCase =1; colCase < )
			String comparisonOperator = listSkinIrritation.get(rowCnt)[4];
			String strAte = listSkinIrritation.get(rowCnt)[5];
			boolean judge = this.judgeRow(comparisonOperator, strAte, ateMix);
			if(judge) {
				break;
			}
		}

		mapSkinIrritation.put("kubun", listSkinIrritation.get(rowCnt)[0]);
		mapSkinIrritation.put("pictogram", listSkinIrritation.get(rowCnt)[1]);
		mapSkinIrritation.put("signalWord", listSkinIrritation.get(rowCnt)[2]);
		mapSkinIrritation.put("hazardInfo", listSkinIrritation.get(rowCnt)[3]);
		return mapSkinIrritation;
	}
	*/

	private Map<String, Float> getMapAllCasesRatio() {
		/*
		 * case1, case2, case3, case12, case123 をHashMapに代入する
		 * mapAllCasesRatio = {"case1":o%, "case2":o%, "case3":o%, "case12":o%,
		 * "case123":o%}
		 */
		Map<String, Float> mapAllCasesRatio = new HashMap<>();

		mapAllCasesRatio.put("case1", mapSkinIrritationRatio.get("区分1"));
		mapAllCasesRatio.put("case2", mapSkinIrritationRatio.get("区分2"));
		mapAllCasesRatio.put("case3", mapSkinIrritationRatio.get("区分3"));

		float ratio12 = (mapSkinIrritationRatio.get("区分1") * 10) +
				mapSkinIrritationRatio.get("区分2");
		mapAllCasesRatio.put("case12", ratio12);

		float ratio123 = (mapSkinIrritationRatio.get("区分1") * 10) +
				mapSkinIrritationRatio.get("区分2") +
				mapSkinIrritationRatio.get("区分3");
		mapAllCasesRatio.put("case123", ratio123);


		return mapAllCasesRatio;
	}

	private String[] getSkinIrritationRowCol(
			Map<String, Float> mapAllCasesRatio) {
		/*
		 * getSkinCaseからnull以外が返ってきた時点で、skinIrritationRow(行)
		 * が決まる。null以外(skinCase)が返ってこなかったら、最後の行が
		 * skinIrritationRowとなる
		 */
		int lastRow = (listSkinIrritation.size() - 1);
		String[] skinIrritationRowCol = { String.valueOf(lastRow), "-" }; //初期値
		String skinCase = null;
		for (int i = 1; i < listSkinIrritation.size(); i++) {
			skinCase = this.getSkinCase(listSkinIrritation.get(i), mapAllCasesRatio);
			if (skinCase != null) {
				skinIrritationRowCol[0] = String.valueOf(i);
				skinIrritationRowCol[1] = skinCase;
				break;
			}
		}
		//skinCase がnullだったら、skinIrritationRowColは初期値のままreturn
		return skinIrritationRowCol;

	}

	private String getSkinCase(String[] skinIrritationLine,
			                                    Map<String, Float> mapAllCasesRatio) {
		/*
		 * skinCaseは列に相当する。
		 * 表の一行とmapAllCasesRatioを受け取って、何列目でtrueとなるか？
		 * trueとなった時点で、skinCase("case1" or "case2" or "case3" or
		 * "case12" or "case123" ) を返す
		 * |----------------|-----------|----|------|--|----|--|----|--|----|--|----|--|----|----|
		 * |区分            |ピクトグラ |注意|危報  |op|区分|op|区分|op|ase3|op|se12|op|e123|列番|
		   |----------------|-----------|----|------|--|----|--|----|--|----|--|----|--|----|----|
		   |区分１          |corrosion  |危険|重篤な|>=|5   |>=|9999|>=|9999|>=|9999|>=|9999|39  |
		   |区分２          |exclamation|警告|皮膚刺|>=|1   |>=|10  |>=|9999|>=|10  |>=|9999|39  |
		   |区分３          |-          |警告|軽度の|>=|9999|>=|1   |>=|10  |>=|1   |>=|10  |39  |
		   |区分に該当しない|-          |-   |-     |>=|9999|>=|9999|>=|9999|>=|9999|>=|9999|39  |
		   |----------------|-----------|----|------|--|----|--|----|--|----|--|----|--|----|----|
		 */

		String skinCase = null;
		String[] cases = { "case1", "case2", "case3", "case12", "case123" };
		for (int i = 4; i < (skinIrritationLine.length - 2); i += 2) {

			boolean boolCol = false;

			String operator = skinIrritationLine[i];
			//表は%表示なので、100で割って、ratioに合わせる
			float limitRatio = (Float.parseFloat(skinIrritationLine[i + 1])) / 100;
			//casesの要素数0~4をiで表現するには(i-4)/2となる
			float caseRatio = mapAllCasesRatio.get(cases[(i - 4) / 2]);

			//operator:表の不等号、 skinRatio:表の閾値、
			//caseRatio:case1,2,3,12,123の計算値
			boolCol = this.boolJudge(operator, limitRatio, caseRatio);

			if (boolCol) {
				skinCase = cases[(i - 4) / 2];
				return skinCase;
			}

		}
		return skinCase; //skinCaseはnullのままreturn
	}

	private boolean boolJudge(String comparisonOperator, float limitRatio,
			float caseRatio) {
		/*
		 * 表の比較演算子、表の閾値（文字列）、原料のratioを受け取って、
		 * 判定する。limitRatio:表の閾値. caseRatio:case1,2,12,123の計算値
		 */
		if (comparisonOperator.equals("<=")) {
			if (caseRatio <= limitRatio) {
				return true;
			}
			return false;
		}
		if (comparisonOperator.equals("<")) {
			if (caseRatio < limitRatio) {
				return true;
			}
			return false;
		}
		if (comparisonOperator.equals(">=")) {
			if (caseRatio >= limitRatio) {
				return true;
			}
			return false;
		}
		if (comparisonOperator.equals(">")) {
			if (caseRatio > limitRatio) {
				return true;
			}
			return false;
		}

		return false;

	}
}
