package components.calcComponents;

import java.util.ArrayList;
import java.util.List;

import structures.StructSolDb;

public class ResultDb {

	private float allTMBZ;
	private final String[] TMBZ = { "G-123TMBZ", "G-124TMBZ", "G-135TMBZ",
			"G-TMBZ", "G-ALL-TMBZ", "G-SONOTA" };
	private final int N_H_ZOKU_NO = 1999;

	//listStructSolから配列TMBZ(ﾄﾘﾒﾁﾙﾍﾞﾝｾﾞﾝ)の要素が存在すれば、
	//そのﾘｽﾄlistTMBZを作る
	public List<StructSolDb> createListTMBZ(List<StructSolDb> listStructSol) {

		List<StructSolDb> listTMBZ = new ArrayList<>();
		for (StructSolDb line : listStructSol) {
			/*
			if (line.hinban.equals("G-SONOTA")) {
			    listTMBZ.add(line);
			}*/
			for (int i = 0; i < TMBZ.length; i++) {
				if (TMBZ[i].equals(line.hinban)) {
					listTMBZ.add(line);
					break;
				}
			}
		}
		return listTMBZ;
	}

	public void setAllTMBZ(List<StructSolDb> listTMBZ) {
		allTMBZ = 0;
		for (StructSolDb line : listTMBZ) {
			allTMBZ += line.ratio;
		}
	}

	public float getAllTMBZ() {
		return allTMBZ;
	}

