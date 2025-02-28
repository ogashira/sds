package excels;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csves.UpdateCsv;
import excels.formats.IFSdsFormat;
import structures.StructInputForm;
import structures.StructPhysicalData;
import structures.StructSolDb;
import structures.StructTableForLabel;

public class ExcelGhsInsert {

	private GhsGetter ghsGetter;
	private IFSdsFormat sdsFormat;
	private List<StructSolDb> listStructSol;
	private StructInputForm inputForm;
	private List<StructPhysicalData> listPhysicalData;

	ExcelGhsInsert(StructInputForm inputForm,
			List<StructSolDb> listStructSol,
			List<StructSolDb> resultDb,
			List<StructPhysicalData> listPhysicalData,
			IFSdsFormat sdsFormat) {

		ghsGetter = new GhsGetter(inputForm,
									    listStructSol,
									    resultDb,
									    listPhysicalData);
		this.sdsFormat = sdsFormat;
		this.listPhysicalData = listPhysicalData;
		this.listStructSol = listStructSol;
		this.inputForm = inputForm;
	}

	void insertGhs() {
		//物理化学的危険性を挿入する
		this.insertPhysicalHazards();

		//急性毒性を挿入する
		this.insertAcuteToxicity();

		this.insertSkinIrritation();

		this.insertEyeIrritation();

		this.insertLiquidSensitization();

		this.insertGasSensitization();

		this.insertSkinSensitization();

		this.insertMutagenicity();
		this.insertCarcinogenic();
		this.insertReproductiveToxicity();
		this.insertMilk();
		this.insertSingle1();
		this.insertSingle2();
		this.insertSingle3();
		this.insertRepeat1();
		this.insertRepeat2();
		this.insertAspiration();
		this.insertShort();
		this.insertLong();
		this.insertOzone();

		this.insertPict();
		this.insertSignal();

		//危険有害性情報insertHazardInfoは最後の最後(physicalData)の
		//最後、成分表の後に入力して不要行を削除するので、
		//ExcelWorkComponentのinsertComponentの後に実行する。

		//sec3地域情報、sec15消防法を挿入する
		this.insertSyoubouhou();
	}

	private void insertPhysicalHazards() {
		Map<String, String> physicalHazardsMap = ghsGetter.getPhysicalHazards();
		for (Map.Entry<String, String> entry : physicalHazardsMap.entrySet()) {
			sdsFormat.insertSection2(entry.getValue(), entry.getKey());
		}
	}

	private void insertAcuteToxicity() {
		//Section2

		Map<String, String> mapOral = ghsGetter.getMapOral();
		Map<String, String> mapTransdermal = ghsGetter.getMapTransdermal();
		Map<String, String> mapInhalationGas = ghsGetter.getMapInhalationGas();
		Map<String, String> mapInhalationSteam = ghsGetter.getMapInhalationSteam();
		Map<String, String> mapInhalationDust = ghsGetter.getMapInhalationDust();

		sdsFormat.insertSection2(mapOral.get("kubun"), "急毒経口");
		sdsFormat.insertSection2(mapTransdermal.get("kubun"), "急毒経皮");
		sdsFormat.insertSection2(mapInhalationGas.get("kubun"), "急毒気体");
		sdsFormat.insertSection2(mapInhalationSteam.get("kubun"), "急毒蒸気");
		sdsFormat.insertSection2(mapInhalationDust.get("kubun"), "急毒粉塵");

		//Section11
		//関数kubunToIntの戻り値が0の場合は区分1～5以外なのでstrOralをそのまま使う。
		//1~5の場合は、ATEmixの値を追加する。
		String strOral = mapOral.get("kubun");
		if (this.kubunToInt(strOral) > 0) {
			strOral = strOral +
					" LD50 " +
					mapOral.get("ateMix") +
					"mg/kg(ATEmix, 経口)";
		}

		String strTransdermal = mapTransdermal.get("kubun");
		if (this.kubunToInt(strTransdermal) > 0) {
			strTransdermal = strTransdermal +
					" LD50 " +
					mapTransdermal.get("ateMix") +
					"mg/kg(ATEmix, 経皮)";
		}

		String strInhalationGas = mapInhalationGas.get("kubun");
		if (this.kubunToInt(strInhalationGas) > 0) {
			strInhalationGas = strInhalationGas +
					" LD50 " +
					mapInhalationGas.get("ateMix") +
					"mg/kg(ATEmix, 吸入:気体)";
		}

		String strInhalationSteam = mapInhalationSteam.get("kubun");
		if (this.kubunToInt(strInhalationSteam) > 0) {
			strInhalationSteam = strInhalationSteam +
					" LD50 " +
					mapInhalationSteam.get("ateMix") +
					"mg/L(ATEmix, 吸入:蒸気)";
		}

		String strInhalationDust = mapInhalationDust.get("kubun");
		if (this.kubunToInt(strInhalationDust) > 0) {
			strInhalationDust = strInhalationDust +
					" LD50 " +
					mapInhalationDust.get("ateMix") +
					"mg/kg(ATEmix, 吸入:粉塵,ミスト)";
		}

		sdsFormat.insertSection11(strOral, "s11急毒経口");
		sdsFormat.insertSection11(strTransdermal, "s11急毒経皮");
		sdsFormat.insertSection11(strInhalationGas, "s11急毒気体");
		sdsFormat.insertSection11(strInhalationSteam, "s11急毒蒸気");
		sdsFormat.insertSection11(strInhalationDust, "s11急毒粉塵");

	}

