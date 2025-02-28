package components.calcComponents;

import java.util.ArrayList;
import java.util.List;

import csves.DownLoadCsv;
import csves.GetPath;

public class Ps {

	private List<String[]> psData;
	private String file;
	private String encode;

	public Ps() {
		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからflashpoint.csvのﾊﾟｽを取得する
		file = GetPath.getPath("ps090907");
		encode = "shift-JIS";
		DownLoadCsv dlCsv = new DownLoadCsv(file, encode);
		psData = dlCsv.getCsvData();
	}

	//全てのpsﾃﾞｰﾀ
	public List<String[]> getPsData() {
		return psData;
	}

	public List<String[]> getPsPurpose(String sikakariHb) {
		/*
		 * 20230613 ps090907に、S6-UVPP3A-Sの配合が２つ入っていたため
		 * tableForNoticeが正しく作られなかった。データベースps090907
		 * から重複する仕掛品を全て探すのは困難なため、
		 * codeを修正して重複する仕掛品があっても正しく抽出できるように
		 * する。最初に登場した仕掛品を抽出する。
		 */
		List<String[]> psPurpose = new ArrayList<>();
		/*
		 * psDataの品番列[0]を上から見て行って、sikakariHbと一致したら
		 * psPurposeにaddする。そして、次の行の品番列がsikakariHbと
		 * 違っていたらisBreakをtrueにしてforから抜ける。
		 * 一番最後の行は、isBreakがfalseのままならそのまま追加する。
		 * 最終行が単独配合でも上からの続きでもうまくいくはず。
		 * うまくいかなかった。
		 * 20230626　仕掛品表に載ってなくても（仕掛品番）が無い時に、
		 * 一番最後の原料だけが原料として取得されるバグができてた。
		 * 最後に書いてある原料47-151が取得されていたｱｸﾘﾙｱﾐﾄﾞなど
		 * 一番最後は仕掛品と一致したら追加に変更した。
		 */

		boolean isBreak = false;
		for (int i = 0; i < psData.size() -1; i++ ) {
			if (psData.get(i)[0].equals(sikakariHb)) {
				psPurpose.add(psData.get(i));
				if(!psData.get(i+1)[0].equals(psData.get(i)[0])) {
					isBreak = true;
					break;
				}
			}
		}
		if(psData.get(psData.size() -1)[0].equals(sikakariHb)) {
			psPurpose.add(psData.get(psData.size()-1));
		}

		if(! isBreak) {
			System.out.println("psﾏｽﾀに仕掛品がありません。プログラム終了します  (-_-;)");
			System.exit(0);
		}


		return psPurpose;
	}

	//PSのトータル
	public float getTotal(List<String[]> psPurpose) {
		float total = 0;
		for (String[] line : psPurpose) {
			total += Float.parseFloat(line[3]);
		}
		return total;
	}

	//トータル1としたときの配合
	public void getPsRate(List<String[]> psPurpose, float total) {
		for (String[] line : psPurpose) {
			line[3] = Float.toString(Float.parseFloat(line[3]) / total);
		}
	}

}
