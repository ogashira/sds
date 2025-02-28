package ghs.hazardToEnv;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

class ShortTarm {
	private List<String[]> listShort;
	private float unknownRatio;
	private Map<String, List<String[]>> mapKubunRatioMulti;
	private float[] calcLimits;
	private CalcEnv calcEnv;
	private int[] rowCol;



	ShortTarm(List<String[]> listFilterdGmiccs, float nonListRatio){

		String filePath = GetPath.getPath("short");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listShort = dl.getCsvData();

		//unknownRatioを求めておく

		//区分の列番(表のlast2に記載)区分列番:110, 乗率列番:122
		int retuban = Integer.parseInt(
				            listShort.get(1)[listShort.get(0).length -2]);
		//gmiccsの列indexを求める
		int dataInfo = retuban -1; //区分あり、分類できない などの情報 列:47=index:46
		float nonRatio = 0f;
		for(String[] line : listFilterdGmiccs) {
			if(line[dataInfo].equals("分類できない")
					    || line[dataInfo].equals("")
					    || line[dataInfo] == null) {
				nonRatio += Float.parseFloat(line[line.length -1 ]);
			}
		}
		unknownRatio = nonListRatio + nonRatio;

		calcEnv = new CalcEnv(listShort);
		mapKubunRatioMulti = calcEnv.getMapKubunRatioMulti(listFilterdGmiccs);

		float kubun1M = this.getKubun1M();
		float kubun1M10Kubun2 = this.getKubun1M10Kubun2();
		float kubun1M100Kubun210Kubun3 = this.getKubun1M100Kubun210Kubun3();

		//配列を作っておく
		calcLimits = new float[3];
		calcLimits[0] = kubun1M;
		calcLimits[1] = kubun1M10Kubun2;
		calcLimits[2] = kubun1M100Kubun210Kubun3;

		rowCol = this.getRowCol();


	}

	Map<String, List<String[]>> returnMapKubunRatioMulti(){
		return mapKubunRatioMulti;
	}


	private float getKubun1M() {
		if(mapKubunRatioMulti.get("区分1") == null) {
			return 0;
		}

		List<String[]> ratioMultiCas = mapKubunRatioMulti.get("区分1");
		float kubun1M = 0f;
		for(String[] line : ratioMultiCas) {
			kubun1M += (Float.parseFloat(line[0]) * 100 * Float.parseFloat(line[1]));
		}
		return kubun1M;
	}

	private float getKubun1M10Kubun2() {
		float kubun1M10 = 0f;
		float kubun2 = 0f;
		if(mapKubunRatioMulti.get("区分1") == null) {
			kubun1M10 = 0;
		}else {
			for(String[] line : mapKubunRatioMulti.get("区分1")) {
				kubun1M10 +=
	         (Float.parseFloat(line[0]) * 100 * 10 * Float.parseFloat(line[1]));
			}
		}
		if(mapKubunRatioMulti.get("区分2") == null) {
			kubun2 = 0;
		} else {
			for(String[] line : mapKubunRatioMulti.get("区分2")) {
				kubun2 += Float.parseFloat(line[0]) * 100;
			}
		}
		return kubun1M10 + kubun2;
	}

	private float getKubun1M100Kubun210Kubun3() {
		float kubun1M100 = 0f;
		float kubun210 = 0f;
		float kubun3 = 0f;
		if(mapKubunRatioMulti.get("区分1") == null) {
			kubun1M100 = 0;
		}else {
			for(String[] line : mapKubunRatioMulti.get("区分1")) {
				kubun1M100 +=
	         (Float.parseFloat(line[0]) * 100 * 100 * Float.parseFloat(line[1]));
			}
		}
		if(mapKubunRatioMulti.get("区分2") == null) {
			kubun210 = 0;
		} else {
			for(String[] line : mapKubunRatioMulti.get("区分2")) {
				kubun210 += (Float.parseFloat(line[0]) * 100 * 10);
			}
		}
		if(mapKubunRatioMulti.get("区分3") == null) {
			kubun3 = 0;
		} else {
			for(String[] line : mapKubunRatioMulti.get("区分3")) {
				kubun3 += Float.parseFloat(line[0]) * 100;
			}
		}

		return kubun1M100 + kubun210 + kubun3;

	}

	private int[] getRowCol() {
		int[] rowCol = calcEnv.getRowCol(calcLimits);
		return rowCol;
	}

	Map<String,String> getMapShort(){
		Map<String, String> mapShort = calcEnv.getMapEnv(rowCol, unknownRatio);
		return mapShort;
	}

	List<String> getListShortCas(){
		List<String> listShortCas = calcEnv.getListEnvCas(
				                                  rowCol, mapKubunRatioMulti);
		return listShortCas;
	}




}
