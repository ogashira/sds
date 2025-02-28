package tools;

import java.text.Normalizer;

public class ToFloat {

	public static float getToFloat(String str) throws NumberFormatException {
		str = Normalizer.normalize(str, Normalizer.Form.NFKC);
		str = str.replaceAll("．", ".");
		str = str.replaceAll("([^0-9.])", "");
		float floatPercent = Float.parseFloat(str);

		return floatPercent;
	}

	public static String zenToHan(String str) {
		//nullが渡されたら、nullを返す
		String strHan = null;
		if (str != null) {
			strHan = Normalizer.normalize(str, Normalizer.Form.NFKC);
		}
		return strHan;
	}
}
