package ghs.hazardToEnv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CalcEnv {
	/*
	 * 短期有害、長期有害の時に呼び出して使う。
	 * ｵｿﾞﾝ層は、getRowCol, getMapEnvのみ使う。
	 */

	private List<String[]> listEnv;//listShort or listLong


	CalcEnv(List<String[]> listEnv) {
		this.listEnv = listEnv;
	}

	Map<String, List<String[]>> getMapKubunRatioMulti(
			                                  List<String[]> listFilterdGmiccs){
		Map<String, List<String[]>> mapKubunRatioMulti = new HashMap<>();

		if(listFilterdGmiccs.size() == 0) {
			//Gmiccsに原料が一つも無い場合はmapKubunRatioMultiを自作してreturn
			String[] ratioMultiCas = {"0", "0", ""};
			List<String[]> tempList = new ArrayList<>();
			tempList.add(ratioMultiCas);

			mapKubunRatioMulti.put("区分1", tempList);
			return mapKubunRatioMulti;
		}

		int kubunIndex = Integer.parseInt(
				              listEnv.get(1)[listEnv.get(0).length -2]) -1;
		int multiIndex = Integer.parseInt(
				              listEnv.get(1)[listEnv.get(0).length -1]) -1; //M値の列
		int ratioIndex = listFilterdGmiccs.get(0).length -1 ; //Gmiccsの最後の列

		for(String[] line : listFilterdGmiccs) {
			String cas   = line[2];
			String kubun = line[kubunIndex];
			String multi = line[multiIndex];
			String ratio = line[ratioIndex];
			String[] ratioMultiCas = {ratio, multi, cas};

			if(mapKubunRatioMulti.get(kubun) == null) {
				List<String[]> listRatioMulti = new ArrayList<>();
				listRatioMulti.add(ratioMultiCas);
				mapKubunRatioMulti.put(kubun, listRatioMulti);
			}else {
				List<String[]> tempList = mapKubunRatioMulti.get(kubun);
				tempList.add(ratioMultiCas);
				mapKubunRatioMulti.put(kubun, tempList );
			}
		}
		return mapKubunRatioMulti;
	}


	int[] getRowCol(float[] calcLimits) {
		//calcLimits = {kubun1M, kubun1M10Kubun2, kubun1M100Kubun210Kubun3}
		int rowCol[] = {listEnv.size() -1 , 0};
		int i;
		int j;
		boolean isExist = false;
		for(i = 4; i<listEnv.get(0).length - 2; i+=2) {
			//calcLimitsの要素数をiで表すと(i-4)/2となる
			float calcLimit = calcLimits[(i-4)/2];
			for(j = 1; j<listEnv.size() -1; j++ ) {
				String operator = listEnv.get(j)[i];
				float tableLimit = Float.parseFloat(listEnv.get(j)[i+1]);
				isExist = boolJudge(operator, tableLimit, calcLimit);
				if(isExist) {
					rowCol[0] = j;
					rowCol[1] = i + 1;
					return rowCol;
				}
			}
		}
		return rowCol;
	}

	private boolean boolJudge(String operator, float tableLimit,
			float calcLimit) {
		/*
		 * 表の比較演算子、表のATE（文字列）、ATEMixを受け取って、
		 * 判定する。比較演算子が"-"の時はtrue
		 */
		if (operator.equals("<=")) {
			if (calcLimit <= tableLimit) {
				return true;
			}
			return false;
		}
		if (operator.equals("<")) {
			if (calcLimit < tableLimit) {
				return true;
			}
			return false;
		}
		if (operator.equals(">=")) {
			if (calcLimit >= tableLimit) {
				return true;
			}
			return false;
		}
		if (operator.equals(">")) {
			if (calcLimit > tableLimit) {
				return true;
			}
			return false;
		}

		return false;
	}

	Map<String, String> getMapEnv(int[] rowCol, float unknownRatio){
		Map<String, String> mapEnv = new HashMap<>();
		//ozoneの場合はgetMapOzoneを使う

		//全ての成分にデータまたは情報が全くない場合は、「分類できない」
		if(unknownRatio == 1) {
			mapEnv.put("kubun", "分類できない");
			mapEnv.put("pictogram", "-");
			mapEnv.put("signalWord", "-");
			mapEnv.put("hazardInfo", "-");
			return mapEnv;
		}

		int myRow = rowCol[0];

		mapEnv.put("kubun", listEnv.get(myRow)[0]);
		mapEnv.put("pictogram", listEnv.get(myRow)[1]);
		mapEnv.put("signalWord", listEnv.get(myRow)[2]);
		mapEnv.put("hazardInfo", listEnv.get(myRow)[3]);

		//分類処理をした結果、区分が得られなかった場合で、未知の成分を
		//含有している場合は、「分類できない」とする
		if(unknownRatio >= 0.01 && mapEnv.get("kubun").contains("区分に該当しない")) {
			mapEnv.put("kubun", "分類できない");
		}

		return mapEnv;
	}

	Map<String, String> getMapOzone(int[] rowCol, float unknownRatio){
		Map<String, String> mapEnv = new HashMap<>();
		//ozoneの場合はこっちを使う

		//全ての成分にデータまたは情報が全くない場合は、「分類できない」
		if(unknownRatio == 1) {
			mapEnv.put("kubun", "分類できない");
			mapEnv.put("pictogram", "-");
			mapEnv.put("signalWord", "-");
			mapEnv.put("hazardInfo", "-");
			return mapEnv;
		}

		int myRow = rowCol[0];

		mapEnv.put("kubun", listEnv.get(myRow)[0]);
		mapEnv.put("pictogram", listEnv.get(myRow)[1]);
		mapEnv.put("signalWord", listEnv.get(myRow)[2]);
		mapEnv.put("hazardInfo", listEnv.get(myRow)[3]);


		return mapEnv;
	}
	List<String> getListEnvCas(int[] rowCol,
			                     Map<String,List<String[]>> mapKubunRatioMulti){
		int myCol = rowCol[1];
		String colHeadline = listEnv.get(0)[myCol];

		//表の列見出しに"区分1","区分2","区分3"という文字が含まれるか
		//を調べて、含まれる場合はそれぞれのcasをlistに追加する
		List<String> listEnvCas = new ArrayList<>();

		if(colHeadline.contains("区分1")) {
			if(mapKubunRatioMulti.get("区分1") != null) {
				for(String[] line : mapKubunRatioMulti.get("区分1")) {
					listEnvCas.add(line[2]);
				}
			}
		}
		if(colHeadline.contains("区分2")) {
			if(mapKubunRatioMulti.get("区分2") != null) {
				for(String[] line : mapKubunRatioMulti.get("区分2")) {
					listEnvCas.add(line[2]);
				}
			}
		}
		if(colHeadline.contains("区分3")) {
			if(mapKubunRatioMulti.get("区分3") != null) {
				for(String[] line : mapKubunRatioMulti.get("区分3")) {
					listEnvCas.add(line[2]);
				}
			}
		}
		return listEnvCas;
	}
}