	private int kubunToInt(String str) {
		int intKubun = 0;
		//文字に含まれる数字を半角にする。
		String smallStr = Normalizer.normalize(str, Normalizer.Form.NFKC);
		//数字以外の文字を空文字にする
		smallStr = smallStr.replaceAll("[^0-9]", "");

		//空文字しかなかったら即リターン
		if (smallStr.equals("")) {
			return intKubun;
		}

		intKubun = Integer.parseInt(smallStr);

		return intKubun;
	}

	private void insertSkinIrritation() {
		// section2
		Map<String, String> mapSkinIrritation = ghsGetter.getMapSkinIrritation();
		sdsFormat.insertSection2(mapSkinIrritation.get("kubun"), "皮膚刺激");

		// section11

		//物質名を、で繋げて一つの文字にする。
		List<String> skinCas = ghsGetter.getSkinCas();
		String hinmeis = this.getHinmeis(skinCas);

		//区分が１以上だったら、区分の後に品名を付ける。
		String strSkinIrritation = mapSkinIrritation.get("kubun");
		if (this.kubunToInt(strSkinIrritation) > 0) {
			strSkinIrritation = strSkinIrritation + hinmeis;
		}

		sdsFormat.insertSection11(strSkinIrritation, "s11皮膚刺激");
	}

	private void insertEyeIrritation() {

		// section2
		Map<String, String> mapEyeIrritation = ghsGetter.getMapEyeIrritation();
		sdsFormat.insertSection2(mapEyeIrritation.get("kubun"), "眼刺激性");

		// section11

		//物質名を、で繋げて一つの文字にする。
		List<String> eyeCas = ghsGetter.getEyeCas();
		String hinmeis = this.getHinmeis(eyeCas);

		//区分が１以上だったら、区分の後に品名を付ける。
		String strEyeIrritation = mapEyeIrritation.get("kubun");
		if (this.kubunToInt(strEyeIrritation) > 0) {
			strEyeIrritation = strEyeIrritation + hinmeis;
		}

		sdsFormat.insertSection11(strEyeIrritation, "s11眼刺激性");
	}

	private void insertLiquidSensitization() {
		// section2
		Map<String, String> mapLiquidSensitization = ghsGetter.
				                                    getMapLiquidSensitization();
		String valueKubun = mapLiquidSensitization.get("kubun");
		sdsFormat.insertSection2(valueKubun, "呼吸器感作固液体");

		// section11
		Map<String,List<String>> mapLiquidSensitizationCas =
				              ghsGetter.getMapLiquidSensitizationCas();
		//myKeysにキーを入れる。キーは一つしかないはず。
		List<String> myKeys = new ArrayList<>();
		for(String myKey : mapLiquidSensitizationCas.keySet()) {
			myKeys.add(myKey);
		}

		List<String> casNos = mapLiquidSensitizationCas.get(myKeys.get(0));

		//リストcasNosをString hinmeisにする。
		String hinmeis = this.getHinmeis(casNos);

		//区分が１以上だったら、区分の後に品名を付ける。
		String valueKubunHinmei = valueKubun;
		if (this.kubunToInt(valueKubun) > 0) {
			valueKubunHinmei = valueKubunHinmei +"  " + hinmeis;
		}

		sdsFormat.insertSection11(valueKubunHinmei, "s11呼吸器感作固液体");
	}


	private void insertGasSensitization() {

		// section2
		Map<String, String> mapGasSensitization = ghsGetter.
				                                    getMapGasSensitization();
		String valueKubun = mapGasSensitization.get("kubun");
		sdsFormat.insertSection2(valueKubun, "呼吸器感作気体");

		// section11
		Map<String,List<String>> mapGasSensitizationCas =
				              ghsGetter.getMapGasSensitizationCas();
		//myKeysにキーを入れる。キーは一つしかないはず。
		List<String> myKeys = new ArrayList<>();
		for(String myKey : mapGasSensitizationCas.keySet()) {
			myKeys.add(myKey);
		}

		List<String> casNos = mapGasSensitizationCas.get(myKeys.get(0));

		//リストcasNosをString hinmeisにする。
		String hinmeis = this.getHinmeis(casNos);

		//区分が１以上だったら、区分の後に品名を付ける。
		String valueKubunHinmei = valueKubun;
		if (this.kubunToInt(valueKubun) > 0) {
			valueKubunHinmei = valueKubunHinmei + "  " + hinmeis;
		}

		sdsFormat.insertSection11(valueKubunHinmei, "s11呼吸器感作気体");
	}

