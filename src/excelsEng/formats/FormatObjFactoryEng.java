package excelsEng.formats;

import structures.StructInputForm;

public class FormatObjFactoryEng {

	public static IFSdsFormatEng create(StructInputForm inputForm) {

		IFSdsFormatEng sdsFormat = null;
		switch (inputForm.paintType) {
		case "Ippan":
			sdsFormat = new IppanEng(inputForm);
			break;
		case "Yusei":
			sdsFormat = new YuseiEng(inputForm);
			break;
		case "Metal":
			sdsFormat = new MetalEng(inputForm);
			break;
		case "Bpo":
			sdsFormat = new BpoEng(inputForm);
			break;
		}
		return sdsFormat;
	}
}
