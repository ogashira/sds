package ghs.hazardToHealth.sensitization;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

class SkinSensitization {

	private float nonListRatio;
	private List<String[]> listSkinSensitization;
	private CalcSensitization calcSensitization;

	SkinSensitization(List<String[]> listFilterdGmiccs, float nonListRatio) {

		String filePath = GetPath.getPath("skinSensitization");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listSkinSensitization = dl.getCsvData();

		this.nonListRatio = nonListRatio;

		calcSensitization = new CalcSensitization(
				                      listFilterdGmiccs, listSkinSensitization);
	}


	Map<String, List<String>> getMapSkinSensitizationCas() {

		return calcSensitization.getMapSensitizationCas();
	}


	Map<String, String> getMapSkinSensitization() {

		return calcSensitization.getMapSensitization(nonListRatio);
	}
}

