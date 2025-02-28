package components.calcComponents;

import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

public class SolventDb {

	private List<String[]> solventDbData;
	private List<String[]> solventDbAll;
	private String file;
	private String encode;
	private Map<String, Float> dupliBunkai;

	/*
	List<String[]> solventDbDataは、原料分解して、重複を削除したMap<String,
	Float> dupliBunkaiのｷｰで抽出した溶剤dbのﾃﾞｰﾀ。
	*/

	public SolventDb(Map<String, Float> dupliBunkai) {

		this.dupliBunkai = dupliBunkai;
		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからflashpoint.csvのﾊﾟｽを取得する
		file = GetPath.getPath("solDb");
		encode = "UTF-8";
		DownLoadCsv dlCsv = new DownLoadCsv(file, encode);
		//仕掛品原料の重複を除いた溶剤dBのﾃﾞｰﾀ
		solventDbData = dlCsv.getCsvData(this.dupliBunkai);

	}

	public SolventDb() {

		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからflashpoint.csvのﾊﾟｽを取得する
		file = GetPath.getPath("solDb");
		encode = "UTF-8";
		DownLoadCsv dlCsv = new DownLoadCsv(file, encode);
		//仕掛品原料の重複を除いた溶剤dBのﾃﾞｰﾀ
		solventDbAll = dlCsv.getCsvData();
	}

	public List<String[]> getSolventDbData() {
		return solventDbData;
	}

	public List<String[]> getSolventDbAll() {
		return solventDbAll;
	}

}
