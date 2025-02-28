package ghs.hazardToHealth.skinEyeIrritation;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import csves.DownLoadCsv;
import csves.GetPath;

class EyeIrritation {

	private List<String[]> listEyeIrritation;
	private List<String[]> listFilterdGmiccs;
	private float nonListRatio;
	private Map<String, Float> mapEyeIrritationRatio;
	private Map<String, List<String>> mapEyeIrritationCas;
	private String[] eyeIrritationRowCol;

	EyeIrritation(List<String[]> listFilterdGmiccs, float nonListRatio){
		String filePath = GetPath.getPath("eyeIrritation");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listEyeIrritation = dl.getCsvData();

		this.listFilterdGmiccs = listFilterdGmiccs;
		this.nonListRatio = nonListRatio;
		mapEyeIrritationRatio = this.getMapEyeIrritationRatio();
		mapEyeIrritationCas = this.getMapEyeIrritationCas();
		Map<String, Float> mapAllCasesRatio = this.getMapAllCasesRatio();
		eyeIrritationRowCol = this.getEyeIrritationRowCol(mapAllCasesRatio);
	}

	List<String[]> getListEyeIrritation() {
		return listEyeIrritation;
	}

	String[] returnEyeIrritationRowCol() {
		return eyeIrritationRowCol;
	}

	Map<String, List<String>> returnMapEyeIrritationCas() {
		return mapEyeIrritationCas;
	}

	/*
	 * mainのメソッドこれを求めるためにある。
	 */
	Map<String, String> getMapEyeIrritation() {

		Map<String, String> mapEyeIrritation = new HashMap<>();

		//unknownRatioを求めておく。
		//unknownRatio = nonListRatio + 「分類できない」のratio
		float unknownRatio = this.getUnkownRatio();

		//全ての成分にデータが全くなく、または評価するに不十分な場合は、
		//「分類できない」として処理を行わない。
		if (unknownRatio >= 1) {
			mapEyeIrritation.put("kubun", "分類できない");
			mapEyeIrritation.put("pictogram", "-");
			mapEyeIrritation.put("signalWord", "-");
			mapEyeIrritation.put("hazardInfo", "-");
			return mapEyeIrritation;
		}

		int row = Integer.parseInt(eyeIrritationRowCol[0]);
		mapEyeIrritation.put("kubun", listEyeIrritation.get(row)[0]);
		mapEyeIrritation.put("pictogram", listEyeIrritation.get(row)[1]);
		mapEyeIrritation.put("signalWord", listEyeIrritation.get(row)[2]);
		mapEyeIrritation.put("hazardInfo", listEyeIrritation.get(row)[3]);

		//分類処理をした結果、「区分に該当しない」となった際、未知の成分合計
		//濃度が考慮濃度の最小値(1%)以上含有している場合は「分類できない」と
		//する
		if (unknownRatio >= 0.01 && mapEyeIrritation.get("kubun").equals(
				"区分に該当しない")) {
			mapEyeIrritation.put("kubun", "分類できない");
		}

		return mapEyeIrritation;
	}

	private float getUnkownRatio() {

		if (mapEyeIrritationRatio.get("分類できない") == null) {
			return nonListRatio;
		}

		return nonListRatio + mapEyeIrritationRatio.get("分類できない");
	}

