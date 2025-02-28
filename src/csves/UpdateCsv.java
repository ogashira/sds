package csves;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import components.calcComponents.SolventDb;
import structures.StructInputForm;
import structures.StructNite;
import structures.StructTableForLabel;

public class UpdateCsv {

	public static void updateSolventDb(List<StructNite> multiStructNite) {

		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからflashpoint.csvのﾊﾟｽを取得する
		//PathsｸﾗｽのstaticﾒｿｯﾄﾞgetPath()で取得する。
		String filePath = GetPath.getPath("solDb");

		final String encode = "UTF-8";

		SolventDb solventDb = new SolventDb();
		List<String[]> solventDbAll = solventDb.getSolventDbAll();

		//niteのﾃﾞｰﾀは％表示なので、溶剤DBに記入するときは100で割る
		//記入するとき、","は"、"に変換。floatの","は""にする。
		for (StructNite niteLine : multiStructNite) {
			if (niteLine.isSuccess) {
				for (String[] solLine : solventDbAll) {
					if (solLine[4].equals(niteLine.casNo)) {

						if (!(niteLine.rangeOfNotification == 9999)) {
							solLine[6] = Float.toString((niteLine.rangeOfNotification) / 100).replace(",", "");
						}

						if (!(niteLine.prtrNo5.equals("null"))) {
							solLine[7] = niteLine.prtrNo5.replace(",", "、");
						}

						if (!(niteLine.niSsanEiPpm == 9999)) {
							solLine[19] = Float.toString(niteLine.niSsanEiPpm).replace(",", "");
						}

						if (!(niteLine.aneihouNo.equals("null"))) {
							solLine[25] = niteLine.aneihouNo.replace(",", "、");
						}

						if (!(niteLine.kasinhouNo.equals("null"))) {
							solLine[28] = niteLine.kasinhouNo.replace(",", "、");
						}

						if (!(niteLine.kasinhouType.equals("null"))) {
							solLine[29] = niteLine.kasinhouType.replace(",", "、");
						}

						if (!(niteLine.prtrNo5.equals("null"))) {
							solLine[30] = niteLine.prtrNo5.replace(",", "、");
						}

						if (!(niteLine.prtrBunrui4.equals("null"))) {
							solLine[31] = niteLine.prtrBunrui4.replace(",", "、");
						}

						if (!(niteLine.prtrBunrui5.equals("null"))) {
							solLine[32] = niteLine.prtrBunrui5.replace(",", "、");
						}

						if (!(niteLine.tokkasokuKubun.equals("null"))) {
							solLine[33] = niteLine.tokkasokuKubun.replace(",", "、");
						}

						if (!(niteLine.tokkasokuNo.equals("null"))) {
							solLine[34] = niteLine.tokkasokuNo.replace(",", "、");
						}

						if (!(niteLine.tokkasokuTargetRange == 9999)) {
							solLine[35] = Float.toString((niteLine.tokkasokuTargetRange) / 100).replace(",", "");
						}

						if (!(niteLine.kanriNoudo.equals("null"))) {
							solLine[36] = niteLine.kanriNoudo.replace(",", "、");
						}

						if (!(niteLine.rangeOfDisplay == 9999)) {
							solLine[37] = Float.toString((niteLine.rangeOfDisplay) / 100).replace(",", "");
						}

						if (!(niteLine.yuukisokuKubun.equals("null"))) {
							solLine[38] = niteLine.yuukisokuKubun.replace(",", "、");
						}

						if (!(niteLine.yuukisokuNo.equals("null"))) {
							solLine[39] = niteLine.yuukisokuNo.replace(",", "、");
						}

						if (!(niteLine.dokugekiBunrui.equals("null"))) {
							solLine[40] = niteLine.dokugekiBunrui.replace(",", "、");
						}

						if (!(niteLine.dokugekiNo.equals("null"))) {
							solLine[41] = niteLine.dokugekiNo.replace(",", "、");
						}

						if (!(niteLine.unNo.equals("null"))) {
							solLine[42] = niteLine.unNo.replace(",", "、");
						}

						if (!(niteLine.unClass.equals("null"))) {
							solLine[43] = niteLine.unClass.replace(",", "、");
						}

						if (!(niteLine.unName.equals("null"))) {
							solLine[44] = niteLine.unName.replace(",", "、");
						}

						if (!(niteLine.hsCode.equals("null"))) {
							solLine[45] = niteLine.hsCode.replace(",", "、");
						}

						solLine[46] = niteLine.update;
					}
				}
			}
		}

		CreateCsv createCsv = new CreateCsv();
		createCsv.toCsv(solventDbAll, filePath, encode);

	}


