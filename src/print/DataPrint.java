package print;

import java.util.List;
import java.util.Map;

import structures.StructInputForm;
import structures.StructNite;
import structures.StructPhysicalData;
import structures.StructSolDb;
import structures.StructTableForLabel;
import structures.StructTableForNotice;
import structures.StructUnInfo;

public class DataPrint {

	public void printStructSol(StructSolDb sonota) {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("resultDb");
		System.out.println();
		System.out.print(sonota.hinban + ", ");
		System.out.print(sonota.ratio + ", ");
		System.out.print(sonota.zokuNo + ", ");
		System.out.print(sonota.hinmei + ", ");
		System.out.print(sonota.sdsDisplayName + ", ");
		System.out.print(sonota.casNo + ", ");
		System.out.print(sonota.rouan + ", ");
		System.out.print(sonota.rouanMin + ", ");
		System.out.print(sonota.prtr + ", ");
		System.out.print(sonota.prtrMin + ", ");
		System.out.print(sonota.flashPoint + ", ");
		System.out.print(sonota.ignitionPoint + ", ");
		System.out.print(sonota.expLowerLimit + ", ");
		System.out.print(sonota.expUpperLimit + ", ");
		System.out.print(sonota.boilPoint + ", ");
		System.out.print(sonota.vaporPressure + ", ");
		System.out.print(sonota.steamSg + ", ");
		System.out.print(sonota.evaporationRate + ", ");
		System.out.print(sonota.oshaPel + ", ");
		System.out.print(sonota.acgih + ", ");
		System.out.print(sonota.niSsanEi + ", ");
		System.out.print(sonota.ld50 + ", ");
		System.out.print(sonota.counter + ", ");
		System.out.print(sonota.nittokouName + ", ");
		System.out.print(sonota.nittokouEng + ", ");
		System.out.print(sonota.sdsNameEng + ", ");
		System.out.print(sonota.rouanTuuti + ", ");
		System.out.print(sonota.jap + ", ");
		System.out.print(sonota.chn + ", ");
		System.out.print(sonota.kasinhouNo + ", ");
		System.out.print(sonota.kasinhouType + ", ");
		System.out.print(sonota.prtrNo5 + ", ");
		System.out.print(sonota.prtrBunrui4 + ", ");
		System.out.print(sonota.prtrBunrui5 + ", ");
		System.out.print(sonota.tokkasokuKubun + ", ");
		System.out.print(sonota.tokkasokuNo + ", ");
		System.out.print(sonota.tokkasokuTargetRange + ", ");
		System.out.print(sonota.kanriNoudo + ", ");
		System.out.print(sonota.displayMin + ", ");
		System.out.print(sonota.yuukisokuKubun + ", ");
		System.out.print(sonota.yuukisokuNo + ", ");
		System.out.print(sonota.dokugekiBunrui + ", ");
		System.out.print(sonota.dokugekiNo + ", ");
		System.out.print(sonota.unNo + ", ");
		System.out.print(sonota.unClass + ", ");
		System.out.print(sonota.unName + ", ");
		System.out.print(sonota.hsCode + ", ");
		System.out.print(sonota.update + ", ");
	}