	/*
	 * mainのメソッド。これを求めるためにある。
	 */
	List<String> getEyeCas(Map<String, List<String>> mapSkinIrritationCas) {
		List<String> eyeCas = new ArrayList<>();
		String eyeCase = eyeIrritationRowCol[1];

		//Map<kubun, list> でmap.get(kubun)をしたときに、kubunがnullだった場合
		//エラーになるので、getOrDefaultでtempListを指定する。
		final List<String> tempList = new ArrayList<>();
		tempList.add("-");




		if (eyeCase.equals("s1&e1")) {
			//皮膚区分1と眼区分1を結合して、重複をなくす
			eyeCas = mapSkinIrritationCas.getOrDefault("区分1", tempList);
			eyeCas.addAll(mapEyeIrritationCas.getOrDefault("区分1", tempList));
			//重複を除去して"-"を除去
			eyeCas = eyeCas.stream().distinct().collect(Collectors.toList());
			//"-"が含まれていたら削除する
			if(eyeCas.contains("-")) {
				eyeCas.remove(eyeCas.indexOf("-"));
			}
			return eyeCas;
		}
		if (eyeCase.equals("e2&e2a")) {
			//皮膚区分1と眼区分1を結合して、重複をなくす
			eyeCas = mapEyeIrritationCas.getOrDefault("区分2", tempList);
			eyeCas.addAll(mapEyeIrritationCas.getOrDefault("区分2A", tempList));
			//重複を除去して"-"を除去
			eyeCas = eyeCas.stream().distinct().collect(Collectors.toList());
			if(eyeCas.contains("-")) {
				eyeCas.remove(eyeCas.indexOf("-"));
			}
			return eyeCas;
		}

		if (eyeCase.equals("10*e1&e2&e2a&e2b")) {
			//皮膚区分1と眼区分1を結合して、重複をなくす
			eyeCas = mapEyeIrritationCas.getOrDefault("区分1", tempList);
			eyeCas.addAll(mapEyeIrritationCas.getOrDefault("区分2", tempList));
			eyeCas.addAll(mapEyeIrritationCas.getOrDefault("区分2A", tempList));
			eyeCas.addAll(mapEyeIrritationCas.getOrDefault("区分2B", tempList));
			//重複を除去して"-"を除去
			eyeCas = eyeCas.stream().distinct().collect(Collectors.toList());
			if(eyeCas.contains("-")) {
				eyeCas.remove(eyeCas.indexOf("-"));
			}
			return eyeCas;
		}

		if (eyeCase.equals("10*(s1&e1)&e2&e2a&e2b")) {
			//皮膚区分1と眼区分1を結合して、重複をなくす
			eyeCas = mapSkinIrritationCas.getOrDefault("区分1", tempList);
			eyeCas.addAll(mapEyeIrritationCas.getOrDefault("区分1", tempList));
			eyeCas.addAll(mapEyeIrritationCas.getOrDefault("区分2", tempList));
			eyeCas.addAll(mapEyeIrritationCas.getOrDefault("区分2A", tempList));
			eyeCas.addAll(mapEyeIrritationCas.getOrDefault("区分2B", tempList));
			//重複を除去して"-"を除去
			eyeCas = eyeCas.stream().distinct().collect(Collectors.toList());
			if(eyeCas.contains("-")) {
				eyeCas.remove(eyeCas.indexOf("-"));
			}
			return eyeCas;
		}

		return eyeCas;
	}

	private Map<String, Float> getMapEyeIrritationRatio() {
		/*
		 * Map<String, Float>
		 * mapEyeIrritationRatio = {"区分1": o%, "区分2":o%, "区分2A":o%, "区分2B":o%,
		 * "分類できない":o%,"区分に該当しない":o%, "区分に該当しない(分類対象外):o%,
		 * "区分1Mix":o%} 皮膚区分1と眼区分1の合計。
		 * これらの内の存在するキー:バリューのみ。存在しないキーはHashMapには入っていない。
		 * 空白は「分類できない」とする。
		 */

		Map<String, Float> mapEyeIrritationRatio = new HashMap<>();
		//gmiccsの皮膚刺激性の列
		int retuban = listEyeIrritation.get(0).length - 1;
		int kubunCol = Integer.parseInt(listEyeIrritation.get(1)[retuban]) - 1;
		//HashMapのキーがnullだったら、valueにratioを入力
		//キーが既に存在していたら、valueのratioに加算する。
		float ratio = 0;
		for (String[] line : listFilterdGmiccs) {
			//文字に含まれる数字を半角にする。
			//区分１-> 区分1 にする。
			String kubun = Normalizer.normalize(line[kubunCol], Normalizer.Form.NFKC);

			if (kubun.equals("") || kubun == null) {
				kubun = "分類できない";
			}
			if (mapEyeIrritationRatio.get(line[kubunCol]) == null) {
				mapEyeIrritationRatio.put(kubun, Float.parseFloat(
						line[line.length - 1]));
			} else {
				ratio = mapEyeIrritationRatio.get(line[kubunCol]);
				mapEyeIrritationRatio.put(kubun, ratio += Float.parseFloat(line[line.length - 1]));
			}
		}

		//mapEyeIrritationRatioの区分1,2,3がnullだったら0.0にしておく
		if (mapEyeIrritationRatio.get("区分1") == null) {
			mapEyeIrritationRatio.put("区分1", 0f);
		}
		if (mapEyeIrritationRatio.get("区分2") == null) {
			mapEyeIrritationRatio.put("区分2", 0f);
		}
		if (mapEyeIrritationRatio.get("区分2A") == null) {
			mapEyeIrritationRatio.put("区分2A", 0f);
		}
		if (mapEyeIrritationRatio.get("区分2B") == null) {
			mapEyeIrritationRatio.put("区分2B", 0f);
		}

		//一つの成分が皮膚区分1及び眼区分1の両方に分類されていた場合、その濃度は計算に
		//一度だけ入れる。getMapAllCasesRatioで皮膚と眼の区分1の合計が必要になるので
		//ここで作っておく。（ここじゃないと作れない)
		int eyeCol = Integer.parseInt(listEyeIrritation.get(1)[retuban]) - 1;
		int skinCol = eyeCol - 1; // 眼刺激のひとつ前の列が皮膚刺激
		float kubun1Mix = 0;
		for (String[] line : listFilterdGmiccs) {
			String skinKubun = Normalizer.normalize(line[skinCol], Normalizer.Form.NFKC);
			String eyeKubun = Normalizer.normalize(line[eyeCol], Normalizer.Form.NFKC);

			//皮膚だけ区分1、眼だけ区分1、皮膚と眼が区分1の時、ratioを足す。
			if (skinKubun.contains("区分1") && (!eyeKubun.contains("区分1"))) {
				kubun1Mix += Float.parseFloat(line[line.length - 1]);
			}
			if ((!skinKubun.contains("区分1")) && eyeKubun.contains("区分1")) {
				kubun1Mix += Float.parseFloat(line[line.length - 1]);
			}
			if (skinKubun.contains("区分1") && eyeKubun.contains("区分1")) {
				kubun1Mix += Float.parseFloat(line[line.length - 1]);
			}
		}

		mapEyeIrritationRatio.put("区分1Mix", kubun1Mix);

		return mapEyeIrritationRatio;
	}

