package components.calcComponents;

import java.util.ArrayList;
import java.util.List;

import structures.StructPhysicalData;
import structures.StructSolDb;

public class ImpMaxValue implements IFPhysicalValue {

	private String physicalItem;

	public ImpMaxValue(String physicalItem) {
		this.physicalItem = physicalItem;
	}

	private float getItemValue(StructSolDb line) {
		float itemValue = -9999f;

		switch (this.physicalItem) {
		case "爆発限界上限値":
			itemValue = line.expUpperLimit;
			break;
		case "蒸気圧":
			itemValue = line.vaporPressure;
			break;
		case "蒸気比重":
			itemValue = line.steamSg;
			break;
		case "蒸発速度":
			itemValue = line.evaporationRate;
			break;
		}

		return itemValue;
	}

	public StructPhysicalData getStructPhysicalData(List<StructSolDb> resultDb) {
		StructPhysicalData structPhysicalData = new StructPhysicalData();
		float maxData = -999f;
		List<String> maxSolvents = new ArrayList<>();
		List<String> maxSolventsEng = new ArrayList<>();
		List<String> maxSolventsChn = new ArrayList<>();

		for (StructSolDb line : resultDb) {
			float itemValue = this.getItemValue(line);

			if (itemValue == maxData) {
				maxSolvents.add(line.sdsDisplayName);
				maxSolventsEng.add(line.sdsNameEng);
				maxSolventsChn.add(line.chn);
			} else if (itemValue > maxData) {
				maxData = itemValue;
				maxSolvents.clear();
				maxSolventsEng.clear();
				maxSolventsChn.clear();
				maxSolvents.add(line.sdsDisplayName);
				maxSolventsEng.add(line.sdsNameEng);
				maxSolventsChn.add(line.chn);
			}
		}
		structPhysicalData.item = this.physicalItem;
		structPhysicalData.value = maxData;
		structPhysicalData.applicableSolvent = maxSolvents;
		structPhysicalData.applicableSolventEng = maxSolventsEng;
		structPhysicalData.applicableSolventChn = maxSolventsChn;

		return structPhysicalData;
	}
}
