package tools;

import java.util.ArrayList;
import java.util.List;

import structures.StructSolDb;

public class CopyStruct {
	/**
	 * 構造体のリストは参照型なので、新たにリストをnewして、
	 * 新リストに元のﾘｽﾄの構造体をaddすると、addした構造体は元のﾘｽﾄの構造体を
	 * 参照してしまう。そして、新リストの構造体を変更すると、元リストの構造体
	 * も変更されてしまう。
	 * 構造体をコピーするクラスを作った。
	 * */

	public static List<StructSolDb> copyListStructSolDb(
			List<StructSolDb> structList) {
		/*
		 * 構造体リストの要素が参照型なので、コピーする
		 */
		List<StructSolDb> copiedStructList = new ArrayList<>();
		for (StructSolDb line : structList) {
			StructSolDb lineCopied = new StructSolDb();

			lineCopied.hinban = line.hinban;
			lineCopied.ratio = line.ratio;
			lineCopied.zokuNo = line.zokuNo;
			lineCopied.hinmei = line.hinmei;
			lineCopied.sdsDisplayName = line.sdsDisplayName;
			lineCopied.casNo = line.casNo;
			lineCopied.rouan = line.rouan;
			lineCopied.rouanMin = line.rouanMin;
			lineCopied.prtr = line.prtr;
			lineCopied.prtrMin = line.prtrMin;
			lineCopied.flashPoint = line.flashPoint;
			lineCopied.ignitionPoint = line.ignitionPoint;
			lineCopied.expLowerLimit = line.expLowerLimit;
			lineCopied.expUpperLimit = line.expUpperLimit;
			lineCopied.boilPoint = line.boilPoint;
			lineCopied.vaporPressure = line.vaporPressure;
			lineCopied.steamSg = line.steamSg;
			lineCopied.evaporationRate = line.evaporationRate;
			lineCopied.oshaPel = line.oshaPel;
			lineCopied.acgih = line.acgih;
			lineCopied.niSsanEi = line.niSsanEi;
			lineCopied.ld50 = line.ld50;
			lineCopied.counter = line.counter;
			lineCopied.nittokouName = line.nittokouName;
			lineCopied.nittokouEng = line.nittokouEng;
			lineCopied.sdsNameEng = line.sdsNameEng;
			lineCopied.rouanTuuti = line.rouanTuuti;
			lineCopied.jap = line.jap;
			lineCopied.chn = line.chn;
			lineCopied.kasinhouNo = line.kasinhouNo;
			lineCopied.kasinhouType = line.kasinhouType;
			lineCopied.prtrNo5 = line.prtrNo5;
			lineCopied.prtrBunrui4 = line.prtrBunrui4;
			lineCopied.prtrBunrui5 = line.prtrBunrui5;
			lineCopied.tokkasokuKubun = line.tokkasokuKubun;
			lineCopied.tokkasokuNo = line.tokkasokuNo;
			lineCopied.tokkasokuTargetRange = line.tokkasokuTargetRange;
			lineCopied.kanriNoudo = line.kanriNoudo;
			lineCopied.displayMin = line.displayMin;
			lineCopied.yuukisokuKubun = line.yuukisokuKubun;
			lineCopied.yuukisokuNo = line.yuukisokuNo;
			lineCopied.dokugekiBunrui = line.dokugekiBunrui;
			lineCopied.dokugekiNo = line.dokugekiNo;
			lineCopied.unNo = line.unNo;
			lineCopied.unClass = line.unClass;
			lineCopied.unName = line.unName;
			lineCopied.hsCode = line.hsCode;
			lineCopied.update = line.update;

			copiedStructList.add(lineCopied);
		}

		return copiedStructList;
	}

	public static StructSolDb copyStructSolDb(StructSolDb struct) {
		//構造体１個をｺﾋﾟｰする

		StructSolDb copiedStruct = new StructSolDb();
		copiedStruct.hinban = struct.hinban;
		copiedStruct.ratio = struct.ratio;
		copiedStruct.zokuNo = struct.zokuNo;
		copiedStruct.hinmei = struct.hinmei;
		copiedStruct.sdsDisplayName = struct.sdsDisplayName;
		copiedStruct.casNo = struct.casNo;
		copiedStruct.rouan = struct.rouan;
		copiedStruct.rouanMin = struct.rouanMin;
		copiedStruct.prtr = struct.prtr;
		copiedStruct.prtrMin = struct.prtrMin;
		copiedStruct.flashPoint = struct.flashPoint;
		copiedStruct.ignitionPoint = struct.ignitionPoint;
		copiedStruct.expLowerLimit = struct.expLowerLimit;
		copiedStruct.expUpperLimit = struct.expUpperLimit;
		copiedStruct.boilPoint = struct.boilPoint;
		copiedStruct.vaporPressure = struct.vaporPressure;
		copiedStruct.steamSg = struct.steamSg;
		copiedStruct.evaporationRate = struct.evaporationRate;
		copiedStruct.oshaPel = struct.oshaPel;
		copiedStruct.acgih = struct.acgih;
		copiedStruct.niSsanEi = struct.niSsanEi;
		copiedStruct.ld50 = struct.ld50;
		copiedStruct.counter = struct.counter;
		copiedStruct.nittokouName = struct.nittokouName;
		copiedStruct.nittokouEng = struct.nittokouEng;
		copiedStruct.sdsNameEng = struct.sdsNameEng;
		copiedStruct.rouanTuuti = struct.rouanTuuti;
		copiedStruct.jap = struct.jap;
		copiedStruct.chn = struct.chn;
		copiedStruct.kasinhouNo = struct.kasinhouNo;
		copiedStruct.kasinhouType = struct.kasinhouType;
		copiedStruct.prtrNo5 = struct.prtrNo5;
		copiedStruct.prtrBunrui4 = struct.prtrBunrui4;
		copiedStruct.prtrBunrui5 = struct.prtrBunrui5;
		copiedStruct.tokkasokuKubun = struct.tokkasokuKubun;
		copiedStruct.tokkasokuNo = struct.tokkasokuNo;
		copiedStruct.tokkasokuTargetRange = struct.tokkasokuTargetRange;
		copiedStruct.kanriNoudo = struct.kanriNoudo;
		copiedStruct.displayMin = struct.displayMin;
		copiedStruct.yuukisokuKubun = struct.yuukisokuKubun;
		copiedStruct.yuukisokuNo = struct.yuukisokuNo;
		copiedStruct.dokugekiBunrui = struct.dokugekiBunrui;
		copiedStruct.dokugekiNo = struct.dokugekiNo;
		copiedStruct.unNo = struct.unNo;
		copiedStruct.unClass = struct.unClass;
		copiedStruct.unName = struct.unName;
		copiedStruct.hsCode = struct.hsCode;
		copiedStruct.update = struct.update;

		return copiedStruct;

	}
}
