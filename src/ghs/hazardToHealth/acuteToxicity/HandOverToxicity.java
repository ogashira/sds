package ghs.hazardToHealth.acuteToxicity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandOverToxicity {

	private List<String[]> listFilterdGmiccs;
	private float nonListRatio;
	private Map<String, String> mapOral;
	private Map<String, String> mapTransdermal;
	private Map<String, String> mapInhalationGas;
	private Map<String, String> mapInhalationSteam;
	private Map<String, String> mapInhalationDust;



	public HandOverToxicity(List<String[]> listFilterdGmiccs,
			float nonListRatio) {
		this.listFilterdGmiccs = listFilterdGmiccs;
		this.nonListRatio = nonListRatio;

		mapOral = this.getMapOral();
		mapTransdermal = this.getMapTransdermal();
		mapInhalationGas = this.getMapInhalationGas();
		mapInhalationSteam = this.getMapInhalationSteam();
		mapInhalationDust = this.getMapInhalationDust();
	}

	public Map<String, String> returnMapOral(){
		return mapOral;
	}

	public Map<String, String> returnMapTransdermal(){
		return mapTransdermal;
	}

	public Map<String, String> returnMapInhalationGas(){
		return mapInhalationGas;
	}

	public Map<String, String> returnMapInhalationSteam(){
		return mapInhalationSteam;
	}

	public Map<String, String> returnMapInhalationDust(){
		return mapInhalationDust;
	}

	private Map<String, String> getMapOral() {
		Map<String, String> mapOral = new HashMap<>();
		ABSAcuteToxicity oral = new Oral(listFilterdGmiccs, nonListRatio);

		mapOral = oral.getMapToxicity();

		return mapOral;
	}

	private Map<String, String> getMapTransdermal() {
		Map<String, String> mapTransdermal = new HashMap<>();
		ABSAcuteToxicity transdermal = new Transdermal(listFilterdGmiccs,
				nonListRatio);

		mapTransdermal = transdermal.getMapToxicity();

		return mapTransdermal;
	}

	private Map<String, String> getMapInhalationGas() {
		Map<String, String> mapInhalationGas = new HashMap<>();
		ABSAcuteToxicity inhalationGas = new InhalationGas(listFilterdGmiccs,
				nonListRatio);

		mapInhalationGas = inhalationGas.getMapToxicity();

		return mapInhalationGas;
	}

	private Map<String, String> getMapInhalationSteam() {
		Map<String, String> mapInhalationSteam = new HashMap<>();
		ABSAcuteToxicity inhalationSteam = new InhalationSteam(
				listFilterdGmiccs, nonListRatio);

		mapInhalationSteam = inhalationSteam.getMapToxicity();

		return mapInhalationSteam;
	}

	private Map<String, String> getMapInhalationDust() {
		Map<String, String> mapInhalationDust = new HashMap<>();
		ABSAcuteToxicity inhalationDust = new InhalationDust(
				listFilterdGmiccs, nonListRatio);

		mapInhalationDust = inhalationDust.getMapToxicity();

		return mapInhalationDust;
	}

	public Map<String, List<String>> getMapDocro() {

		Map<String, List<String>> mapDocro = new HashMap<>();
		List<String> picts = new ArrayList<>();
		String allPict[] = {  mapOral.get("pictogram"),
						      mapTransdermal.get("pictogram"),
						      mapInhalationGas.get("pictogram"),
						      mapInhalationSteam.get("pictogram"),
						      mapInhalationDust.get("pictogram")
						   };

		for(String pict : allPict) {
			if((!pict.equals("-")) && (!picts.contains(pict))) {
				picts.add(pict);
			}
		}
		mapDocro.put("docro", picts);

		return mapDocro;
	}

	public List<String> getListSignalToxicity(){
		List<String> listSignalToxicity = new ArrayList<>();

		String allSignal[] = {  mapOral.get("signalWord"),
						      mapTransdermal.get("signalWord"),
						      mapInhalationGas.get("signalWord"),
						      mapInhalationSteam.get("signalWord"),
						      mapInhalationDust.get("signalWord")
						   };
		for(String signal : allSignal) {
			if((!signal.equals("-")) && (!listSignalToxicity.contains(signal))) {
				listSignalToxicity.add(signal);
			}
		}
		return listSignalToxicity;
	}

	public List<String> getListHazardToxicity(){
		List<String> listHazardToxicity = new ArrayList<>();

		String allHazard[] = {  mapOral.get("hazardInfo"),
						      mapTransdermal.get("hazardInfo"),
						      mapInhalationGas.get("hazardInfo"),
						      mapInhalationSteam.get("hazardInfo"),
						      mapInhalationDust.get("hazardInfo")
						   };
		for(String hazard : allHazard) {
			if(!hazard.equals("-") && !hazard.equals("") && hazard != null) {
				listHazardToxicity.add(hazard);
			}
		}
		return listHazardToxicity;

	}
}
