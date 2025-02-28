package main;

import excels.InsertExcel;
import structures.StructInputForm;

public class Controller {

	private StructInputForm inputForm;

	/*
	private List<StructSolDb> listStructSol;
	private List<StructSolDb> resultDb;
	private List<StructTableForLabel> tableForLabel;
	private List<StructTableForNotice> tableForNotice;
	private List<StructPhysicalData> listPhysicalData;
	private StructUnInfo unInfo; //空のuninfoをinsertExcelに渡す
	private List<StructNite> multiStructNite;
	*/

	public Controller(StructInputForm inputForm) {
		this.inputForm = inputForm;

	}

	public void start() {
		System.out.println();
		System.out.println("SDS作成中です.............(-。-)y-゜゜゜");
		//component, un, 法令データを取得する
		InsertExcel insertExcel = new InsertExcel(inputForm);
		insertExcel.updateSolbentDb();
		//excelにcomponent,un,法令データを入力する
		insertExcel.insertExcel();

		System.out.println();
		System.out.println("和文SDS作成しました。   (^o^)v");

		if(inputForm.hinban.matches("^S.*-EX$")) {

			System.out.println();
			System.out.println("英文SDS作成中です.........(-。-)y-゜゜゜");

			insertExcel.insertExcelEng();

			System.out.println();
			System.out.println("英文SDS作成しました。    (^o^)v");

			if(inputForm.isChn) {
				System.out.println();
				System.out.println("中文SDS作成中です.........(-。-)y-゜゜゜");
				System.out.println();
				insertExcel.insertExcelChn();
				System.out.println("中文SDS作成しました。    (^o^)v");
			}
		} else {
			if(inputForm.isChn) {
				System.out.println();
				System.out.println("-EX 品番でないと中文SDSは作成できません!");
			}
		}
		System.out.println();
		System.out.println();
		System.out.println("プログラム終了です       (^.^)/~~~");


	}

}
