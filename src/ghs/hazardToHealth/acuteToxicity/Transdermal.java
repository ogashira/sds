package ghs.hazardToHealth.acuteToxicity;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

class Transdermal extends ABSAcuteToxicity {

	float unknownRatio;
	List<String[]> listTransdermal;
	float ateMix;
	Map<String, String> mapTransdermal;

	Transdermal(List<String[]> listFilterdGmiccs, float nonListRatio) {
		super(listFilterdGmiccs, nonListRatio);
		String filePath = GetPath.getPath("transdermal");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listTransdermal = dl.getCsvData();
		nonAteRatio = super.getNonAteRatio(listTransdermal);
		unknownRatio = nonListRatio + nonAteRatio;
		ateMix = super.getAteMix(listTransdermal, unknownRatio);
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
		mapTransdermal = super.getMapToxicity(listTransdermal, ateMix,
				unknownRatio);
		return mapTransdermal;
	}

}
