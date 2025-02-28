package ghs.hazardToHealth.skinEyeIrritation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandOverSkinEye {

	private SkinIrritation skin;
	private EyeIrritation eye;
	private Map<String,String> mapSkinIrritation;
	private Map<String,String> mapEyeIrritation;

	public HandOverSkinEye(List<String[]> listFilterdGmiccs,
			float nonListRatio) {
		skin = new SkinIrritation(listFilterdGmiccs, nonListRatio);
		//Map<String, Float> mapSkinIrritationRatio = skin.returnMapSkinIrritationRatio();
		eye = new EyeIrritation(listFilterdGmiccs, nonListRatio);

		mapSkinIrritation = this.getMapSkinIrritation();
		mapEyeIrritation = this.getMapEyeIrritation();
	}


	public Map<String, String> returnMapSkinIrritation(){
		return mapSkinIrritation;
	}

	public Map<String, String> returnMapEyeIrritation(){
		return mapEyeIrritation;
	}



	public List<String[]> getListSkinIrritation() {
		return skin.getListSkinIrritation();
	}

	private Map<String, String> getMapSkinIrritation() {
		return skin.getMapSkinIrritation();
	}

	public List<String> getSkinCas() {
		return skin.getSkinCas();
	}

	private Map<String, String> getMapEyeIrritation() {
		return eye.getMapEyeIrritation();
	}

	public List<String> getEyeCas() {
		Map<String, List<String>> mapSkinIrritationCas = skin.returnMapSkinIrritationCas();

		return eye.getEyeCas(mapSkinIrritationCas);
	}


	public Map<String, List<String>> getMapCorrosion(){

		Map<String, List<String>> mapCorrosion = new HashMap<>();
		List<String> picts = new ArrayList<>();
		String allPict[] = {  mapSkinIrritation.get("pictogram"),
						      mapEyeIrritation.get("pictogram")
						   };

		//重複を避けて、"-"も避ける
		for(String pict : allPict) {
			if((!pict.equals("-")) && (!picts.contains(pict))) {
				picts.add(pict);
			}
		}
		//corrosionがあれば、exclamationは不要なので、
		if(picts.contains("corrosion") && picts.contains("exclamation")) {
			picts.remove(picts.indexOf("exclamation"));

		}
		mapCorrosion.put("corrosion", picts);

		return mapCorrosion;
	}


	public List<String> getListSignalIrritation(){
		List<String> listSignalIrritation = new ArrayList<>();

		String allSignal[] = {  mapSkinIrritation.get("signalWord"),
						      mapEyeIrritation.get("signalWord")
						   };
		for(String signal : allSignal) {
			if((!signal.equals("-")) && (!listSignalIrritation.contains(signal))) {
				listSignalIrritation.add(signal);
			}
		}
		return listSignalIrritation;
	}

	public List<String> getListHazardIrritation(){
		List<String> listHazardIrritation = new ArrayList<>();

		String allHazard[] = { mapSkinIrritation.get("hazardInfo"),
				               mapEyeIrritation.get("hazardInfo"),
						   };
		for(String hazard : allHazard) {
			if(!hazard.equals("-") && !hazard.equals("") && hazard != null) {
				listHazardIrritation.add(hazard);
			}
		}
		return listHazardIrritation;

	}
}
