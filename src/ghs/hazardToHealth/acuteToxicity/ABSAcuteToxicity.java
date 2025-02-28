package ghs.hazardToHealth.acuteToxicity;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class ABSAcuteToxicity {

	//	protected List<String[]> listOral;
	protected List<String[]> listFilterdGmiccs; //Gmiccsを必要な原料でfilter
	protected float nonListRatio; //Gmiccsに載っていない原料の合計量
	protected float nonAteRatio; //区分が1~5以外でATEが空白である原料の合計値

	abstract float getUnknownRatio();

	abstract float getAteMix();

	abstract Map<String, String> getMapToxicity();

	ABSAcuteToxicity(List<String[]> listFilterdGmiccs, float nonListRatio) {
		this.listFilterdGmiccs = listFilterdGmiccs;
		this.nonListRatio = nonListRatio;
	}

	// nonAteRatio 区分が1~5以外でATEが空白である原料の合計量
	float getNonAteRatio(List<String[]> listToxicity) {
		nonAteRatio = 0;

		//GmiccsのATEの列と区分の列
		int ateCol = Integer.parseInt(listToxicity.get(1)[8]);
		int kubunCol = Integer.parseInt(listToxicity.get(1)[7]);

		//kubunToIntに区分の文字を渡すと、区分の数値が返ってくる。
		//区分1~5以外の場合は0が返ってくる。
		for (String[] line : listFilterdGmiccs) {
			int intKubun = this.kubunToInt(line[kubunCol - 1]);
			if (line[ateCol - 1].equals("") && intKubun == 0) {
				nonAteRatio += Float.parseFloat(line[(line.length) - 1]);
			}
		}

		return nonAteRatio;
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

	float getAteMix(List<String[]> listToxicity, float unknownRatio) {
		//GmiccsのATEの列と区分の列
		int ateCol = Integer.parseInt(listToxicity.get(1)[8]);
		int kubunCol = Integer.parseInt(listToxicity.get(1)[7]);
		float ate = 0;
		float ateMix = 0;
		float ratio;
		//ateが""で、区分1~5以外では何もしない。
		//ateが"”の時、区分に応じてateに変換値を代入してratio/ateを加算
		//ateがある場合は、ratio/ateを加算
		//for文を抜けたら、最後に(1-unknownRatio)の割り算を行う
		for (String[] line : listFilterdGmiccs) {
			int intKubun = this.kubunToInt(line[kubunCol - 1]);

			if (line[ateCol - 1].equals("") && intKubun == 0) {
				continue;
			}

			if (line[ateCol - 1].equals("") && intKubun > 0) {
				ate = Float.parseFloat(listToxicity.get(intKubun)[6]);
				ratio = Float.parseFloat(line[line.length - 1]);
				ateMix += (ratio / ate);
				continue;
			}

			ate = Float.parseFloat(line[ateCol - 1]);
			ratio = Float.parseFloat(line[line.length - 1]);
			ateMix += (ratio / ate);
		}

		if (ateMix == 0) {
			return 0;
		}

		ateMix = (1 - unknownRatio) / ateMix;

		return ateMix;

	}

	Map<String, String> getMapToxicity(List<String[]> listToxicity,
			float ateMix, float unknownRatio) {
		/*
		 * listToxicity : oral表 or transdermal表 or inhalationGas表 or
		 * inhalationSteam表 or inhalationDust表
		 * <=の記号と ATEの文字列(数字)とateMixをjudgeRowに渡して、
		 * true,falseの判定をしてもらう。trueならば、その時点でfor文を
		 * 抜け、その時のrowCntの値が表の行数となる。
		 * その行数を使ってMap(ateMix,kubun,pictogram,signalWord,hazardInfo)を作る。
		 */

		Map<String, String> mapToxicity = new HashMap<>();

		int rowCnt = 1;

		for (int i = 1; i < listToxicity.size(); i++) {
			String comparisonOperator = listToxicity.get(i)[4];
			String strAte = listToxicity.get(i)[5];
			boolean judge = this.judgeRow(comparisonOperator, strAte, ateMix);
			if (judge) {
				break;
			}
			rowCnt++;
		}

		//分類結果が区分５になった場合、jisでは区分５を採用していないので、
		//「区分に該当しない」に置き換える。
		if (rowCnt == 5) {
			rowCnt = 6;
		}

		mapToxicity.put("ateMix", new DecimalFormat("#.0").format(ateMix));
		mapToxicity.put("kubun", listToxicity.get(rowCnt)[0]);
		mapToxicity.put("pictogram", listToxicity.get(rowCnt)[1]);
		mapToxicity.put("signalWord", listToxicity.get(rowCnt)[2]);
		mapToxicity.put("hazardInfo", listToxicity.get(rowCnt)[3]);

		//「区分に該当しない」となって、未知の成分合計濃度が1%以上の場合は
		//「分類できない」とする。
		if (unknownRatio >= 0.01 && mapToxicity.get("kubun").equals(
				"区分に該当しない")) {
			mapToxicity.put("kubun", "分類できない");
		}

		//全ての成分にデータまたは情報が全くない、または評価するのに不十分な場合は
		//「分類できない」として処理を行わない。
		//「区分に該当しない(分類対象外)」以外であったら以下の処理をする
		if (unknownRatio >= 1 && mapToxicity.get("ateMix").contains("分類対象外")) {
			mapToxicity.put("ateMix", "-");
			mapToxicity.put("kubun", "分類できない");
			mapToxicity.put("pictogram", "-");
			mapToxicity.put("signalWord", "-");
			mapToxicity.put("hazardInfo", "-");

			return mapToxicity;
		}

		return mapToxicity;

	}

	private boolean judgeRow(String comparisonOperator, String strAte,
			float ateMix) {
		/*
		 * 表の比較演算子、表のATE（文字列）、ATEMixを受け取って、
		 * 判定する。比較演算子が"-"の時はtrue
		 */
		if (comparisonOperator.equals("-")) {
			return true;
		}
		//ateMix==0の時trueになってしまわないように、&& ateMix>0を追加した。
		if (comparisonOperator.equals("<=")) {
			if (ateMix <= Float.parseFloat(strAte) && ateMix > 0) {
				return true;
			}
			return false;
		}
		if (comparisonOperator.equals("<")) {
			if (ateMix < Float.parseFloat(strAte) && ateMix > 0) {
				return true;
			}
			return false;
		}
		if (comparisonOperator.equals(">=")) {
			if (ateMix >= Float.parseFloat(strAte)) {
				return true;
			}
			return false;
		}
		if (comparisonOperator.equals(">")) {
			if (ateMix > Float.parseFloat(strAte)) {
				return true;
			}
			return false;
		}

		return false;

	}

}
