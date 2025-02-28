package ghs.physicalHazards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import structures.StructSolDb;

public class HandOverPhysical {
	/*
	 * 物理化学的危険性データの受け渡しを行う。
	 * physicalHazardsパッケージでは唯一のpublicｸﾗｽ
	 */

	//FlammableLiquidまたはCombustibleSolidのインスタンス
	private IFPhysicalHazards hazardsObj;
	private Map<String, String> mapPhysical;

	//コンストラクタオーバーロード flashPoint, boilingPointが
	//渡ってきたら引火性液体をインスタンス化する
	public HandOverPhysical(float flashPoint, float boilingPoint,
			float ignitionPoint) {

		hazardsObj = Factory.create(flashPoint, boilingPoint, ignitionPoint);
		mapPhysical = hazardsObj.getMapPhysical();
	}

	//コンストラクタオーバーロード resultDbがわたってきたら
	//可燃性固体をインスタンス化する
	public HandOverPhysical(List<StructSolDb> resultDb) {

		hazardsObj = Factory.create(resultDb);
		mapPhysical = hazardsObj.getMapPhysical();

	}

	public Map<String, String> getPhysicalHazardsMap() {
		Map<String, String> physicalHazardsMap = hazardsObj.getPhysicalHazardsMap();
		return physicalHazardsMap;
	}

	public String getSyoubouhou() {
		return hazardsObj.getSyoubouhou();
	}

	public Map<String, String> getMapPhysical() {
		return mapPhysical;
	}

	public Map<String, List<String>> getMapFire(){
		Map<String, List<String>> mapFire = new HashMap<>();
		List<String> pict = new ArrayList<>();
		if(!mapPhysical.get("pictogram").equals("-")) {
			pict.add(mapPhysical.get("pictogram"));
		}
		mapFire.put("fire", pict);

		return mapFire;
	}


	public List<String> getListSignalPhysical(){
		List<String> listSignalPhysical = new ArrayList<>();
		listSignalPhysical.add(mapPhysical.get("signalWord"));
		return listSignalPhysical;
	}


	public String getStrHazardPhysical() {
		return mapPhysical.get("hazardInfo");
	}

}
