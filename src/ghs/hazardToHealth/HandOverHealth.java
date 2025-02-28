package ghs.hazardToHealth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ghs.hazardToHealth.acuteToxicity.HandOverToxicity;
import ghs.hazardToHealth.aspirationHazard.HandOverAspiration;
import ghs.hazardToHealth.exposure.HandOverExposure;
import ghs.hazardToHealth.reproduction.HandOverReproduction;
import ghs.hazardToHealth.sensitization.HandOverSensitization;
import ghs.hazardToHealth.skinEyeIrritation.HandOverSkinEye;
import structures.StructSolDb;

public class HandOverHealth {

	private List<String[]> listFilterdGmiccs;
	private float nonListRatio;
	private HandOverToxicity handOverToxicity;
	private HandOverSkinEye handOverSkinEye;
	private HandOverSensitization handOverSensitization;
	private HandOverReproduction handOverReproduction;
	private HandOverExposure handOverExposure;
	private HandOverAspiration handOverAspiration;

	public HandOverHealth(List<StructSolDb> listStructSol) {

		GmiccsRegistered gmiccsRegistered = new GmiccsRegistered();
		listFilterdGmiccs = gmiccsRegistered.getFilterdGmiccs(
				listStructSol);
		//gmiccsRegisteredに載っていない原料のratio(float)
		nonListRatio = gmiccsRegistered.getNonListRatio();

		handOverToxicity = new HandOverToxicity(
				                              listFilterdGmiccs, nonListRatio);
		handOverSkinEye = new HandOverSkinEye(listFilterdGmiccs, nonListRatio);
		handOverSensitization = new HandOverSensitization(
				                              listFilterdGmiccs, nonListRatio);
		handOverReproduction = new HandOverReproduction(
				                              listFilterdGmiccs, nonListRatio);
		handOverExposure = new HandOverExposure(
				                              listFilterdGmiccs, nonListRatio);

		handOverAspiration = new HandOverAspiration();
	}

	public List<String[]> getListFilterdGmiccs() {

		return listFilterdGmiccs;
	}

	public float getNonListRatio() {
		return nonListRatio;
	}

	public Map<String, String> getMapOral() {
		return handOverToxicity.returnMapOral();
	}

	public Map<String, String> getMapTransdermal() {
		return handOverToxicity.returnMapTransdermal();
	}

	public Map<String, String> getMapInhalationGas() {
		return handOverToxicity.returnMapInhalationGas();
	}

	public Map<String, String> getMapInhalationSteam() {
		return handOverToxicity.returnMapInhalationSteam();
	}

	public Map<String, String> getMapInhalationDust() {
		return handOverToxicity.returnMapInhalationDust();
	}

	public Map<String, String> getMapSkinIrritation() {
		return handOverSkinEye.returnMapSkinIrritation();
	}

	public List<String> getSkinCas() {
		return handOverSkinEye.getSkinCas();
	}

	public Map<String, String> getMapEyeIrritation() {
		return handOverSkinEye.returnMapEyeIrritation();
	}

	public List<String> getEyeCas() {
		return handOverSkinEye.getEyeCas();
	}


	public Map<String, List<String>> getMapLiquidSensitizationCas() {
		return handOverSensitization.getMapLiquidSensitizationCas();
	}

	public Map<String, String> getMapLiquidSensitization() {
		return handOverSensitization.returnMapLiquidSensitization();
	}

	public Map<String, List<String>> getMapGasSensitizationCas() {
		return handOverSensitization.getMapGasSensitizationCas();
	}
	public Map<String, String> getMapGasSensitization() {
		return handOverSensitization.returnMapGasSensitization();
	}

	public Map<String, List<String>> getMapSkinSensitizationCas() {
		return handOverSensitization.getMapSkinSensitizationCas();
	}
	public Map<String, String> getMapSkinSensitization() {
		return handOverSensitization.returnMapSkinSensitization();
	}