	private void insertSkinSensitization() {
		// section2
		Map<String, String> mapSkinSensitization = ghsGetter.
				                                    getMapSkinSensitization();
		String valueKubun = mapSkinSensitization.get("kubun");
		sdsFormat.insertSection2(valueKubun, "皮膚感作性");

		// section11
		Map<String,List<String>> mapSkinSensitizationCas =
				              ghsGetter .getMapSkinSensitizationCas();
		//myKeysにキーを入れる。キーは一つしかないはず。
		List<String> myKeys = new ArrayList<>();
		for(String myKey : mapSkinSensitizationCas.keySet()) {
			myKeys.add(myKey);
		}

		List<String> casNos = mapSkinSensitizationCas.get(myKeys.get(0));

		//リストcasNosをString hinmeisにする。
		String hinmeis = this.getHinmeis(casNos);

		//区分が１以上だったら、区分の後に品名を付ける。
		String valueKubunHinmei = valueKubun;
		if (this.kubunToInt(valueKubun) > 0) {
			valueKubunHinmei = valueKubunHinmei + "  " + hinmeis;
		}

		sdsFormat.insertSection11(valueKubunHinmei, "s11皮膚感作性");

	}

	private void insertMutagenicity() {
		// section2
		Map<String, String> mapMutagenicity = ghsGetter.
				                                    getMapMutagenicity();
		String valueKubun = mapMutagenicity.get("kubun");
		sdsFormat.insertSection2(valueKubun, "変異原性");

		// section11
		Map<String,List<String>> mapMutagenicityCas =
				              ghsGetter .getMapMutagenicityCas();
		//myKeysにキーを入れる。キーは一つしかないはず。
		List<String> myKeys = new ArrayList<>();
		for(String myKey : mapMutagenicityCas.keySet()) {
			myKeys.add(myKey);
		}

		List<String> casNos = mapMutagenicityCas.get(myKeys.get(0));

		//リストcasNosをString hinmeisにする。
		String hinmeis = this.getHinmeis(casNos);

		//区分が１以上だったら、区分の後に品名を付ける。
		String valueKubunHinmei = valueKubun;
		if (this.kubunToInt(valueKubun) > 0) {
			valueKubunHinmei = valueKubunHinmei + "  " + hinmeis;
		}

		sdsFormat.insertSection11(valueKubunHinmei, "s11変異原性");
	}

	private void insertCarcinogenic() {
		// section2
		Map<String, String> mapCarcinogenic = ghsGetter.
				                                    getMapCarcinogenic();
		String valueKubun = mapCarcinogenic.get("kubun");
		sdsFormat.insertSection2(valueKubun, "発がん性");

		// section11
		Map<String,List<String>> mapCarcinogenicCas =
				              ghsGetter .getMapCarcinogenicCas();
		//myKeysにキーを入れる。キーは一つしかないはず。
		List<String> myKeys = new ArrayList<>();
		for(String myKey : mapCarcinogenicCas.keySet()) {
			myKeys.add(myKey);
		}

		List<String> casNos = mapCarcinogenicCas.get(myKeys.get(0));

		//リストcasNosをString hinmeisにする。
		String hinmeis = this.getHinmeis(casNos);

		//区分が１以上だったら、区分の後に品名を付ける。
		String valueKubunHinmei = valueKubun;
		if (this.kubunToInt(valueKubun) > 0) {
			valueKubunHinmei = valueKubunHinmei + "  " + hinmeis;
		}

		sdsFormat.insertSection11(valueKubunHinmei, "s11発がん性");
	}

	private void insertReproductiveToxicity() {
		// section2
		Map<String, String> mapReproductiveToxicity = ghsGetter.
				                                   getMapReproductiveToxicity();
		String valueKubun = mapReproductiveToxicity.get("kubun");
		sdsFormat.insertSection2(valueKubun, "生殖毒性");

		// section11
		Map<String,List<String>> mapReproductiveToxicityCas =
				             ghsGetter .getMapReproductiveToxicityCas();
		//myKeysにキーを入れる。キーは一つしかないはず。
		List<String> myKeys = new ArrayList<>();
		for(String myKey : mapReproductiveToxicityCas.keySet()) {
			myKeys.add(myKey);
		}

		List<String> casNos = mapReproductiveToxicityCas.get(myKeys.get(0));

		//リストcasNosをString hinmeisにする。
		String hinmeis = this.getHinmeis(casNos);

		//区分が１以上だったら、区分の後に品名を付ける。
		String valueKubunHinmei = valueKubun;
		if (this.kubunToInt(valueKubun) > 0) {
			valueKubunHinmei = valueKubunHinmei + "  " + hinmeis;
		}

		sdsFormat.insertSection11(valueKubunHinmei, "s11生殖毒性");
	}

	private void insertMilk() {

		final String KUBUN = "授乳に対するまたは授乳を介した影響に関する追加区分";

		// section2
		Map<String, String> mapMilk = ghsGetter. getMapMilk();
		String valueKubun = mapMilk.get("kubun");
		sdsFormat.insertSection2(valueKubun, "生殖毒性授乳");

		// section11
		Map<String,List<String>> mapMilkCas =
				                   ghsGetter .getMapMilkCas();
		//myKeysにキーを入れる。キーは一つしかないはず。
		List<String> myKeys = new ArrayList<>();
		for(String myKey : mapMilkCas.keySet()) {
			myKeys.add(myKey);
		}

		List<String> casNos = mapMilkCas.get(myKeys.get(0));

		//リストcasNosをString hinmeisにする。
		String hinmeis = this.getHinmeis(casNos);

		//区分が１以上だったら、区分の後に品名を付ける。
		//ではなく、ここの区分は「授乳に対するまたは授乳を介した影響に関する追加区分」
		//くそ長いやつ
		String valueKubunHinmei = valueKubun;
		if (valueKubun.equals(KUBUN)) {
			valueKubunHinmei = valueKubunHinmei + "  " + hinmeis;
		}

		sdsFormat.insertSection11(valueKubunHinmei, "s11生殖毒性授乳");
	}

