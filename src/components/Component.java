package components;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

//import csves.Sikakari;
//import csves.Ps;
//import csves.GenryouBunkai;
//import csves.SolventDb;
//import components.calcComponents.StructSol;
import components.calcComponents.CreateStruct;
import components.calcComponents.GenryouBunkai;
import components.calcComponents.MeasuredFlashPoint;
import components.calcComponents.PhysicalData;
import components.calcComponents.Ps;
import components.calcComponents.ResultDb;
import components.calcComponents.ScrapingNite;
import components.calcComponents.Sikakari;
import components.calcComponents.SolventDb;
import components.calcComponents.TableOfComponent;
import structures.StructNite;
import structures.StructPhysicalData;
import structures.StructSolDb;
import structures.StructTableForLabel;
import structures.StructTableForNotice;
import tools.CopyStruct;

public class Component {

	private String hinban;

	public Component(String hinban) {
		this.hinban = hinban;
	}

	public List<StructSolDb> getListStructSol() {

		//仕掛品番を求める
		Sikakari sikakari = new Sikakari(this.hinban);
		String sikakariHb = sikakari.getSikakariHb();
		//仕掛品番の配合ﾃﾞｰﾀ
		Ps ps = new Ps();
		List<String[]> psPurpose = ps.getPsPurpose(sikakariHb);

		//仕掛品番の百分率
		float total = ps.getTotal(psPurpose);
		ps.getPsRate(psPurpose, total); //psPurposeの参照渡し

		//原料分解表のﾃﾞｰﾀ
		GenryouBunkai genryouBunkai = new GenryouBunkai();
		//List<String[]> genryouBunkaiData = genryouBunkai.
		//                                    getGenryouBunkaiData();

		//{genryou, float} の HashMapで重複を削除したもの
		Map<String, Float> dupliBunkai = genryouBunkai.getListGenryouBunkai(psPurpose);

		//dupliBunkaiの原料の溶剤DBを取得する
		SolventDb solDb = new SolventDb(dupliBunkai);
		List<String[]> solventDbData = solDb.getSolventDbData();

		//dupliBunkaiと溶剤DBを繋げて構造体ﾘｽﾄlistStructSolを作る
		//[[genryou, float, 溶剤DB....................].....]
		CreateStruct createStruct = new CreateStruct();
		List<StructSolDb> listStructSol = createStruct.getListStructSol(
				solventDbData, dupliBunkai);

		return listStructSol;
	}



	//    public List<StructSolDb> getResultDb(List<StructSolDb> listStructSol) {
	public List<StructSolDb> getResultDb(List<StructSolDb> listStructSol) {

		//TMBZ,sonota のみの構造体ﾘｽﾄを作る
		ResultDb rd = new ResultDb();
		List<StructSolDb> listTMBZ = rd.createListTMBZ(listStructSol);

		rd.setAllTMBZ(listTMBZ); //allTMBZをｾｯﾄ TMBZの合計値
		//float allTMBZ = rd.getAllTMBZ();  // allTMBZをｹﾞｯﾄ

		//resultDb を作る 現時点ではTMBZ,sonotaしか入っていない。
		List<StructSolDb> resultDb = rd.createResultDb(listTMBZ);

		//resultDbにTMBZ,sonota以外の原料ﾃﾞｰﾀをlistStructSolから入れる。
		rd.addResultDb(resultDb, listStructSol);

		//N_Hの解決 G-N-H, G-N-H_D750, G-N-H_D800, G-N-H_IPSOL
		//の属No1999をまとめるhinbanはG-N-Hにする。
		//rd.bundleN-H(resultDb);
		//List<StructSolDb> resultDbN_H = new ArrayList<>();
		resultDb = rd.bundleN_H(resultDb);

		return resultDb;
	}

	public List<StructPhysicalData> getListPhysicalData(
			List<StructSolDb> listStructSol) {

		PhysicalData physicalData = new PhysicalData(listStructSol);
		List<StructPhysicalData> listPhysicalData = physicalData.createListPhysicalData();

		MeasuredFlashPoint measuredFlashPoint = new MeasuredFlashPoint(this.hinban);
		List<StructPhysicalData> changedListPhysicalData = measuredFlashPoint
				.changeMeasuredFlashPoint(listPhysicalData);

		return changedListPhysicalData;
	}

	public List<StructNite> getMultiStructNite(List<StructSolDb> resultDb,
			String renewalDeadline) {
		//渡ってきているのは、listStructSol
		//HashSetにcasNoを入れて、重複を消してからscrapingNiteに渡す
		//dupliCasNos:-> 重複をなくしたcasNo

		HashSet<String> dupliCasNos = new HashSet<>();
		List<StructNite> multiStructNite = new ArrayList<>();

		int intRenewalDeadline = Integer.parseInt(renewalDeadline);
		for (StructSolDb line : resultDb) {
			int updateDate = Integer.parseInt(line.update);
			if (updateDate < intRenewalDeadline) {
				dupliCasNos.add(line.casNo);
			}
		}

		for (String casNo : dupliCasNos) {
			if (!(casNo.equals("ND"))) {
				System.out.println(casNo + " をスクレイピング中");
				ScrapingNite scrapingNite = new ScrapingNite(casNo);
				StructNite structNite = scrapingNite.getStructNite();
				multiStructNite.add(structNite);
			}
		}
		return multiStructNite;
	}

