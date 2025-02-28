package ghs.hazardToHealth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;
import structures.StructSolDb;

class GmiccsRegistered {
	/*
	 * gmiccsRegistered.csvをダウンロードしてデータをゲット。
	 * 含有する原料成分の行のみを抽出する。
	 */

	List<String[]> listGmiccsRegistered; // gmiccsRegistered.csvのデータ
	Map<String, Float> nonListGmiccs; // gmiccsRegisteredに未掲載のデータ
	float nonListRatio; // 成分が未掲載の合計値nonListGmiccsのvalueの合計
	/*
	未知の成分は、各項目(経口、経皮...)での未知の成分を求めて、それに
	nonListRatioをプラスした値となる。各項目での未知の成分は、113列(ATE)が
	空白で、区分が１～５以外であるため変換値を適合できないケースである。
	*/

	GmiccsRegistered() {
		String filePath = GetPath.getPath("gmiccsRegistered");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listGmiccsRegistered = dl.getCsvData();

	}

	List<String[]> getListGmiccsRegistered() {
		return listGmiccsRegistered;
	}

	List<String[]> getFilterdGmiccs(List<StructSolDb> listStructSol) {
		/*
		 * filterdGmiccs-> listGmiccsRegisteredから求める塗料の原料
		 * のみのデータを抽出したもの。
		 * 同時にnonListGmiccs(HashMap),nonListRatio(float)も作成する。
		 */
		List<String[]> filterdGmiccs = new ArrayList<>();
		nonListGmiccs = new HashMap<>();
		nonListRatio = 0;

		//G-ALL-TMBZ, G-SONOTAの場合はスルーする。(listStructSolのデータは
		//この２つを除かないと合計1にならないから
		for (StructSolDb line : listStructSol) {
			if (line.hinban.equals("G-ALL-TMBZ") ||
					line.hinban.equals("G-SONOTA")) {
				continue;
			}
			boolean isIn = false;
			for (String[] Gline : listGmiccsRegistered) {
				if ((line.casNo.equals(Gline[2])) && (line.ratio > 0)) {
					//gmiccsRegisteredの最終列にratioを文字列変換して挿入
					Gline[Gline.length - 1] = Float.toString(line.ratio);
					filterdGmiccs.add(Gline);
					isIn = true;
					break;
				}
			}
			if (isIn == false) {
				nonListGmiccs.put(line.casNo, line.ratio);
				nonListRatio += line.ratio;
			}
		}
		return filterdGmiccs;
	}

	Map<String, Float> getNonListGmiccs() {
		return nonListGmiccs;
	}

	float getNonListRatio() {
		return nonListRatio;
	}

}