	public Map<String, List<String>> getMapMutagenicityCas() {
		return handOverReproduction.getMapMutagenicityCas();
	}
	public Map<String, String> getMapMutagenicity() {
		return handOverReproduction.returnMapMutagenicity();
	}
	public Map<String, List<String>> getMapCarcinogenicCas() {
		return handOverReproduction.getMapCarcinogenicCas();
	}
	public Map<String, String> getMapCarcinogenic() {
		return handOverReproduction.returnMapCarcinogenic();
	}
	public Map<String, List<String>> getMapReproductiveToxicityCas() {
		return handOverReproduction.getMapReproductiveToxicityCas();
	}
	public Map<String, String> getMapReproductiveToxicity() {
		return handOverReproduction.returnMapReproductiveToxicity();
	}
	public Map<String, List<String>> getMapMilkCas() {
		return handOverReproduction.getMapMilkCas();
	}
	public Map<String, String> getMapMilk() {
		return handOverReproduction.returnMapMilk();
	}


	public Map<String, List<String[]>> getMapKubunOrganCasSingle() {
		return handOverExposure.getMapKubunOrganCasSingle();
	}

	public Map<String, String> getMapSingle1(){
		return handOverExposure.returnMapSingle1();
	}
	public Map<String, String> getMapSingle2(){
		return handOverExposure.returnMapSingle2();
	}
	public Map<String, String> getMapSingle3(){
		return handOverExposure.returnMapSingle3();
	}

	public List<String> getListOrganSingle1(){
		return handOverExposure.getListOrganSingle1();
	}
	public List<String> getListOrganSingle2(){
		return handOverExposure.getListOrganSingle2();
	}
	public List<String> getListOrganSingle3(){
		return handOverExposure.getListOrganSingle3();
	}
	public List<String> getListCasSingle1(){
		return handOverExposure.getListCasSingle1();
	}
	public List<String> getListCasSingle2(){
		return handOverExposure.getListCasSingle2();
	}
	public List<String> getListCasSingle3(){
		return handOverExposure.getListCasSingle3();
	}


	public Map<String, String> getMapRepeat1(){
		return handOverExposure.returnMapRepeat1();
	}
	public Map<String, String> getMapRepeat2(){
		return handOverExposure.returnMapRepeat2();
	}
	public List<String> getListOrganRepeat1(){
		return handOverExposure.getListOrganRepeat1();
	}
	public List<String> getListOrganRepeat2(){
		return handOverExposure.getListOrganRepeat2();
	}
	public List<String> getListCasRepeat1(){
		return handOverExposure.getListCasRepeat1();
	}
	public List<String> getListCasRepeat2(){
		return handOverExposure.getListCasRepeat2();
	}


	public Map<String, String> getMapAspiration(){
		return handOverAspiration.returnMapAspiration();
	}

	public Map<String, List<String>> getMapDocro(){
		return handOverToxicity.getMapDocro();
	}


	public Map<String, List<String>> getMapCorrosion(){
		/*
		 * mapCorrosionを作るときに、handOverSkinEyeで作ったmapCorrosion
		 * のほかに、mapPictSensitizationも必要になる。
		 * 呼吸器感さ性にhealthがあったら、皮膚刺激、眼刺激のexclamationは不要となっているので
		 */
		Map<String, List<String>> tempMapCorrosion =
				                      handOverSkinEye.getMapCorrosion();
		List<String> listTempCorrosion = tempMapCorrosion.get("corrosion");
		Map<String, List<String>> mapPictSensitization =
							handOverSensitization.getMapPictSensitization();
		List<String> listPictSensitization = mapPictSensitization.get("pict");
		Map<String, List<String>> mapCorrosion = new HashMap<>();

		//呼吸器感さ性にhealthがあったら、皮膚刺激、眼刺激のexclamationは不要
		if(listTempCorrosion.contains("exclamation") &&
				                    listPictSensitization.contains("health")) {
			listTempCorrosion.remove(listTempCorrosion.indexOf("exclamation"));
		}
		mapCorrosion.put("corrosion", listTempCorrosion);

		return mapCorrosion;
	}


