package ghs.hazardToHealth.reproduction;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;
import ghs.hazardToHealth.sensitization.CalcSensitization;

public class Milk {

	private float nonListRatio;
	private List<String[]> listMilk;
	private CalcSensitization calcSensitization;

	Milk(List<String[]> listFilterdGmiccs, float nonListRatio) {

		String filePath = GetPath.getPath("milk");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listMilk = dl.getCsvData();

		this.nonListRatio = nonListRatio;

		calcSensitization = new CalcSensitization(
				                    listFilterdGmiccs, listMilk);

	}


	Map<String, List<String>> getMapMilkCas() {

		return calcSensitization.getMapSensitizationCas();
	}


	Map<String, String> getMapMilk() {

		return calcSensitization.getMapSensitization(nonListRatio);
	}
}
