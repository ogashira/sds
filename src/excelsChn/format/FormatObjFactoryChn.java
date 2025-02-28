package excelsChn.format;

import structures.StructInputForm;

public class FormatObjFactoryChn {

	public static IFSdsFormatChn create(StructInputForm inputForm) {

		IFSdsFormatChn sdsFormat = null;
		switch (inputForm.paintType) {
		case "Ippan":
			sdsFormat = new IppanChn(inputForm);
			break;
		case "Yusei":
			sdsFormat = new YuseiChn(inputForm);
			break;
		case "Metal":
			sdsFormat = new MetalChn(inputForm);
			break;
		case "Bpo":
			sdsFormat = new BpoChn(inputForm);
			break;
		}
		return sdsFormat;
	}
}
