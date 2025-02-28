package excels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import components.CreateComponent;
import csves.DownLoadCsv;
import csves.GetPath;
import excels.formats.FormatObjFactory;
import excels.formats.IFSdsFormat;
import structures.StructInputForm;
import structures.StructPhysicalData;
import structures.StructSolDb;
import structures.StructTableForLabel;
import structures.StructTableForNotice;
import structures.StructUnInfo;

class ExcelComponentInsert {

	private StructInputForm inputForm;
	private IFSdsFormat sdsFormat;
	private List<StructSolDb> resultDb;
	private List<StructSolDb> listStructSol;
	private List<StructTableForLabel> tableForLabel;
	private List<StructTableForNotice> tableForNotice;
	private List<StructPhysicalData> listPhysicalData;
	private StructUnInfo unInfo;
	private CreateComponent createComponent;
	private ExcelGhsInsert excelGhsInsert;

	ExcelComponentInsert(StructInputForm inputForm) {

		// ↓ これの順番を変えるな！
		this.inputForm = inputForm;
		createComponent = new CreateComponent(inputForm);
		listStructSol = this.getListStructSol();
		resultDb = this.getResultDb();
		listPhysicalData = this.getListPhysicalData();
		tableForNotice = this.getTableForNotice();
		tableForLabel = this.getTableForLabel();
		unInfo = new StructUnInfo();

		sdsFormat = FormatObjFactory.create(inputForm);

		excelGhsInsert = new ExcelGhsInsert(inputForm, listStructSol,
				resultDb, listPhysicalData, sdsFormat);

	}


	private List<StructSolDb> getResultDb() {
		return createComponent.createResultDb();
	}

	private List<StructSolDb> getListStructSol() {
		return createComponent.createListStructSol();
	}

	private List<StructPhysicalData> getListPhysicalData() {
		return createComponent.createListPhysicalData();
	}

	private List<StructTableForNotice> getTableForNotice() {
		return createComponent.createTableForNotice();
	}

	private List<StructTableForLabel> getTableForLabel() {
		return createComponent.createTableForLabel();
	}


	//excelsEngのExcelWorkComponentEngに渡す
	public List<StructSolDb> returnResultDb(){
		return resultDb;
	}
	public List<StructSolDb> returnListStructSol(){
		return listStructSol;
	}
	public List<StructPhysicalData> returnListPhysicalData(){
		return listPhysicalData;
	}
	public List<StructTableForNotice> returnTableForNotice(){
		return tableForNotice;
	}
	public List<StructTableForLabel> returnTableForLabel() {
		return tableForLabel;
	}
	public StructInputForm returnInputForm() {
		return inputForm;
	}








	void updateSolbentDb() {
		//溶剤dbを更新する
		createComponent.updateSolbentDb();
	}

	void insertHeader() {
		sdsFormat.insertHeader();
	}