	public static void updateGhsDb(StructInputForm inputForm, float flashPoint,
	                                     float boilingPoint, List<String> listPict,
	                                     Map<String, Map<String, String>> allMap,
	                                     Map<String,String> mapHazardInfoExposure) {

		final String encode = "UTF-8";
		String filePath = GetPath.getPath("ghsDb2");
		DownLoadCsv dl = new DownLoadCsv(filePath, encode);
		List<String[]> listGhsDb = dl.getCsvData();

		String hinban = inputForm.hinban;
		String[] updateLine;
		Boolean isExist = false;
		for (String[] ghsDbLine : listGhsDb) {
			if (ghsDbLine[0].equals(hinban)) {
				lineUpDate(ghsDbLine, inputForm, flashPoint, boilingPoint,
						                     listPict, allMap,mapHazardInfoExposure);

				isExist = true;
				break;
			}
		}
		if(!isExist) {
			updateLine = new String[87];
			lineUpDate(updateLine, inputForm, flashPoint, boilingPoint,
					                         listPict, allMap, mapHazardInfoExposure);
			listGhsDb.add(updateLine);
		}


		CreateCsv createCsv = new CreateCsv();
		createCsv.toCsv(listGhsDb, filePath, encode);
	}


	private static void lineUpDate(String[] line, StructInputForm inputForm,
			       float flashPoint, float boilingPoint, List<String> listPict,
			                             Map<String, Map<String,String>> allMap,
			                             Map<String,String> mapHazardInfoExposure) {

		line[0] = inputForm.hinban;
		line[1] = inputForm.displayName;
		line[2] = inputForm.tantou;
		if(line[3] == null) {line[3] = "";}
		line[4] = inputForm.paintAppe;
		line[5] = "特異臭";
		line[6] = inputForm.SG;
		line[7] = String.valueOf(flashPoint);
		line[8] = String.valueOf(boilingPoint);
		if(line[9] == null) {line[9] = ""; }
		if(line[10] == null) {line[10] = "";}
		if(line[11] == null) {line[11] = "";}
		if(inputForm.singleMixture == "Single") {
			line[12] = inputForm.ghsBunrui;
			line[13] = inputForm.ghsNo;
			line[14] = inputForm.ghsName;
			line[15] = inputForm.ghsYouki;
		}else {
			line[12] = "ｸﾗｽ3";
			line[13] = "1263";
			line[14] = "ﾍﾟｲﾝﾄ";
			line[15] = getYouki(flashPoint);
		}
		if(allMap.get("mapPhysical").get("kubun").equals("-") ||
				       allMap.get("mapPhysical").get("kubun").equals("分類できない") ||
				       allMap.get("mapPhysical").get("kubun").equals("区分に該当しない")) {
			line[16] = "";
		} else {
			line[16] = allMap.get("mapPhysical").get("kubun");
		}

		if(listPict.contains("fire"))       { line[17] = "○";}else{line[17] = "×";}
		if(listPict.contains("docro"))      { line[18] = "○";}else{line[18] = "×";}
		if(listPict.contains("exclamation")){ line[19] = "○";}else{line[19] = "×";}
		if(listPict.contains("corrosion"))  { line[20] = "○";}else{line[20] = "×";}
		if(listPict.contains("health"))     { line[21] = "○";}else{line[21] = "×";}
		if(listPict.contains("fish"))       { line[22] = "○";}else{line[22] = "×";}

		if(line[23] == null) {line[23] = "";}

		if(allMap.get("mapPhysical").get("signalWord").equals("-")) {
			line[24] = "";
		} else {
			line[24] = allMap.get("mapPhysical").get("signalWord");
		}

		if(line[25] == null) {line[25] = "";}

		if(allMap.get("mapOral").get("signalWord").equals("-")) {
			line[26] = "";
		} else {
			line[26] = allMap.get("mapOral").get("signalWord");
		}
		if(allMap.get("mapTransdermal").get("signalWord").equals("-")) {
			line[27] = "";
		} else {
			line[27] = allMap.get("mapTransdermal").get("signalWord");
		}
		if(allMap.get("mapInhalationGas").get("signalWord").equals("-")) {
			line[28] = "";
		} else {
			line[28] = allMap.get("mapInhalationGas").get("signalWord");
		}
		if(allMap.get("mapInhalationSteam").get("signalWord").equals("-")) {
			line[29] = "";
		} else {
			line[29] = allMap.get("mapInhalationSteam").get("signalWord");
		}
		if(allMap.get("mapInhalationDust").get("signalWord").equals("-")) {
			line[30] = "";
		} else {
			line[30] = allMap.get("mapInhalationDust").get("signalWord");
		}
		if(allMap.get("mapSkinIrritation").get("signalWord").equals("-")) {
			line[31] = "";
		} else {
			line[31] = allMap.get("mapSkinIrritation").get("signalWord");
		}
		if(allMap.get("mapEyeIrritation").get("signalWord").equals("-")) {
			line[32] = "";
		} else {
			line[32] = allMap.get("mapEyeIrritation").get("signalWord");
		}
		if(allMap.get("mapLiquidSensitization").get("signalWord").equals("-")) {
			line[33] = "";
		} else {
			line[33] = allMap.get("mapLiquidSensitization").get("signalWord");
		}
		if(allMap.get("mapGasSensitization").get("signalWord").equals("-")) {
			line[34] = "";
		} else {
			line[34] = allMap.get("mapGasSensitization").get("signalWord");
		}
		if(allMap.get("mapSkinSensitization").get("signalWord").equals("-")) {
			line[35] = "";
		} else {
			line[35] = allMap.get("mapSkinSensitization").get("signalWord");
		}
		if(allMap.get("mapMutagenicity").get("signalWord").equals("-")) {
			line[36] = "";
		} else {
			line[36] = allMap.get("mapMutagenicity").get("signalWord");
		}
		if(allMap.get("mapCarcinogenic").get("signalWord").equals("-")) {
			line[37] = "";
		} else {
			line[37] = allMap.get("mapCarcinogenic").get("signalWord");
		}
		if(allMap.get("mapReproductiveToxicity").get("signalWord").equals("-")) {
			line[38] = "";
		} else {
			line[38] = allMap.get("mapReproductiveToxicity").get("signalWord");
		}
		if(allMap.get("mapMilk").get("signalWord").equals("-")) {
			line[39] = "";
		} else {
			line[39] = allMap.get("mapMilk").get("signalWord");
		}

		//単回暴露1~3のsignalWord
		boolean isDanger = false;
		boolean isCaution = false;
		String singles[] = {allMap.get("mapSingle1").get("signalWord"),
				           allMap.get("mapSingle2").get("signalWord"),
				           allMap.get("mapSingle3").get("signalWord")};
		for(String str : singles) {
			if(str.equals("危険")) {
				isDanger = true;
			}
			if(str.equals("警告")) {
				isCaution = true;
			}
		}
		if(isDanger) {
			line[40] = "危険";
		} else if(!isDanger && isCaution) {
			line[40] = "警告";
		} else if(!isDanger && !isCaution) {
			line[40] = "";
		}


		//反復暴露1~2 のsignalWord
		isDanger = false;
		isCaution = false;
		String repeats[] = {allMap.get("mapRepeat1").get("signalWord"),
				           allMap.get("mapRepeat2").get("signalWord")  };

		for(String str : repeats) {
			if(str.equals("危険")) {
				isDanger = true;
			}
			if(str.equals("警告")) {
				isCaution = true;
			}
		}
		if(isDanger) {
			line[41] = "危険";
		} else if(!isDanger && isCaution) {
			line[41] = "警告";
		} else if(!isDanger && !isCaution) {
			line[41] = "";
		}


		if(allMap.get("mapAspiration").get("signalWord").equals("-")) {
			line[42] = "";
		} else {
			line[42] = allMap.get("mapAspiration").get("signalWord");
		}
		if(allMap.get("mapShort").get("signalWord").equals("-")) {
			line[43] = "";
		} else {
			line[43] = allMap.get("mapShort").get("signalWord");
		}
		if(allMap.get("mapLong").get("signalWord").equals("-")) {
			line[44] = "";
		} else {
			line[44] = allMap.get("mapLong").get("signalWord");
		}
		if(allMap.get("mapOzone").get("signalWord").equals("-")) {
			line[45] = "";
		} else {
			line[45] = allMap.get("mapOzone").get("signalWord");
		}


		if(allMap.get("mapPhysical").get("hazardInfo").equals("-")) {
			line[46] = "";
		} else {
			line[46] = allMap.get("mapPhysical").get("hazardInfo");
		}

		if(line[47] == null) {line[47] = "";}

		if(allMap.get("mapOral").get("hazardInfo").equals("-")) {
			line[48] = "";
		} else {
			line[48] = allMap.get("mapOral").get("hazardInfo");
		}
		if(allMap.get("mapTransdermal").get("hazardInfo").equals("-")) {
			line[49] = "";
		} else {
			line[49] = allMap.get("mapTransdermal").get("hazardInfo");
		}
		if(allMap.get("mapInhalationGas").get("hazardInfo").equals("-")) {
			line[50] = "";
		} else {
			line[50] = allMap.get("mapPlationGashysical").get("hazardInfo");
		}
		if(allMap.get("mapInhalationSteam").get("hazardInfo").equals("-")) {
			line[51] = "";
		} else {
			line[51] = allMap.get("mapInhalationSteam").get("hazardInfo");
		}
		if(allMap.get("mapInhalationDust").get("hazardInfo").equals("-")) {
			line[52] = "";
		} else {
			line[52] = allMap.get("mapInhalationDust").get("hazardInfo");
		}
		if(allMap.get("mapSkinIrritation").get("hazardInfo").equals("-")) {
			line[53] = "";
		} else {
			line[53] = allMap.get("mapSkinIrritation").get("hazardInfo");
		}
		if(allMap.get("mapEyeIrritation").get("hazardInfo").equals("-")) {
			line[54] = "";
		} else {
			line[54] = allMap.get("mapEyeIrritation").get("hazardInfo");
		}
		if(allMap.get("mapLiquidSensitization").get("hazardInfo").equals("-")) {
			line[55] = "";
		} else {
			line[55] = allMap.get("mapLiquidSensitization").get("hazardInfo");
		}
		if(allMap.get("mapGasSensitization").get("hazardInfo").equals("-")) {
			line[56] = "";
		} else {
			line[56] = allMap.get("mapGasSensitization").get("hazardInfo");
		}
		if(allMap.get("mapSkinSensitization").get("hazardInfo").equals("-")) {
			line[57] = "";
		} else {
			line[57] = allMap.get("mapSkinSensitization").get("hazardInfo");
		}
		if(allMap.get("mapMutagenicity").get("hazardInfo").equals("-")) {
			line[58] = "";
		} else {
			line[58] = allMap.get("mapMutagenicity").get("hazardInfo");
		}
		if(allMap.get("mapCarcinogenic").get("hazardInfo").equals("-")) {
			line[59] = "";
		} else {
			line[59] = allMap.get("mapCarcinogenic").get("hazardInfo");
		}
		if(allMap.get("mapReproductiveToxicity").get("hazardInfo").equals("-")) {
			line[60] = "";
		} else {
			line[60] = allMap.get("mapReproductiveToxicity").get("hazardInfo");
		}
		if(allMap.get("mapMilk").get("hazardInfo").equals("-")) {
			line[61] = "";
		} else {
			line[61] = allMap.get("mapMilk").get("hazardInfo");
		}

		String singleInfo = "";
		if(!(allMap.get("mapSingle1").get("hazardInfo").equals("-"))) {
			singleInfo = allMap.get("mapSingle1").get("hazardInfo");
		}else if(!(allMap.get("mapSingle2").get("hazardInfo").equals("-"))) {
			singleInfo = allMap.get("mapSingle2").get("hazardInfo");
		}else if(!(allMap.get("mapSingle3").get("hazardInfo").equals("-"))) {
			singleInfo = allMap.get("mapSingle3").get("hazardInfo");
		}
		line[62] = singleInfo;


		String repeatInfo = "";
		if(!(allMap.get("mapRepeat1").get("hazardInfo").equals("-"))) {
			repeatInfo = allMap.get("mapRepeat1").get("hazardInfo");
		}else if(!(allMap.get("mapRepeat2").get("hazardInfo").equals("-"))) {
			repeatInfo = allMap.get("mapRepeat2").get("hazardInfo");
		}
		line[63] = repeatInfo;


		if(allMap.get("mapAspiration").get("hazardInfo").equals("-")) {
			line[64] = "";
		} else {
			line[64] = allMap.get("mapAspiration").get("hazardInfo");
		}

		if(allMap.get("mapShort").get("hazardInfo").equals("-")) {
			line[65] = "";
		} else {
			line[65] = allMap.get("mapShort").get("hazardInfo");
		}
		if(allMap.get("mapLong").get("hazardInfo").equals("-")) {
			line[66] = "";
		} else {
			line[66] = allMap.get("mapLong").get("hazardInfo");
		}
		if(allMap.get("mapOzone").get("hazardInfo").equals("-")) {
			line[67] = "";
		} else {
			line[67] = allMap.get("mapOzone").get("hazardInfo");
		}

		//単回暴露1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		if(allMap.get("mapSingle1").get("kubun").equals("-") ||
				       allMap.get("mapSingle1").get("kubun").equals("分類できない") ||
				       allMap.get("mapSingle1").get("kubun").equals("区分に該当しない")) {
			line[68] = "";
		} else {
			line[68] = allMap.get("mapSingle1").get("kubun");
		}

