package excelsChn;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import csves.DownLoadCsv;
import csves.GetPath;
import excelsChn.format.FormatObjFactoryChn;
import excelsChn.format.IFSdsFormatChn;
import structures.StructInputForm;
import structures.StructPhysicalData;
import structures.StructSolDb;
import structures.StructTableForLabel;
import structures.StructTableForNotice;
import structures.StructUnInfo;
import translation.Translation;

class ExcelComponentInsertChn {

	private StructInputForm inputForm;
	private IFSdsFormatChn sdsFormat;
	private List<StructSolDb> resultDb;
	private List<StructSolDb> listStructSol;
	private List<StructTableForLabel> tableForLabel;
	private List<StructTableForNotice> tableForNotice;
	private List<StructPhysicalData> listPhysicalData;
	private StructUnInfo unInfo;
	private ExcelGhsInsertChn excelGhsInsertChn;
	private Translation translation;

	ExcelComponentInsertChn(List<StructSolDb> resultDb,
			                 List<StructSolDb> listStructSol,
			                 List<StructTableForLabel> tableForLabel,
			                 List<StructTableForNotice> tableForNotice,
			                 List<StructPhysicalData> listPhysicalData,
							 StructInputForm inputForm              ){

		this.resultDb = resultDb;
		this.listStructSol = listStructSol;
		this.tableForLabel = tableForLabel;
		this.tableForNotice = tableForNotice;
		this.listPhysicalData = listPhysicalData;
		this.inputForm = inputForm;
		unInfo = new StructUnInfo();

		sdsFormat = FormatObjFactoryChn.create(inputForm);

		excelGhsInsertChn = new ExcelGhsInsertChn(inputForm, listStructSol,
				resultDb, listPhysicalData, sdsFormat);

		translation = new Translation();

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
		String tantouChn = translation.getChn(inputForm.tantou);
		sdsFormat.insertSection1(tantouChn, "tantou");

	}

	void insertYouto() {

		String strYouto = null;
		if(inputForm.hinban.contains("S10-E-C") ||
				          inputForm.hinban.contains("S10-GCL2021")) {
			strYouto = translation.getChn("剥離剤");
		}
		 	strYouto = translation.getChn("塗装用");
		sdsFormat.insertSection1(strYouto, "youto");
	}