	void insertDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy年MM月dd日");
		String date = dtf.format(cal.getTime());
		sdsFormat.insertSection1(date, "date");
	}

	void insertHinmei() {
		sdsFormat.insertSection1(inputForm.displayName, "hinmei");
	}

	void insertTantou() {
		sdsFormat.insertSection1(inputForm.tantou, "tantou");
	}

	void insertYouto() {

		String strYouto = null;
		if(inputForm.hinban.contains("S10-E-C") ||
				          inputForm.hinban.contains("S10-GCL2021")) {
			strYouto = "剥離剤";
		}
		 	strYouto = "塗装用";
		sdsFormat.insertSection1(strYouto, "youto");
	}

	void insertIsSingle() {
		String isSingle = "";
		if (inputForm.singleMixture.equals("Single") &&
				              (!inputForm.hinban.contains("-MT-"))) {
			isSingle = "化学物質";
		} else {
			isSingle = "混合物";
		}
		sdsFormat.insertSection3(isSingle, "isSingle");
	}

	void insertHinmei2() {
		sdsFormat.insertSection3(inputForm.displayName, "hinmei2");
	}

	void insertKanriNoudo() {

		String kanriNoudo = "";
		float smaller = 9999;
		for (StructSolDb line : resultDb) {
			smaller = Math.min(smaller, line.acgih);
			smaller = Math.min(smaller, line.niSsanEi);
		}
		if (smaller == 9999 || smaller == 0) {
			kanriNoudo = "ﾃﾞｰﾀなし";
		} else {
			kanriNoudo = smaller + "ppm";
		}
		sdsFormat.insertSection8(kanriNoudo, "kanriNoudo");
	}

	void insertAcgih() {
		String acgih = "";
		String acgihYouso = "";
		float smaller = 9999;
		//最小値を求める
		for (StructSolDb line : resultDb) {
			smaller = Math.min(smaller, line.acgih);
		}
		//最小値の成分名を求める
		for (StructSolDb line : resultDb) {
			if (smaller == line.acgih) {
				if (acgihYouso.equals("")) {
					acgihYouso = line.sdsDisplayName;
				} else {
					acgihYouso = acgihYouso + ", " + line.sdsDisplayName;
				}
			}

		}

		if (smaller == 9999 || smaller == 0) {
			acgih = "ﾃﾞｰﾀなし";
			acgihYouso = "";
		} else {
			acgih = smaller + "ppm";
			acgihYouso = "(" + acgihYouso + "により類推)";
		}
		sdsFormat.insertSection8(acgih, "acgih");
		sdsFormat.insertSection8(acgihYouso, "acgihYouso");
	}

	void insertNiSsanEi() {
		String niSsanEi = "";
		String niSsanEiYouso = "";
		float smaller = 9999;
		//最小値を求める
		for (StructSolDb line : resultDb) {
			smaller = Math.min(smaller, line.niSsanEi);
		}
		//最小値の成分名を求める
		for (StructSolDb line : resultDb) {
			if (smaller == line.niSsanEi) {
				if (niSsanEiYouso.equals("")) {
					niSsanEiYouso = line.sdsDisplayName;
				} else {
					niSsanEiYouso = niSsanEiYouso + ", " + line.sdsDisplayName;
				}
			}

		}

		if (smaller == 9999 || smaller == 0) {
			niSsanEi = "ﾃﾞｰﾀなし";
			niSsanEiYouso = "";
		} else {
			niSsanEi = smaller + "ppm";
			niSsanEiYouso = "(" + niSsanEiYouso + "により類推)";
		}
		sdsFormat.insertSection8(niSsanEi, "niSsanEi");
		sdsFormat.insertSection8(niSsanEiYouso, "niSsanEiYouso");

	}

	void insertCondition() {
		/*
		 * 物理的状態と色を求めて、insertInfoに送る
		 * 塗料外観の文字列中に"液体"が含まれていれば、状態は液体で、色は
		 * 塗料外観から"液体"を抜いた文字列。
		 * 品番の文字列中に"-MT-"が含まれていれば、状態は"固体(ﾍﾟｰｽﾄ状)"で、
		 * 色は"銀色"
		 */
		String condition = "";
		String color = "";
		String paintAppe = inputForm.paintAppe;
		if (paintAppe.contains("液体")) {
			condition = "液体";
			color = paintAppe.replace("液体", "");
		} else if (inputForm.hinban.contains("-MT-")) {
			condition = "固体(ﾍﾟｰｽﾄ状)";
			color = "銀色";
		}

		sdsFormat.insertSection9(condition, "condition");
		sdsFormat.insertSection9(color, "color");
	}

	void insertPhysicalData() {
		String boilPoint = "";
		String expLowerLimit = "";
		String expUpperLimit = "";
		String flashPoint = "";
		String ignitionPoint = "";
		String vaporPressure = "";

		for (StructPhysicalData line : listPhysicalData) {
			if (line.item.equals("沸点")) {
				if (line.value == 9999) {
					boilPoint = "ﾃﾞｰﾀなし";
				} else {
					boilPoint = line.value + "℃";
				}
			} else if (line.item.equals("爆発限界上限値")) {
				if (line.value == -9999) {
					expUpperLimit = "ﾃﾞｰﾀなし";
				} else {
					expUpperLimit = line.value + "%";
				}
			} else if (line.item.equals("爆発限界下限値")) {
				if (line.value == 9999) {
					expLowerLimit = "ﾃﾞｰﾀなし";
				} else {
					expLowerLimit = line.value + "%";
				}
			} else if (line.item.equals("引火点")) {
				if (line.value == 9999) {
					flashPoint = "ﾃﾞｰﾀなし";
				} else {
					flashPoint = line.value + "℃";
				}
			} else if (line.item.equals("発火点")) {
				if (line.value == 9999) {
					ignitionPoint = "ﾃﾞｰﾀなし";
				} else {
					ignitionPoint = line.value + "℃";
				}
			} else if (line.item.equals("蒸気圧")) {
				if (line.value == 9999) {
					vaporPressure = "ﾃﾞｰﾀなし";
				} else {
					vaporPressure = line.value + "kPa(20℃)";
				}
			}
		}
		sdsFormat.insertSection9(boilPoint, "boilPoint");
		sdsFormat.insertSection9(expLowerLimit, "lowerLimit");
		sdsFormat.insertSection9(expUpperLimit, "upperLimit");
		sdsFormat.insertSection9(flashPoint, "flashPoint");
		sdsFormat.insertSection9(ignitionPoint, "ignitionPoint");
		sdsFormat.insertSection9(vaporPressure, "vaporPressure");
	}

	void insertSG() {
		String SG = inputForm.SG;
		sdsFormat.insertSection9(SG, "SG");
	}

	void insertUnInfo() {
		float flashPoint = 9999;
		if (inputForm.singleMixture == "Single") {
			unInfo.unNo = inputForm.ghsNo;
			unInfo.unName = inputForm.ghsName;
			unInfo.unClass = inputForm.ghsBunrui;
			unInfo.unYouki = inputForm.ghsYouki;
		} else {
			unInfo.unNo = "1263";
			unInfo.unName = "ﾍﾟｲﾝﾄ";
			unInfo.unClass = "ｸﾗｽ3";
			for (StructPhysicalData line : listPhysicalData) {
				if (line.item == "引火点") {
					flashPoint = line.value;
					break;
				}
			}
			if (flashPoint < 23) {
				unInfo.unYouki = "2";
			} else if (flashPoint >= 23 & flashPoint <= 60.5) {
				unInfo.unYouki = "3";
			} else {
				unInfo.unYouki = "非危険物";
			}
		}
		sdsFormat.insertSection14(unInfo.unNo, "unNo");
		sdsFormat.insertSection14(unInfo.unName, "unName");
		sdsFormat.insertSection14(unInfo.unClass, "unClass");
		sdsFormat.insertSection14(unInfo.unYouki, "unYouki");

	}

	void insertGanGensei() {
		/*
		 * がん原生物質.csvを取り込んで、そこに記載されているcasNoが
		 * listStructSolに存在したら、物質名をinsertInfo(str, item)に
		 * 渡す。閾値はsds通知義務と同じ。TMBZに関しては、123, 124, 135
		 * それぞれのcasNoと全てを合計したTMBZ(25551-13-7)も調べる。
		 */
		//がん原生データのダウンロード
		String filePath = GetPath.getPath("ganGensei");
		DownLoadCsv dlCsv = new DownLoadCsv(filePath, "SHIFT-JIS");
		List<String[]> listGanGensei = dlCsv.getCsvData();

		//TMBZとしての合計と、25551-13-7のrouanMinを求めておく
		float sumTMBZ = 0;
		float sumTMBZrouanMin = 0;
		for (StructSolDb line : listStructSol) {
			if (line.casNo == "526-73-8" ||
					line.casNo == "95-63-6" ||
					line.casNo == "108-67-8" ||
					line.casNo == "25551-13-7") {
				sumTMBZ += line.ratio;
				if (line.casNo == "25551-13-7") {
					sumTMBZrouanMin = line.rouanMin;
				}
			}
		}

		//listStructSolのcasが25551-13-7ではない場合は、そのcasNoががん原生に
		//あるか調べて、含有量がrouanMin以上だったら、String ganGenseiに追記する。
		String ganGensei = "該当なし";
		for (StructSolDb line : listStructSol) {
			if (!(line.casNo.equals("25551-13-7"))) {
				for (String[] ganLine : listGanGensei) {
					if (line.casNo.equals(ganLine[0]) && (line.ratio * 100) >= line.rouanMin) {
						if (ganGensei.equals("該当なし")) {
							ganGensei = line.sdsDisplayName;
						} else {
							ganGensei = ganGensei + ", " + line.sdsDisplayName;
						}
					}
				}
			}
		}
		//sumTMBZがsumTMBZrouanMinよりも大きかったら25551-13-7ががん原生に
		//あるか調べて、あったら、ﾄﾘﾒﾁﾙﾍﾞﾝｾﾞﾝを追記する。
		if (sumTMBZ >= sumTMBZrouanMin) {
			for (String[] ganLine : listGanGensei) {
				if (ganLine[0].equals("25551-13-7")) {
					if (ganGensei.equals("該当なし")) {
						ganGensei = "ﾄﾘﾒﾁﾙﾍﾞﾝｾﾞﾝ";
						break;
					} else {
						ganGensei = ganGensei + ", " + "ﾄﾘﾒﾁﾙﾍﾞﾝｾﾞﾝ";
						break;
					}
				}
			}
		}

		//strとitemをexcelFormatに渡す
		sdsFormat.insertSection15(ganGensei, "ganGensei");
	}

	void insertTokkasoku() {
		//tableForNoticeから、特化則の第一類、第二類、第三類に挿入する文字を
		//作って、excelFormatに渡す。

		String tokkasoku1 = "該当なし";
		String tokkasoku2 = "該当なし";
		String tokkasoku3 = "該当なし";

		for (StructSolDb line : resultDb) {

			if (line.tokkasokuKubun.equals("第一類物質") &&
					(line.ratio * 100) > line.tokkasokuTargetRange) {
				if (tokkasoku1.equals("該当なし")) {
					tokkasoku1 = line.sdsDisplayName;
				} else {
					tokkasoku1 = tokkasoku1 + ", " + line.sdsDisplayName;
				}
			} else if (line.tokkasokuKubun.equals("第二類物質") &&
					(line.ratio * 100) > line.tokkasokuTargetRange) {
				if (tokkasoku2.equals("該当なし")) {
					tokkasoku2 = line.sdsDisplayName;
				} else {
					tokkasoku2 = tokkasoku2 + ", " + line.sdsDisplayName;
				}
			} else if (line.tokkasokuKubun.equals("第三類物質") &&
					(line.ratio * 100) > line.tokkasokuTargetRange) {
				if (tokkasoku3.equals("該当なし")) {
					tokkasoku3 = line.sdsDisplayName;
				} else {
					tokkasoku3 = tokkasoku3 + ", " + line.sdsDisplayName;
				}
			}
		}

		//それぞれの文字列をexcelFormatに渡す
		sdsFormat.insertSection15(tokkasoku1, "tokkasoku1");
		sdsFormat.insertSection15(tokkasoku2, "tokkasoku2");
		sdsFormat.insertSection15(tokkasoku3, "tokkasoku3");
	}

	void insertYuukisoku() {

		//有機則第一種が５％以上含有している場合は第一種、第二種が５％以上
		//含有している場合は第二種、第三種が５％以上含有している場合は
		//第三種とする

		float total1 = 0;
		float total2 = 0;
		float total3 = 0;

		String yuukisokuKubun = "該当なし";

		for (StructSolDb line : resultDb) {
			if (line.yuukisokuKubun.equals("第一種有機溶剤等")) {
				total1 += line.ratio;
			} else if (line.yuukisokuKubun.equals("第二種有機溶剤等")) {
				total2 += line.ratio;
			} else if (line.yuukisokuKubun.equals("第三種有機溶剤等")) {
				total3 += line.ratio;
			}
		}

		if (total1 >= 0.05) {
			yuukisokuKubun = "第一種有機溶剤等";
		} else if (total2 >= 0.05) {
			yuukisokuKubun = "第二種有機溶剤等";
		} else if (total3 >= 0.05) {
			yuukisokuKubun = "第三種有機溶剤等";
		}

		sdsFormat.insertSection15(yuukisokuKubun, "yuukisoku");
	}

	void insertLabelDisplay() {

		//名称を表示する物質 tableForLabelのStructTableForLabel.isDisplay
		//がtrueのものを挿入する。


		String labelDisplay = "該当なし";

		for (StructTableForLabel line : tableForLabel) {
			if (line.isDisplay) {
				if (labelDisplay.equals("該当なし")) {
					labelDisplay = line.component;
				} else {
					labelDisplay = labelDisplay + ", " + line.component;
				}
			}
		}
		sdsFormat.insertSection15(labelDisplay, "labelDisplay");
	}

	void insertSdsNotification() {

		//最初にresultDbの重複をなくしておく。casNoで重複を探す
		List<StructSolDb> dupliResultDb = new ArrayList<>();
		for (StructSolDb beforeLine : resultDb) {
			Boolean isExist = false;
			for (StructSolDb dupliLine : dupliResultDb) {
				if (dupliLine.casNo.equals(beforeLine.casNo)) {
					dupliLine.ratio += beforeLine.ratio;
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				dupliResultDb.add(beforeLine);
			}
		}
		//ratioが、StructSolDb rouanMin以上の物質を挿入する

		String sdsNotification = "該当なし";

		for (StructSolDb line : dupliResultDb) {
			if ((line.ratio * 100) >= line.rouanMin) {
				if (sdsNotification.equals("該当なし")) {
					sdsNotification = line.hinmei;
				} else {
					sdsNotification = sdsNotification + ", " + line.hinmei;
				}
			}
		}
		sdsFormat.insertSection15(sdsNotification, "sdsNotification");

	}

	void insertPrtr() {

		String prtr1 = "該当なし";
		String prtr2 = "該当なし";
		String prtrToku1 = "該当なし";
		for (StructSolDb line : resultDb) {
			if (line.prtrBunrui5.equals("第一種") &&
					(line.ratio * 100) > 1.0) {
				if (prtr1.equals("該当なし")) {
					prtr1 = line.sdsDisplayName;
				} else {
					prtr1 = prtr1 + ", " + line.sdsDisplayName;
				}
			} else if (line.prtrBunrui5.equals("第二種") &&
					(line.ratio * 100) > 1.0) {
				if (prtr2.equals("該当なし")) {
					prtr2 = line.sdsDisplayName;
				} else {
					prtr2 = prtr2 + ", " + line.sdsDisplayName;
				}
			} else if (line.prtrBunrui5.equals("特定第一種") &&
					(line.ratio * 100) > 1.0) {
				if (prtrToku1.equals("該当なし")) {
					prtrToku1 = line.sdsDisplayName;
				} else {
					prtrToku1 = prtrToku1 + ", " + line.sdsDisplayName;
				}
			}
		}
		sdsFormat.insertSection15(prtr1, "prtr1");
		sdsFormat.insertSection15(prtr2, "prtr2");
		sdsFormat.insertSection15(prtrToku1, "prtrToku1");
	}

	void insertKasinhouYuusen() {

		String kasinhouYuusen = "該当なし";
		for (StructSolDb line : resultDb) {
			if (line.kasinhouType.equals("優先評価化学物質")) {
				if (kasinhouYuusen.equals("該当なし")) {
					kasinhouYuusen = line.sdsDisplayName;
				} else {
					kasinhouYuusen = kasinhouYuusen + ", " + line.sdsDisplayName;
				}
			}
		}
		sdsFormat.insertSection15(kasinhouYuusen, "kasinhouYuusen");
	}

	void insertShipSaftyAct() {

		String strShipSaftyAct = "引火性液体類";
		if(inputForm.hinban.contains("-MT-")) {
			strShipSaftyAct = "各法令に従うこと";
		}

		sdsFormat.insertSection15(strShipSaftyAct, "shipSaftyAct");
	}

	void insertAviationAct() {

		String strAviationAct = "引火性液体";
		if(inputForm.hinban.contains("-MT-")) {
			strAviationAct = "各法令に従うこと";
		}

		sdsFormat.insertSection15(strAviationAct, "aviationAct");
	}

	void insertGhs() {
		excelGhsInsert.insertGhs();
	}


	void insertComponent() {

		//最後に成分表を挿入して不要な行を削除する。、ファイルに出力
		sdsFormat.insertTableOfComponent(tableForNotice);
	}


	void insertHazardInfo() {
		//最最後に危険有害性情報を挿入して、不要な行を削除する。
		excelGhsInsert.insertHazardInfo();
	}


	void outputExcel() {
		sdsFormat.outputExcel();
	}

	void updateGhsDb() {
		excelGhsInsert.updateGhsDb();
	}

	void updateDb() {
		excelGhsInsert.updateDb(tableForLabel);
	}
}
