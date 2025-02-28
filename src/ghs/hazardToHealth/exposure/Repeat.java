package ghs.hazardToHealth.exposure;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

public class Repeat {

	private List<String[]> listRepeat;
	private CalcExposure calcExposure;
	private Map<String,List<String[]>> mapKubunOrganCas;
	private float unknownRatio;


	Repeat(List<String[]> listFilterdGmiccs, float nonListRatio) {

		String filePath = GetPath.getPath("repeat");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listRepeat = dl.getCsvData();


		calcExposure = new CalcExposure(
				                      listFilterdGmiccs, listRepeat);
		mapKubunOrganCas = calcExposure.returnMapKubunOrganCas();

		//unknownRatioを求めておく

		//スタートの列番(表のlast2とlastに記載) stt:78
		int sttRetuban = Integer.parseInt(
				            listRepeat.get(1)[listRepeat.get(0).length -2]);
		//gmiccsの列indexを求める
		int dataInfo = sttRetuban -1; //区分あり、分類できない などの情報 列:78=index:77
		float nonRatio = 0f;
		for(String[] line : listFilterdGmiccs) {
			if(line[dataInfo].equals("分類できない")
					    || line[dataInfo].equals("")
					    || line[dataInfo] == null) {
				nonRatio += Float.parseFloat(line[line.length -1 ]);
			}
		unknownRatio = nonListRatio + nonRatio;

		}
	}


	Map<String, List<String[]>> returnMapKubunOrganCas(){
		return mapKubunOrganCas;
	}

	Map<String, String> getMapRepeat1(){
		return calcExposure.getMapExposure("区分1", unknownRatio);
	}
	Map<String, String> getMapRepeat2(){
		return calcExposure.getMapExposure("区分2", unknownRatio);
	}



	List<String> getListOrganRepeat1(){
		return calcExposure.returnListOrgan1();
	}
	List<String> getListOrganRepeat2(){
		return calcExposure.returnListOrgan2();
	}
	List<String> getListCasRepeat1(){
		return calcExposure.returnListCas1();
	}
	List<String> getListCasRepeat2(){
		return calcExposure.returnListCas2();
	}
}
