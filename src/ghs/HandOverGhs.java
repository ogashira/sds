package ghs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ghs.hazardToEnv.HandOverEnv;
import ghs.hazardToHealth.HandOverHealth;
import ghs.physicalHazards.HandOverPhysical;
import structures.StructSolDb;

public class HandOverGhs {

	private HandOverPhysical handOverPhysical;
	private HandOverHealth handOverHealth;
	private HandOverEnv handOverEnv;
	private Map<String, String> physicalHazardsMap;
	private Map<String, String> mapPhysical;

	//コンストラクタオーバーロード　flashPoint, boilingPoint, ignitionPoint
	//が渡ってきたら、それらを渡して、HandOverPhysicalをインスタンス
	//HandOverPhysicalでは引火性液体がインスタンス化される
	public HandOverGhs(float flashPoint, float boilingPoint,
			float ignitionPoint) {

		handOverPhysical = new HandOverPhysical(
				flashPoint, boilingPoint, ignitionPoint);

		//このコンストラクタでmapPhysicalを得る(引火性液体)
		mapPhysical = this.getMapPhysical();
	}

	//コンストラクタオーバーロード resultDb
	//が渡ってきたら、それらを渡して、HandOverPhysicalをインスタンス
	//HandOverPhysicalでは可燃性固体がインスタンス化される
	//handOverHealthがインスタンス化される時はlistStructSolが渡ってくる。
	//同じ型なのでそのまま使う。
	public HandOverGhs(List<StructSolDb> resultDb, String ghsItem) {
		//ghsItem(physical,health,env)によって、インスタンスを変える。

		if (ghsItem.equals("physical")) {
			handOverPhysical = new HandOverPhysical(resultDb); //resultDb
			//このコンストラクタでmapPhysicalを得る(可燃性固体)
			mapPhysical = this.getMapPhysical();
		}

		if (ghsItem.equals("health")) {
			handOverHealth = new HandOverHealth(resultDb); //listStructSol
		}

	}

	//オーバーロード
	public HandOverGhs(List<StructSolDb> listStructSol) {
		//HandOverHealthをインスタンス化してlistFilterdGmiccs,nonListRatioを
		//取得する
		HandOverHealth handOverHealth = new HandOverHealth(listStructSol);
		List<String[]> listFilterdGmiccs = handOverHealth.getListFilterdGmiccs();
		float nonListRatio = handOverHealth.getNonListRatio();
		handOverEnv = new HandOverEnv(listFilterdGmiccs, nonListRatio);

	}


	//physixalHazards
	//key: 爆発物、可燃性ガス、エアゾール、酸化性ガス、.....など
	public Map<String, String> getPhysicalHazardsMap() {
		physicalHazardsMap = handOverPhysical.getPhysicalHazardsMap();
		return physicalHazardsMap;
	}

	public String getSyoubouhou() {
		return handOverPhysical.getSyoubouhou();
	}

	//key: ateMix, kubun, pictogram, signalWord, hazardInfo
	public Map<String, String> getMapPhysical() {
		mapPhysical = handOverPhysical.getMapPhysical();
		return mapPhysical;
	}

	//hazardToHealth.acuteToxicity
	public Map<String, String> getMapOral() {
		return handOverHealth.getMapOral();
	}

	public Map<String, String> getMapTransdermal() {
		return handOverHealth.getMapTransdermal();
	}

	public Map<String, String> getMapInhalationGas() {
		return handOverHealth.getMapInhalationGas();
	}

	public Map<String, String> getMapInhalationSteam() {
		return handOverHealth.getMapInhalationSteam();
	}

	public Map<String, String> getMapInhalationDust() {
		return handOverHealth.getMapInhalationDust();
	}

	public Map<String, String> getMapSkinIrritation() {
		return handOverHealth.getMapSkinIrritation();
	}

	public List<String> getSkinCas() {
		return handOverHealth.getSkinCas();
	}

	public Map<String, String> getMapEyeIrritation() {
		return handOverHealth.getMapEyeIrritation();
	}

	public List<String> getEyeCas() {
		return handOverHealth.getEyeCas();
	}


	public Map<String, List<String>> getMapLiquidSensitizationCas() {
		return handOverHealth.getMapLiquidSensitizationCas();
	}
	public Map<String, String> getMapLiquidSensitization() {
		return handOverHealth.getMapLiquidSensitization();
	}

