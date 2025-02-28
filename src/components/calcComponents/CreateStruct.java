package components.calcComponents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import structures.StructSolDb;

public class CreateStruct {

	public List<StructSolDb> getListStructSol(List<String[]> solventDbData,
			Map<String, Float> dupliBunkai) {
		List<StructSolDb> listStructSol = new ArrayList<>();

		for (String[] line : solventDbData) {
			StructSolDb structSol = new StructSolDb();
			structSol.hinban = line[0];
			structSol.ratio = dupliBunkai.get(line[0]);
			structSol.zokuNo = (line[1].equals("")) ? 0
					: Integer.parseInt(
							line[1]);
			//structSol.zokuNo = Integer.parseInt(line[1]);
			structSol.hinmei = (line[2].equals("")) ? "null" : line[2];
			structSol.sdsDisplayName = (line[3].equals("")) ? "null" : line[3];
			structSol.casNo = (line[4].equals("")) ? "null" : line[4];
			structSol.rouan = (line[5].equals("")) ? "null" : line[5];
			structSol.rouanMin = (line[6].equals("")) ? 9999
					: Float.parseFloat(
							line[6]) * 100;
			//                                                    100倍して%に
			structSol.prtr = (line[7].equals("")) ? "null" : line[7];
			structSol.prtrMin = (line[8].equals("")) ? 9999
					: Float.parseFloat(
							line[8]) * 100;
			//                                                    100倍して%に
			structSol.flashPoint = (line[9].equals("")) ? 9999 : Float.parseFloat(line[9]);
			structSol.ignitionPoint = (line[10].equals("")) ? 9999 : Float.parseFloat(line[10]);
			structSol.expLowerLimit = (line[11].equals("")) ? 9999 : Float.parseFloat(line[11]);
			structSol.expUpperLimit = (line[12].equals("")) ? -9999 : Float.parseFloat(line[12]);
			structSol.boilPoint = (line[13].equals("")) ? 9999
					: Float.parseFloat(
							line[13]);
			structSol.vaporPressure = (line[14].equals("")) ? -9999 : Float.parseFloat(line[14]);
			structSol.steamSg = (line[15].equals("")) ? -9999
					: Float.parseFloat(
							line[15]);
			structSol.evaporationRate = (line[16].equals("")) ? -9999 : Float.parseFloat(line[16]);
			structSol.oshaPel = (line[17].equals("")) ? 9999
					: Float.parseFloat(
							line[17]);
			structSol.acgih = (line[18].equals("")) ? 9999
					: Float.parseFloat(
							line[18]);
			structSol.niSsanEi = (line[19].equals("")) ? 9999
					: Float.parseFloat(
							line[19]);
			structSol.ld50 = (line[20].equals("")) ? 9999
					: Float.parseFloat(
							line[20]);
			structSol.counter = (line[21].equals("")) ? -1
					: Integer.parseInt(
							line[21]);
			structSol.nittokouName = (line[22].equals("")) ? "null" : line[22];
			structSol.nittokouEng = (line[23].equals("")) ? "null" : line[23];
			structSol.sdsNameEng = (line[24].equals("")) ? "null" : line[24];
			structSol.rouanTuuti = (line[25].equals("")) ? "null" : line[25];
			structSol.jap = (line[26].equals("")) ? "null" : line[26];
			structSol.chn = (line[27].equals("")) ? "null" : line[27];
			structSol.kasinhouNo = (line[28].equals("")) ? "null" : line[28];
			structSol.kasinhouType = (line[29].equals("")) ? "null" : line[29];
			structSol.prtrNo5 = (line[30].equals("")) ? "null" : line[30];
			structSol.prtrBunrui4 = (line[31].equals("")) ? "null" : line[31];
			structSol.prtrBunrui5 = (line[32].equals("")) ? "null" : line[32];
			structSol.tokkasokuKubun = (line[33].equals("")) ? "null" : line[33];
			structSol.tokkasokuNo = (line[34].equals("")) ? "null" : line[34];
			structSol.tokkasokuTargetRange = (line[35].equals("")) ? 9999
					: Float.parseFloat(line[35]) * 100;
			structSol.kanriNoudo = (line[36].equals("")) ? "null" : line[36];
			structSol.displayMin = (line[37].equals("")) ? 9999 : Float.parseFloat(line[37]) * 100;
			//                                                    100倍して%に
			structSol.yuukisokuKubun = (line[38].equals("")) ? "null" : line[38];
			structSol.yuukisokuNo = (line[39].equals("")) ? "null" : line[39];
			structSol.dokugekiBunrui = (line[40].equals("")) ? "null" : line[40];
			structSol.dokugekiNo = (line[41].equals("")) ? "null" : line[41];
			structSol.unNo = (line[42].equals("")) ? "null" : line[42];
			structSol.unClass = (line[43].equals("")) ? "null" : line[43];
			structSol.unName = (line[44].equals("")) ? "null" : line[44];
			structSol.hsCode = (line[45].equals("")) ? "null" : line[45];
			structSol.update = (line[46].equals("")) ? "1111" : line[46];

			listStructSol.add(structSol);

		}
		return listStructSol;
	}

}