	public Map<String, List<String>> getMapHealth (){
		//pictogramのまとめ

		Map<String, List<String>> mapPictSensitization =
							handOverSensitization.getMapPictSensitization();

		Map<String, List<String>> mapPictReproduction =
							handOverReproduction.getMapPictReproduction();

		Map<String, List<String>> mapPictExposure =
							         handOverExposure.getMapPictExposure();

		Map<String, List<String>> mapPictAspiration =
							     handOverAspiration.getMapPictAspiration();

		Map<String, List<String>> mapHealth = new HashMap<>();

		List<String> picts = new ArrayList<>();

		for(String pict : mapPictSensitization.get("pict")) {
			if((!pict.equals("-")) && (!picts.contains(pict))) {
				picts.add(pict);
			}
		}
		for(String pict : mapPictReproduction.get("pict")) {
			if((!pict.equals("-")) && (!picts.contains(pict))) {
				picts.add(pict);
			}
		}
		for(String pict : mapPictExposure.get("pict")) {
			if((!pict.equals("-")) && (!picts.contains(pict))) {
				picts.add(pict);
			}
		}
		for(String pict : mapPictAspiration.get("pict")) {
			if((!pict.equals("-")) && (!picts.contains(pict))) {
				picts.add(pict);
			}
		}

		mapHealth.put("health", picts);

		return mapHealth;
	}

	public List<String> getListSignalHealth() {
		List<String> listSignalToxicity =
				                handOverToxicity.getListSignalToxicity();
		List<String> listSignalIrritation =
				                handOverSkinEye.getListSignalIrritation();
		List<String> listSignalSensitization =
				       handOverSensitization.getListSignalSensitization();
		List<String> listSignalReproduction =
				         handOverReproduction.getListSignalReproduction();
		List<String> listSignalExposure =
				                 handOverExposure.getListSignalExposure();
		List<String> listSignalAspiration =
				             handOverAspiration.getListSignalAspiration();

		List<String> listSignalHealth = new ArrayList<>();

		List<List<String>> allList = new ArrayList<>();
		allList.add(listSignalToxicity);
		allList.add(listSignalIrritation);
		allList.add(listSignalSensitization);
		allList.add(listSignalReproduction);
		allList.add(listSignalExposure);
		allList.add(listSignalAspiration);


		for(List<String> list : allList) {
			if(list.size()==0) {
				continue;
			}
			for(String signal : list) {
				if((!signal.equals("-")) && (!listSignalHealth.contains(signal))) {
					listSignalHealth.add(signal);
				}
			}
		}
		return listSignalHealth;
	}


	public List<String> getListHazardHealth() {
		//単回暴露と反復暴露はlistに入れない。
		//ExcelWorkGhsで臓器を追加して文を作るため
		List<String> listHazardToxicity =
				                handOverToxicity.getListHazardToxicity();
		List<String> listHazardIrritation =
				                handOverSkinEye.getListHazardIrritation();
		List<String> listHazardSensitization =
				       handOverSensitization.getListHazardSensitization();
		List<String> listHazardReproduction =
				         handOverReproduction.getListHazardReproduction();
//		List<String> listHazardExposure =
//				                 handOverExposure.getListHazardExposure();


		List<String> listHazardHealth = new ArrayList<>();

		List<List<String>> allList = new ArrayList<>();
		allList.add(listHazardToxicity);
		allList.add(listHazardIrritation);
		allList.add(listHazardSensitization);
		allList.add(listHazardReproduction);
//		allList.add(listHazardExposure);


		for(List<String> list : allList) {
			if(list.size() == 0) {
				continue;
			}
			for(String hazard : list) {
				//"-"や""は絶対に無いから(このlistを作るときに排除ずみ)
				listHazardHealth.add(hazard);
			}
		}



		return listHazardHealth;
	}


	public String getStrHazardAspiration() {
		return handOverAspiration.getStrHazardAspiration();
	}
}
