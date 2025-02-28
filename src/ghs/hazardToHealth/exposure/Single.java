package ghs.hazardToHealth.exposure;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

public class Single {

	private List<String[]> listSingle;
	private CalcExposure calcExposure;
	private Map<String,List<String[]>> mapKubunOrganCas;
	private float unknownRatio;


	Single(List<String[]> listFilterdGmiccs, float nonListRatio) {

		String filePath = GetPath.getPath("single");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listSingle = dl.getCsvData();


		calcExposure = new CalcExposure(
				                      listFilterdGmiccs, listSingle);
		mapKubunOrganCas = calcExposure.returnMapKubunOrganCas();

		//unknownRatioを求めておく

		//スタートの列番(表のlast2とlastに記載) stt:47
		int sttRetuban = Integer.parseInt(
				            listSingle.get(1)[listSingle.get(0).length -2]);
		//gmiccsの列indexを求める
		int dataInfo = sttRetuban -1; //区分あり、分類できない などの情報 列:47=index:46
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

	Map<String, String> getMapSingle1(){
		return calcExposure.getMapExposure("区分1", unknownRatio);
	}
	Map<String, String> getMapSingle2(){
		return calcExposure.getMapExposure("区分2", unknownRatio);
	}
	Map<String, String> getMapSingle3(){
		return calcExposure.getMapExposure("区分3", unknownRatio);
	}



	List<String> getListOrganSingle1(){
		return calcExposure.returnListOrgan1();
	}
	List<String> getListOrganSingle2(){
		return calcExposure.returnListOrgan2();
	}
	List<String> getListCasSingle1(){
		return calcExposure.returnListCas1();
	}
	List<String> getListCasSingle2(){
		return calcExposure.returnListCas2();
	}
	List<String> getListOrganSingle3(){
		return calcExposure.returnListOrganSingle3();
	}
	List<String> getListCasSingle3(){
		return calcExposure.returnListCasSingle3();
	}






	/*
	Map<String, List<String>> getMapSkinSensitizationCas() {

		return calcSensitization.getMapSensitizationCas();
	}


	Map<String, String> getMapSkinSensitization() {

		return calcSensitization.getMapSensitization(nonListRatio);
	}
	*/
}
