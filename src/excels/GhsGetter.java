package excels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ghs.HandOverGhs;
import structures.StructInputForm;
import structures.StructPhysicalData;
import structures.StructSolDb;

public class GhsGetter {

	private HandOverGhs handOverGhsPhysical;
	private HandOverGhs handOverGhsHealth;
	private HandOverGhs handOverGhsEnv;
	private StructInputForm inputForm;
	private List<StructSolDb> resultDb;
	private List<StructPhysicalData> listPhysicalData;

	public GhsGetter(StructInputForm inputForm,
			List<StructSolDb> listStructSol,
			List<StructSolDb> resultDb,
			List<StructPhysicalData> listPhysicalData
			) {

		this.inputForm = inputForm;
		this.resultDb = resultDb;
		this.listPhysicalData = listPhysicalData;

		float flashPoint = 9999;
		float boilingPoint = 9999;
		float ignitionPoint = 9999;

		for (StructPhysicalData line : this.listPhysicalData) {
			if (line.item.equals("引火点")) {
				flashPoint = line.value;
			}
			if (line.item.equals("沸点")) {
				boilingPoint = line.value;
			}
			if (line.item.equals("発火点")) {
				ignitionPoint = line.value;
			}
		}

		/*
		 * Single, -MT- ならば、resultDbを与えて、固体をインスタンス化する
		 * そうでなければ、flashPoint, boilingPoint を与えて液体を
		 * インスタンス化する。
		 */
		if (this.inputForm.singleMixture.equals("Single") &&
				inputForm.hinban.contains("-MT-")) {
			handOverGhsPhysical = new HandOverGhs(this.resultDb, "physical");
		} else {
			handOverGhsPhysical = new HandOverGhs(flashPoint, boilingPoint, ignitionPoint);
		}

		handOverGhsHealth = new HandOverGhs(listStructSol, "health"); //overLoad
		handOverGhsEnv = new HandOverGhs(listStructSol); //overLoad

	}


	public Map<String, String> getPhysicalHazards() {

		Map<String, String> physicalHazardsMap;
		physicalHazardsMap = handOverGhsPhysical.getPhysicalHazardsMap();

		return physicalHazardsMap;
	}


	public Map<String, String> getMapOral(){
		return handOverGhsHealth.getMapOral();
	}
	public Map<String, String> getMapTransdermal(){
		return handOverGhsHealth.getMapTransdermal();
	}
	public Map<String, String> getMapInhalationGas(){
		return handOverGhsHealth.getMapInhalationGas();
	}
	public Map<String, String> getMapInhalationSteam(){
		return handOverGhsHealth.getMapInhalationSteam();
	}
	public Map<String, String> getMapInhalationDust(){
		return handOverGhsHealth.getMapInhalationDust();
	}


	public Map<String, String> getMapSkinIrritation() {
		return handOverGhsHealth.getMapSkinIrritation();
	}


	public List<String> getSkinCas(){
		return handOverGhsHealth.getSkinCas();
	}

	public Map<String, String> getMapEyeIrritation(){
		return handOverGhsHealth.getMapEyeIrritation();
	}
	public List<String> getEyeCas(){
		return handOverGhsHealth.getEyeCas();
	}

	public Map<String, String> getMapLiquidSensitization(){
		return handOverGhsHealth. getMapLiquidSensitization();
	}
	public Map<String,List<String>> getMapLiquidSensitizationCas(){
		return handOverGhsHealth .getMapLiquidSensitizationCas();
	}


	public Map<String, String> getMapGasSensitization(){
		return handOverGhsHealth. getMapGasSensitization();
	}

	public Map<String,List<String>> getMapGasSensitizationCas(){
		return handOverGhsHealth .getMapGasSensitizationCas();
	}

	public Map<String, String> getMapSkinSensitization(){
		return handOverGhsHealth. getMapSkinSensitization();
	}

	public Map<String,List<String>> getMapSkinSensitizationCas(){
		return handOverGhsHealth .getMapSkinSensitizationCas();
	}

	public Map<String, String> getMapMutagenicity(){
		return handOverGhsHealth. getMapMutagenicity();
	}

	public Map<String,List<String>> getMapMutagenicityCas(){
		return handOverGhsHealth .getMapMutagenicityCas();
	}


	public Map<String, String> getMapCarcinogenic(){
		return handOverGhsHealth. getMapCarcinogenic();
	}

	public Map<String,List<String>> getMapCarcinogenicCas(){
		return handOverGhsHealth .getMapCarcinogenicCas();
	}

	public Map<String, String> getMapReproductiveToxicity(){
		return handOverGhsHealth. getMapReproductiveToxicity();
	}

	public Map<String,List<String>> getMapReproductiveToxicityCas(){
		return handOverGhsHealth .getMapReproductiveToxicityCas();
	}

	public Map<String, String> getMapMilk(){
		return handOverGhsHealth. getMapMilk();
	}

	public Map<String,List<String>> getMapMilkCas(){
		return handOverGhsHealth .getMapMilkCas();
	}

