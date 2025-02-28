package ghs.hazardToHealth.reproduction;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;
import ghs.hazardToHealth.sensitization.CalcSensitization;

public class ReproductiveToxicity {

	private float nonListRatio;
	private List<String[]> listReproductiveToxicity;
	private CalcSensitization calcSensitization;

	ReproductiveToxicity(List<String[]> listFilterdGmiccs, float nonListRatio) {

		String filePath = GetPath.getPath("reproductiveToxicity");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listReproductiveToxicity = dl.getCsvData();

		this.nonListRatio = nonListRatio;

		calcSensitization = new CalcSensitization(
				                    listFilterdGmiccs, listReproductiveToxicity);
	}


	Map<String, List<String>> getMapReproductiveToxicityCas() {

		return calcSensitization.getMapSensitizationCas();
	}


	Map<String, String> getMapReproductiveToxicity() {

		return calcSensitization.getMapSensitization(nonListRatio);
	}
}
