package ghs.hazardToHealth.aspirationHazard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

public class AspirationHazard {

	private List<String[]> listAspiration;


	AspirationHazard() {

		String filePath = GetPath.getPath("aspiration");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listAspiration = dl.getCsvData();
	}

	Map<String, String> getMapAspiration(){

		Map<String, String> mapAspiration = new HashMap<>();

		mapAspiration.put("kubun", listAspiration.get(1)[0]);
		mapAspiration.put("pictogram", listAspiration.get(1)[1]);
		mapAspiration.put("signalWord", listAspiration.get(1)[2]);
		mapAspiration.put("hazardInfo", listAspiration.get(1)[3]);


		return mapAspiration;
	}
}