	@SuppressWarnings("unused")
	public List<StructSolDb> createResultDb(List<StructSolDb> listTMBZ) {

		//resultDbには、その他(G-SONOTA)と、TMBZの計算結果をaddする
		//TMBZで必要なものを表示する。sonotaはｾﾞﾛでも後々使うので表示する。

		List<StructSolDb> resultDb = new ArrayList<>();

		float ratio123 = 0;
		float ratio124 = 0;
		float ratio135 = 0;
		float ratioOtherTMBZ = 0;
		float ratioAllTMBZ = 0;
		float ratioSonota = 0;

		int index123 = 0;
		int index124 = 0;
		int index135 = 0;
		int indexOtherTMBZ = 0;
		int indexAllTMBZ = 0;
		int indexSonota = 0;

		//各TMBZの配合量とindexを取得する。
		for (int i = 0; i < listTMBZ.size(); i++) {
			if (listTMBZ.get(i).hinban.equals("G-123TMBZ")) {
				ratio123 = listTMBZ.get(i).ratio;
				index123 = i;
			} else if (listTMBZ.get(i).hinban.equals("G-124TMBZ")) {
				ratio124 = listTMBZ.get(i).ratio;
				index124 = i;
			} else if (listTMBZ.get(i).hinban.equals("G-135TMBZ")) {
				ratio135 = listTMBZ.get(i).ratio;
				index135 = i;
			} else if (listTMBZ.get(i).hinban.equals("G-TMBZ")) {
				ratioOtherTMBZ = listTMBZ.get(i).ratio;
				indexOtherTMBZ = i;
			} else if (listTMBZ.get(i).hinban.equals("G-ALL-TMBZ")) {
				ratioAllTMBZ = listTMBZ.get(i).ratio;
				indexAllTMBZ = i;
			} else if (listTMBZ.get(i).hinban.equals("G-SONOTA")) {
				ratioSonota = listTMBZ.get(i).ratio;
				indexSonota = i;
			}
		}
		//必要なTMBZを追加する。配合量も計算する。
		//20240327 修正
		// ratio135が0.00999999967....の場合、0.01未満と判定されてしまう
		// S9-U330-THで135-TMBZが表示されなくなってしまう。
		// ratio135 = ((double)Math.round(ratio135 * 1000)) / 1000;
		// を使って少数第４位(あえて3位ではない)を四捨五入する


		ratio123 = ((float)Math.round(ratio123 * 1000)) / 1000;
		ratio124 = ((float)Math.round(ratio124 * 1000)) / 1000;
		ratio135 = ((float)Math.round(ratio135 * 1000)) / 1000;
		ratioOtherTMBZ = ((float)Math.round(ratioOtherTMBZ * 1000)) / 1000;
		ratioAllTMBZ = ((float)Math.round(ratioAllTMBZ * 1000)) / 1000;
		ratioSonota = ((float)Math.round(ratioSonota * 1000)) / 1000;

		final float KIKAKU = 0.01f;



		if (ratio124 >= KIKAKU && ratio135 >= KIKAKU) {
			resultDb.add(listTMBZ.get(index124));
			resultDb.add(listTMBZ.get(index135));
			ratioOtherTMBZ = ratio123 + ratioOtherTMBZ;
			if (ratioOtherTMBZ >= KIKAKU) {
				listTMBZ.get(indexOtherTMBZ).ratio = ratioOtherTMBZ;
				resultDb.add(listTMBZ.get(indexOtherTMBZ));
				resultDb.add(listTMBZ.get(indexSonota));
			} else {
				listTMBZ.get(indexSonota).ratio = ratioOtherTMBZ;
				resultDb.add(listTMBZ.get(indexSonota));
			}
		} else if (ratio124 >= KIKAKU && ratio135 < KIKAKU) {
			resultDb.add(listTMBZ.get(index124));
			ratioOtherTMBZ = ratio123 + ratio135 + ratioOtherTMBZ;
			if (ratioOtherTMBZ >= KIKAKU) {
				listTMBZ.get(indexOtherTMBZ).ratio = ratioOtherTMBZ;
				resultDb.add(listTMBZ.get(indexOtherTMBZ));
				resultDb.add(listTMBZ.get(indexSonota));
			} else {
				listTMBZ.get(indexSonota).ratio = ratioOtherTMBZ;
				resultDb.add(listTMBZ.get(indexSonota));
			}
		} else if (ratio135 >= KIKAKU && ratio124 < KIKAKU) {
			resultDb.add(listTMBZ.get(index135));
			ratioOtherTMBZ = ratio123 + ratio124 + ratioOtherTMBZ;
			if (ratioOtherTMBZ >= KIKAKU) {
				listTMBZ.get(indexOtherTMBZ).ratio = ratioOtherTMBZ;
				resultDb.add(listTMBZ.get(indexOtherTMBZ));
				resultDb.add(listTMBZ.get(indexSonota));
			} else {
				listTMBZ.get(indexSonota).ratio = ratioOtherTMBZ;
				resultDb.add(listTMBZ.get(indexSonota));
			}
		} else if (ratio135 < KIKAKU && ratio124 < KIKAKU) {
			ratioOtherTMBZ = ratio123 + ratio124 + ratio135 +
					ratioOtherTMBZ;
			if (ratioOtherTMBZ >= KIKAKU) {
				listTMBZ.get(indexAllTMBZ).ratio = ratioOtherTMBZ;
				resultDb.add(listTMBZ.get(indexAllTMBZ));
				resultDb.add(listTMBZ.get(indexSonota));
			} else {
				listTMBZ.get(indexSonota).ratio = ratioOtherTMBZ;
				resultDb.add(listTMBZ.get(indexSonota));
			}
		}


		return resultDb;
	}

	//TMBZとsonota以外の残りの原料をresultDbに追加する
	public void addResultDb(List<StructSolDb> resultDb,
			List<StructSolDb> listStructSol) {

		for (StructSolDb line : listStructSol) {
			boolean isMatch = false;
			for (String tmbz : TMBZ) {
				if (line.hinban.equals(tmbz)) {
					isMatch = true;
				}
			}
			if (isMatch == false) {
				resultDb.add(line);
			}
		}
	}