	private Map<String, List<String>> getMapEyeIrritationCas() {
		/*
		 * Map<String, List<String>>
		 * mapEyeIrritationCas = {"区分1":[108-88-3, 1330-20-4,......], "区分2":[....]...}
		 */

		Map<String, List<String>> mapEyeIrritationCas = new HashMap<>();

		//gmiccsの眼刺激性の列
		int retuban = listEyeIrritation.get(0).length -1;
		int kubunCol = Integer.parseInt(listEyeIrritation.get(1)[retuban]) - 1;
		//HashMapのキーがnullだったら、valueにcasNoのListを入力
		//キーが既に存在していたら、valueのListにcasNoをaddする。
		for (String[] line : listFilterdGmiccs) {
			//区分１の数値を小文字にしておく
			String kubun = Normalizer.normalize(line[kubunCol], Normalizer.Form.NFKC);
			if (mapEyeIrritationCas.get(line[kubunCol]) == null) {
				List<String> newCasNos = new ArrayList<>();
				newCasNos.add(line[2]);
				mapEyeIrritationCas.put(kubun, newCasNos);
			} else {
				List<String> casNos = mapEyeIrritationCas.get(line[kubunCol]);
				casNos.add(line[2]);
				mapEyeIrritationCas.put(kubun, casNos);
			}
		}

		return mapEyeIrritationCas;
	}

	/*
	Map<String, String> getMapEyeIrritation(){
		Map<String, String> mapEyeIrritation = new HashMap<>();

		int rowCnt;
		int colCase;
		//colCase=1:case1, colCase=2:case2, colCase=3:case3, colCase=4:case12,
		//colCase=5:case123 caseもintで表して、for文で回す。
		for(rowCnt = 1; rowCnt < listEyeIrritation.size(); rowCnt++) {
			for(colCase =1; colCase < )
			String comparisonOperator = listEyeIrritation.get(rowCnt)[4];
			String strAte = listEyeIrritation.get(rowCnt)[5];
			boolean judge = this.judgeRow(comparisonOperator, strAte, ateMix);
			if(judge) {
				break;
			}
		}

		mapEyeIrritation.put("kubun", listEyeIrritation.get(rowCnt)[0]);
		mapEyeIrritation.put("pictogram", listEyeIrritation.get(rowCnt)[1]);
		mapEyeIrritation.put("signalWord", listEyeIrritation.get(rowCnt)[2]);
		mapEyeIrritation.put("hazardInfo", listEyeIrritation.get(rowCnt)[3]);
		return mapEyeIrritation;
	}
	*/

