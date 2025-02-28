package ghs.hazardToHealth.sensitization;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalcSensitization {

	private List<String[]> listSensitization;
	private List<String[]> listFilterdGmiccs;
	private String[] sensitizationRowCol;

	public CalcSensitization(List<String[]> listFilterdGmiccs,
			                        List<String[]> listSensitization) {
		this.listFilterdGmiccs = listFilterdGmiccs;
		//Sensitization.csvの表
		this.listSensitization = listSensitization;

		sensitizationRowCol = this.getSensitizationRowCol();

	}


	public Map<String, String> getMapSensitization(float nonListRatio){
		int myRow = Integer.parseInt(sensitizationRowCol[0]);
		Map<String, String> mapSensitization =  new HashMap<>();

		//unknownRaioを求める

		//gmiccsの感作性の列 (listSensitizationの最終列に書いてある列番号-1)
		String[] sensitizationLine = listSensitization.get(1);
		int kubunCol = Integer.parseInt(
				sensitizationLine[sensitizationLine.length - 1]) - 1;

		Float ratio = 0f;
		for (String[] line : listFilterdGmiccs) {
			if (line[kubunCol].equals("分類できない")
					     || line[kubunCol].equals("")
					     || line[kubunCol] ==  null)  {
				ratio += Float.parseFloat(line[line.length - 1]);
			}
		}

		float unknownRatio = ratio + nonListRatio;

		mapSensitization.put("kubun", listSensitization.get(myRow)[0]);
		mapSensitization.put("pictogram", listSensitization.get(myRow)[1]);
		mapSensitization.put("signalWord", listSensitization.get(myRow)[2]);
		mapSensitization.put("hazardInfo", listSensitization.get(myRow)[3]);


		//分類処理をした結果、「区分に該当しない」となった際、未知の成分合計
		//濃度が考慮濃度の最小値(1%)以上含有している場合は「分類できない」と
		//する
		if (unknownRatio >= 0.01 && mapSensitization.get("kubun").equals(
				                                          "区分に該当しない")) {
			mapSensitization.put("kubun", "分類できない");
		}

		return mapSensitization;

	}


	public Map<String, List<String>> getMapSensitizationCas() {
		/*
		 * Map<String, List<String>>
		 * mapSkinIrritationCas = {"区分1":[108-88-3, 1330-20-4,......], "区分2":[....]...}
		 * 初期期 = {"-" : ["-"]}
		 */


		Map<String, List<String>> mapSensitizationCas = new HashMap<>();
		List<String> casNos = new ArrayList<>();

		int myRow = Integer.parseInt(sensitizationRowCol[0]);
		String myKubun = sensitizationRowCol[1]; //区分1Aなど

		if (myKubun.equals("-")) {
			casNos.add("-");
			mapSensitizationCas.put("-", casNos);

			//nullが返るは間違い {"-":["-"]}が返る
			return mapSensitizationCas;
		}

		Float limitRatio = 0f;
		for (int i = 0; i < listSensitization.get(0).length; i++) {
			if (myKubun.equals(listSensitization.get(0)[i])) {
				limitRatio = Float.parseFloat(listSensitization.get(myRow)[i])/100;
			}
		}

		//listSensitizationの最終列 - 1
		int lastCol = listSensitization.get(1).length - 1;
		//gmiccsの皮膚刺激性の列
		int kubunCol = Integer.parseInt(listSensitization.get(1)[lastCol]) - 1;

		for (String[] line : listFilterdGmiccs) {
			//区分１の数値を小文字にしておく
			String kubun = Normalizer.normalize(line[kubunCol], Normalizer.Form.NFKC);
			float ratio = Float.parseFloat(line[line.length - 1]);
			if (kubun.equals(myKubun) && ratio >= limitRatio) {
				casNos.add(line[2]);
			}
		}

		mapSensitizationCas.put(myKubun, casNos);


		return mapSensitizationCas;
	}

	private String[] getSensitizationRowCol() {
		/*
		 * getSkinCaseからnull以外が返ってきた時点で、skinIrritationRowCol[0]
		 * (行)が決まる。null以外(skinCase)が返ってこなかったら、最後の行が
		 * sensitizationRowCol[0]となる
		 */
		int lastRow = (listSensitization.size() - 1);
		String[] sensitizationRowCol = { String.valueOf(lastRow), "-" };
		String sensitizationCol = null;
		for (int i = 1; i < listSensitization.size(); i++) {
			sensitizationCol = this.getSensitizationCol(listSensitization.get(i));
			if (sensitizationCol != null) {
				sensitizationRowCol[0] = String.valueOf(i);
				sensitizationRowCol[1] = sensitizationCol;
				return sensitizationRowCol;
			}
		}
		return sensitizationRowCol; //初期値のままreturn

	}

	private String getSensitizationCol(String[] sensitizationLine) {

		/*
		 * 表の一行を受け取って、何列目でtrueとなるか？
		 * trueとなった時点で、sensitizationCol("区分1A" or "区分1" or "区分1B"
		 * を返す
		 * |----------------|-----------|----|------|--|------|--|-----|--|------|----|
		 * |区分            |ピクトグラ |注意|危報  |op|区分1A|op|区分1|op|区分1B|列番|
		   |----------------|-----------|----|------|--|------|--|-----|--|------|----|
		   |区分１          |health     |危険|吸入る|>=|0.1   |>=|1.0  |>=|1.0   |41  |
		   |区分に該当しない|-          |-   |-     |>=|9999  |>=|9999 |>=|9999  |41  |
		   |----------------|-----------|----|------|--|------|--|-----|--|------|----|
		 */

		String sensitizationCol = null;
		for (int i = 4; i < (sensitizationLine.length - 2); i += 2) {

			String operator = sensitizationLine[i];
			//表は%表示なので、100で割って、ratioに合わせる
			float limitRatio = (Float.parseFloat(sensitizationLine[i + 1])) / 100;
			//表の見出し(区分1、区分1A、区分1B)を取得する。
			sensitizationCol = listSensitization.get(0)[i + 1];

			//gmiccsの皮膚感作性の列 (listSensitizationの最終列に書いてある列番号-1)
			// ここで、sensitizationColは初期化されてしまう。
			int kubunCol = Integer.parseInt(
					sensitizationLine[sensitizationLine.length - 1]) - 1;

			Float ratio = 0f;
			for (String[] line : listFilterdGmiccs) {
				String kubun = Normalizer.normalize(line[kubunCol],
						Normalizer.Form.NFKC);
				boolean boolCol = false;
				if (kubun.equals(sensitizationCol)) {
					ratio = Float.parseFloat(line[line.length - 1]);

					boolCol = this.boolJudge(operator, limitRatio, ratio);


					if (boolCol) {
						return sensitizationCol; //区分1Aなど
					}
				}
			}
		}


		// sensitizationCol は for文の中で引っかからなくても、  書き換えられてしまうので、
		//ここで nullに戻しておく。
		sensitizationCol = null;

		return sensitizationCol; //nullが返る
	}

	private boolean boolJudge(String comparisonOperator, float limitRatio,
			float caseRatio) {
		/*
		 * 表の比較演算子、表のATE（文字列）、ATEMixを受け取って、
		 * 判定する。比較演算子が"-"の時はtrue
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
