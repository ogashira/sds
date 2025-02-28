package ghs.hazardToHealth.acuteToxicity;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

class Oral extends ABSAcuteToxicity {

	float unknownRatio;
	List<String[]> listOral;
	float ateMix;
	Map<String, String> mapOral;

	Oral(List<String[]> listFilterdGmiccs, float nonListRatio) {
		//nonListRatio: gmiccsに載っていない原料の合計(float)
		super(listFilterdGmiccs, nonListRatio);

		String filePath = GetPath.getPath("oral");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listOral = dl.getCsvData();
		// nonAteRatio 区分が1~5以外でATEが空白である原料の合計量
		nonAteRatio = super.getNonAteRatio(listOral);
		unknownRatio = nonListRatio + nonAteRatio;
		ateMix = super.getAteMix(listOral, unknownRatio);

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
		mapOral = super.getMapToxicity(listOral, ateMix, unknownRatio);
		return mapOral;
	}

}