	public List<StructSolDb> newerListStructSol(
			List<StructSolDb> listStructSol, List<StructNite> multiStructNite) {

		//ListStructSolのﾃﾞｰﾀを最新のniteﾃﾞｰﾀで更新する。
		//ｽｸﾚｲﾋﾟﾝｸﾞに成功した原料のみ行うisSuccess==trueの場合のみ

		for (StructNite nite : multiStructNite) {
			if (nite.isSuccess) {
				for (StructSolDb solDb : listStructSol) {
					if (nite.casNo.equals(solDb.casNo) /*&& solDb.ratio > 0*/) {
						if (!(nite.rangeOfNotification == 9999)) {
							solDb.rouanMin = nite.rangeOfNotification;
						}
						if (!(nite.prtrNo5.equals("null"))) {
							solDb.prtr = nite.prtrNo5;
						}
						if (!(nite.niSsanEiPpm == 9999)) {
							solDb.niSsanEi = nite.niSsanEiPpm;
						}
						if (!(nite.aneihouNo.equals("null"))) {
							solDb.rouanTuuti = nite.aneihouNo;
						}
						if (!(nite.kasinhouNo.equals("null"))) {
							solDb.kasinhouNo = nite.kasinhouNo;
						}
						if (!(nite.kasinhouType.equals("null"))) {
							solDb.kasinhouType = nite.kasinhouType;
						}
						if (!(nite.prtrNo5.equals("null"))) {
							solDb.prtrNo5 = nite.prtrNo5;
						}
						if (!(nite.prtrBunrui4.equals("null"))) {
							solDb.prtrBunrui4 = nite.prtrBunrui4;
						}
						if (!(nite.prtrBunrui5.equals("null"))) {
							solDb.prtrBunrui5 = nite.prtrBunrui5;
						}
						if (!(nite.tokkasokuKubun.equals("null"))) {
							solDb.tokkasokuKubun = nite.tokkasokuKubun;
						}
						if (!(nite.tokkasokuNo.equals("null"))) {
							solDb.tokkasokuNo = nite.tokkasokuNo;
						}
						if (!(nite.tokkasokuTargetRange == 9999)) {
							solDb.tokkasokuTargetRange = nite.tokkasokuTargetRange;
						}
						if (!(nite.kanriNoudo.equals("null"))) {
							solDb.kanriNoudo = nite.kanriNoudo;
						}
						if (!(nite.rangeOfDisplay == 9999)) {
							solDb.displayMin = nite.rangeOfDisplay;
						}
						if (!(nite.yuukisokuKubun.equals("null"))) {
							solDb.yuukisokuKubun = nite.yuukisokuKubun;
						}
						if (!(nite.yuukisokuNo.equals("null"))) {
							solDb.yuukisokuNo = nite.yuukisokuNo;
						}
						if (!(nite.dokugekiBunrui.equals("null"))) {
							solDb.dokugekiBunrui = nite.dokugekiBunrui;
						}
						if (!(nite.dokugekiNo.equals("null"))) {
							solDb.dokugekiNo = nite.dokugekiNo;
						}
						if (!(nite.unNo.equals("null"))) {
							solDb.unNo = nite.unNo;
						}
						if (!(nite.unClass.equals("null"))) {
							solDb.unClass = nite.unClass;
						}
						if (!(nite.unName.equals("null"))) {
							solDb.unName = nite.unName;
						}
						if (!(nite.hsCode.equals("null"))) {
							solDb.hsCode = nite.hsCode;
						}

						solDb.update = nite.update;
					}
				}
			}
		}
		return listStructSol;
	}

	public List<StructNite> addToMultiStructNite(
			List<StructSolDb> resultDb, List<StructNite> multiStructNite) {

		//multiStructNiteにratioとsdsDisplayNameを追加する

		for (StructNite nite : multiStructNite) {
			for (StructSolDb result : resultDb) {
				if (nite.casNo.equals(result.casNo)) {
					nite.ratio = result.ratio;
					nite.sdsDisplayName = result.sdsDisplayName;
				}
			}
		}
		return multiStructNite;
	}

	public List<StructTableForNotice> getListComponentForNotice(List<StructSolDb> resultDb) {

		//listComponentForNoticeSDS通知用のcomponentを作る
		//労安通知閾値以下は、sonotaにまとめる。summaryForResultDb

		//staticﾒｿｯﾄﾞの呼び出しresultDbのｺﾋﾟｰを作ってそれを渡す。
		List<StructSolDb> copiedResultDb = CopyStruct.copyListStructSolDb(resultDb);
		TableOfComponent tableOfComponent = new TableOfComponent();
		List<StructSolDb> listComponentForNotice = tableOfComponent.summaryComponentForNotice(copiedResultDb);
		//最終的な通知用ﾃｰﾌﾞﾙを作る
		List<StructTableForNotice> tableForNotice = tableOfComponent.getTableForNotice(listComponentForNotice);

		return tableForNotice;

	}

	public List<StructTableForLabel> getListComponentForLabel(List<StructSolDb> resultDb) {
		//ラベル表示用のcomponentを作る
		//staticﾒｿｯﾄﾞの呼び出しresultDbのｺﾋﾟｰを作ってそれを渡す。
		List<StructSolDb> copiedResultDb = CopyStruct.copyListStructSolDb(resultDb);
		TableOfComponent tableOfComponent = new TableOfComponent();
		//最終的なラベル用ﾃｰﾌﾞﾙを作る
		List<StructTableForLabel> tableForLabel = tableOfComponent.getTableForLabel(copiedResultDb);

		return tableForLabel;
	}

}
