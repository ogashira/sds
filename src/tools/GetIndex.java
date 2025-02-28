package tools;

import java.util.List;

import structures.StructSolDb;

public class GetIndex {

	public static int getFlammableLiquidIndex(float flashPoint, float boilingPoint) {

		if (flashPoint < 23 && boilingPoint <= 35) {
			return 1;
		}
		if (flashPoint < 23 && boilingPoint > 35) {
			return 2;
		}
		if (flashPoint <= 60) {
			return 3;
		}
		if (flashPoint <= 93) {
			return 4;
		}
		return 5;
	}

	public static int getCombustibleSolidIndex(List<String[]> combustibleSolid,
			List<StructSolDb> resultDb) {

		int index = 0;
		for (String[] line : combustibleSolid) {
			for (StructSolDb result : resultDb) {
				if (line[0].equals(result.hinban))
					return index;
			}
			index++;
		}
		return 0;

	}

}