	void insertIsSingle() {
		String isSingle = "";
		if (inputForm.singleMixture.equals("Single") &&
				              (! inputForm.hinban.contains("-MT-"))) {
			isSingle = translation.getChn("化学物質");
		} else {
			isSingle = translation.getChn("混合物");
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
			kanriNoudo = translation.getChn("ﾃﾞｰﾀなし");
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
					acgihYouso = line.chn;
				} else {
					acgihYouso = acgihYouso + ", " + line.chn;
				}
			}

		}

		if (smaller == 9999 || smaller == 0) {
			acgih = translation.getChn("ﾃﾞｰﾀなし");
			acgihYouso = "";
		} else {
			acgih = smaller + "ppm";
			acgihYouso = "(" + acgihYouso + translation.getChn("により類推") + ")";
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
					niSsanEiYouso = line.chn;
				} else {
					niSsanEiYouso = niSsanEiYouso + ", " + line.chn;
				}
			}

		}

		if (smaller == 9999 || smaller == 0) {
			niSsanEi = translation.getChn("ﾃﾞｰﾀなし");
			niSsanEiYouso = "";
		} else {
			niSsanEi = smaller + "ppm";
			niSsanEiYouso = "(" + niSsanEiYouso + translation.getChn("により類推") + ")";
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
		String colorChn = null;
		String paintAppe = inputForm.paintAppe;
		if (paintAppe.contains("液体")) {
			condition = translation.getChn("液体");
			color = paintAppe.replace("液体", "");
			colorChn = translation.getChn(color);

		} else if (inputForm.hinban.contains("-MT-")) {
			condition = translation.getChn("固体(ﾍﾟｰｽﾄ状)");
			colorChn = translation.getChn("銀色");
		}

		sdsFormat.insertSection9(condition, "condition");
		sdsFormat.insertSection9(colorChn, "color");
	}

	void insertPhysicalData() {
		String boilPoint = "";
		String expLowerLimit = "";
		String expUpperLimit = "";
		String flashPoint = "";
		String ignitionPoint = "";
		String vaporPressure = "";

		String noData = translation.getChn("ﾃﾞｰﾀなし");
		for (StructPhysicalData line : listPhysicalData) {

			if (line.item.equals("沸点")) {
				if (line.value == 9999) {
					boilPoint = noData;
				} else {
					boilPoint = line.value + "℃";
				}
			} else if (line.item.equals("爆発限界上限値")) {
				if (line.value == -9999) {
					expUpperLimit = noData;
				} else {
					expUpperLimit = line.value + "%";
				}
			} else if (line.item.equals("爆発限界下限値")) {
				if (line.value == 9999) {
					expLowerLimit = noData;
				} else {
					expLowerLimit = line.value + "%";
				}
			} else if (line.item.equals("引火点")) {
				if (line.value == 9999) {
					flashPoint = noData;
				} else {
					flashPoint = line.value + "℃";
				}
			} else if (line.item.equals("発火点")) {
				if (line.value == 9999) {
					ignitionPoint = noData;
				} else {
					ignitionPoint = line.value + "℃";
				}
			} else if (line.item.equals("蒸気圧")) {
				if (line.value == 9999) {
					vaporPressure = noData;
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
			unInfo.unName = translation.getChn(inputForm.ghsName);
			unInfo.unClass = translation.getChn(inputForm.ghsBunrui);
			unInfo.unYouki = inputForm.ghsYouki;
		} else {
			unInfo.unNo = "1263";
			unInfo.unName = translation.getChn("ﾍﾟｲﾝﾄ");
			unInfo.unClass = translation.getChn("ｸﾗｽ3");
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
				unInfo.unYouki = translation.getChn("非危険物");
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
		String nasi = translation.getChn("該当なし");
		String ganGensei = nasi;
		for (StructSolDb line : listStructSol) {
			if (!(line.casNo.equals("25551-13-7"))) {
				for (String[] ganLine : listGanGensei) {
					if (line.casNo.equals(ganLine[0]) && (line.ratio * 100) >= line.rouanMin) {
						if (ganGensei.equals(nasi)) {
							ganGensei = line.chn;
						} else {
							ganGensei = ganGensei + ", " + line.chn;
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
					if (ganGensei.equals(nasi)) {
						ganGensei = translation.getChn("ﾄﾘﾒﾁﾙﾍﾞﾝｾﾞﾝ");
						break;
					} else {
						ganGensei = ganGensei + ", " + translation.getChn("ﾄﾘﾒﾁﾙﾍﾞﾝｾﾞﾝ") ;
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
		String nasi = translation.getChn("該当なし");
		String tokkasoku1 = nasi;
		String tokkasoku2 = nasi;
		String tokkasoku3 = nasi;

		for (StructSolDb line : resultDb) {

			if (line.tokkasokuKubun.equals("第一類物質") &&
					(line.ratio * 100) > line.tokkasokuTargetRange) {
				if (tokkasoku1.equals(nasi)) {
					tokkasoku1 = line.chn;
				} else {
					tokkasoku1 = tokkasoku1 + ", " + line.chn;
				}
			} else if (line.tokkasokuKubun.equals("第二類物質") &&
					(line.ratio * 100) > line.tokkasokuTargetRange) {
				if (tokkasoku2.equals(nasi)) {
					tokkasoku2 = line.chn;
				} else {
					tokkasoku2 = tokkasoku2 + ", " + line.chn;
				}
			} else if (line.tokkasokuKubun.equals("第三類物質") &&
					(line.ratio * 100) > line.tokkasokuTargetRange) {
				if (tokkasoku3.equals(nasi)) {
					tokkasoku3 = line.chn;
				} else {
					tokkasoku3 = tokkasoku3 + ", " + line.chn;
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

		String yuukisokuKubun = translation.getChn("該当なし");

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
			yuukisokuKubun = translation.getChn("第一種有機溶剤等");
		} else if (total2 >= 0.05) {
			yuukisokuKubun = translation.getChn("第二種有機溶剤等");
		} else if (total3 >= 0.05) {
			yuukisokuKubun = translation.getChn("第三種有機溶剤等");
		}

		sdsFormat.insertSection15(yuukisokuKubun, "yuukisoku");
	}

	void insertLabelDisplay() {

		//名称を表示する物質 tableForLabelのStructTableForLabel.isDisplay
		//がtrueのものを挿入する。


		String nasi = translation.getChn("該当なし");
		String labelDisplay = nasi;

		for (StructTableForLabel line : tableForLabel) {
			if (line.isDisplay) {
				if (labelDisplay.equals(nasi)) {
					labelDisplay = line.chn;
				} else {
					labelDisplay = labelDisplay + ", " + line.chn;
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

		String nasi = translation.getChn("該当なし");
		String sdsNotification = nasi;

		for (StructSolDb line : dupliResultDb) {
			if ((line.ratio * 100) >= line.rouanMin) {
				if (sdsNotification.equals(nasi)) {
					sdsNotification = line.chn;
				} else {
					sdsNotification = sdsNotification + ", " + line.chn;
				}
			}
		}
		sdsFormat.insertSection15(sdsNotification, "sdsNotification");

	}

	void insertPrtr() {

		String nasi = translation.getChn("該当なし");
		String prtr1 = nasi;
		String prtr2 = nasi;
		String prtrToku1 = nasi;
		for (StructSolDb line : resultDb) {
			if (line.prtrBunrui5.equals("第一種") &&
					(line.ratio * 100) > 1.0) {
				if (prtr1.equals(nasi)) {
					prtr1 = line.chn;
				} else {
					prtr1 = prtr1 + ", " + line.chn;
				}
			} else if (line.prtrBunrui5.equals("第二種") &&
					(line.ratio * 100) > 1.0) {
				if (prtr2.equals(nasi)) {
					prtr2 = line.chn;
				} else {
					prtr2 = prtr2 + ", " + line.chn;
				}
			} else if (line.prtrBunrui5.equals("特定第一種") &&
					(line.ratio * 100) > 1.0) {
				if (prtrToku1.equals(nasi)) {
					prtrToku1 = line.chn;
				} else {
					prtrToku1 = prtrToku1 + ", " + line.chn;
				}
			}
		}
		sdsFormat.insertSection15(prtr1, "prtr1");
		sdsFormat.insertSection15(prtr2, "prtr2");
		sdsFormat.insertSection15(prtrToku1, "prtrToku1");
	}

	void insertKasinhouYuusen() {

		String nasi = translation.getChn("該当なし");
		String kasinhouYuusen = nasi;
		for (StructSolDb line : resultDb) {
			if (line.kasinhouType.equals("優先評価化学物質")) {
				if (kasinhouYuusen.equals(nasi)) {
					kasinhouYuusen = line.chn;
				} else {
					kasinhouYuusen = kasinhouYuusen + ", " + line.chn;
				}
			}
		}
		sdsFormat.insertSection15(kasinhouYuusen, "kasinhouYuusen");
	}

	void insertShipSaftyAct() {

		String strShipSaftyAct = translation.getChn("引火性液体類");
		if(inputForm.hinban.contains("-MT-")) {
			strShipSaftyAct = translation.getChn("各法令に従うこと");
		}

		sdsFormat.insertSection15(strShipSaftyAct, "shipSaftyAct");
	}

	void insertAviationAct() {

		String strAviationAct = translation.getChn("引火性液体");
		if(inputForm.hinban.contains("-MT-")) {
			strAviationAct = translation.getChn("各法令に従うこと");
		}

		sdsFormat.insertSection15(strAviationAct, "aviationAct");
	}

	void insertGhs() {
		excelGhsInsertChn.insertGhs();
	}


	void insertComponent() {

		//最後に成分表を挿入して不要な行を削除する。、ファイルに出力
		sdsFormat.insertTableOfComponent(tableForNotice);
	}


	void insertHazardInfo() {
		//最最後に危険有害性情報を挿入して、不要な行を削除する。
		excelGhsInsertChn.insertHazardInfo();
	}


	void outputExcel() {
		sdsFormat.outputExcel();
	}

}
