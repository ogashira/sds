package ghs.hazardToHealth.sensitization;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

public class RespiratorySensitizationLiquid {


	private float nonListRatio;
	private List<String[]> listLiquidSensitization;
	private CalcSensitization calcSensitization;

	RespiratorySensitizationLiquid(List<String[]> listFilterdGmiccs,
			                                           float nonListRatio) {

		String filePath = GetPath.getPath("respiratorySensitizationLiquid");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listLiquidSensitization = dl.getCsvData();

		this.nonListRatio = nonListRatio;

		calcSensitization = new CalcSensitization(
				listFilterdGmiccs, listLiquidSensitization);
	}

	Map<String, List<String>> getMapLiquidSensitizationCas() {

		return calcSensitization.getMapSensitizationCas();
	}

	Map<String, String> getMapLiquidSensitization() {

		return calcSensitization.getMapSensitization(nonListRatio);
	}
}
