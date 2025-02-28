package ghs.physicalHazards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;
import structures.StructSolDb;

class CombustibleSolid extends ABSPhysicalHazards {

	private List<String[]> combustibleSolid;
	private String file;
	private String encode;
	private int index; //表の何行目か
	private List<StructSolDb> resultDb;

	CombustibleSolid(List<StructSolDb> resultDb) {
		//スーパークラスでphysicalHazardsが作られる。
		super();
		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからflashpoint.csvのﾊﾟｽを取得する
		file = GetPath.getPath("combustibleSolid");
		encode = "shift-jis";
		DownLoadCsv dlCsv = new DownLoadCsv(file, encode);
		combustibleSolid = dlCsv.getCsvData();
		this.resultDb = resultDb;
		index = this.getIndex(combustibleSolid, this.resultDb);

	}

	@Override
	public Map<String, String> getMapPhysical() {
		Map<String, String> mapPhysical = new HashMap<>();

		mapPhysical.put("ateMix", "0");
		mapPhysical.put("al", combustibleSolid.get(index)[0]);
		mapPhysical.put("kubun", combustibleSolid.get(index)[1]);
		mapPhysical.put("pictogram", combustibleSolid.get(index)[2]);
		mapPhysical.put("signalWord", combustibleSolid.get(index)[3]);
		mapPhysical.put("hazardInfo", combustibleSolid.get(index)[4]);

		return mapPhysical;

	}

	@Override
	public String getSyoubouhou() {

		return combustibleSolid.get(index)[5];
	}

	@Override
	public Map<String, String> getPhysicalHazardsMap() {
		//物理化学的危険性の表physicalHazardsから、固体
		//(3列目)のデータのHashMapを作る
		Map<String, String> physicalHazardsMap = new HashMap<>();
		for (int i = 1; i < physicalHazards.size(); i++) {
			physicalHazardsMap.put(physicalHazards.get(i)[0],
					physicalHazards.get(i)[2]);
			//line[0]は項目、line[2]は3列目だから、固体のデータ
			//１行目の項目は抜くのでi=1から

		}
		String physicalKubun = combustibleSolid.get(index)[1];
		physicalHazardsMap.put("可燃性固体", physicalKubun);

		return physicalHazardsMap;
	}

	private int getIndex(List<String[]> combustibleSolid,
			List<StructSolDb> resultDb) {

		int index = 0;
		for (String[] line : combustibleSolid) {
			for (StructSolDb result : resultDb) {
				if (line[0].equals(result.hinban))
					return index;
			}
			index++;
		}
		try {
			if (index == 0) {
				throw new Exception("引火性固体のindex検索でエラーです");
			}
		} catch (Exception e) {
			System.out.println("indexが0のため中止です。");
			System.exit(0);
		}
		return index;
	}
}
