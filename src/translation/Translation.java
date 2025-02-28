package translation;

import java.util.List;

import csves.DownLoadCsv;
import csves.GetPath;

public class Translation {

	List<String[]> listTranslation;

	public Translation() {
		String filePath = GetPath.getPath("translationDb");
		DownLoadCsv dl = new DownLoadCsv(filePath, "utf-8");
		listTranslation = dl.getCsvData();
	}


	public String getEng(String jap) {
		String eng = "";

		if(jap.equals("")) {
			return "";
		}
		for(String[] line : listTranslation) {
			if(line[0].equals(jap)) {
				return line[3];
			}
		}

		System.out.println("「" + jap + "」" +  "を英訳できませんでした。");

		return eng;
	}


	public String getChn(String jap) {
		String chn = "";

		if(jap.equals("")) {
			return "";
		}
		for(String[] line : listTranslation) {
			if(line[0].equals(jap)) {
				return line[2];
			}
		}

		System.out.println("「" + jap + "」" +  "を中訳できませんでした。");

		return chn;
	}


	public String getTwn(String jap) {
		String twn = "";

		if(jap.equals("")) {
			return "";
		}
		for(String[] line : listTranslation) {
			if(line[0].equals(jap)) {
				return line[4];
			}
		}
		return twn;
	}
	public String getVet(String jap) {
		String vet = "";

		if(jap.equals("")) {
			return "";
		}
		for(String[] line : listTranslation) {
			if(line[0].equals(jap)) {
				return line[5];
			}
		}
		return vet;
	}
}
