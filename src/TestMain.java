import java.util.HashMap;
import java.util.List;
import java.util.Map;

import csves.DownLoadCsv;
import csves.GetPath;

public class TestMain {

	public static void main(String[] args) {
		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからflashpoint.csvのﾊﾟｽを取得する
		String file = GetPath.getPath("flammableLiquid");
		String encode = "shift-JIS";
		DownLoadCsv dlCsv = new DownLoadCsv(file, encode);
		List<String[]> flammableLiquid = dlCsv.getCsvData();

		float flashPoint = 22;
		float boilingPoint = 35;

		int rowCnt = 1;
		Map<String, String> operators = new HashMap<>();
		Map<String, String> points = new HashMap<>();
		for (int i = 1; i < flammableLiquid.size(); i++) {
			operators.put("flashOperator", flammableLiquid.get(i)[4]);
			operators.put("boilingOperator", flammableLiquid.get(i)[6]);
			points.put("strFlash", flammableLiquid.get(i)[5]);
			points.put("strBoil", flammableLiquid.get(i)[7]);

			if (boolFlash(operators, points, flashPoint, boilingPoint)) {
				System.out.println(rowCnt);
				return;

			}
			rowCnt++;

		}
	}

	static boolean boolFlash(Map<String, String> operators,
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

	static boolean boolBoil(String operator, String strPoint, float boilingPoint) {
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
}