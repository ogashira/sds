package components;

import java.util.List;

import csves.UpdateCsv;
import structures.StructInputForm;
import structures.StructNite;
import structures.StructPhysicalData;
import structures.StructSolDb;
import structures.StructTableForLabel;
import structures.StructTableForNotice;

public class CreateComponent {

	private String hinban;
	private StructInputForm inputForm;
	private String renewalDeadline;

	private List<StructSolDb> listStructSol;
	private List<StructSolDb> resultDb;
	private List<StructTableForLabel> tableForLabel;
	private List<StructTableForNotice> tableForNotice;
	private List<StructPhysicalData> listPhysicalData;
	private List<StructNite> halfwayMultiStructNite;
	//niteから取ってきた情報最終的に成分量、sds表示名を追加してmultiStructNiteになる。
	private List<StructNite> multiStructNite;
	private Component component;

	public CreateComponent(StructInputForm inputForm) {
		this.inputForm = inputForm;
		hinban = inputForm.hinban;
		renewalDeadline = inputForm.renewalDeadline;
		component = new Component(hinban);
	}

	public List<StructSolDb> createListStructSol() {
		/*
		 * listStructSolを作成する。スクレイピングした時は、
		 * multiStructNiteを作成してlistStructSolの情報を
		 * 上書きする。
		 */

		// 1 溶剤の重複は除いてある。tmbzが全部はいっている。
		listStructSol = component.getListStructSol();

		halfwayMultiStructNite = null;
		if (inputForm.isNiteScraping) { //scrapingする場合
			// 3 Niteからｽｸﾚｲﾋﾟﾝｸﾞしたdata
			halfwayMultiStructNite = component.getMultiStructNite(listStructSol,
					renewalDeadline);

			// 4 listStructSolの情報をhalfwayMultiStructNiteで上書きする。
			listStructSol = component.newerListStructSol(listStructSol, halfwayMultiStructNite);
		}

		return listStructSol;

	}

	public List<StructSolDb> createResultDb() {

		// 2 resultDbを作って、tmbzをまとめる
		resultDb = component.getResultDb(listStructSol);

		return resultDb;
	}

	public List<StructPhysicalData> createListPhysicalData() {

		listPhysicalData = component.getListPhysicalData(listStructSol);
		return listPhysicalData;

	}

	public List<StructTableForNotice> createTableForNotice() {

		/*resultDbを基にtableForNoticeを作っていくが、
		  途中、新しくnewしたArrayListにresultDbの要素を入れる。
		  その時、要素の構造体は参照値なので、新たにArrayListに入れた
		  resultDbの構造体のratioをﾌﾟﾗｽ
		  などして代えると、もとのresuluDbの要素の構造体のratioも変わって
		  しまう。totalが1.0でなくなってしまう.
		  そこで、tools.CopyStructｸﾗｽのcopyListStructSolDbﾒｿｯﾄﾞに
		  resultDbを与えて、ｺﾋﾟｰを作るようにした。このﾒｿｯﾄﾞはstaticで
		  ある。
		 */
		// 6 tableForNoticeを作る。
		tableForNotice = component.getListComponentForNotice(resultDb);

		return tableForNotice;

	}

	public List<StructTableForLabel> createTableForLabel() {

		//8 tableForLabelを作る
		tableForLabel = component.getListComponentForLabel(resultDb);


		return tableForLabel;

	}


	public void updateSolbentDb() {

		if (inputForm.isNiteScraping) { //scrapingした場合
			// 5 multiStructNiteに成分量とsds表示名を追加する
			multiStructNite = component.addToMultiStructNite(resultDb, halfwayMultiStructNite);
		} else {
			multiStructNite = null;
		}
		if (multiStructNite != null && multiStructNite.size() > 0) {
			UpdateCsv.updateSolventDb(multiStructNite);
		}
	}

}