		if(line[69] == null) {line[69] = "";}

		if(mapHazardInfoExposure.get("organSingle1").equals("-(-)")) {
			line[70] = "";
		} else {
			line[70] = mapHazardInfoExposure.get("organSingle1");
		}
		if(line[71] == null) {line[71] = "";}

		//単回暴露2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		if(allMap.get("mapSingle2").get("kubun").equals("-") ||
				       allMap.get("mapSingle2").get("kubun").equals("分類できない") ||
				       allMap.get("mapSingle2").get("kubun").equals("区分に該当しない")) {
			line[72] = "";
		} else {
			line[72] = allMap.get("mapSingle2").get("kubun");
		}

		if(line[73] == null) {line[73] = "";}

		if(mapHazardInfoExposure.get("organSingle2").equals("-(-)")) {
			line[74] = "";
		} else {
			line[74] = mapHazardInfoExposure.get("organSingle2");
		}
		if(line[75] == null) {line[75] = "";}

		//単回暴露3 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		if(allMap.get("mapSingle3").get("kubun").equals("-") ||
				       allMap.get("mapSingle3").get("kubun").equals("分類できない") ||
				       allMap.get("mapSingle3").get("kubun").equals("区分に該当しない")) {
			line[76] = "";
		} else {
			line[76] = allMap.get("mapSingle3").get("kubun");
		}

		if(line[77] == null) {line[77] = "";}

		if(mapHazardInfoExposure.get("organSingle3").equals("-(-)")) {
			line[78] = "";
		} else {
			line[78] = mapHazardInfoExposure.get("organSingle3");
		}

		//反復暴露1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		if(allMap.get("mapRepeat1").get("kubun").equals("-") ||
				       allMap.get("mapRepeat1").get("kubun").equals("分類できない") ||
				       allMap.get("mapRepeat1").get("kubun").equals("区分に該当しない")) {
			line[79] = "";
		} else {
			line[79] = allMap.get("mapRepeat1").get("kubun");
		}

		if(line[80] == null) {line[80] = "";}

		if(mapHazardInfoExposure.get("organRepeat1").equals("-(-)")) {
			line[81] = "";
		} else {
			line[81] = mapHazardInfoExposure.get("organRepeat1");
		}

		if(line[82] == null) {line[82] = "";}

		//反復暴露2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		if(allMap.get("mapRepeat2").get("kubun").equals("-") ||
				       allMap.get("mapRepeat2").get("kubun").equals("分類できない") ||
				       allMap.get("mapRepeat2").get("kubun").equals("区分に該当しない")) {
			line[83] = "";
		} else {
			line[83] = allMap.get("mapRepeat2").get("kubun");
		}

		if(line[84] == null) {line[84] = "";}

		if(mapHazardInfoExposure.get("organRepeat2").equals("-(-)")) {
			line[85] = "";
		} else {
			line[85] = mapHazardInfoExposure.get("organRepeat2");
		}

		//計算日>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMdd");
		String update = dtf.format(cal.getTime());
		line[86] = update;
	}

	private static String getYouki(float flashPoint) {
		if (flashPoint < 23) {
			return "2";
		}
		if (flashPoint >= 23 & flashPoint <= 60.5) {
			return "3";
		}
		return "非危険物";
	}


	//db_2の更新>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public static void updateDb(StructInputForm inputForm,
			                       List<StructTableForLabel> listTableForLabel,
			                       String syoubouhou) {

		final String encode = "UTF-8";
		String filePath = GetPath.getPath("db2");
		DownLoadCsv dl = new DownLoadCsv(filePath, encode);
		List<String[]> listDb = dl.getCsvData();

		String hinban = inputForm.hinban;
		String[] updateLine;
		Boolean isExist = false;  //db_2に品番があるか？
		for (String[] dbLine : listDb) {
			if (dbLine[0].equals(hinban)) {
				dbLineUpDate(dbLine, inputForm, listTableForLabel, syoubouhou);

				isExist = true;
				break;
			}
		}
		if(!isExist) {    // 品番が無い場合は新たに配列を作る
			updateLine = new String[32];
			dbLineUpDate(updateLine, inputForm, listTableForLabel, syoubouhou);
			listDb.add(updateLine);
		}

		CreateCsv createCsv = new CreateCsv();
		createCsv.toCsv(listDb, filePath, encode);
	}


	private static void dbLineUpDate(String[] line, StructInputForm inputForm,
			                             List<StructTableForLabel> listTableForLabel,
			                             String syoubouhou) {

		line[0] = inputForm.hinban;
		line[1] = inputForm.displayName;
		line[2] = inputForm.tantou;
		if(line[3] == null) {line[3] = "";}
		if(line[4] == null) {line[4] = "";}
		if(line[5] == null) {line[5] = "";}
		if(line[6] == null) {line[6] = "";}
		if(line[7] == null) {line[7] = "";}
		if(line[8] == null) {line[8] = "";}
		line[9] = inputForm.displayName;
		if(line[10] == null) {line[10] = "";}
		if(line[11] == null) {line[11] = "";}

		if(syoubouhou.contains("特殊引火物")) {
			line[12] = "特殊引火物";
			line[13] = "1";
		}else if(syoubouhou.contains("第１石油類")) {
			line[12] = "1";
			line[13] = "2";
		}else if(syoubouhou.contains("第２石油類")) {
			line[12] = "2";
			line[13] = "3";
		}else if(syoubouhou.contains("第３石油類")) {
			line[12] = "3";
			line[13] = "3";
		}else if(syoubouhou.contains("第４石油類")) {
			line[12] = "4";
			line[13] = "3";
		}else if(syoubouhou.contains("動植物油類")){
			line[12] = "動植物油類";
			line[13] = "3";
		}else if(syoubouhou.contains("引火性固体")) {
			line[12] = "引火性固体";
			line[13] = "3";
		}else if(syoubouhou.contains("可燃性固体類")){
			line[12] = "可燃性固体類";
			line[13] = "";
		} else {
			line[12] ="No data";
			line[13] ="No data";
		}

		if(inputForm.hinban.matches("^S.*-EX$")) {
			line[14] = "輸出";
		}else {
			line[14] = "国内";
		}

		if(line[15] == null) {line[15] = "";}
		if(line[16] == null) {line[16] = "";}
		if(line[17] == null) {line[17] = "";}

		line[18] =  ""; //ここは労安表示をｺﾋﾟﾍﾟするので、必ず空白にしておく

		if(line[19] == null) {line[19] = "";}
		if(line[20] == null) {line[20] = "";}


		if(inputForm.singleMixture.equals("Single")) {
			line[21] = inputForm.ghsNo;
		}else {
			line[21] = "1263";
		}

		//計算日>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMdd");
		String update = dtf.format(cal.getTime());
		line[22] = update;

		// 労安表示中国% >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		line[23] = getRouan(listTableForLabel, "chn", true);

		if(line[24] == null) {line[20] = "";}
		if(line[25] == null) {line[20] = "";}
		if(line[26] == null) {line[20] = "";}
		if(line[27] == null) {line[20] = "";}
		if(line[28] == null) {line[20] = "";}

		if(inputForm.hinban.matches("^S.*-EX$")) {
			line[29] = getRouan(listTableForLabel, "eng", true);
			line[30] = getRouan(listTableForLabel, "eng", false);
		}else {
			line[29] = getRouan(listTableForLabel, "jap", true);
			line[30] = getRouan(listTableForLabel, "jap", false);
		}
		line[31] = getRouan(listTableForLabel, "chn", false);
	}



	private static String getRouan(List<StructTableForLabel> listTableForLabel,
			                                 String country, boolean isPercent) {
		/*
		 * listTableForLabelが空なら""を返す。
		 * からでなければfor文にはいり、isDisplayがfalseだったら即continue
		 * rouanPercentに文字列を作っていく country(chn, eng, jap)によって
		 * label.chn, label.eng, label.componentに振り分ける
		 */
		String rouan = "";
		if(listTableForLabel.size() > 0) {
			for(StructTableForLabel label : listTableForLabel) {

				if(!label.isDisplay) {
					continue;
				}
				rouan = addRouan(rouan, label, country, isPercent);
				}
			}
		//listTableForLabel が空の場合、isDisplayが全てfalseの場合は""が返る
		return rouan;
	}

	private static String addRouan(String rouan, StructTableForLabel label,
			                                 String country, Boolean isPercent) {
		String lang = "";
		if(country.equals("eng")) {lang = label.eng;}
		if(country.equals("jap")) {lang = label.component;}
		if(country.equals("chn")) {lang = label.chn;}

		if(country.equals("chn") && isPercent) {
			if(rouan.equals("")) {
				rouan = lang + "(" + label.component + ")" + label.contentRate;
			}else {
				rouan = rouan + "、" + lang + "(" + label.component + ")"
			                                                    + label.contentRate;
			}
		}
		if(country.equals("chn") && !isPercent) {
			if(rouan.equals("")) {
				rouan = lang + "(" + label.component + ")";
			}else {
				rouan = rouan + "、" + lang + "(" + label.component + ")" ;
			}
		}
		//countryがengまたはjapの場合は、変数 langに代入して実行する

		if((country.equals("eng") || country.equals("jap")) && isPercent) {
			if(rouan.equals("")) {
				rouan = lang + label.contentRate;
			}else {
				rouan = rouan + "、" + lang + label.contentRate;
			}
		}
		if((country.equals("eng") || country.equals("jap")) && !isPercent) {
			if(rouan.equals("")) {
				rouan = lang;
			}else {
				rouan = rouan + "、" + lang;
			}
		}
		return rouan;
	}
}
