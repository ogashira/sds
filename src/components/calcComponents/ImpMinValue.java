package components.calcComponents;

import java.util.ArrayList;
import java.util.List;

import structures.StructPhysicalData;
import structures.StructSolDb;

public class ImpMinValue implements IFPhysicalValue {

	private String physicalItem;

	public ImpMinValue(String physicalItem) {
		this.physicalItem = physicalItem;
	}

	private float getItemValue(StructSolDb line) {
		float itemValue = 9999f;

		switch (this.physicalItem) {
		case "引火点":
			itemValue = line.flashPoint;
			break;
		case "発火点":
			itemValue = line.ignitionPoint;
			break;
		case "爆発限界下限値":
			itemValue = line.expLowerLimit;
			break;
		case "沸点":
			itemValue = line.boilPoint;
			break;
		case "OSHA PEL":
			itemValue = line.oshaPel;
			break;
		case "ACGIH TWA":
			itemValue = line.acgih;
			break;
		case "日本産業衛生学会":
			itemValue = line.niSsanEi;
			break;
		case "急性毒性LD50":
			itemValue = line.ld50;
			break;
		}

		return itemValue;
	}

	public StructPhysicalData getStructPhysicalData(List<StructSolDb> listStructSol) {
		StructPhysicalData structPhysicalData = new StructPhysicalData();
		float minData = 999f;
		List<String> minSolvents = new ArrayList<>();
		List<String> minSolventsEng = new ArrayList<>();
		List<String> minSolventsChn = new ArrayList<>();

		for (StructSolDb line : listStructSol) {
			if (!(line.ratio == 0)) {
				float itemValue = this.getItemValue(line);

				if (itemValue == minData) {
					minSolvents.add(line.sdsDisplayName);
					minSolventsEng.add(line.sdsNameEng);
					minSolventsChn.add(line.chn);
				} else if (itemValue < minData) {
					minData = itemValue;
					minSolvents.clear();
					minSolventsEng.clear();
					minSolventsChn.clear();
					minSolvents.add(line.sdsDisplayName);
					minSolventsEng.add(line.sdsNameEng);
					minSolventsChn.add(line.chn);
				}
			}
		}
		structPhysicalData.item = this.physicalItem;
		structPhysicalData.value = minData;
		structPhysicalData.applicableSolvent = minSolvents;
		structPhysicalData.applicableSolventEng = minSolventsEng;
		structPhysicalData.applicableSolventChn = minSolventsChn;

		return structPhysicalData;
	}
}
