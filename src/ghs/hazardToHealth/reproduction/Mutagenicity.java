package ghs.hazardToHealth.reproduction;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;
import ghs.hazardToHealth.sensitization.CalcSensitization;

public class Mutagenicity {

	private float nonListRatio;
	private List<String[]> listMutagenicity;
	private CalcSensitization calcSensitization;

	Mutagenicity(List<String[]> listFilterdGmiccs, float nonListRatio) {

		String filePath = GetPath.getPath("mutagenicity");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listMutagenicity = dl.getCsvData();

		this.nonListRatio = nonListRatio;

		calcSensitization = new CalcSensitization(
				                    listFilterdGmiccs, listMutagenicity);
	}


	Map<String, List<String>> getMapMutagenicityCas() {

		return calcSensitization.getMapSensitizationCas();
	}


	Map<String, String> getMapMutagenicity() {

		return calcSensitization.getMapSensitization(nonListRatio);
	}
}
