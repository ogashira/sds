package ghs.physicalHazards;

import java.util.Map;

interface IFPhysicalHazards {

	//key: ateMix, kubun, pictogram, signalWord, hazardInfo
	Map<String, String> getMapPhysical();

	//key: 爆発物、可燃性ガス、エアゾール、酸化性ガス、.....など
	Map<String, String> getPhysicalHazardsMap();

	String getSyoubouhou();

}
