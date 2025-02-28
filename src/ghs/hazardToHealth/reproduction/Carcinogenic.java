package ghs.hazardToHealth.reproduction;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;
import ghs.hazardToHealth.sensitization.CalcSensitization;


public class Carcinogenic {
	private float nonListRatio;
	private List<String[]> listCarcinogenic;
	private CalcSensitization calcSensitization;

	Carcinogenic(List<String[]> listFilterdGmiccs, float nonListRatio) {

		String filePath = GetPath.getPath("carcinogenic");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listCarcinogenic = dl.getCsvData();

		this.nonListRatio = nonListRatio;

		calcSensitization = new CalcSensitization(
				                    listFilterdGmiccs, listCarcinogenic);
	}


	Map<String, List<String>> getMapCarcinogenicCas() {

		Map<String, List<String>> mapCarcinogenicCas = calcSensitization.getMapSensitizationCas();

//		return calcSensitization.getMapSensitizationCas();
		return mapCarcinogenicCas;
	}


	Map<String, String> getMapCarcinogenic() {
		Map<String, String> mapCarcinogenic = calcSensitization.getMapSensitization(nonListRatio);
		//return calcSensitization.getMapSensitization(nonListRatio);
		return mapCarcinogenic;
	}

}
