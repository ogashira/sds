package ghs.hazardToHealth.exposure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandOverExposure {

	private Single single;
	private Repeat repeat;
	private Map<String, String> mapSingle1;
	private Map<String, String> mapSingle2;
	private Map<String, String> mapSingle3;
	private Map<String, String> mapRepeat1;
	private Map<String, String> mapRepeat2;


	public HandOverExposure(List<String[]> listFilterdGmiccs,
			                                               float nonListRatio) {

		single = new Single(listFilterdGmiccs, nonListRatio);
		repeat = new Repeat(listFilterdGmiccs, nonListRatio);

		mapSingle1 = this.getMapSingle1();
		mapSingle2 = this.getMapSingle2();
		mapSingle3 = this.getMapSingle3();
		mapRepeat1 = this.getMapRepeat1();
		mapRepeat2 = this.getMapRepeat2();
	}


	public Map<String, String> returnMapSingle1(){
		return mapSingle1;
	}
	public Map<String, String> returnMapSingle2(){
		return mapSingle2;
	}
	public Map<String, String> returnMapSingle3(){
		return mapSingle3;
	}
	public Map<String, String> returnMapRepeat1(){
		return mapRepeat1;
	}
	public Map<String, String> returnMapRepeat2(){
		return mapRepeat2;
	}



	public Map<String,List<String[]>> getMapKubunOrganCasSingle(){
		return single.returnMapKubunOrganCas();
	}

	private Map<String, String> getMapSingle1(){
		 Map<String, String> mapSingle1 = single.getMapSingle1();
		//return single.getMapSingle1();
		 return mapSingle1;
	}

	private Map<String, String> getMapSingle2(){
		 Map<String, String> mapSingle2 = single.getMapSingle2();
		//return single.getMapSingle2();
		 return mapSingle2;
	}

	private Map<String, String> getMapSingle3(){
		 Map<String, String> mapSingle3 = single.getMapSingle3();
		//return single.getMapSingle2();
		 return mapSingle3;
	}

	public List<String> getListOrganSingle1(){
		return single.getListOrganSingle1();
	}

	public List<String> getListOrganSingle2(){
		List<String> listOrganSingle2 = single.getListOrganSingle2();

		//return single.getListOrganSingle2();
		return listOrganSingle2;
	}

	public List<String> getListOrganSingle3(){
		return single.getListOrganSingle3();
	}

	public List<String> getListCasSingle1(){
		return single.getListCasSingle1();
	}
	public List<String> getListCasSingle2(){
		return single.getListCasSingle2();
	}
	public List<String> getListCasSingle3(){
		return single.getListCasSingle3();
	}




	private Map<String, String> getMapRepeat1(){
		return repeat.getMapRepeat1();
	}

	private Map<String, String> getMapRepeat2(){
		return repeat.getMapRepeat2();
	}

	public List<String> getListOrganRepeat1(){
		return repeat.getListOrganRepeat1();
	}

	public List<String> getListOrganRepeat2(){
		return repeat.getListOrganRepeat2();
	}

	public List<String> getListCasRepeat1(){
		return repeat.getListCasRepeat1();
	}
	public List<String> getListCasRepeat2(){
		return repeat.getListCasRepeat2();
	}


	public Map<String, List<String>> getMapPictExposure(){

		Map<String, List<String>> mapPictExposure = new HashMap<>();
		List<String> picts = new ArrayList<>();
		String allPict[] = {  mapSingle1.get("pictogram"),
						      mapSingle2.get("pictogram"),
						      mapSingle3.get("pictogram"),
						      mapRepeat1.get("pictogram"),
						      mapRepeat2.get("pictogram")
						   };

		for(String pict : allPict) {
			if((!pict.equals("-")) && (!picts.contains(pict))) {
				picts.add(pict);
			}
		}
		mapPictExposure.put("pict", picts);

		return mapPictExposure;
	}


	public List<String> getListSignalExposure(){
		List<String> listSignalExposure = new ArrayList<>();

		String allSignal[] = {  mapSingle1.get("signalWord"),
						        mapSingle2.get("signalWord"),
						        mapSingle3.get("signalWord"),
						        mapRepeat1.get("signalWord"),
						        mapRepeat2.get("signalWord")
						     };
		for(String signal : allSignal) {
			if((!signal.equals("-")) && (!listSignalExposure.contains(signal))) {
				listSignalExposure.add(signal);
			}
		}
		return listSignalExposure;
	}


	/*
	 * 単回暴露と反復暴露のhazardInfoは作らない。
	 * ExcelWorkGhsで臓器を追加して作成するため
	public List<String> getListHazardExposure(){
		List<String> listHazardExposure = new ArrayList<>();

		String allHazard[] = {   mapSingle1.get("hazardInfo"),
				                 mapSingle2.get("hazardInfo"),
				                 mapSingle3.get("hazardInfo"),
				                 mapRepeat1.get("hazardInfo"),
				                 mapRepeat2.get("hazardInfo")
						   };
		for(String hazard : allHazard) {
			if(!hazard.equals("-") && !hazard.equals("") && hazard != null) {
				listHazardExposure.add(hazard);
			}
		}
		return listHazardExposure;

	}
	*/
}
