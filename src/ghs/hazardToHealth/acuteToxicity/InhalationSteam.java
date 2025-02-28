package ghs.hazardToHealth.acuteToxicity;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

class InhalationSteam extends ABSAcuteToxicity {

	float unknownRatio;
	List<String[]> listSteam;
	float ateMix;
	Map<String, String> mapInhalationSteam;

	InhalationSteam(List<String[]> listFilterdGmiccs, float nonListRatio) {
		super(listFilterdGmiccs, nonListRatio);

		String filePath = GetPath.getPath("inhalationSteam");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listSteam = dl.getCsvData();
		nonAteRatio = super.getNonAteRatio(listSteam);
		unknownRatio = nonListRatio + nonAteRatio;
		ateMix = super.getAteMix(listSteam, unknownRatio);
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
		mapInhalationSteam = super.getMapToxicity(listSteam, ateMix,
				unknownRatio);

		return mapInhalationSteam;
	}

}
