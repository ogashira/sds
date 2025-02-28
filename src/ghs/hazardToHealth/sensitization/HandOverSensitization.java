package ghs.hazardToHealth.sensitization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandOverSensitization {

	private RespiratorySensitizationGas gas;
	private RespiratorySensitizationLiquid liquid;
	private SkinSensitization skin;

	private Map<String, String> mapLiquidSensitization;
	private Map<String, String> mapGasSensitization;
	private Map<String, String> mapSkinSensitization;


	public HandOverSensitization(List<String[]> listFilterdGmiccs,
			float nonListRatio) {

		liquid = new RespiratorySensitizationLiquid(listFilterdGmiccs,
				                                        nonListRatio);
		gas = new RespiratorySensitizationGas(listFilterdGmiccs,
				                                        nonListRatio);
		skin = new SkinSensitization(listFilterdGmiccs, nonListRatio);

		mapLiquidSensitization = this.getMapLiquidSensitization();
		mapGasSensitization = this.getMapGasSensitization();
		mapSkinSensitization = this.getMapSkinSensitization();
	}


	public Map<String, String> returnMapLiquidSensitization(){
		return mapLiquidSensitization;
	}

	public Map<String, String> returnMapGasSensitization(){
		return mapGasSensitization;
	}

	public Map<String, String> returnMapSkinSensitization(){
		return mapSkinSensitization;
	}


	public Map<String, List<String>> getMapLiquidSensitizationCas(){
		return liquid.getMapLiquidSensitizationCas();
	}

	private Map<String, String> getMapLiquidSensitization(){
		return liquid.getMapLiquidSensitization();
	}


	public Map<String, List<String>> getMapGasSensitizationCas(){
		return gas.getMapGasSensitizationCas();
	}

	private Map<String, String> getMapGasSensitization(){
		return gas.getMapGasSensitization();
	}

	public Map<String, List<String>> getMapSkinSensitizationCas() {
		return skin.getMapSkinSensitizationCas();
	}

	private Map<String, String> getMapSkinSensitization() {
		return skin.getMapSkinSensitization();
	}

	public Map<String, List<String>> getMapPictSensitization(){

		Map<String, List<String>> mapPictSensitization = new HashMap<>();
		List<String> picts = new ArrayList<>();
		String allPict[] = {  mapLiquidSensitization.get("pictogram"),
						      mapGasSensitization.get("pictogram"),
						      mapSkinSensitization.get("pictogram")
						   };

		for(String pict : allPict) {
			if((!pict.equals("-")) && (!picts.contains(pict))) {
				picts.add(pict);
			}
		}
		//呼吸器感さ性のhealthがあったら、皮膚感さ性のexclamationは不要
		if(picts.contains("health") && picts.contains("exclamation")) {
			picts.remove(picts.indexOf("exclamation"));

		}
		mapPictSensitization.put("pict", picts);

		return mapPictSensitization;
	}


	public List<String> getListSignalSensitization(){
		List<String> listSignalSensitization = new ArrayList<>();

		String allSignal[] = {  mapLiquidSensitization.get("signalWord"),
						      mapGasSensitization.get("signalWord"),
						      mapSkinSensitization.get("signalWord")
						   };
		for(String signal : allSignal) {
			if((!signal.equals("-")) && (!listSignalSensitization.contains(signal))) {
				listSignalSensitization.add(signal);
			}
		}
		return listSignalSensitization;
	}


	public List<String> getListHazardSensitization(){
		List<String> listHazardSensitization = new ArrayList<>();

		String allHazard[] = { mapLiquidSensitization.get("hazardInfo"),
				               mapGasSensitization.get("hazardInfo"),
				               mapSkinSensitization.get("hazardInfo")
						   };
		for(String hazard : allHazard) {
			if(!hazard.equals("-") && !hazard.equals("") && hazard != null) {
				listHazardSensitization.add(hazard);
			}
		}
		return listHazardSensitization;

	}
}
