package excels.formats;

import structures.StructInputForm;

public class FormatObjFactory {

	public static IFSdsFormat create(StructInputForm inputForm) {

		IFSdsFormat sdsFormat = null;
		switch (inputForm.paintType) {
		case "Ippan":
			sdsFormat = new IppanJap(inputForm);
			break;
		case "Yusei":
			sdsFormat = new YuseiJap(inputForm);
			break;
		case "Metal":
			sdsFormat = new MetalJap(inputForm);
			break;
		case "Bpo":
			sdsFormat = new BpoJap(inputForm);
			break;
		}
		return sdsFormat;
	}
}