	private void insertSingle1() {
		List<String> listOrganSingle1 = ghsGetter.getListOrganSingle1();
		List<String> listCasSingle1 = ghsGetter.getListCasSingle1();
		Map<String, String> mapSingle1 = ghsGetter.getMapSingle1();

		// section2 区分1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		String organKubun;
		if(listOrganSingle1.size()>0) {
			organKubun = mapSingle1.get("hazardInfo") + "(";
			for(int i=0; i<listOrganSingle1.size() -1; i++) {
				organKubun = organKubun + listOrganSingle1.get(i) + "、";
			}
			organKubun = organKubun
						  + listOrganSingle1.get(listOrganSingle1.size()-1) + ")";
		} else {
			organKubun = mapSingle1.get("hazardInfo");
		}

		//区分1~3なら、臓器を追加する。それ以外は、分類できないまたは区分に該当しないを追記
		String organKubunPlus;
		if(this.kubunToInt(mapSingle1.get("kubun")) > 0){
			organKubunPlus = "区分1" + "  " + organKubun;
		}else {
			organKubunPlus = "区分1" + "  " + mapSingle1.get("kubun");
		}

		sdsFormat.insertSection2(organKubunPlus, "単回ばく露1");

		//section11 区分1
		String casKubun;
		casKubun = this.getHinmeis(listCasSingle1);

		//区分1~3なら、臓器を追加する。それ以外は、分類できないまたは区分に該当しないを追記
		String casKubunPlus;
		if(this.kubunToInt(mapSingle1.get("kubun")) > 0){
			casKubunPlus = "区分1" + "  " + casKubun;
		}else {
			casKubunPlus = "区分1" + "  " + mapSingle1.get("kubun");
		}
		sdsFormat.insertSection11(casKubunPlus, "s11単回ばく露1");
	}



	private void insertSingle2() {
		List<String> listOrganSingle2 = ghsGetter.getListOrganSingle2();
		List<String> listCasSingle2 = ghsGetter.getListCasSingle2();
		Map<String, String> mapSingle2 = ghsGetter.getMapSingle2();

		// section2 区分2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		String organKubun;
		if(listOrganSingle2.size()>0) {
			organKubun = mapSingle2.get("hazardInfo") + "(";
			for(int i=0; i<listOrganSingle2.size() -1; i++) {
				organKubun = organKubun + listOrganSingle2.get(i) + "、";
			}
			organKubun = organKubun
						  + listOrganSingle2.get(listOrganSingle2.size()-1) + ")";
		} else {
			organKubun = mapSingle2.get("hazardInfo");
		}

		//区分1~3なら、臓器を追加する。それ以外は、分類できないまたは区分に該当しないを追記
		String organKubunPlus;
		if(this.kubunToInt(mapSingle2.get("kubun")) > 0){
			organKubunPlus = "区分2" + "  " + organKubun;
		}else {
			organKubunPlus = "区分2" + "  " + mapSingle2.get("kubun");
		}

		sdsFormat.insertSection2(organKubunPlus, "単回ばく露2");

		//section11 区分2
		String casKubun;
		casKubun = this.getHinmeis(listCasSingle2);

		//区分1~3なら、臓器を追加する。それ以外は、分類できないまたは区分に該当しないを追記
		String casKubunPlus;
		if(this.kubunToInt(mapSingle2.get("kubun")) > 0){
			casKubunPlus = "区分2" + "  " + casKubun;
		}else {
			casKubunPlus = "区分2" + "  " + mapSingle2.get("kubun");
		}
		sdsFormat.insertSection11(casKubunPlus, "s11単回ばく露2");
	}

	private void insertSingle3() {
		List<String> listOrganSingle3 = ghsGetter.getListOrganSingle3();
		List<String> listCasSingle3 = ghsGetter.getListCasSingle3();
		Map<String, String> mapSingle3 = ghsGetter.getMapSingle3();

		// section2 区分3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		String organKubun;
		if(listOrganSingle3.size()>0) {
			organKubun = mapSingle3.get("hazardInfo") + "(";
			for(int i=0; i<listOrganSingle3.size() -1; i++) {
				organKubun = organKubun + listOrganSingle3.get(i) + "、";
			}
			organKubun = organKubun
						  + listOrganSingle3.get(listOrganSingle3.size()-1) + ")";
		} else {
			organKubun = mapSingle3.get("hazardInfo");
		}

		//区分1~3なら、臓器を追加する。それ以外は、分類できないまたは区分に該当しないを追記
		String organKubunPlus;
		if(this.kubunToInt(mapSingle3.get("kubun")) > 0){
			organKubunPlus = "区分3" + "  " + organKubun;
		}else {
			organKubunPlus = "区分3" + "  " + mapSingle3.get("kubun");
		}

		sdsFormat.insertSection2(organKubunPlus, "単回ばく露3");

		//section11 区分3
		String casKubun;
		casKubun = this.getHinmeis(listCasSingle3);

		//区分1~3なら、臓器を追加する。それ以外は、分類できないまたは区分に該当しないを追記
		String casKubunPlus;
		if(this.kubunToInt(mapSingle3.get("kubun")) > 0){
			casKubunPlus = "区分3" + "  " + casKubun;
		}else {
			casKubunPlus = "区分3" + "  " + mapSingle3.get("kubun");
		}
		sdsFormat.insertSection11(casKubunPlus, "s11単回ばく露3");
	}


