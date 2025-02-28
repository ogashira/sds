package ghs.hazardToHealth.exposure;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CalcExposure {

	private List<String[]> listFilterdGmiccs;
	private List<String[]> listExposure;
	private Map<String,List<String[]>> mapKubunOrganCas;
	private List<String> listOrgan1;       //区分1の臓器リスト
	private List<String> listOrgan2;       //区分2の臓器リスト
	private List<String> listOrganSingle3; //区分3の臓器リスト
	private List<String> listCas1;         //区分1のcasリスト
	private List<String> listCas2;         //区分2のcasリスト
	private List<String> listCasSingle3;   //区分3のcasリスト
	/*
	/*
	 * mapKubunOrganCas = {区分1:[[呼吸器,7429-90-5]], 区分2:[[中枢神経系,1330-20-7],
	 * [呼吸器,1330-20-7],[肝臓,1330-20-7],[腎臓,1330-20-7]], 区分3:[[麻酔作用,141-78-6],
	 * [気道刺激性,141-78-6]], 区分に該当しない:[[気道刺激性,526-73-8],[麻酔作用,526-73-8],
	 * [気道刺激性,95-63-6],[麻酔作用,95-63-6],[気道刺激性,108-67-8],.....]}
	 *
	 */

	CalcExposure(List<String[]> listFilterdGmiccs, List<String[]> listExposure){

		this.listFilterdGmiccs = listFilterdGmiccs;
		this.listExposure  = listExposure;
		mapKubunOrganCas = this.getMapKubunOrganCas();
		listOrgan1 = this.getListOrgan("区分1");
		listOrgan2 = this.getListOrgan("区分2");
		//区分2が区分1に重複しないようにする 2023/5/23 仕様変更
		this.duplicates();
		listOrganSingle3 = this.getListOrganSingle3();
		listCas1 = this.getListCas("区分1");
		listCas2 = this.getListCas("区分2");
		listCasSingle3 = this.getListCasSingle3();
	}

	Map<String,List<String[]>> returnMapKubunOrganCas(){
		return mapKubunOrganCas;
	}
	List<String> returnListOrgan1(){
		return listOrgan1;
	}
	List<String> returnListOrgan2(){
		return listOrgan2;
	}
	List<String> returnListOrganSingle3(){
		return listOrganSingle3;
	}
	List<String> returnListCas1(){
		return listCas1;
	}
	List<String> returnListCas2(){
		return listCas2;
	}
	List<String> returnListCasSingle3(){
		return listCasSingle3;
	}



	private void duplicates() {
		for(String organ1 : listOrgan1) {
			if(listOrgan2.contains(organ1)) {
				listOrgan2.remove(listOrgan2.indexOf(organ1));
			}
		}
	}


	Map<String,String> getMapExposure(String kubun, Float unknownRatio){
		Map<String,String> mapExposure = new HashMap<>();
		//- kubun: 区分1 or 区分2.  - 区分3はSingleで作る
		//mapKubunOrganCasに区分1が存在しない場合は
		//{"区分1":"-"}を返す。
		//未知の成分合計濃度が考慮濃度の最小値以上含有している場合は
		//分類できないとする
		if(mapKubunOrganCas.get(kubun) == null) {
			if(unknownRatio >= 0.01) {
				mapExposure.put("kubun", "分類できない");
			}else {
				mapExposure.put("kubun", "区分に該当しない");
			}
			mapExposure.put("pictogram", "-");
			mapExposure.put("signalWord", "-");
			mapExposure.put("hazardInfo", "-");
			return mapExposure;
		}

		if(kubun.equals("区分3")) {
			this.getKubun3(mapExposure);
			return mapExposure;
		}

		//kubunと表の一列目が一致した行を代入する
		for(String[] line : listExposure) {
			if(line[0].equals(kubun)) {
				mapExposure.put("kubun", line[0]);
				mapExposure.put("pictogram", line[1]);
				mapExposure.put("signalWord", line[2]);
				mapExposure.put("hazardInfo", line[3]);
			}
		}
		return mapExposure;
	}

	private void getKubun3(Map<String, String> mapExposure){
		//表の１列目から区分3の行を探す
		int row3 ;
		for(row3 = 1; row3 < listExposure.size()-1; row3++ ) {
			if(listExposure.get(row3)[0].equals("区分3")) {
				break;
			}
		}
		//row3が区分3(気道刺激性),row3 + 1 が区分3(麻酔作用)
		if(listOrganSingle3.contains("気道刺激性")) {
			mapExposure.put("kubun", listExposure.get(row3)[0]);
			mapExposure.put("pictogram",listExposure.get(row3)[1] );
			mapExposure.put("signalWord",listExposure.get(row3)[2] );
			String tempHazard = listExposure.get(row3)[3] + "、"
					                 + listExposure.get(row3 + 1)[3];
			mapExposure.put("hazardInfo", tempHazard);
			return;
		}

		mapExposure.put("kubun", listExposure.get(row3+1)[0]);
		mapExposure.put("pictogram",listExposure.get(row3+1)[1] );
		mapExposure.put("signalWord",listExposure.get(row3+1)[2] );
		mapExposure.put("hazardInfo",listExposure.get(row3+1)[3] );
	}

	private List<String> getListOrgan(String kubun){
		//区分1、区分2はここで作る。
		//区分3は、Single.javaで作る
		List<String> listOrgan = new ArrayList<>();
		//nullの場合は即リターン
		if(mapKubunOrganCas.get(kubun) == null) {
			listOrgan.add("-");
			return listOrgan;
		}
		//重複させないようにlistOrganにaddする
		List<String[]> listOrganCas = mapKubunOrganCas.get(kubun);
		for(String[] line : listOrganCas) {
			if(!listOrgan.contains(line[0])) {
				listOrgan.add(line[0]);
			}
		}

		return listOrgan;
	}

	private List<String> getListOrganSingle3(){
		/*
		 * 区分3の場合だけこの関数を使う
		 *区分1, 区分2で呼吸器が該当する場合は、気道刺激性は表示しない
		 */
		List<String> listOrganSingle3 = new ArrayList<>();
		//nullなら即リターン
		if(mapKubunOrganCas.get("区分3") == null) {
			listOrganSingle3.add("-");
			return listOrganSingle3;
		}

		List<String[]> listOrganCas = mapKubunOrganCas.get("区分3");
		//区分1, 区分2で呼吸器が該当するか？
		boolean isRes = this.isRespiration();
		if(isRes) {
			for(String[] arrOrganCas : listOrganCas) {
				//臓器が気道刺激性ではなく、リストに無ければ追加する
				//重複を防ぐため
				if(!(arrOrganCas[0].equals("気道刺激性")) &&
						         !(listOrganSingle3.contains(arrOrganCas[0]))) {
					listOrganSingle3.add(arrOrganCas[0]);
				}
			}
			return listOrganSingle3;
		}
		for(String[] arrOrganCas : listOrganCas) {
			//リストに無ければ追加する
			if(!listOrganSingle3.contains(arrOrganCas[0])) {
				listOrganSingle3.add(arrOrganCas[0]);
			}
		}
		return listOrganSingle3;
	}



	private List<String> getListCas(String kubun){
		//区分1、区分2はここで作る。
		//区分3は、Single.javaで作る
		List<String> listCas = new ArrayList<>();
		//nullの場合は即リターン
		if(mapKubunOrganCas.get(kubun) == null) {
			listCas.add("-");
			return listCas;
		}
		//重複させないようにlistOrganにaddする
		List<String[]> listOrganCas = mapKubunOrganCas.get(kubun);
		for(String[] line : listOrganCas) {
			if(!listCas.contains(line[1])) {
				listCas.add(line[1]);
			}
		}
		return listCas;
	}


	private List<String> getListCasSingle3(){
		/*
		 * 区分3の場合だけこの関数を使う
		 *区分1, 区分2で呼吸器が該当する場合は、気道刺激性は表示しない
		 */
		List<String> listCasSingle3 = new ArrayList<>();
		//nullなら即リターン
		if(mapKubunOrganCas.get("区分3") == null) {
			listCasSingle3.add("-");
			return listCasSingle3;
		}

		List<String[]> listOrganCas = mapKubunOrganCas.get("区分3");
		//区分1, 区分2で呼吸器が該当するか？
		boolean isRes = this.isRespiration();
		if(isRes) {
			for(String[] arrOrganCas : listOrganCas) {
				//臓器が気道刺激性ではなく、リストに無ければ追加する
				//重複を防ぐため
				if(!(arrOrganCas[0].equals("気道刺激性")) &&
						         !(listCasSingle3.contains(arrOrganCas[1]))) {
					listCasSingle3.add(arrOrganCas[1]);
				}
			}
			return listCasSingle3;
		}
		for(String[] arrOrganCas : listOrganCas) {
			//リストに無ければ追加する
			if(!listCasSingle3.contains(arrOrganCas[1])) {
				listCasSingle3.add(arrOrganCas[1]);
			}
		}
		return listCasSingle3;
	}

	private boolean isRespiration() {
		/*
		 * listOrganSingle1,listOrganSingle2に「呼吸器」が
		 * 入っているかを判定する。
		 */
		boolean isRes = false;
		if(listOrgan1.contains("呼吸器")) {
			isRes = true;
		}
		if(listOrgan2.contains("呼吸器")) {
			isRes = true;
		}

		return isRes;
	}

	private Map<String,List<String[]>> getMapKubunOrganCas(){
		Map<String,List<String[]>> mapKubunOrganCas = new HashMap<>();


		//スタートとエンドの列番(表のlast2とlastに記載) stt:47, end:77
		int sttRetuban = Integer.parseInt(
				            listExposure.get(1)[listExposure.get(0).length -2]);
		int endRetuban = Integer.parseInt(
				            listExposure.get(1)[listExposure.get(0).length -1]);

		//gmiccsの列indexを求める
		//int dataInfo = sttRetuban -1; //区分あり、分類できない などの情報 列:47=index:46
		int sttData = sttRetuban;     //ここから３つ置きにデータが始まる index:47
		int endData = endRetuban -1;  //ここで終わり 列:77=index:76

		for(String[] line : listFilterdGmiccs) {
			for(int i = sttData; i<endData; i+=3) {
				if(line[i].equals("") || line[i] == null) {
					break; //空白なら次のiからはずっと空白
				}

				//区分１の数値を小文字にしておく
				String kubun;
				String tempKubun = Normalizer.normalize(line[i], Normalizer.Form.NFKC);
				String organ = line[i+1];
				String cas = line[2];
				float ratio = Float.parseFloat(line[line.length - 1]);
				//区分3の時だけ、区分3気道刺激性,区分3麻酔作用 に変える。
				if(tempKubun.equals("区分3")) {
					kubun = tempKubun + organ;
				}else {
					kubun = tempKubun;
				}

				//表の列のindex番号を求める 検索できない場合は0が返ってくる
				int colIndex = this.getColIndex(kubun);
				//表の行のindex番号を求める
				int rowIndex = this.getRowIndex(colIndex, ratio);
				//表から区分を求める
				kubun = listExposure.get(rowIndex)[0];


				if(mapKubunOrganCas.get(kubun)==null) {
					/*
					 * mapのキー(区分1~3)がnullだったら、新たに[臓器, casNo]の配列作って、
					 * それをListにaddして、map(キー=kubun)にputする。
					 * nullでなかったら、新たに、[臓器,casNo]の配列を作って、
					 * MapでgetしたListにaddする。
					 */
					//新たにlistOrganCasを作る
					List<String[]> listOrganCas = new ArrayList<>();
					String organCas[] = new String[2];
					organCas[0] = organ;
					organCas[1] = cas;
					listOrganCas.add(organCas);
					mapKubunOrganCas.put(kubun, listOrganCas);
				}else {
					String organCas[] = new String[2];
					organCas[0] = organ;
					organCas[1] = cas;
					List<String[]> tempList = mapKubunOrganCas.get(kubun);
					tempList.add(organCas);
					mapKubunOrganCas.put(kubun, tempList);
				}

			}

		}

		return mapKubunOrganCas;
	}

	private int getRowIndex(int colIndex, float ratio) {
		/*
		 * rowIndexを最大にセットしておいて、1からforで回す。
		 * boolJudgeでtrueが返ってきたら、rowIndexをリターン
		 * forで引っかからなかったら、最大値をreturnする。
		 */
		int rowIndex;
		for(rowIndex=1; rowIndex<listExposure.size() -1; rowIndex++ ) {
			//不等号はcolIndexの一つ左にあるから-1。
			String operator = listExposure.get(rowIndex)[colIndex - 1];
			float limitRatio = Float.parseFloat(
					                  listExposure.get(rowIndex)[colIndex])/100;
			if(boolJudge(operator,limitRatio,ratio)) {
				return rowIndex;
			}
		}
		rowIndex = listExposure.size() -1 ; //最大行数

		return rowIndex;
	}


	private boolean boolJudge(String comparisonOperator, float limitRatio,
			float caseRatio) {
		/*
		 * 表の比較演算子、表のATE（文字列）、ATEMixを受け取って、
		 * 判定する。比較演算子が"-"の時はtrue
		 */
		if (comparisonOperator.equals("<=")) {
			if (caseRatio <= limitRatio) {
				return true;
			}
			return false;
		}
		if (comparisonOperator.equals("<")) {
			if (caseRatio < limitRatio) {
				return true;
			}
			return false;
		}
		if (comparisonOperator.equals(">=")) {
			if (caseRatio >= limitRatio) {
				return true;
			}
			return false;
		}
		if (comparisonOperator.equals(">")) {
			if (caseRatio > limitRatio) {
				return true;
			}
			return false;
		}

		return false;

	}
	private int getColIndex(String kubun) {
		int colIndex = 0;
		for(String strColIndex : listExposure.get(0)) {
			if(strColIndex.equals(kubun)) {
				break;
			}
			colIndex++;
		}

		//もし、colIndexが３以下または列数以上だったら何らかのエラーが考えられる
		//これは、区分に該当しないにさせる
		if (colIndex <= 3 || colIndex >= listExposure.get(0).length -1) {
			return 0; //表で列を検索できない場合は0を返す
		}

		return colIndex;
	}

}
