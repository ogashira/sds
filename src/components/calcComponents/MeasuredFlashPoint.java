package components.calcComponents;

import java.util.List;

import csves.DownLoadCsv;
import csves.GetPath;
import structures.StructPhysicalData;

public class MeasuredFlashPoint {

	private List<String[]> measuredFlashPointData;
	private String sikakariHb;

	public MeasuredFlashPoint(String hinban) {

		String file;
		String encode;

		Sikakari sikakari = new Sikakari(hinban);
		sikakariHb = sikakari.getSikakariHb();

		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからflashpoint.csvのﾊﾟｽを取得する
		file = GetPath.getPath("flashPoint");
		encode = "shift-JIS";

		DownLoadCsv dlCsv = new DownLoadCsv(file, encode);
		measuredFlashPointData = dlCsv.getCsvData();
	}

	public List<StructPhysicalData> changeMeasuredFlashPoint(
			List<StructPhysicalData> listPhysicalData) {
		for (String[] line : measuredFlashPointData) {
			if (line[0].equals(sikakariHb)) {
				if (!(line[1].equals(""))) {
					float measuredFlashPoint = Float.parseFloat(line[1]);
					for (StructPhysicalData physical : listPhysicalData) {
						if (physical.item.equals("引火点")) {
							physical.value = measuredFlashPoint;
							physical.applicableSolvent.clear();
							physical.applicableSolvent.add(line[2]);
							break;
						}
					}
				}
				break;
			}
		}
		return listPhysicalData;
	}
}