	public List<StructSolDb> bundleN_H(List<StructSolDb> resultDb) {
		/*
		 * N_Hの問題解決
		*/

		//resultDbから属No1999の構造体を探す

		//StructSolDbの中の構造体は書き換えない。
		//N_H_InResultDbに入れるだけ。（参照しているから、
		//N_H_InResultDbのﾃﾞｰﾀを変えると、resultDbの中も変わってしまう。
		StructSolDb N_H_InResultDb = new StructSolDb();
		boolean isN_H = false;
		for (StructSolDb line : resultDb) {
			if (line.zokuNo == N_H_ZOKU_NO) {
				N_H_InResultDb = line;
				isN_H = true;
				break; //1999ならどれでも良い。
			}
		}
		if (!isN_H) {
			return resultDb;
		}
		//属No1999が無かったら、resultDbをそのままreturnする

		//N_Hにひとつずつ代入して、ｵﾌﾞｼﾞｪｸﾄをｺﾋﾟｰ（別のｵﾌﾞｼﾞｪｸﾄを作る）
		StructSolDb N_H = new StructSolDb();

		N_H.hinban = "G-N_H";
		N_H.ratio = 0;
		N_H.zokuNo = N_H_ZOKU_NO;
		N_H.hinmei = N_H_InResultDb.hinmei;
		N_H.sdsDisplayName = N_H_InResultDb.sdsDisplayName;
		N_H.casNo = N_H_InResultDb.casNo;
		N_H.rouan = N_H_InResultDb.rouan;
		N_H.rouanMin = N_H_InResultDb.rouanMin;
		N_H.prtr = N_H_InResultDb.prtr;
		N_H.prtrMin = N_H_InResultDb.prtrMin;
		N_H.flashPoint = N_H_InResultDb.flashPoint;
		N_H.ignitionPoint = N_H_InResultDb.ignitionPoint;
		N_H.expLowerLimit = N_H_InResultDb.expLowerLimit;
		N_H.expUpperLimit = N_H_InResultDb.expUpperLimit;
		N_H.boilPoint = N_H_InResultDb.boilPoint;
		N_H.vaporPressure = N_H_InResultDb.vaporPressure;
		N_H.steamSg = N_H_InResultDb.steamSg;
		N_H.evaporationRate = N_H_InResultDb.evaporationRate;
		N_H.oshaPel = N_H_InResultDb.oshaPel;
		N_H.acgih = N_H_InResultDb.acgih;
		N_H.niSsanEi = N_H_InResultDb.niSsanEi;
		N_H.ld50 = N_H_InResultDb.ld50;
		N_H.counter = N_H_InResultDb.counter;
		N_H.nittokouName = N_H_InResultDb.nittokouName;
		N_H.nittokouEng = N_H_InResultDb.nittokouEng;
		N_H.sdsNameEng = N_H_InResultDb.sdsNameEng;
		N_H.rouanTuuti = N_H_InResultDb.rouanTuuti;
		N_H.jap = N_H_InResultDb.jap;
		N_H.chn = N_H_InResultDb.chn;
		N_H.kasinhouNo = N_H_InResultDb.kasinhouNo;
		N_H.kasinhouType = N_H_InResultDb.kasinhouType;
		N_H.prtrNo5 = N_H_InResultDb.prtrNo5;
		N_H.prtrBunrui4 = N_H_InResultDb.prtrBunrui4;
		N_H.prtrBunrui5 = N_H_InResultDb.prtrBunrui5;
		N_H.tokkasokuKubun = N_H_InResultDb.tokkasokuKubun;
		N_H.tokkasokuNo = N_H_InResultDb.tokkasokuNo;
		N_H.tokkasokuTargetRange = N_H_InResultDb.tokkasokuTargetRange;
		N_H.kanriNoudo = N_H_InResultDb.kanriNoudo;
		N_H.displayMin = N_H_InResultDb.displayMin;
		N_H.yuukisokuKubun = N_H_InResultDb.yuukisokuKubun;
		N_H.yuukisokuNo = N_H_InResultDb.yuukisokuNo;
		N_H.dokugekiBunrui = N_H_InResultDb.dokugekiBunrui;
		N_H.dokugekiNo = N_H_InResultDb.dokugekiNo;
		N_H.unNo = N_H_InResultDb.unNo;
		N_H.unClass = N_H_InResultDb.unClass;
		N_H.unName = N_H_InResultDb.unName;
		N_H.hsCode = N_H_InResultDb.hsCode;
		N_H.update = N_H_InResultDb.update;

		//resultDbを回して、1999があったら、N_Hのratioにﾌﾟﾗｽする。
		//1999でなければ、resultDbN_Hに構造体を追加する。
		List<StructSolDb> resultDbN_H = new ArrayList<>();
		for (StructSolDb line : resultDb) {
			if (line.zokuNo == N_H_ZOKU_NO) {
				N_H.ratio += line.ratio;
			} else {
				resultDbN_H.add(line);
			}
		}
		resultDbN_H.add(N_H);

		return resultDbN_H;

	}

}