	public void printResultDb(List<StructSolDb> resultDb) {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("resultDb");
		System.out.println();

		for (StructSolDb line : resultDb) {
			System.out.print(line.hinban + ", ");
			System.out.print(line.ratio + ", ");
			System.out.print(line.zokuNo + ", ");
			System.out.print(line.hinmei + ", ");
			System.out.print(line.sdsDisplayName + ", ");
			System.out.print(line.casNo + ", ");
			System.out.print(line.rouan + ", ");
			System.out.print(line.rouanMin + ", ");
			System.out.print(line.prtr + ", ");
			System.out.print(line.prtrMin + ", ");
			System.out.print(line.flashPoint + ", ");
			System.out.print(line.ignitionPoint + ", ");
			System.out.print(line.expLowerLimit + ", ");
			System.out.print(line.expUpperLimit + ", ");
			System.out.print(line.boilPoint + ", ");
			System.out.print(line.vaporPressure + ", ");
			System.out.print(line.steamSg + ", ");
			System.out.print(line.evaporationRate + ", ");
			System.out.print(line.oshaPel + ", ");
			System.out.print(line.acgih + ", ");
			System.out.print(line.niSsanEi + ", ");
			System.out.print(line.ld50 + ", ");
			System.out.print(line.counter + ", ");
			System.out.print(line.nittokouName + ", ");
			System.out.print(line.nittokouEng + ", ");
			System.out.print(line.sdsNameEng + ", ");
			System.out.print(line.rouanTuuti + ", ");
			System.out.print(line.jap + ", ");
			System.out.print(line.chn + ", ");
			System.out.print(line.kasinhouNo + ", ");
			System.out.print(line.kasinhouType + ", ");
			System.out.print(line.prtrNo5 + ", ");
			System.out.print(line.prtrBunrui4 + ", ");
			System.out.print(line.prtrBunrui5 + ", ");
			System.out.print(line.tokkasokuKubun + ", ");
			System.out.print(line.tokkasokuNo + ", ");
			System.out.print(line.tokkasokuTargetRange + ", ");
			System.out.print(line.kanriNoudo + ", ");
			System.out.print(line.displayMin + ", ");
			System.out.print(line.yuukisokuKubun + ", ");
			System.out.print(line.yuukisokuNo + ", ");
			System.out.print(line.dokugekiBunrui + ", ");
			System.out.print(line.dokugekiNo + ", ");
			System.out.print(line.unNo + ", ");
			System.out.print(line.unClass + ", ");
			System.out.print(line.unName + ", ");
			System.out.print(line.hsCode + ", ");
			System.out.print(line.update + ", ");

			System.out.println();
		}
	}

	public void printInputForm(StructInputForm inputForm) {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("inputForm");
		System.out.println();

		System.out.println(inputForm.tantou);
		System.out.println(inputForm.hinban);
		System.out.println(inputForm.displayName);
		System.out.println(inputForm.SG);
		System.out.println(inputForm.paintAppe);
		System.out.println(inputForm.paintType);
		System.out.println(inputForm.singleMixture);
		System.out.println(inputForm.ghsBunrui);
		System.out.println(inputForm.ghsNo);
		System.out.println(inputForm.ghsYouki);
		System.out.println(inputForm.ghsName);
		System.out.println(inputForm.isNiteScraping);
		System.out.println(inputForm.renewalDeadline);
	}

