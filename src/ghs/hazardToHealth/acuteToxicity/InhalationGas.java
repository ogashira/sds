package ghs.hazardToHealth.acuteToxicity;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

class InhalationGas extends ABSAcuteToxicity {

	float unknownRatio;
	List<String[]> listGas;
	float ateMix;
	Map<String, String> mapInhalationGas;

	InhalationGas(List<String[]> listFilterdGmiccs, float nonListRatio) {
		super(listFilterdGmiccs, nonListRatio);

		String filePath = GetPath.getPath("inhalationGas");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listGas = dl.getCsvData();
		nonAteRatio = super.getNonAteRatio(listGas);
		unknownRatio = nonListRatio + nonAteRatio;
		ateMix = 0;

	}

	@Override
	float getUnknownRatio() {
		return unknownRatio;
	}

	@Override
	float getAteMix() {
		return ateMix;
	}

	@Override
	Map<String, String> getMapToxicity() {
		mapInhalationGas = super.getMapToxicity(listGas, ateMix, unknownRatio);
		return mapInhalationGas;
	}

}