	public Map<String, List<String>> getMapGasSensitizationCas() {
		return handOverHealth.getMapGasSensitizationCas();
	}
	public Map<String, String> getMapGasSensitization() {
		return handOverHealth.getMapGasSensitization();
	}
	public Map<String, List<String>> getMapSkinSensitizationCas() {
		return handOverHealth.getMapSkinSensitizationCas();
	}
	public Map<String, String> getMapSkinSensitization() {
		return handOverHealth.getMapSkinSensitization();
	}


	public Map<String, List<String>> getMapMutagenicityCas() {
		return handOverHealth.getMapMutagenicityCas();
	}
	public Map<String, String> getMapMutagenicity() {
		return handOverHealth.getMapMutagenicity();
	}
	public Map<String, List<String>> getMapCarcinogenicCas() {
		return handOverHealth.getMapCarcinogenicCas();
	}
	public Map<String, String> getMapCarcinogenic() {
		return handOverHealth.getMapCarcinogenic();
	}
	public Map<String, List<String>> getMapReproductiveToxicityCas() {
		return handOverHealth.getMapReproductiveToxicityCas();
	}
	public Map<String, String> getMapReproductiveToxicity() {
		return handOverHealth.getMapReproductiveToxicity();
	}
	public Map<String, List<String>> getMapMilkCas() {
		return handOverHealth.getMapMilkCas();
	}
	public Map<String, String> getMapMilk() {
		return handOverHealth.getMapMilk();
	}


	public Map<String, List<String[]>> getMapKubunOrganCasSingle() {
		return handOverHealth.getMapKubunOrganCasSingle();
	}
	public Map<String, String> getMapSingle1(){
		return handOverHealth.getMapSingle1();
	}
	public Map<String, String> getMapSingle2(){
		return handOverHealth.getMapSingle2();
	}
	public Map<String, String> getMapSingle3(){
		return handOverHealth.getMapSingle3();
	}

	public List<String> getListOrganSingle1(){
		return handOverHealth.getListOrganSingle1();
	}
	public List<String> getListOrganSingle2(){
		return handOverHealth.getListOrganSingle2();
	}
	public List<String> getListOrganSingle3(){
		return handOverHealth.getListOrganSingle3();
	}
	public List<String> getListCasSingle1(){
		return handOverHealth.getListCasSingle1();
	}
	public List<String> getListCasSingle2(){
		return handOverHealth.getListCasSingle2();
	}
	public List<String> getListCasSingle3(){
		return handOverHealth.getListCasSingle3();
	}



	public Map<String, String> getMapRepeat1(){
		return handOverHealth.getMapRepeat1();
	}
	public Map<String, String> getMapRepeat2(){
		return handOverHealth.getMapRepeat2();
	}

	public List<String> getListOrganRepeat1(){
		return handOverHealth.getListOrganRepeat1();
	}
	public List<String> getListOrganRepeat2(){
		return handOverHealth.getListOrganRepeat2();
	}
	public List<String> getListCasRepeat1(){
		return handOverHealth.getListCasRepeat1();
	}
	public List<String> getListCasRepeat2(){
		return handOverHealth.getListCasRepeat2();
	}


	public Map<String, String> getMapAspiration(){
		return handOverHealth.getMapAspiration();
	}


	public Map<String, String> getMapShort(){
		return handOverEnv.returnMapShort();
	}
	public List<String> getListShortCas(){
		return handOverEnv.getlistShortCas();
	}
	public Map<String, String> getMapLong(){
		return handOverEnv.returnMapLong();
	}
	public List<String> getListLongCas(){
		return handOverEnv.getListLongCas();
	}
	public Map<String, String> getMapOzone(){
		return handOverEnv.returnMapOzone();
	}
	public List<String> getListOzoneCas(){
		return handOverEnv.getListOzoneCas();
	}

