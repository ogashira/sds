package ghs.hazardToEnv;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

public class Ozone {

	private List<String[]> listOzone;
	private float unknownRatio;
	private float kubun1;
	private float[] calcLimits;
	private CalcEnv calcEnv;
	private int[] rowCol;
	private List<String> listOzoneCas;



	Ozone(List<String[]> listFilterdGmiccs, float nonListRatio){

		String filePath = GetPath.getPath("ozone");
		DownLoadCsv dl = new DownLoadCsv(filePath, "shift-jis");
		listOzone = dl.getCsvData();

		//unknownRatioを求めておく

		//区分の列番(表のlast2に記載)区分列番:112
		int retuban = Integer.parseInt(
				            listOzone.get(1)[listOzone.get(0).length -1]);
		//gmiccsの列indexを求める
		int dataInfo = retuban -1; //区分あり、分類できない などの情報 列:112=index:111
		//unknownRatioとlistOzoneCasとkubun1の濃度を同時に作る
		float nonRatio = 0f;
		kubun1 = 0f;
		listOzoneCas = new ArrayList<>();
		listOzoneCas = null;
		for(String[] line : listFilterdGmiccs) {
			String tempKubun = Normalizer.normalize(line[dataInfo], Normalizer.Form.NFKC);
			if(tempKubun.equals("分類できない")
					    || line[dataInfo].equals("")
					    || line[dataInfo] == null) {
				nonRatio += Float.parseFloat(line[line.length -1 ]);
			}else if(tempKubun.equals("区分1")) {
				kubun1 += Float.parseFloat(line[line.length -1 ]);
				listOzoneCas.add(line[2]);
			}
		}
		unknownRatio = nonListRatio + nonRatio;


		//配列を作っておく ozoneは要素が１つしかないけど
		calcLimits = new float[1];
		calcLimits[0] = kubun1;

		calcEnv = new CalcEnv(listOzone);

		rowCol = this.getRowCol();
	}




	private int[] getRowCol() {
		int[] rowCol = calcEnv.getRowCol(calcLimits);
		return rowCol;
	}

	Map<String,String> getMapOzone(){
		Map<String, String> mapOzone = calcEnv.getMapOzone(rowCol, unknownRatio);
		return mapOzone;
	}

	List<String> returnListOzoneCas(){
		return listOzoneCas;
	}




}
