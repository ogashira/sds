package components.calcComponents;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import structures.StructSolDb;
import structures.StructTableForLabel;
import structures.StructTableForNotice;
import tools.CopyStruct;

public class TableOfComponent {

	public List<StructSolDb> summaryComponentForNotice(List<StructSolDb> resultDb) {

		/**
		 * 渡ってくるのはcopiedResultDb
		 *  -> 樹脂分のlist(listResin)
		 *      -> 通知義務のﾘｽﾄ(listTuuti)
		 *          -> その他(sonota)
		 * 上記のようにresultDbを３つに分ける。
		 * listResinの属NO16以下は一つにまとめる。
		 * listResinは属Noで昇順にｿｰﾄ後
		 * listTuutiはratioで降順にｿｰﾄ
		 * 最後に、３つをくっつけて、listTableOfComponentの完成！
		 *
		 *
		 *
		 * resultDbの表示不要の成分をその他に入れてまとめる
		 * List<StructSolDb>の中のsonotaをｺﾋﾟｰして、そこに
		 * 非表示の成分のratioを加算していく。
		 * 最後に、sonotaをsumResultDbについかする。*/

		List<StructSolDb> listResin = new ArrayList<>();//樹脂分ﾘｽﾄ
		List<StructSolDb> listTuuti = new ArrayList<>();//通知義務ﾘｽﾄ
		StructSolDb sonota = new StructSolDb(); //その他
		List<StructSolDb> componentForNotice = new ArrayList<>();

		//sonotaをｺﾋﾟｰして準備しておく
		//sonotaをnewして別のresultDbを参照しないようにする
		for (StructSolDb line : resultDb) {
			if (line.hinban.equals("G-SONOTA")) {
				//sonota = line; とするとresultDbを参照してしまう。
				//構造体lineのｺﾋﾟｰを作る
				//こうすれば、sonotaとresultDbが別のｵﾌﾞｼﾞｪｸﾄになる。
				sonota = CopyStruct.copyStructSolDb(line);
				break;
			}
		}

		//その他以外は、->
		//閾値以上だったらlineTuutiに追加
		//閾値以下だったら->
		//属noが16以下だったら、listResinに追加
		//属noが16以上だったら、ratioをsonotaにﾌﾟﾗｽする
		for (StructSolDb line : resultDb) {
			float ratioPer = line.ratio * 100;
			if (!(line.hinban.equals("G-SONOTA"))) {
				if (ratioPer >= line.rouanMin || ratioPer >= line.prtrMin) {
					//listTuutiに追加する
					listTuuti.add(line);
				} else {
					if (line.zokuNo <= 16) {
						line.casNo = "-"; //cas非表示は-に変更
						listResin.add(line);
					} else {
						//sonotaのratioに追加
						sonota.ratio += line.ratio;
					}
				}
			}
		}
		sonota.casNo = "-"; //その他のcasも-に変更

		//listTuutiのSDS表示名をhinmeiに変更しておく。
		//TMPTAの表示名はｱｸﾘﾙ樹脂であったが、法令改訂により、TMPTAの通知義務が
		//発生した。データベースは通知義務なしとなっているが、
		//niteのｽｸﾚｲﾋﾟﾝｸﾞの結果、通知義務ありとなるので、ﾄﾘﾒﾁﾛｰﾙﾌﾟﾛﾊﾟﾝﾄﾘｱｸﾘﾚｰﾄと
		//表示させる必要があるから
		for (StructSolDb line : listTuuti) {
			line.sdsDisplayName = line.hinmei;
		}



		//IK50でｱﾙﾐ顔料が重複したための措置。両顔料とも同casNoだが、通知義務あり
		//listTuutiのCasNOの重複をなくしratioを調整する
		//listDupliTuutiの中に重複するcasnoがあったら、ratioをﾌﾟﾗｽ
		//無かったら、listDupliTuutiに追加する
		List<StructSolDb> listDupliTuuti = new ArrayList<>();
		for (StructSolDb resin : listTuuti) {
			Boolean isExist = false;
			for (StructSolDb dupliResin : listDupliTuuti) {
				if (dupliResin.casNo.equals(resin.casNo)) {
					dupliResin.ratio += resin.ratio;
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				listDupliTuuti.add(resin);
			}
		}



		//listResinの属NOの重複をなくしratioを調整する
		//listDupliResinの中に重複する属noがあったら、ratioをﾌﾟﾗｽ
		//無かったら、listDupliResinに追加する
		List<StructSolDb> listDupliResin = new ArrayList<>();
		for (StructSolDb resin : listResin) {
			Boolean isExist = false;
			for (StructSolDb dupliResin : listDupliResin) {
				if (dupliResin.zokuNo == resin.zokuNo) {
					dupliResin.ratio += resin.ratio;
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				listDupliResin.add(resin);
			}
		}

		//listDupliResinとlistDupliTuutiをsortする
		//
		//属noの配列を作ってsortする
		int[] zokuArr = new int[listDupliResin.size()];
		int i = 0;
		for (StructSolDb line : listDupliResin) {
			zokuArr[i] = line.zokuNo;
			i++;
		} //listDupliResinがnullでも動いた！
		Arrays.sort(zokuArr);

		//listDupliResinをsortする
		//zokuArrの順にlistDupliResinSortedに追加していく
		List<StructSolDb> listDupliResinSorted = new ArrayList<>();
		for (i = 0; i < zokuArr.length; i++) {
			for (StructSolDb line : listDupliResin) {
				if (zokuArr[i] == line.zokuNo) {
					listDupliResinSorted.add(line);
				}
			}
		}

		// listTuutiSorted作る> listDupliTuutiに要素がある場合のみ>>>>>>>>>>
		List<StructSolDb> listTuutiSorted = new ArrayList<>();
		if(listDupliTuuti.size()>0) {
			//ratioの配列を作ってsortする
			float[] ratioArr = new float[listDupliTuuti.size()];
			i = 0;
			for (StructSolDb line : listDupliTuuti) {
				ratioArr[i] = line.ratio;
				i++;
			}

			//ratioArrのダブりをなくしておく 20230406に発見したバグ
			//これをやらないと、成分表に重複する原料が発生してしまう
			//ratioArrを上から順に見ていき、自分より下に同じratioが
			//あったらtrueを返す。同じものがあったらuniquRatioAttに
			//0を入れ、無ければratioをいれる。
			float uniqueRatioArr[] = new float[ratioArr.length];
			int cnt = 0;
			//ratioArrの要素が１つの場合はforの中に入らない
			for (i = 0; i < (ratioArr.length - 1); i++) {
				boolean isDist = false;
				for (int j = i + 1; j < ratioArr.length; j++)
					if (ratioArr[i] == ratioArr[j]) {
						isDist = true;
					}
				if (!isDist) {
					uniqueRatioArr[cnt] = ratioArr[i];
					cnt++;
				} else {
					uniqueRatioArr[cnt] = 0;
					cnt++;
				}
			}

			//ratioArrの最後は無条件で入れる
			uniqueRatioArr[cnt] = ratioArr[ratioArr.length - 1];


			//uniqueRatioArrをソートする
			Arrays.sort(uniqueRatioArr);

			//listDupliTuutiをsortする
			//ratioArrの逆順にlistTuutiSortedに追加していく
			//ratioが0の場合は追加しない
			for (i = ratioArr.length - 1; i >= 0; i--) {
				for (StructSolDb line : listDupliTuuti) {
					if (uniqueRatioArr[i] == 0) {
						break;
					}
					if (uniqueRatioArr[i] == line.ratio) {
						listTuutiSorted.add(line);
					}
				}
			}
		}
		// listTuutiSorted作る> ここまで>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		//３つを結合
		for (StructSolDb line : listDupliResinSorted) {
			componentForNotice.add(line);
		}
		for (StructSolDb line : listTuutiSorted) {
			componentForNotice.add(line);
		}
		if(sonota.ratio > 0) {
			componentForNotice.add(sonota);
		}


		//aneihouNo, prtrNoが空白またはnullの場合は"-"に変更
		//kasinhouNoが空白またはnullの場合は"あり"に変更
		for (StructSolDb line : componentForNotice) {

			if (line.rouanTuuti.equals("") || line.rouanTuuti.equals("null")) {
				line.rouanTuuti = "-";
			}
			if (line.prtr.equals("") || line.prtr.equals("null")) {
				line.prtr = "-";
			}
			if (line.prtrNo5.equals("") || line.prtrNo5.equals("null")) {
				line.prtrNo5 = "-";
			}
			if (line.kasinhouNo.equals("") || line.kasinhouNo.equals("null")) {
				line.kasinhouNo = "あり";
			}
		}

		return componentForNotice;
	}

	public List<StructTableForNotice> getTableForNotice(
			                          List<StructSolDb> listComponentForNotice) {

		//最終的なSDS用のﾃｰﾌﾞﾙを作る
		List<StructTableForNotice> tableForNotice = new ArrayList<>();
		for (StructSolDb line : listComponentForNotice) {
			StructTableForNotice tableRow = new StructTableForNotice();

			tableRow.component = line.sdsDisplayName;
			String marumeSds = getMarumeSds(line.ratio,
					line.rouanMin, line.prtrMin);
			tableRow.contentRate = marumeSds;
			tableRow.casNo = line.casNo;
			tableRow.aneihouNo = line.rouanTuuti;
			tableRow.prtrNo = line.prtrNo5;
			tableRow.kasinhouNo = line.kasinhouNo;
			tableRow.zokuNo = line.zokuNo;

			tableRow.sdsNameEng = line.sdsNameEng;
			tableRow.chn = line.chn;

			tableForNotice.add(tableRow);
		}


		return tableForNotice;
	}

	public List<StructTableForLabel> getTableForLabel(
			                                 List<StructSolDb> resultDb) {

		//最終的なﾗﾍﾞﾙ用の情報ﾃｰﾌﾞﾙを作る
		List<StructTableForLabel> tempTableForLabel = new ArrayList<>();
		for (StructSolDb line : resultDb) {
			StructTableForLabel tableRow = new StructTableForLabel();

			tableRow.hinban = line.hinban;
			tableRow.casNo = line.casNo;
			tableRow.component = line.hinmei;
			String marumeLabel = getMarumeLabel(line.ratio);
			tableRow.contentRate = marumeLabel;
			tableRow.isDisplay = judgeIsDisplay(line.ratio, line.displayMin);
			tableRow.eng = line.sdsNameEng;
			tableRow.chn = line.chn;
			tableRow.vet = null; //現時点ではvetの情報なし

			tempTableForLabel.add(tableRow);
		}

		//temptableForLabelの重複を無くしておく
		List<StructTableForLabel> tableForLabel = new ArrayList<>();
		for (StructTableForLabel beforeTable : tempTableForLabel) {
			Boolean isExist = false;
			for (StructTableForLabel afterTable : tableForLabel) {
				if (afterTable.casNo.equals(beforeTable.casNo)) {
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				tableForLabel.add(beforeTable);
			}
		}


		return tableForLabel;
	}

	private boolean judgeIsDisplay(float ratio, float displayMin) {
		boolean isDisplay = false;
		if (ratio * 100 >= displayMin) {
			isDisplay = true;
		}
		return isDisplay;
	}

	private String getMarumeSds(float ratio, float rouanMin, float prtrMin) {

		float ratioPersent = ratio * 100;
		BigDecimal bd = new BigDecimal(ratioPersent);
		BigDecimal round = bd.setScale(-1, RoundingMode.HALF_UP);
		BigDecimal roundup = bd.setScale(1, RoundingMode.UP);

		BigDecimal diff = bd.subtract(round);

		BigDecimal ZERO = new BigDecimal("0");
		BigDecimal TWO_HALF = new BigDecimal("2.5");
		BigDecimal FIVE = new BigDecimal("5");
		BigDecimal TEN = new BigDecimal("10");

		String marumeSds = null;

		if (ratioPersent <= rouanMin && ratioPersent <= prtrMin) {
			if (ratioPersent < 0.5) {
				marumeSds = "<0.5%";
			} else if (ratioPersent < 1) {
				marumeSds = "<1%";
			} else if (ratioPersent < 2) {
				marumeSds = "<2%";
			} else if (ratioPersent < 3) {
				marumeSds = "<3%";
			} else if (ratioPersent < 4) {
				marumeSds = "<4%";
			} else if (ratioPersent < 5) {
				marumeSds = "<5%";
			} else if (ratioPersent > 95) {
				marumeSds = ">95%";
			} else {
				//diffが0よりも大きい場合
				if (diff.compareTo(ZERO) == 1) {
					if (bd.compareTo(round.add(TWO_HALF)) == -1) {
						marumeSds = (round.subtract(FIVE)).toPlainString()
								+ "~"
								+ (round.add(FIVE)).toPlainString()
								+ "%";
					} else {
						marumeSds = round.toPlainString()
								+ "~"
								+ (round.add(TEN)).toPlainString()
								+ "%";
					}
				} else {
					if (bd.compareTo(round.subtract(TWO_HALF)) == -1) {
						marumeSds = (round.subtract(TEN)).toPlainString()
								+ "~"
								+ round.toPlainString()
								+ "%";
					} else {
						marumeSds = (round.subtract(FIVE)).toPlainString()
								+ "~"
								+ (round.add(FIVE)).toPlainString()
								+ "%";
					}
				}
			}

		} else {
			if (bd.compareTo(roundup) == -1) {
				marumeSds = "<" + roundup.toPlainString() + "%";
			} else {
				marumeSds = "<=" + roundup.toPlainString() + "%";
				//toPlainString()を付ければ、E+2のような表記はなくなる
				//プレーンな表記となる。
			}
		}
		return marumeSds;
	}

	private String getMarumeLabel(float ratio) {

		float ratioPersent = ratio * 100;
		BigDecimal bd = new BigDecimal(ratioPersent);
		BigDecimal round = bd.setScale(-1, RoundingMode.HALF_UP);
		//BigDecimal roundup = bd.setScale(1, RoundingMode.UP);

		BigDecimal diff = bd.subtract(round);

		BigDecimal ZERO = new BigDecimal("0");
		BigDecimal TWO_HALF = new BigDecimal("2.5");
		BigDecimal FIVE = new BigDecimal("5");
		BigDecimal TEN = new BigDecimal("10");

		String marumeLabel = null;

		if (ratioPersent < 5) {
			marumeLabel = "<5%";
		} else if (ratioPersent > 95) {
			marumeLabel = "95~100%";
		} else {
			//diffが0よりも大きい場合
			//compareTo：引数よりも大きい場合-> 1
			//           引数よりも小さい場合-> -1
			//           引数と等しい場合    -> 0
			if (diff.compareTo(ZERO) == 1) {
				if (bd.compareTo(round.add(TWO_HALF)) == -1) {
					marumeLabel = (round.subtract(FIVE)).toPlainString()
							+ "~"
							+ (round.add(FIVE)).toPlainString()
							+ "%";
				} else {
					marumeLabel = round.toPlainString()
							+ "~"
							+ (round.add(TEN)).toPlainString()
							+ "%";
				}
			} else {
				if (bd.compareTo(round.subtract(TWO_HALF)) == -1) {
					marumeLabel = (round.subtract(TEN)).toPlainString()
							+ "~"
							+ round.toPlainString()
							+ "%";
				} else {
					marumeLabel = (round.subtract(FIVE)).toPlainString()
							+ "~"
							+ (round.add(FIVE)).toPlainString()
							+ "%";
				}
			}
		}
		//toPlainString()を付ければ、E+2のような表記はなくなる
		//プレーンな表記となる。

		return marumeLabel;
	}
}