	private void insertRepeat1() {

		List<String> listOrganRepeat1 = ghsGetter.getListOrganRepeat1();
		List<String> listCasRepeat1 = ghsGetter.getListCasRepeat1();
		Map<String, String> mapRepeat1 = ghsGetter.getMapRepeat1();

		// section2 区分1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		String organKubun;
		if(listOrganRepeat1.size()>0) {
			organKubun = mapRepeat1.get("hazardInfo") + "(";
			for(int i=0; i<listOrganRepeat1.size() -1; i++) {
				organKubun = organKubun + listOrganRepeat1.get(i) + "、";
			}
			organKubun = organKubun
						  + listOrganRepeat1.get(listOrganRepeat1.size()-1) + ")";
		} else {
			organKubun = mapRepeat1.get("hazardInfo");
		}

		//区分1~3なら、臓器を追加する。それ以外は、分類できないまたは区分に該当しないを追記
		String organKubunPlus;
		if(this.kubunToInt(mapRepeat1.get("kubun")) > 0){
			organKubunPlus = "区分1" + "  " + organKubun;
		}else {
			organKubunPlus = "区分1" + "  " + mapRepeat1.get("kubun");
		}

		sdsFormat.insertSection2(organKubunPlus, "反復ばく露1");

		//section11 区分1
		String casKubun;
		casKubun = this.getHinmeis(listCasRepeat1);

		//区分1~3なら、臓器を追加する。それ以外は、分類できないまたは区分に該当しないを追記
		String casKubunPlus;
		if(this.kubunToInt(mapRepeat1.get("kubun")) > 0){
			casKubunPlus = "区分1" + "  " + casKubun;
		}else {
			casKubunPlus = "区分1" + "  " + mapRepeat1.get("kubun");
		}
		sdsFormat.insertSection11(casKubunPlus, "s11反復ばく露1");
	}


	private void insertRepeat2() {

		List<String> listOrganRepeat2 = ghsGetter.getListOrganRepeat2();
		List<String> listCasRepeat2 = ghsGetter.getListCasRepeat2();
		Map<String, String> mapRepeat2 = ghsGetter.getMapRepeat2();

		// section2 区分2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		String organKubun;
		if(listOrganRepeat2.size()>0) {
			organKubun = mapRepeat2.get("hazardInfo") + "(";
			for(int i=0; i<listOrganRepeat2.size() -1; i++) {
				organKubun = organKubun + listOrganRepeat2.get(i) + "、";
			}
			organKubun = organKubun
						  + listOrganRepeat2.get(listOrganRepeat2.size()-1) + ")";
		} else {
			organKubun = mapRepeat2.get("hazardInfo");
		}

		//区分1~3なら、臓器を追加する。それ以外は、分類できないまたは区分に該当しないを追記
		String organKubunPlus;
		if(this.kubunToInt(mapRepeat2.get("kubun")) > 0){
			organKubunPlus = "区分2" + "  " + organKubun;
		}else {
			organKubunPlus = "区分2" + "  " + mapRepeat2.get("kubun");
		}

		sdsFormat.insertSection2(organKubunPlus, "反復ばく露2");

		//section11 区分2
		String casKubun;
		casKubun = this.getHinmeis(listCasRepeat2);

		//区分1~3なら、臓器を追加する。それ以外は、分類できないまたは区分に該当しないを追記
		String casKubunPlus;
		if(this.kubunToInt(mapRepeat2.get("kubun")) > 0){
			casKubunPlus = "区分2" + "  " + casKubun;
		}else {
			casKubunPlus = "区分2" + "  " + mapRepeat2.get("kubun");
		}
		sdsFormat.insertSection11(casKubunPlus, "s11反復ばく露2");
	}


	private void insertAspiration() {
		Map<String, String> mapAspiration = ghsGetter.getMapAspiration();
		// section2
		String valueKubun = mapAspiration.get("kubun");

		sdsFormat.insertSection2(valueKubun, "誤えん有害性");

		// section11
		sdsFormat.insertSection11(valueKubun, "s11誤えん有害性");
	}