	//ghsDb2更新に必要な全てのmapを得る
	public Map<String, Map<String,String>> getAllMap(){

	Map<String, String> mapPhysical = this.getMapPhysical();
	Map<String, String> mapOral = this.getMapOral();
	Map<String, String> mapTransdermal = this.getMapTransdermal();
	Map<String, String> mapInhalationGas = this.getMapInhalationGas();
	Map<String, String> mapInhalationSteam = this.getMapInhalationSteam();
	Map<String, String> mapInhalationDust = this.getMapInhalationDust();
	Map<String, String> mapSkinIrritation = this.getMapSkinIrritation();
	Map<String, String> mapEyeIrritation = this.getMapEyeIrritation();
	Map<String, String> mapLiquidSensitization = this.getMapLiquidSensitization();
	Map<String, String> mapGasSensitization = this.getMapGasSensitization();
	Map<String, String> mapSkinSensitization = this.getMapSkinSensitization();
	Map<String, String> mapMutagenicity = this.getMapMutagenicity();
	Map<String, String> mapCarcinogenic = this.getMapCarcinogenic();
	Map<String, String> mapReproductiveToxicity = this.getMapReproductiveToxicity();
	Map<String, String> mapMilk = this.getMapMilk();
	Map<String, String> mapSingle1 = this.getMapSingle1();
	Map<String, String> mapSingle2 = this.getMapSingle2();
	Map<String, String> mapSingle3 = this.getMapSingle3();
	Map<String, String> mapRepeat1 = this.getMapRepeat1();
	Map<String, String> mapRepeat2 = this.getMapRepeat2();
	Map<String, String> mapAspiration = this.getMapAspiration();
	Map<String, String> mapShort = this.getMapShort();
	Map<String, String> mapLong = this.getMapLong();
	Map<String, String> mapOzone = this.getMapOzone();

	Map<String, Map<String,String>> allMap = new HashMap<>();
	allMap.put("mapPhysical", mapPhysical);
	allMap.put("mapOral", mapOral);
	allMap.put("mapTransdermal", mapTransdermal);
	allMap.put("mapInhalationGas", mapInhalationGas);
	allMap.put("mapInhalationSteam", mapInhalationSteam);
	allMap.put("mapInhalationDust", mapInhalationDust);
	allMap.put("mapSkinIrritation", mapSkinIrritation);
	allMap.put("mapEyeIrritation", mapEyeIrritation);
	allMap.put("mapLiquidSensitization", mapLiquidSensitization);
	allMap.put("mapGasSensitization", mapGasSensitization);
	allMap.put("mapSkinSensitization", mapSkinSensitization);
	allMap.put("mapMutagenicity", mapMutagenicity);
	allMap.put("mapCarcinogenic", mapCarcinogenic);
	allMap.put("mapReproductiveToxicity", mapReproductiveToxicity);
	allMap.put("mapMilk", mapMilk);
	allMap.put("mapSingle1", mapSingle1);
	allMap.put("mapSingle2", mapSingle2);
	allMap.put("mapSingle3", mapSingle3);
	allMap.put("mapRepeat1", mapRepeat1);
	allMap.put("mapRepeat2", mapRepeat2);
	allMap.put("mapAspiration", mapAspiration);
	allMap.put("mapShort", mapShort);
	allMap.put("mapLong", mapLong);
	allMap.put("mapOzone", mapOzone);

	return allMap;
}



	//pictogram>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	//ExcelWorkGhsから呼び出される
	public Map<String, List<String>> getMapFire(){
		//handOverGhsPhysicalのインスタンスを使って呼び出される
		return handOverPhysical.getMapFire();
	}
	public Map<String, List<String>> getMapDocro(){
		//handOverGhsHealthのインスタンスを使って呼び出される
		return handOverHealth.getMapDocro();
	}
	public Map<String, List<String>> getMapCorrosion(){
		//handOverGhsHealthのインスタンスを使って呼び出される
		return handOverHealth.getMapCorrosion();
	}
	public Map<String, List<String>> getMapHealth(){
		//handOverGhsHealthのインスタンスを使って呼び出される
		return handOverHealth.getMapHealth();
	}
	public Map<String, List<String>> getMapFish() {
		//handOverGhsEnvのインスタンスを使って呼び出される
		return handOverEnv.getMapFish();
	}
	//pictogram>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//signalWord>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public List<String> getListSignalPhysical(){
		return handOverPhysical.getListSignalPhysical();
	}
	public List<String> getListSignalHealth(){
		return handOverHealth.getListSignalHealth();
	}
	public List<String> getListSignalEnv(){
		return handOverEnv.getListSignalEnv();
	}
	//signalWord>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	//hazardInfo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public String getStrHazardPhysical(){
		return handOverPhysical.getStrHazardPhysical();
	}
	public List<String> getListHazardHealth(){
		return handOverHealth.getListHazardHealth();
	}
	public String getStrHazardAspiration(){
		return handOverHealth.getStrHazardAspiration();
	}
	public List<String> getListHazardEnv(){
		return handOverEnv.getListHazardEnv();
	}
	//hazardInfo>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


}