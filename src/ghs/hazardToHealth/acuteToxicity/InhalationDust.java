package ghs.hazardToHealth.acuteToxicity;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

class InhalationDust extends ABSAcuteToxicity {

	float unknownRatio;
	List<String[]> listDust;
	float ateMix;
	Map<String, String> mapInhalationDust;

	InhalationDust(List<String[]> listFilterdGmiccs, float nonListRatio) {
		super(listFilterdGmiccs, nonListRatio);

		String filePath = GetPath.getPath("inhalationDust");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listDust = dl.getCsvData();
		nonAteRatio = super.getNonAteRatio(listDust);
		unknownRatio = nonListRatio + nonAteRatio;
		ateMix = super.getAteMix(listDust, unknownRatio);
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
		mapInhalationDust = super.getMapToxicity(listDust, ateMix,
				unknownRatio);
		return mapInhalationDust;
	}
}