	private void insertShort() {
		Map<String, String> mapShort = ghsGetter.getMapShort();
		//ピクトグラムfishの判定をするのでmapLongも取得する
		Map<String, String> mapLong = ghsGetter.getMapLong();
		// section2
		String valueKubun = mapShort.get("kubun");
		sdsFormat.insertSection2(valueKubun, "水生環境急性");

		//section12
		List<String> listShortCas = ghsGetter.getListShortCas();
		String hinmeis = "";
		if (listShortCas != null) {
			hinmeis = this.getHinmeis(listShortCas);
		}

		//区分が１以上だったら、区分の後に品名を付ける。
		String valueKubunHinmei = valueKubun;
		if (this.kubunToInt(valueKubun) > 0) {
			valueKubunHinmei = valueKubunHinmei + "  " + hinmeis;
		}

		sdsFormat.insertSection12(valueKubunHinmei, "s12水生環境急性");

		//20231013 海洋汚染物質が全て該当していたので（豊通からクレーム）
		//fishマークのみ海洋汚染物質とし、それ以外は「-」にする。
		//ピクトグラムが「」だったら「」を記入し、それ以外は何もしないで
		//sdsFormatに記入されたままの表記とする
		if(mapShort.get("pictogram").equals("-") &&
				                mapLong.get("pictogram").equals("-")) {
			String marinePollu = "-";
			sdsFormat.insertSection14(marinePollu, "marinePollu");
		}

	}

	private void insertLong() {
		Map<String, String> mapLong = ghsGetter.getMapLong();
		// section2
		String valueKubun = mapLong.get("kubun");
		sdsFormat.insertSection2(valueKubun, "水生環境慢性");

		//section12
		List<String> listLongCas = ghsGetter.getListLongCas();
		String hinmeis = "";
		if (listLongCas != null) {
			hinmeis = this.getHinmeis(listLongCas);
		}

		//区分が１以上だったら、区分の後に品名を付ける。
		String valueKubunHinmei = valueKubun;
		if (this.kubunToInt(valueKubun) > 0) {
			valueKubunHinmei = valueKubunHinmei + "  " + hinmeis;
		}

		sdsFormat.insertSection12(valueKubunHinmei, "s12水生環境慢性");
	}

	private void insertOzone() {
		Map<String, String> mapOzone = ghsGetter.getMapOzone();
		//section2
		String valueKubun = mapOzone.get("kubun");
		sdsFormat.insertSection2(valueKubun, "オゾン層有害性");

		//section12
		List<String> listOzoneCas = ghsGetter.getListOzoneCas();
		String hinmeis = "";
		if (listOzoneCas != null) {
			hinmeis = this.getHinmeis(listOzoneCas);
		}

		//区分が１以上だったら、区分の後に品名を付ける。
		String valueKubunHinmei = valueKubun;
		if (this.kubunToInt(valueKubun) > 0) {
			valueKubunHinmei = valueKubunHinmei + "  " + hinmeis;
		}

		sdsFormat.insertSection12(valueKubunHinmei, "s12オゾン層有害性");
	}

	private void insertPict() {
		List<String> listPict = ghsGetter.getListPict();
		sdsFormat.insertPictogram(listPict);
	}


	private void insertSignal() {
		String signalWord = "";
		List<String> listSignalPhysical = ghsGetter.getListSignalPhysical();
		List<String> listSignalHealth = ghsGetter.getListSignalHealth();
		List<String> listSignalEnv = ghsGetter.getListSignalEnv();
		List<String> signals = new ArrayList<>();

		for(String signal:listSignalPhysical) {
			signals.add(signal);
		}
		for(String signal:listSignalHealth) {
			signals.add(signal);
		}
		for(String signal:listSignalEnv) {
			signals.add(signal);
		}

		if(signals.contains("危険")) {
			signalWord = "危険";
		}else {
			if(signals.contains("警告")) {
				signalWord = "警告";
			}
		}

		sdsFormat.insertSection2(signalWord, "SIGNAL_WORD");

	}

		//危険有害性情報insertHazardInfoは最後の最後(physicalData)の
		//最後、成分表の後に入力して不要行を削除するので、
		//ExcelWorkComponentのinsertComponentの後に実行する。

		//sec3地域情報、sec15消防法を挿入する
	private void insertSyoubouhou() {
		String syoubouhou = ghsGetter.getSyoubouhou();
		String tiiki = "消防法" + "  " + syoubouhou;

		sdsFormat.insertSection15(syoubouhou, "syoubouhou");
		sdsFormat.insertSection3(tiiki, "tiikiInfo");
	}


