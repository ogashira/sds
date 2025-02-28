package ghs.physicalHazards;

import java.util.List;

import structures.StructSolDb;

class Factory {

	//オーバーロード flashPoint, boilingPointが渡ってきたら引火性液体をインスタンス化する
	static IFPhysicalHazards create(float flashPoint, float boilingPoint,
			float ignitionPoint) {

		return new FlammableLiquid(flashPoint, boilingPoint, ignitionPoint);
	}

	//オーバーロード resultDbがわたってきたら可燃性固体をインスタンス化する
	static IFPhysicalHazards create(List<StructSolDb> resultDb) {

		return new CombustibleSolid(resultDb);
	}

}