	private Map<String, Float> getMapAllCasesRatio(){
		/*
		 * mapSkinIrritationRateが必要
		 * s1|e1, s1&e1, e2|e2a, 10*e1&(e2|e2a), 10*(s1&e1)&(e2a|e2b) を
		 * HashMapに代入する
		 * mapAllCasesRatio = {"s1|e1":[o%,o%], "s1&e1":[o%], "e2|e2a":[o%,o%],
		 * "10*e1&(e2|e2a)":[o%,o%],"10*(s1&e1)&(e2a|e2b)":[o%,o%]}
		 */
		Map<String, Float> mapAllCasesRatio = new HashMap<>();

		float eye1 = mapEyeIrritationRatio.get("区分1");
		float eye2 = mapEyeIrritationRatio.get("区分2");
		float eye2a = mapEyeIrritationRatio.get("区分2A");
		float eye2b = mapEyeIrritationRatio.get("区分2B");
		float skin1Eye1Mix = mapEyeIrritationRatio.get("区分1Mix");

		//要素数1と2の配列を用意しておく
		//一つの成分が皮膚区分1及び眼区分1の両方に分類されていた場合、その濃度は計算に
		//一度だけ入れる。 skin1 + eye1 の時は skin1Eye1Mixを使う。
		float S1addE1 =  skin1Eye1Mix;
		float E2addE2A = eye2 + eye2a;
		float E1times10addE2addE2AaddE2B = (10 * eye1) + eye2 + eye2a + eye2b;
		float S1addE1_times10addE2addE2AaddE2B = (10 * skin1Eye1Mix + eye2 + eye2a + eye2b);

		mapAllCasesRatio.put("s1&e1", S1addE1);
		mapAllCasesRatio.put("e2&e2a", E2addE2A);
		mapAllCasesRatio.put("10*e1&e2&e2a&e2b",E1times10addE2addE2AaddE2B );
		mapAllCasesRatio.put("10*(s1&e1)&e2&e2a&e2b",S1addE1_times10addE2addE2AaddE2B);


		return mapAllCasesRatio;
	}

	private String[] getEyeIrritationRowCol(
			Map<String, Float> mapAllCasesRatio) {
		/*
		 * getEyeCaseからnull以外が返ってきた時点で、eyeIrritationRow(行)
		 * が決まる。null以外(eyeCase)が返ってこなかったら、最後の行が
		 * eyeIrritationRowとなる
		 */

		int lastRow = (listEyeIrritation.size() - 1);
		String[] eyeIrritationRowCol = { String.valueOf(lastRow), "-"}; //初期値
		String eyeCase = null;
		for (int i = 1; i < listEyeIrritation.size(); i++) {
			eyeCase = this.getEyeCase(listEyeIrritation.get(i), mapAllCasesRatio);
			if (eyeCase != null) {
				eyeIrritationRowCol[0] = String.valueOf(i);
				eyeIrritationRowCol[1] = eyeCase;
				break;
			}
		}

		//eyeCaseがnullだったら、初期値[lastRow, "-"]がreturnされる
		return eyeIrritationRowCol;

	}

	private String getEyeCase(String[] eyeIrritationLine,
			                                Map<String, Float> mapAllCasesRatio) {
		/*
		 * eyeCaseは列に相当する。下の表は皮膚の例
		 * 表の一行とmapAllCasesRatioを受け取って、何列目でtrueとなるか？
		 * trueとなった時点で、"s1|e1", "s1&e1", "e2|e2a", "10*e1&(e2|e2a)",
		 *	"10*(s1&e1)&(e2a|e2b) を返す
		 * |----------------|-----------|----|------|--|----|--|----|--|----|--|----|----|
		 * |区分            |ピクトグラ |注意|危報  |op|s1&e|op|e2&e|op|10*e|op|10*e|列番|
		   |----------------|-----------|----|------|--|----|--|----|--|----|--|----|----|
		   |区分１          |corrosion  |危険|重篤な|>=|9999|>=|9999|>=|9999|>=|9999|39  |
		   |区分２          |exclamation|警告|皮膚刺|>=|10  |>=|9999|>=|10  |>=|9999|39  |
		   |区分に該当しない|-          |-   |-     |>=|9999|>=|9999|>=|9999|>=|9999|39  |
		   |----------------|-----------|----|------|--|----|--|----|--|----|--|----|----|
		 */

		String eyeCase = null;
		String[] cases = {"s1&e1", "e2&e2a", "10*e1&e2&e2a&e2b",
				                                       "10*(s1&e1)&e2&e2a&e2b"};
		for (int i = 4; i < (eyeIrritationLine.length - 2); i += 2) {

			boolean boolCol = false;

			String operator = eyeIrritationLine[i];
			//表は%表示なので、100で割って、ratioに合わせる
			float limitRatio = (Float.parseFloat(eyeIrritationLine[i + 1])) / 100;
			//casesの要素数0~4をiで表現するには(i-4)/2となる
			float caseRatio = mapAllCasesRatio.get(cases[(i - 4) / 2]);

			//operator:表の不等号、 eyeRatio:表の閾値、
			//caseRatio:case1,2,3,12,123の計算値
			boolCol = this.boolJudge(operator, limitRatio, caseRatio);

			if (boolCol) {
				eyeCase = cases[(i - 4) / 2];
				return eyeCase;
			}
		}

		return eyeCase; //eyeCaseはnullのままreturn
	}

	private boolean boolJudge(String comparisonOperator, float limitRatio,
			float caseRatio) {
		/*
		 * 表の比較演算子、表のATE（文字列）、ATEMixを受け取って、
		 * 判定する。
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