	void insertHazardInfo() {
		List<String> listOrganSingle1 = ghsGetter.getListOrganSingle1();
		Map<String, String> mapSingle1 = ghsGetter.getMapSingle1();
		List<String> listOrganSingle2 = ghsGetter.getListOrganSingle2();
		Map<String, String> mapSingle2 = ghsGetter.getMapSingle2();
		List<String> listOrganSingle3 = ghsGetter.getListOrganSingle3();
		Map<String, String> mapSingle3 = ghsGetter.getMapSingle3();
		List<String> listOrganRepeat1 = ghsGetter.getListOrganRepeat1();
		Map<String, String> mapRepeat1 = ghsGetter.getMapRepeat1();
		List<String> listOrganRepeat2 = ghsGetter.getListOrganRepeat2();
		Map<String, String> mapRepeat2 = ghsGetter.getMapRepeat2();

		String strHazardPhysical = ghsGetter.getStrHazardPhysical();
		//listHazardHealthには単回暴露と反復暴露、誤嚥性は含まれていない
		List<String> listHazardHealth = ghsGetter.getListHazardHealth();
		String strHazardSingle1 = this.getOrganKubun(listOrganSingle1, mapSingle1);
		String strHazardSingle2 = this.getOrganKubun(listOrganSingle2, mapSingle2);
		String strHazardSingle3 = this.getOrganKubun(listOrganSingle3, mapSingle3);
		String strHazardRepeat1 = this.getOrganKubun(listOrganRepeat1, mapRepeat1);
		String strHazardRepeat2 = this.getOrganKubun(listOrganRepeat2, mapRepeat2);
		String strHazardAspiration = ghsGetter.getStrHazardAspiration();
		List<String> listHazardEnv = ghsGetter.getListHazardEnv();

		List<String> listHazardInfo = new ArrayList<>();

		//まず、hazardPhysicalをaddする
		this.addStr(listHazardInfo, strHazardPhysical);

		//リストが空で無ければ、中身をaddする(中身は"-",""ではない事がわかってる)
		this.addList(listHazardInfo, listHazardHealth);

		this.addStr(listHazardInfo, strHazardSingle1);
		this.addStr(listHazardInfo, strHazardSingle2);
		this.addStr(listHazardInfo, strHazardSingle3);
		this.addStr(listHazardInfo, strHazardRepeat1);
		this.addStr(listHazardInfo, strHazardRepeat2);
		this.addStr(listHazardInfo, strHazardAspiration);

		this.addList(listHazardInfo, listHazardEnv);

		sdsFormat.insertHazardInfo(listHazardInfo);

	}

	void updateGhsDb() {
		float flashPoint = 9999f;
		float boilingPoint = 9999f;
		for(StructPhysicalData line : listPhysicalData) {
			if(line.item == "引火点") {
				flashPoint = line.value;
			}
			if(line.item == "沸点") {
				boilingPoint = line.value;
			}
		}

		List<String> listPict = ghsGetter.getListPict();
		Map<String,Map<String,String>> allMap = this.getAllMap();

		Map<String,String> mapHazardInfoExposure = this.getMapHazardInfoExposure();

		UpdateCsv.updateGhsDb(inputForm, flashPoint, boilingPoint, listPict, allMap, mapHazardInfoExposure);
	}

	void updateDb(List<StructTableForLabel> tableForLabel) {
		String syoubouhou = ghsGetter.getSyoubouhou();
		UpdateCsv.updateDb(inputForm, tableForLabel, syoubouhou);
	}

	private String getHinmeis(List<String> listCas) {
		String hinmeis = null;
		String hinmei = null;
		if(listCas.size() == 0) {
			return "";
		}

		for (String cas : listCas) {
			hinmei = this.casToHinmei(cas);
			if(hinmei != null) {
				if (hinmeis == null) {
					hinmeis = " (" + hinmei;
				} else {
					hinmeis = hinmeis + "、" + hinmei;
				}
			}
		}
		if(hinmeis != null) {
			hinmeis = hinmeis + ")";
		}
		return hinmeis;
	}

	private String casToHinmei(String strCas) {

		String hinmei = null;
		for (StructSolDb line : listStructSol) {
			if (line.casNo.equals(strCas)) {
				return line.hinmei;
			}
		}
		return hinmei;
	}

	private List<String> addList(List<String> moto, List<String> add){
		if(add.size() ==0 ) {
			return moto;
		}
		for(String str : add) {
			moto.add(str);
		}
		return moto;
	}

	private List<String> addStr(List<String> moto, String add){
		if(add.equals("-") || add.equals("")
				               || add == null || add.equals("-(-)")) {
			return moto;
		}
		moto.add(add);
		return moto;
	}


	private String getOrganKubun(List<String> listOrgan,
			                                  Map<String,String> mapExposure) {

		// listOrganとmapExposure から臓器の障害(中枢神経,肝臓...)を作る
		String organKubun;
		if(listOrgan.size()>0) {
			organKubun = mapExposure.get("hazardInfo") + "(";
			for(int i=0; i<listOrgan.size() -1; i++) {
				organKubun = organKubun + listOrgan.get(i) + "、";
			}
			organKubun = organKubun
						  + listOrgan.get(listOrgan.size()-1) + ")";
		} else {
			organKubun = mapExposure.get("hazardInfo");
		}
		return organKubun;
	}

