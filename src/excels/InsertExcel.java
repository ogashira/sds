package excels;

import java.util.List;

import excelsChn.InsertExcelChn;
import excelsEng.InsertExcelEng;
import structures.StructInputForm;
import structures.StructPhysicalData;
import structures.StructSolDb;
import structures.StructTableForLabel;
import structures.StructTableForNotice;

public class InsertExcel {

	/*
	 * excelに入力する情報を指示する。
	 * データの取得や詳細な入力はExcelWorkで行う
	 */

	private StructInputForm inputForm;
	private ExcelComponentInsert excelComponentInsert;


	public InsertExcel(StructInputForm inputForm) {
		//エクセルのformatをｲﾝｽﾀﾝｽ化する。ippan or bpo or metal or yusei
		this.inputForm = inputForm;
		excelComponentInsert = new ExcelComponentInsert(inputForm);
	}


	public void updateSolbentDb() {
		//溶剤dbを更新する
		excelComponentInsert.updateSolbentDb();
	}

	public void insertExcel() {

		excelComponentInsert.insertHeader();
		excelComponentInsert.insertDate();
		excelComponentInsert.insertHinmei();
		excelComponentInsert.insertTantou();
		excelComponentInsert.insertYouto();
		excelComponentInsert.insertIsSingle();
		excelComponentInsert.insertHinmei2();
		excelComponentInsert.insertKanriNoudo();
		excelComponentInsert.insertAcgih();
		excelComponentInsert.insertNiSsanEi();
		excelComponentInsert.insertCondition();
		excelComponentInsert.insertPhysicalData();
		excelComponentInsert.insertSG();

		//がん原生表をダウンロードして、そこに記載されているcasNoが存在
		//して、含有量がSDS通知義務以上だったら、物質名を表示する。
		//無ければ「該当なし」を記載する。
		excelComponentInsert.insertGanGensei();

		//特化則（第一類、第二類、第三類）それぞれに該当する成分を求めて、
		//excelformatに挿入する。
		excelComponentInsert.insertTokkasoku();

		//有機則第一種が５％以上含有している場合は第一種、第二種が５％以上
		//含有している場合は第二種、第三種が５％以上含有している場合は
		//第三種とする
		excelComponentInsert.insertYuukisoku();

		//名称を表示する物質 tableForLabelのStructTableForLabel.isDisplay
		//がtrueのものを挿入する。
		excelComponentInsert.insertLabelDisplay();

		//名称等を通知する物質 resultDbから、含有量がrouanMinよりも多い
		//物質を挿入する
		excelComponentInsert.insertSdsNotification();

		//prtr法 第一種、第二種、特定第一種それぞれに該当する成分を求めて
		//excelformatに挿入する
		excelComponentInsert.insertPrtr();

		//化審法 優先評価化学物質をexcelformatに挿入する
		excelComponentInsert.insertKasinhouYuusen();

		//unInfo(null)を渡して国連情報を挿入する
		//単体ならinputFormの情報、単体でなければ計算する。
		excelComponentInsert.insertUnInfo();

		//船舶安全法
		excelComponentInsert.insertShipSaftyAct();

		//航空法
		excelComponentInsert.insertAviationAct();

		//GHSの情報を挿入する(危険有害性情報は除く 一番最後)
		excelComponentInsert.insertGhs();

		//成分表を挿入して余分な行を削除する
		excelComponentInsert.insertComponent();

		//危険有害性情報を挿入して、余分な行を削除する
		excelComponentInsert.insertHazardInfo();

		//excelファイルに出力する。
		excelComponentInsert.outputExcel();

		//ghs_db_2を更新する
		excelComponentInsert.updateGhsDb();

		//db_2を更新する
		excelComponentInsert.updateDb();
	}

	public void insertExcelEng() {

		List<StructSolDb> resultDb = excelComponentInsert.returnResultDb();
		List<StructSolDb> listStructSol = excelComponentInsert.returnListStructSol();
		List<StructTableForLabel> tableForLabel = excelComponentInsert.returnTableForLabel();
		List<StructTableForNotice>tableForNotice = excelComponentInsert.returnTableForNotice();
		List<StructPhysicalData> listPhysicalData = excelComponentInsert.returnListPhysicalData();

		InsertExcelEng insertExcelEng = new InsertExcelEng(resultDb,
				                              listStructSol, tableForLabel,
				                              tableForNotice, listPhysicalData,
				                                                     inputForm);
		insertExcelEng.insertExcelEng();
	}

	public void insertExcelChn() {

		List<StructSolDb> resultDb = excelComponentInsert.returnResultDb();
		List<StructSolDb> listStructSol = excelComponentInsert.returnListStructSol();
		List<StructTableForLabel> tableForLabel = excelComponentInsert.returnTableForLabel();
		List<StructTableForNotice>tableForNotice = excelComponentInsert.returnTableForNotice();
		List<StructPhysicalData> listPhysicalData = excelComponentInsert.returnListPhysicalData();

		InsertExcelChn insertExcelChn = new InsertExcelChn(resultDb,
				                              listStructSol, tableForLabel,
				                              tableForNotice, listPhysicalData,
				                                                     inputForm);
		insertExcelChn.insertExcelChn();

	}
}
