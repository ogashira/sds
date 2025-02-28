package excelsEng;

import java.util.List;

import structures.StructInputForm;
import structures.StructPhysicalData;
import structures.StructSolDb;
import structures.StructTableForLabel;
import structures.StructTableForNotice;

public class InsertExcelEng {


	ExcelComponentInsertEng excelComponentInsertEng;

	public  InsertExcelEng(List<StructSolDb>                resultDb,
			                  List<StructSolDb>           listStructSol,
			                  List<StructTableForLabel>   tableForLabel,
			                  List<StructTableForNotice> tableForNotice,
			                  List<StructPhysicalData> listPhysicalData,
							  StructInputForm                 inputForm) {

		excelComponentInsertEng = new ExcelComponentInsertEng(    resultDb,
			        		                              listStructSol,
					                                      tableForLabel,
					                                      tableForNotice,
					                                     listPhysicalData,
					                                      inputForm        );

	}


	public  void insertExcelEng() {
		excelComponentInsertEng.insertHeader();
		excelComponentInsertEng.insertDate();
		excelComponentInsertEng.insertHinmei();
		excelComponentInsertEng.insertTantou();
		excelComponentInsertEng.insertYouto();
		excelComponentInsertEng.insertIsSingle();
		excelComponentInsertEng.insertHinmei2();
		excelComponentInsertEng.insertKanriNoudo();
		excelComponentInsertEng.insertAcgih();
		excelComponentInsertEng.insertNiSsanEi();
		excelComponentInsertEng.insertCondition();
		excelComponentInsertEng.insertPhysicalData();
		excelComponentInsertEng.insertSG();

		//がん原生表をダウンロードして、そこに記載されているcasNoが存在
		//して、含有量がSDS通知義務以上だったら、物質名を表示する。
		//無ければ「該当なし」を記載する。
		excelComponentInsertEng.insertGanGensei();

		//特化則（第一類、第二類、第三類）それぞれに該当する成分を求めて、
		//excelformatに挿入する。
		excelComponentInsertEng.insertTokkasoku();

		//有機則第一種が５％以上含有している場合は第一種、第二種が５％以上
		//含有している場合は第二種、第三種が５％以上含有している場合は
		//第三種とする
		excelComponentInsertEng.insertYuukisoku();

		//名称を表示する物質 tableForLabelのStructTableForLabel.isDisplay
		//がtrueのものを挿入する。
		excelComponentInsertEng.insertLabelDisplay();

		//名称等を通知する物質 resultDbから、含有量がrouanMinよりも多い
		//物質を挿入する
		excelComponentInsertEng.insertSdsNotification();

		//prtr法 第一種、第二種、特定第一種それぞれに該当する成分を求めて
		//excelformatに挿入する
		excelComponentInsertEng.insertPrtr();

		//化審法 優先評価化学物質をexcelformatに挿入する
		excelComponentInsertEng.insertKasinhouYuusen();

		//unInfo(null)を渡して国連情報を挿入する
		//単体ならinputFormの情報、単体でなければ計算する。
		excelComponentInsertEng.insertUnInfo();

		//船舶安全法
		excelComponentInsertEng.insertShipSaftyAct();

		//航空法
		excelComponentInsertEng.insertAviationAct();

		//GHSの情報を挿入する(危険有害性情報は除く 一番最後)
		excelComponentInsertEng.insertGhs();

		//成分表を挿入して余分な行を削除する
		excelComponentInsertEng.insertComponent();

		//危険有害性情報を挿入して、余分な行を削除する
		excelComponentInsertEng.insertHazardInfo();

		//excelファイルに出力する。
		excelComponentInsertEng.outputExcel();
	}

}
