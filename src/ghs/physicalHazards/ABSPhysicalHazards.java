package ghs.physicalHazards;

import java.util.List;

import csves.DownLoadCsv;
import csves.GetPath;

abstract class ABSPhysicalHazards implements
		IFPhysicalHazards {

	/*
	 * IFPhysicalHazardsを実装する。
	 * インターフェースの具象メソッドは、これを継承したサブクラス
	 * が実装する。FlammableLiquid, CombustibleSolid
	 * サブクラスと共用しているデータは、physicalHazardsMap
	 * で、これはコンストラクタで作る。
	 */
	protected List<String[]> physicalHazards;
	private String file;
	private String encode;

	ABSPhysicalHazards() {

		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからphysicalHazards.csvのﾊﾟｽを取得する
		file = GetPath.getPath("physicalHazards");
		encode = "shift-JIS";
		DownLoadCsv dlCsv = new DownLoadCsv(file, encode);
		physicalHazards = dlCsv.getCsvData();

	}

}
