package ghs.hazardToHealth.sensitization;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

public class RespiratorySensitizationGas {

	float nonListRatio;
	List<String[]> listGasSensitization;
	CalcSensitization calcSensitization;


	RespiratorySensitizationGas(List<String[]> listFilterdGmiccs,
			                                           float nonListRatio) {

		String filePath = GetPath.getPath("respiratorySensitizationGas");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listGasSensitization = dl.getCsvData();

		this.nonListRatio = nonListRatio;
		calcSensitization = new CalcSensitization(
				                   listFilterdGmiccs, listGasSensitization);

	}

	Map<String, List<String>> getMapGasSensitizationCas() {

		return calcSensitization.getMapSensitizationCas();
	}


	Map<String, String> getMapGasSensitization() {

		return calcSensitization.getMapSensitization(nonListRatio);
	}
}
