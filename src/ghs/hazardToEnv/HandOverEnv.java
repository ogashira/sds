package ghs.hazardToEnv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandOverEnv {

	private ShortTarm shortTarm;
	private LongTarm longTarm;
	private Ozone ozone;
	private Map<String, String> mapShort;
	private Map<String, String> mapLong;
	private Map<String, String> mapOzone;



	public HandOverEnv(List<String[]> listFilterdGmiccs, float nonListRatio) {

		shortTarm = new ShortTarm(listFilterdGmiccs, nonListRatio);
		longTarm = new LongTarm(listFilterdGmiccs, nonListRatio);
		ozone = new Ozone(listFilterdGmiccs, nonListRatio);

		mapShort = this.getMapShort();
		mapLong = this.getMapLong();
		mapOzone = this.getMapOzone();
	}


	public Map<String, String> returnMapShort(){
		return mapShort;
	}
	public Map<String, String> returnMapLong(){
		return mapLong;
	}
	public Map<String, String> returnMapOzone(){
		return mapOzone;
	}




	private Map<String, String> getMapShort() {
		return shortTarm.getMapShort();
	}

	public List<String> getlistShortCas() {
		return shortTarm.getListShortCas();
	}


	private Map<String, String> getMapLong() {
		return longTarm.getMapLong();
	}

	public List<String> getListLongCas() {
		return longTarm.getListLongCas();
	}

	private Map<String, String> getMapOzone() {
		return ozone.getMapOzone();
	}

	public List<String> getListOzoneCas() {
		return ozone.returnListOzoneCas();
	}


	public Map<String, List<String>> getMapFish(){

		Map<String, List<String>> mapFish = new HashMap<>();
		List<String> picts = new ArrayList<>();
		String allPict[] = {  mapShort.get("pictogram"),
						      mapLong.get("pictogram"),
						      mapOzone.get("pictogram")
						   };

		for(String pict : allPict) {
			if((!pict.equals("-")) && (!picts.contains(pict))) {
				picts.add(pict);
			}
		}
		mapFish.put("fish", picts);

		return mapFish;
	}


	public List<String> getListSignalEnv(){
		List<String> listSignalEnv = new ArrayList<>();

		String allSignal[] = {  mapShort.get("signalWord"),
						        mapLong.get("signalWord"),
						        mapOzone.get("signalWord")
						     };
		for(String signal : allSignal) {
			if((!signal.equals("-")) && (!listSignalEnv.contains(signal))) {
				listSignalEnv.add(signal);
			}
		}
		return listSignalEnv;
	}


	public List<String> getListHazardEnv(){
		List<String> listHazardEnv = new ArrayList<>();
		String allHazard[] = {  mapShort.get("hazardInfo"),
						        mapLong.get("hazardInfo"),
						        mapOzone.get("hazardInfo")
						     };
		for(String hazard : allHazard) {
			if(!hazard.equals("-") && !hazard.equals("") && hazard != null) {
				listHazardEnv.add(hazard);
			}
		}
		return listHazardEnv;
	}
}