	public void printListStructSol(List<StructSolDb> listStructSol) {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("listStructSol");
		System.out.println();

		for (StructSolDb line : listStructSol) {
			System.out.print(line.hinban + ", ");
			System.out.print(line.ratio + ", ");
			System.out.print(line.zokuNo + ", ");
			System.out.print(line.hinmei + ", ");
			System.out.print(line.sdsDisplayName + ", ");
			System.out.print(line.casNo + ", ");
			System.out.print(line.rouan + ", ");
			System.out.print(line.rouanMin + ", ");
			System.out.print(line.prtr + ", ");
			System.out.print(line.prtrMin + ", ");
			System.out.print(line.flashPoint + ", ");
			System.out.print(line.ignitionPoint + ", ");
			System.out.print(line.expLowerLimit + ", ");
			System.out.print(line.expUpperLimit + ", ");
			System.out.print(line.boilPoint + ", ");
			System.out.print(line.vaporPressure + ", ");
			System.out.print(line.steamSg + ", ");
			System.out.print(line.evaporationRate + ", ");
			System.out.print(line.oshaPel + ", ");
			System.out.print(line.acgih + ", ");
			System.out.print(line.niSsanEi + ", ");
			System.out.print(line.ld50 + ", ");
			System.out.print(line.counter + ", ");
			System.out.print(line.nittokouName + ", ");
			System.out.print(line.nittokouEng + ", ");
			System.out.print(line.sdsNameEng + ", ");
			System.out.print(line.rouanTuuti + ", ");
			System.out.print(line.jap + ", ");
			System.out.print(line.chn + ", ");
			System.out.print(line.kasinhouNo + ", ");
			System.out.print(line.kasinhouType + ", ");
			System.out.print(line.prtrNo5 + ", ");
			System.out.print(line.prtrBunrui4 + ", ");
			System.out.print(line.prtrBunrui5 + ", ");
			System.out.print(line.tokkasokuKubun + ", ");
			System.out.print(line.tokkasokuNo + ", ");
			System.out.print(line.tokkasokuTargetRange + ", ");
			System.out.print(line.kanriNoudo + ", ");
			System.out.print(line.displayMin + ", ");
			System.out.print(line.yuukisokuKubun + ", ");
			System.out.print(line.yuukisokuNo + ", ");
			System.out.print(line.dokugekiBunrui + ", ");
			System.out.print(line.dokugekiNo + ", ");
			System.out.print(line.unNo + ", ");
			System.out.print(line.unClass + ", ");
			System.out.print(line.unName + ", ");
			System.out.print(line.hsCode + ", ");
			System.out.print(line.update + ", ");

			System.out.println();
		}
	}

	public void printTableForLabel(List<StructTableForLabel> tableForLabel) {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("tableForLabel");
		System.out.println();
		for (StructTableForLabel line : tableForLabel) {
			System.out.print(line.hinban + ", ");
			System.out.print(line.casNo + ", ");
			System.out.print(line.component + ", ");
			System.out.print(line.contentRate + ", ");
			System.out.print(line.isDisplay + ", ");
			System.out.print(line.eng + ", ");
			System.out.print(line.chn + ", ");
			System.out.print(line.vet + ", ");
			System.out.println();
		}
	}

	public void printTableForNotice(List<StructTableForNotice> tableForNotice) {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("tableForNotice");
		System.out.println();
		for (StructTableForNotice line : tableForNotice) {
			System.out.print(line.component + ", ");
			System.out.print(line.contentRate + ", ");
			System.out.print(line.casNo + ", ");
			System.out.print(line.aneihouNo + ", ");
			System.out.print(line.prtrNo + ", ");
			System.out.print(line.kasinhouNo + ", ");
			System.out.print(line.zokuNo + ", ");
			System.out.print(line.sdsNameEng + ", ");
			System.out.print(line.chn + ", ");
			System.out.println();
		}
	}

