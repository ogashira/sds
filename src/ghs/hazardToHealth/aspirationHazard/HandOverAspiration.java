package ghs.hazardToHealth.aspirationHazard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandOverAspiration {

	private Map<String, String> mapAspiration;

	public HandOverAspiration() {

		mapAspiration = this.getMapAspiration();


	}


	public Map<String,String> returnMapAspiration(){
		return mapAspiration;
	}


	private Map<String, String> getMapAspiration(){
		AspirationHazard aspiration = new AspirationHazard();
		return aspiration.getMapAspiration();
	}

	public Map<String, List<String>> getMapPictAspiration(){

		Map<String, List<String>> mapPictAspiration = new HashMap<>();
		List<String> picts = new ArrayList<>();

		String pict = mapAspiration.get("pictogram");
			if((!pict.equals("-")) && (!picts.contains(pict))) {
				picts.add(pict);
			}
		mapPictAspiration.put("pict", picts);

		return mapPictAspiration;
	}

	public List<String> getListSignalAspiration(){
		List<String> listSignalAspiration = new ArrayList<>();

		String allSignal[] = {  mapAspiration.get("signalWord")
						     };
		for(String signal : allSignal) {
			if((!signal.equals("-")) && (!listSignalAspiration.contains(signal))) {
				listSignalAspiration.add(signal);
			}
		}
		return listSignalAspiration;
	}


	public String getStrHazardAspiration(){
		return mapAspiration.get("hazardInfo");

	}
}
