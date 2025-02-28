package ghs.hazardToHealth.reproduction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandOverReproduction {


	private Mutagenicity mutagenicity;
	private Carcinogenic carcinogenic;
	private ReproductiveToxicity reproductiveToxicity;
	private Milk milk;
	private Map<String, String> mapMutagenicity;
	private Map<String, String> mapCarcinogenic;
	private Map<String, String> mapReproductiveToxicity;
	private Map<String, String> mapMilk;


	public HandOverReproduction(List<String[]> listFilterdGmiccs,
			float nonListRatio) {

		mutagenicity = new Mutagenicity(listFilterdGmiccs,
				                                        nonListRatio);
		carcinogenic = new Carcinogenic(listFilterdGmiccs,
				                                        nonListRatio);
		reproductiveToxicity = new ReproductiveToxicity(
				                     listFilterdGmiccs, nonListRatio);
		milk = new Milk(listFilterdGmiccs, nonListRatio);

		mapMutagenicity = this.getMapMutagenicity();
		mapCarcinogenic = this.getMapCarcinogenic();
		mapReproductiveToxicity = this.getMapReproductiveToxicity();
		mapMilk = this.getMapMilk();

	}



	public Map<String, String> returnMapMutagenicity(){
		return mapMutagenicity;
	}

	public Map<String, String> returnMapCarcinogenic(){
		return mapCarcinogenic;
	}
	public Map<String, String> returnMapReproductiveToxicity(){
		return mapReproductiveToxicity;
	}
	public Map<String, String> returnMapMilk(){
		return mapMilk;
	}



	public Map<String, List<String>> getMapMutagenicityCas(){
		return mutagenicity.getMapMutagenicityCas();
	}

	private Map<String, String> getMapMutagenicity(){
		return mutagenicity.getMapMutagenicity();
	}


	public Map<String, List<String>> getMapCarcinogenicCas(){
		return carcinogenic.getMapCarcinogenicCas();
	}

	private Map<String, String> getMapCarcinogenic(){
		return carcinogenic.getMapCarcinogenic();
	}

	public Map<String, List<String>> getMapReproductiveToxicityCas() {
		return reproductiveToxicity.getMapReproductiveToxicityCas();
	}

	private Map<String, String> getMapReproductiveToxicity() {
		return reproductiveToxicity.getMapReproductiveToxicity();
	}

	public Map<String, List<String>> getMapMilkCas() {
		return milk.getMapMilkCas();
	}

	private Map<String, String> getMapMilk() {
		return milk.getMapMilk();
	}

	public Map<String, List<String>> getMapPictReproduction(){
		Map<String, List<String>> mapPictReproduction = new HashMap<>();
		List<String> picts = new ArrayList<>();
		String allPict[] = {  mapMutagenicity.get("pictogram"),
						      mapCarcinogenic.get("pictogram"),
						      mapReproductiveToxicity.get("pictogram"),
						      mapMilk.get("pictogram")
						   };

		for(String pict : allPict) {
			if((!pict.equals("-")) && (!picts.contains(pict))) {
				picts.add(pict);
			}
		}
		mapPictReproduction.put("pict", picts);

		return mapPictReproduction;
	}

	public List<String> getListSignalReproduction(){
		List<String> listSignalReproduction = new ArrayList<>();

		String allSignal[] = {  mapMutagenicity.get("signalWord"),
						        mapCarcinogenic.get("signalWord"),
						        mapReproductiveToxicity.get("signalWord"),
						        mapMilk.get("signalWord")
						     };
		for(String signal : allSignal) {
			if((!signal.equals("-")) && (!listSignalReproduction.contains(signal))) {
				listSignalReproduction.add(signal);
			}
		}
		return listSignalReproduction;
	}


	public List<String> getListHazardReproduction(){
		List<String> listHazardReproduction = new ArrayList<>();

		String allHazard[] = {   mapMutagenicity.get("hazardInfo"),
				                 mapCarcinogenic.get("hazardInfo"),
				                 mapReproductiveToxicity.get("hazardInfo"),
						         mapMilk.get("hazardInfo")
						   };
		for(String hazard : allHazard) {
			if(!hazard.equals("-") && !hazard.equals("") && hazard != null) {
				listHazardReproduction.add(hazard);
			}
		}

		return listHazardReproduction;

	}
}