	public void printListPhysicalData(List<StructPhysicalData> listPhysicalData) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("物理化学情報");
		System.out.println();
		for (StructPhysicalData physicalData : listPhysicalData) {
			System.out.print(physicalData.item + ",");
			System.out.print(physicalData.value + ",");
			System.out.print(physicalData.applicableSolvent + ",");
			System.out.print(physicalData.applicableSolventEng + ",");
			System.out.print(physicalData.applicableSolventChn + ",");
			System.out.println();
		}

	}

	public void printUnInfo(StructUnInfo unInfo) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("unInfo");
		System.out.println();
		System.out.println(unInfo.unNo);
		System.out.println(unInfo.unName);
		System.out.println(unInfo.unClass);
		System.out.println(unInfo.unYouki);
	}

	public void printMultiStructNite(List<StructNite> multiStructNite) {

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("multiStructNite");
		System.out.println();

		if (multiStructNite == null) {
			System.out.println("multiStructNite is null");
		} else {
			for (StructNite line : multiStructNite) {
				System.out.print(line.casNo + ", ");
				System.out.print(line.kasinhouNo + ", ");
				System.out.print(line.kasinhouType + ", ");
				System.out.print(line.prtrNo4 + ", ");
				System.out.print(line.prtrNo5 + ", ");
				System.out.print(line.prtrBunrui4 + ", ");
				System.out.print(line.prtrBunrui5 + ", ");
				System.out.print(line.aneihouNo + ", ");
				System.out.print(line.rangeOfDisplay + ", ");
				System.out.print(line.rangeOfNotification + ", ");
				System.out.print(line.tokkasokuKubun + ", ");
				System.out.print(line.tokkasokuNo + ", ");
				System.out.print(line.tokkasokuTargetRange + ", ");
				System.out.print(line.yuukisokuKubun + ", ");
				System.out.print(line.yuukisokuNo + ", ");
				System.out.print(line.kanriNoudo + ", ");
				System.out.print(line.dokugekiBunrui + ", ");
				System.out.print(line.dokugekiNo + ", ");
				System.out.print(line.unNo + ", ");
				System.out.print(line.unClass + ", ");
				System.out.print(line.unName + ", ");
				System.out.print(line.hsCode + ", ");
				System.out.print(line.niSsanEiPpm + ", ");
				System.out.print(line.niSsanEiMgParM3 + ", ");
				System.out.print(line.ratio + ", ");
				System.out.print(line.sdsDisplayName + ", ");
				System.out.print(line.update + ", ");
				System.out.print(line.isSuccess + ", ");

				System.out.println();
			}
		}
	}

	public void printMapAllCasesRatio(Map<String, float[]> mapAllCasesRatio) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("mapAllCasesRatio");
		System.out.println();
		for (Map.Entry<String, float[]> entry : mapAllCasesRatio.entrySet()) {
			for (float ratio : entry.getValue()) {
				System.out.println(entry.getKey() + ":" + ratio);
			}
		}
	}

	public void printMapStringFloat(Map<String, Float> map) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("mapStringFloat");
		System.out.println();
		for (Map.Entry<String, Float> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}

	public void printMapStringString(Map<String, String> mapStringString) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("mapStringString");
		System.out.println();
		for(Map.Entry<String, String> entry : mapStringString.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}

	}

	public void printCase(String[] hogecase) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("hogeCase");
		System.out.println();
		for (String hoge : hogecase) {
			System.out.println(hoge);
		}
	}

	public void printMapStringList(Map<String, List<String>> mapStringList) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("mapStringList");
		System.out.println();
		for (Map.Entry<String, List<String>> entry : mapStringList.entrySet()) {
			String K = entry.getKey();
			List<String> V = entry.getValue();
			for (String line : V) {
				System.out.println(K + ":" + line);
			}
		}
	}
	public void printListString(List<String[]> listString) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("listString[]");
		System.out.println();
		for (String[] line : listString) {
			for(String hoge : line) {
				System.out.print(hoge + ",");
			}
			System.out.println();
		}
	}
	public void printList(List<String> myList) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("myList");
		System.out.println();
		for(String myStr : myList) {
			System.out.println(myStr);
		}
	}

	public void printMapStringListArray(
			                    Map<String,List<String[]>> mapStringListArray) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("myList");
		System.out.println();
		for (Map.Entry<String, List<String[]>> entry :
			                                    mapStringListArray.entrySet()) {
			String K = entry.getKey();
			List<String[]> VArray = entry.getValue();
			for (String[] line : VArray) {
				for(String myStr : line) {
					System.out.print(K + ":" + myStr + ":" );
				}
				System.out.println();
			}
		}
	}


	public void printMapStringMapStringString(Map<String, Map<String, String>> allMap) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("allMap");
		System.out.println();
		for (Map.Entry<String, Map<String,String>> entry :
			                                    allMap.entrySet()) {
			String K = entry.getKey();
			Map<String,String> VMap = entry.getValue();
			for (Map.Entry<String,String> entry2 : VMap.entrySet() ) {
					String K2 = entry2.getKey();
					String V = entry2.getValue();
					System.out.println(K + "::" + K2 + ":" + V );
			}
				System.out.println();
		}
	}
}
