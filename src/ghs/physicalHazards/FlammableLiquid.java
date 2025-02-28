package ghs.physicalHazards;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

class FlammableLiquid extends ABSPhysicalHazards {

	private List<String[]> flammableLiquid;
	private String file;
	private String encode;
	private int index; //表の何行目かのindex
	private float flashPoint;
	private float boilingPoint;
	private float ignitionPoint;

	//private List<String[]> physiccalHazards;

	FlammableLiquid(float flashPoint, float boilingPoint,
			float ignitionPoint) {
		//スーパークラスでphysiccalHazardsが作られる。
		super();
		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからflammableLiquid.csvのﾊﾟｽを取得する
		file = GetPath.getPath("flammableLiquid");
		encode = "shift-JIS";
		DownLoadCsv dlCsv = new DownLoadCsv(file, encode);
		flammableLiquid = dlCsv.getCsvData();

		this.flashPoint = flashPoint;
		this.boilingPoint = boilingPoint;
		this.ignitionPoint = ignitionPoint;
		index = this.getIndex();
	}

	private int getIndex() {
		/*
		 * 		|------------------|----------|--------------------|------------|
		 * 		|flashPointOperator|flashPoint|boilingPointOperator|boilingPoint|
		 * 		|------------------|----------|--------------------|------------|
		 * 		|     <            |   23     |        <=          |    35      |
		 * 		|------------------|----------|--------------------|------------|
		 * 		|     <            |   23     |        >           |    35      |
		 * 		|------------------|----------|--------------------|------------|
		 * 		|     <=           |   60     |        -           |    -       |
		 * 		|------------------|----------|--------------------|------------|
		 * 		|     <=           |   93     |        -           |    -       |
		 * 		|------------------|----------|--------------------|------------|
		 * 		|     -            |   -      |        -           |    -       |
		 * 		|------------------|----------|--------------------|------------|
		 * 	このような表に対応するため、getIndexからboolFlashを呼び出して、true
		 * になったらboolBoilを呼び出して、判定する。getIndexにtrueが返ってきた
		 * 時のrowCntの値がインデックスの行となる。
		 */
		int rowCnt = 1;
		Map<String, String> operators = new HashMap<>();
		Map<String, String> points = new HashMap<>();
		for (int i = 1; i < flammableLiquid.size(); i++) {
			operators.put("flashOperator", flammableLiquid.get(i)[4]);
			operators.put("boilingOperator", flammableLiquid.get(i)[6]);
			points.put("strFlash", flammableLiquid.get(i)[5]);
			points.put("strBoil", flammableLiquid.get(i)[7]);

			if (boolFlash(operators, points, flashPoint, boilingPoint)) {
				return rowCnt;

			}
			rowCnt++;
		}
		//ここに来ることはない。がreturnを書かないとビルドが通らない。
		return 0;
	}

	private boolean boolFlash(Map<String, String> operators,
			Map<String, String> points, float flashPoint, float boilingPoint) {
		boolean result = false;
		if (operators.get("flashOperator").equals("-")) {
			result = boolBoil(operators.get("boilingOperator"), points.get("strBoil"),
					boilingPoint);
			return result;
		}
		if (operators.get("flashOperator").equals("<")) {
			if (flashPoint < Float.parseFloat(points.get("strFlash"))) {
				result = boolBoil(operators.get("boilingOperator"), points.get("strBoil"),
						boilingPoint);
				return result;
			}
		}
		if (operators.get("flashOperator").equals("<=")) {
			if (flashPoint <= Float.parseFloat(points.get("strFlash"))) {
				result = boolBoil(operators.get("boilingOperator"), points.get("strBoil"),
						boilingPoint);
				return result;
			}
		}
		if (operators.get("flashOperator").equals(">")) {
			if (flashPoint > Float.parseFloat(points.get("strFlash"))) {
				result = boolBoil(operators.get("boilingOperator"), points.get("strBoil"),
						boilingPoint);
				return result;
			}
		}
		if (operators.get("flashOperator").equals(">=")) {
			if (flashPoint < Float.parseFloat(points.get("strFlash"))) {
				result = boolBoil(operators.get("boilingOperator"), points.get("strBoil"),
						boilingPoint);
				return result;
			}
		}
		return result;

	}

	private boolean boolBoil(String operator, String strPoint, float boilingPoint) {
		if (operator.equals("-")) {
			return true;
		}
		if (operator.equals("<")) {
			if (boilingPoint < Float.parseFloat(strPoint)) {
				return true;
			}
		}
		if (operator.equals("<=")) {
			if (boilingPoint <= Float.parseFloat(strPoint)) {
				return true;
			}
		}
		if (operator.equals(">")) {
			if (boilingPoint > Float.parseFloat(strPoint)) {
				return true;
			}
		}
		if (operator.equals(">=")) {
			if (boilingPoint < Float.parseFloat(strPoint)) {
				return true;
			}
		}
		return false;

	}

	@Override
	public Map<String, String> getMapPhysical() {
		Map<String, String> mapPhysical = new HashMap<>();

		mapPhysical.put("ateMix", "0");
		mapPhysical.put("kubun", flammableLiquid.get(index)[0]);
		mapPhysical.put("pictogram", flammableLiquid.get(index)[1]);
		mapPhysical.put("signalWord", flammableLiquid.get(index)[2]);
		mapPhysical.put("hazardInfo", flammableLiquid.get(index)[3]);

		return mapPhysical;

	}

	@Override
	public String getSyoubouhou() {

		if (flashPoint <= -20 && boilingPoint <= 40) {
			return "危険物第４類  特殊引火物  危険等級Ⅰ";
		}
		if (flashPoint < 100 && ignitionPoint <= 100) {
			return "危険物第４類  特殊引火物  危険等級Ⅰ";
		}
		if (flashPoint < 21) {
			return "危険物第４類  第１石油類  危険等級Ⅱ";
		}
		if (flashPoint < 70) {
			return "危険物第４類  第２石油類  危険等級Ⅲ";
		}
		if (flashPoint < 200) {
			return "危険物第４類  第３石油類  危険等級Ⅲ";
		}
		if (flashPoint < 250) {
			return "危険物第４類  第４石油類  危険等級Ⅲ";
		}

		return "危険物第４類  動植物油類  危険等級Ⅲ";
	}

	@Override
	public Map<String, String> getPhysicalHazardsMap() {
		//物理化学的危険性の表physicalHazardsから、液体
		//(２列目)のデータのHashMapを作る
		Map<String, String> physicalHazardsMap = new HashMap<>();
		for (int i = 1; i < physicalHazards.size(); i++) {
			physicalHazardsMap.put(physicalHazards.get(i)[0],
					physicalHazards.get(i)[1]);
			//line[0]は項目、line[1]は２列目だから、液体のデータ
			//１行目の項目は抜くのでi=1から

		}
		String physicalKubun = flammableLiquid.get(index)[0];
		physicalHazardsMap.put("引火性液体", physicalKubun);

		return physicalHazardsMap;
	}

	/*
	private int getIndex() {
	
		if(flashPoint < 23 && boilingPoint <= 35 ) {
			return 1;
		}
		if(flashPoint < 23 && boilingPoint > 35 ) {
			return 2;
		}
		if(flashPoint <= 60) {
			return 3;
		}
		if(flashPoint <= 93) {
			return 4;
		}
		return 5;
	}
	*/
}