	public List<String> getListOrganSingle1(){
		return handOverGhsHealth.getListOrganSingle1();
	}

	public List<String> getListCasSingle1(){
		return handOverGhsHealth.getListCasSingle1();
	}

	public Map<String, String> getMapSingle1(){
		return handOverGhsHealth.getMapSingle1();
	}

	public List<String> getListOrganSingle2(){
		return handOverGhsHealth.getListOrganSingle2();
	}

	public List<String> getListCasSingle2(){
		return handOverGhsHealth.getListCasSingle2();
	}

	public Map<String, String> getMapSingle2(){
		return handOverGhsHealth.getMapSingle2();
	}

	public List<String> getListOrganSingle3(){
		return handOverGhsHealth.getListOrganSingle3();
	}

	public List<String> getListCasSingle3(){
		return handOverGhsHealth.getListCasSingle3();
	}

	public Map<String, String> getMapSingle3(){
		return handOverGhsHealth.getMapSingle3();
	}

	public List<String> getListOrganRepeat1(){
		return handOverGhsHealth.getListOrganRepeat1();
	}

	public List<String> getListCasRepeat1(){
		return handOverGhsHealth.getListCasRepeat1();
	}

	public Map<String, String> getMapRepeat1(){
		return handOverGhsHealth.getMapRepeat1();
	}

	public List<String> getListOrganRepeat2(){
		return handOverGhsHealth.getListOrganRepeat2();
	}

	public List<String> getListCasRepeat2(){
		return handOverGhsHealth.getListCasRepeat2();
	}

	public Map<String, String> getMapRepeat2(){
		return handOverGhsHealth.getMapRepeat2();
	}

	public Map<String, String> getMapAspiration(){
		return handOverGhsHealth.getMapAspiration();
	}


	public Map<String, String> getMapShort(){
		return handOverGhsEnv.getMapShort();
	}


	public List<String> getListShortCas(){
		return handOverGhsEnv.getListShortCas();
	}



	public Map<String, String> getMapLong(){
		return handOverGhsEnv.getMapLong();
	}


	public List<String> getListLongCas(){
		return handOverGhsEnv.getListLongCas();
	}



	public Map<String, String> getMapOzone(){
		return handOverGhsEnv.getMapOzone();
	}


	public List<String> getListOzoneCas(){
		return handOverGhsEnv.getListOzoneCas();
	}



	public String getSyoubouhou() {
		return handOverGhsPhysical.getSyoubouhou();
	}


	public List<String> getListPict() {

		//同じ、handOverGhsのメソッドを呼び出すが、それぞれインスタンスが違う
		Map<String,List<String>> mapFire = handOverGhsPhysical.getMapFire();
		Map<String,List<String>> mapDocro = handOverGhsHealth.getMapDocro();
		Map<String,List<String>> mapCorrosion = handOverGhsHealth.getMapCorrosion();
		Map<String,List<String>> mapHealth = handOverGhsHealth.getMapHealth();
		Map<String,List<String>> mapFish = handOverGhsEnv.getMapFish();

		List<List<String>> lists = new ArrayList<>();
		lists.add(mapFire.get("fire"));
		lists.add(mapDocro.get("docro"));
		lists.add(mapCorrosion.get("corrosion"));
		lists.add(mapHealth.get("health"));
		lists.add(mapFish.get("fish"));


		List<String> listPict = new ArrayList<>();
		//ﾄﾞｸﾛがあれば、全ての分野のexclamationは不要
		//キーdocroのバリューListに"docro"があるか？
		if(mapDocro.get("docro").contains("docro")) {
			for(List<String> list : lists) {
				for(String pict : list) {
					if(  !pict.equals("-")           &&
					     !pict.equals("exclamation") &&
					     !listPict.contains(pict)
                       ) {
						listPict.add(pict);
					}
				}
			}
		}else{
			for(List<String> list : lists) {
				for(String pict : list) {
					if(!pict.equals("-") && !listPict.contains(pict)) {
						listPict.add(pict);
					}
				}
			}
		}
		return listPict;
	}



	public List<String> getListSignalPhysical(){
		return handOverGhsPhysical.getListSignalPhysical();
	}


	public List<String> getListSignalHealth(){
		return handOverGhsHealth.getListSignalHealth();
	}

	public List<String> getListSignalEnv(){
		return handOverGhsEnv.getListSignalEnv();
	}



	public String getStrHazardPhysical() {
		return handOverGhsPhysical.getStrHazardPhysical();
	}

	//listHazardHealthには単回暴露と反復暴露、誤嚥性は含まれていない
	public List<String> getListHazardHealth(){
		return handOverGhsHealth.getListHazardHealth();
	}


	public String getStrHazardAspiration() {
		return handOverGhsHealth.getStrHazardAspiration();
	}

	public List<String> getListHazardEnv(){
		return handOverGhsEnv.getListHazardEnv();
	}


	public Map<String, String> getMapPhysical(){
		return handOverGhsPhysical.getMapPhysical();
	}



}