	private Map<String,String> getMapHazardInfoExposure(){
		/*
		 * ghs_db_2のupdate用に危険有害性情報＋臓器のmapをわざわざ作る
		 * めんどくせー
		 */
		List<String> listOrganSingle1 = ghsGetter.getListOrganSingle1();
		Map<String, String> mapSingle1 = ghsGetter.getMapSingle1();
		List<String> listOrganSingle2 = ghsGetter.getListOrganSingle2();
		Map<String, String> mapSingle2 = ghsGetter.getMapSingle2();
		List<String> listOrganSingle3 = ghsGetter.getListOrganSingle3();
		Map<String, String> mapSingle3 = ghsGetter.getMapSingle3();
		List<String> listOrganRepeat1 = ghsGetter.getListOrganRepeat1();
		Map<String, String> mapRepeat1 = ghsGetter.getMapRepeat1();
		List<String> listOrganRepeat2 = ghsGetter.getListOrganRepeat2();
		Map<String, String> mapRepeat2 = ghsGetter.getMapRepeat2();

		//臓器が無い場合は"-(-)" が返ってくる
		String strHazardSingle1 = this.getOrganKubun(listOrganSingle1, mapSingle1);
		String strHazardSingle2 = this.getOrganKubun(listOrganSingle2, mapSingle2);
		String strHazardSingle3 = this.getOrganKubun(listOrganSingle3, mapSingle3);
		String strHazardRepeat1 = this.getOrganKubun(listOrganRepeat1, mapRepeat1);
		String strHazardRepeat2 = this.getOrganKubun(listOrganRepeat2, mapRepeat2);

		Map<String, String> mapHazardInfoExposure = new HashMap<>();
		//注意！{"organSingle1": "-(-)"}というmapが存在する
		mapHazardInfoExposure.put("organSingle1", strHazardSingle1);
		mapHazardInfoExposure.put("organSingle2", strHazardSingle2);
		mapHazardInfoExposure.put("organSingle3", strHazardSingle3);
		mapHazardInfoExposure.put("organRepeat1", strHazardRepeat1);
		mapHazardInfoExposure.put("organRepeat2", strHazardRepeat2);

		return mapHazardInfoExposure;
	}


	private Map<String, Map<String, String>> getAllMap() {
		Map<String, String> mapPhysical = ghsGetter.getMapPhysical();
		Map<String, String> mapOral = ghsGetter.getMapOral();
		Map<String, String> mapTransdermal = ghsGetter.getMapTransdermal();
		Map<String, String> mapInhalationGas = ghsGetter.getMapInhalationGas();
		Map<String, String> mapInhalationSteam = ghsGetter.getMapInhalationSteam();
		Map<String, String> mapInhalationDust = ghsGetter.getMapInhalationDust();
		Map<String, String> mapSkinIrritation = ghsGetter.getMapSkinIrritation();
		Map<String, String> mapEyeIrritation = ghsGetter.getMapEyeIrritation();
		Map<String, String> mapLiquidSensitization = ghsGetter.getMapLiquidSensitization();
		Map<String, String> mapGasSensitization = ghsGetter.getMapGasSensitization();
		Map<String, String> mapSkinSensitization = ghsGetter.getMapSkinSensitization();
		Map<String, String> mapMutagenicity = ghsGetter.getMapMutagenicity();
		Map<String, String> mapCarcinogenic = ghsGetter.getMapCarcinogenic();
		Map<String, String> mapReproductiveToxicity = ghsGetter.getMapReproductiveToxicity();
		Map<String, String> mapMilk = ghsGetter.getMapMilk();
		Map<String, String> mapSingle1 = ghsGetter.getMapSingle1();
		Map<String, String> mapSingle2 = ghsGetter.getMapSingle2();
		Map<String, String> mapSingle3 = ghsGetter.getMapSingle3();
		Map<String, String> mapRepeat1 = ghsGetter.getMapRepeat1();
		Map<String, String> mapRepeat2 = ghsGetter.getMapRepeat2();
		Map<String, String> mapAspiration = ghsGetter.getMapAspiration();
		Map<String, String> mapShort = ghsGetter.getMapShort();
		Map<String, String> mapLong = ghsGetter.getMapLong();
		Map<String, String> mapOzone = ghsGetter.getMapOzone();

		Map<String, Map<String,String>> allMap = new HashMap<>();
		allMap.put("mapPhysical", mapPhysical);
		allMap.put("mapOral", mapOral);
		allMap.put("mapTransdermal", mapTransdermal);
		allMap.put("mapInhalationGas", mapInhalationGas);
		allMap.put("mapInhalationSteam", mapInhalationSteam);
		allMap.put("mapInhalationDust", mapInhalationDust);
		allMap.put("mapSkinIrritation", mapSkinIrritation);
		allMap.put("mapEyeIrritation", mapEyeIrritation);
		allMap.put("mapLiquidSensitization", mapLiquidSensitization);
		allMap.put("mapGasSensitization", mapGasSensitization);
		allMap.put("mapSkinSensitization", mapSkinSensitization);
		allMap.put("mapMutagenicity", mapMutagenicity);
		allMap.put("mapCarcinogenic", mapCarcinogenic);
		allMap.put("mapReproductiveToxicity", mapReproductiveToxicity);
		allMap.put("mapMilk", mapMilk);
		allMap.put("mapSingle1", mapSingle1);
		allMap.put("mapSingle2", mapSingle2);
		allMap.put("mapSingle3", mapSingle3);
		allMap.put("mapRepeat1", mapRepeat1);
		allMap.put("mapRepeat2", mapRepeat2);
		allMap.put("mapAspiration", mapAspiration);
		allMap.put("mapShort", mapShort);
		allMap.put("mapLong", mapLong);
		allMap.put("mapOzone", mapOzone);

		return allMap;
	}
}
