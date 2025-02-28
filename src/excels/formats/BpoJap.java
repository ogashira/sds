package excels.formats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import structures.StructInputForm;
import structures.StructTableForNotice;

public class BpoJap extends ABSJapFormat {
	//public class BpoJap extends ABSJapFormat{
	//列も行もexcelのとおり1ｽﾀｰﾄ。実際に使うときは-1
	//{行, 列, MAX文字数}

	//国/地域情報
	private final int[] TIIKI_INFO = { 127, 6, 0 };

	//Section3
	//単一化学物質・混合物の区別
	private final int[] IS_SINGLE = { 131, 7, 0 };
	//製品名２
	private final int[] HINMEI2 = { 132, 3, 0 };

	//Section8
	//管理濃度
	private final int[] KANRI_NOUDO = { 258, 7, 0 };
	//ACGIH
	private final int[] ACGIH = { 260, 7, 0 };
	//ACGIH 要素~より類推
	private final int[] ACGIH_YOUSO = { 260, 9, 79 };
	//日本産業衛生学会
	private final int[] NISSANEI = { 261, 7, 0 };
	//日本産業衛生学会 要素　～より類推
	private final int[] NISSANEI_YOUSO = { 261, 9, 79 };

	//Section9
	//物理的状態
	private final int[] CONDITION = { 276, 9, 0 };
	//色
	private final int[] COLOR = { 277, 9, 0 };
	//沸点または初留点
	private final int[] BOIL_POINT = { 280, 9, 0 };
	//爆発限界上限
	private final int[] UPPER_LIMIT = { 283, 9, 0 };
	//爆発限界下限
	private final int[] LOWER_LIMIT = { 284, 9, 0 };
	//引火点
	private final int[] FLASH_POINT = { 285, 9, 0 };
	//自然発火点
	private final int[] IGNITION_POINT = { 286, 9, 0 };
	//蒸気圧
	private final int[] VAPOR_PRESSURE = { 293, 9, 0 };
	//密度/相対密度
	private final int[] SG = { 294, 9, 0 };

	//Section11
	//急性毒性経口
	final int[] s11急毒経口 = { 314, 10, 77 }; //この70は仮(適当)
	//急性毒性経皮
	final int[] s11急毒経皮 = { 315, 10, 77 };
	//急性毒性吸入：気体
	final int[] s11急毒気体 = { 316, 10, 77 };
	//急性毒性吸入：蒸気
	final int[] s11急毒蒸気 = { 317, 10, 77 };
	//急性毒性吸入：粉塵、ミスト
	final int[] s11急毒粉塵 = { 318, 10, 77 };
	//皮膚刺激/腐食性
	final int[] s11皮膚刺激 = { 319, 10, 77 };
	//眼に対する重篤な損傷性/眼刺激性
	final int[] s11眼刺激性 = { 320, 10, 77 };
	//呼吸器感さ性 固体/液体
	final int[] s11呼吸器感作固液体 = { 321, 10, 77 };
	//呼吸器感さ性 気体
	final int[] s11呼吸器感作気体 = { 322, 10, 77 };
	//皮膚感さ性
	final int[] s11皮膚感作性 = { 323, 10, 77 };
	//生殖細胞変異原性
	final int[] s11変異原性 = { 324, 10, 77 };
	//発がん性
	final int[] s11発がん性 = { 325, 10, 77 };
	//生殖毒性
	final int[] s11生殖毒性 = { 326, 10, 77 };
	//生殖毒性・授乳影響
	final int[] s11生殖毒性授乳 = { 327, 10, 77 };
	//特定標的臓器毒性(単回暴露) 区分1
	final int[] s11単回ばく露1 = { 328, 10, 77 };
	//特定標的臓器毒性(単回暴露) 区分2
	final int[] s11単回ばく露2 = { 329, 10, 77 };
	//特定標的臓器毒性(単回暴露) 区分3
	final int[] s11単回ばく露3 = { 330, 10, 77 };
	//特定標的臓器毒性(反復暴露) 区分1
	final int[] s11反復ばく露1 = { 331, 10, 77 };
	//特定標的臓器毒性(反復暴露) 区分2
	final int[] s11反復ばく露2 = { 332, 10, 77 };
	//誤えん有害性
	final int[] s11誤えん有害性 = { 333, 10, 77 };

	//Section12
	//水生環境有害性 短期(急性)
	final int[] s12水生環境急性 = { 339, 10, 77 };
	//水生環境有害性 長期(慢性)
	final int[] s12水生環境慢性 = { 340, 10, 77 };
	//残留性・分解性
	final int[] s12残留性分解性 = { 341, 10, 77 };
	//生態蓄積性
	final int[] s12生態蓄積性 = { 342, 10, 77 };
	//土壌中の移動性
	final int[] s12土壌中の移動性 = { 343, 10, 77 };
	//オゾン層への有害性
	final int[] s12オゾン層有害性 = { 344, 10, 77 };

	//Section14
	//国連番号
	private final int[] UN_NO = { 363, 9, 0 };
	//品名（国連輸送名）
	private final int[] UN_NAME = { 364, 9, 0 };
	//国連分類
	private final int[] UN_CLASS = { 365, 9, 0 };
	//容器等級
	private final int[] UN_YOUKI = { 366, 9, 0 };
	//海洋汚染物質
	private final int[] marinePollu = { 367, 9, 0 };

	//Section15
	//がん原生物質の挿入行
	private final int[] GAN_GENSEI = { 386, 10, 77 };
	//特化則の挿入行
	private final int[] TOKKASOKU1 = { 388, 10, 77 };
	private final int[] TOKKASOKU2 = { 390, 10, 77 };
	private final int[] TOKKASOKU3 = { 392, 10, 77 };
	//有機則の挿入行
	private final int[] YUUKISOKU = { 394, 10, 77 };
	//名称等を表示すべき有害物の行
	private final int[] LABEL_DISPLAY = { 396, 10, 77 };
	//名称等を通知すべき有害物
	private final int[] SDS_NOTIFICATION = { 398, 10, 77 };
	//第一種指定化学物質prtrの行
	private final int[] PRTR1 = { 401, 10, 77 };
	//第二種指定化学物質prtrの行
	private final int[] PRTR2 = { 403, 10, 77 };
	//特定第一種特定化学物質prtrの行
	private final int[] PRTR_TOKU1 = { 405, 10, 77 };
	//化審法優先評価化学物質の行
	private final int[] KASINHOU_YUUSEN = { 408, 10, 77 };
	//消防法
	private final int[] SYOUBOUHOU = { 410, 9, 0 };
	//船舶安全法
	private final int[] SHIP_SAFTY_ACT = { 412, 9, 0 };
	//航空法
	private final int[] AVIATION_ACT = { 414, 9, 0 };

	//成分表の最初と最後の行、列の最大バイト数 MSｺﾞｼｯｸでﾌｫﾝﾄｻｲｽﾞ10の場合
	private Map<String, Integer> tableComponentSize;

	public BpoJap(StructInputForm inputForm) {

		insertJapFormat = new InsertJapFormat(inputForm, "bpoJap");
		//bpoJap:sdsType
		tableComponentSize = new HashMap<>();
		tableComponentSize.put("tableForNoticeSttRow", 137);
		tableComponentSize.put("tableForNoticeEndRow", 157);
		tableComponentSize.put("maxComponentCol", 28);
		tableComponentSize.put("maxCasNoCol", 12);
		tableComponentSize.put("maxAneihouNoCol", 12);
		tableComponentSize.put("maxPrtrNoCol", 16);
		tableComponentSize.put("maxKasinhouNoCol", 12);
	}

	@Override
	public void insertSection3(String str, String item) {
		// str : 実際に挿入する文字列
		// item : "tokkasoku1", "hinmei" など
		//tiikiInfo, syoubouhouはここでは挿入しない。GHS関連データ
		//挿入の時に行う。
		int[] insertCell = null;
		switch (item) {
		case "tiikiInfo":
			insertCell = TIIKI_INFO;
			break;
		case "isSingle":
			insertCell = IS_SINGLE;
			break;
		case "hinmei2":
			insertCell = HINMEI2;
			break;
		}
		insertJapFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertSection8(String str, String item) {
		int[] insertCell = null;
		switch (item) {
		case "kanriNoudo":
			insertCell = KANRI_NOUDO;
			break;
		case "acgih":
			insertCell = ACGIH;
			break;
		case "acgihYouso":
			insertCell = ACGIH_YOUSO;
			break;
		case "niSsanEi":
			insertCell = NISSANEI;
			break;
		case "niSsanEiYouso":
			insertCell = NISSANEI_YOUSO;
			break;
		}
		insertJapFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertSection9(String str, String item) {
		int[] insertCell = null;
		switch (item) {
		case "condition":
			insertCell = CONDITION;
			break;
		case "color":
			insertCell = COLOR;
			break;
		case "boilPoint":
			insertCell = BOIL_POINT;
			break;
		case "upperLimit":
			insertCell = UPPER_LIMIT;
			break;
		case "lowerLimit":
			insertCell = LOWER_LIMIT;
			break;
		case "flashPoint":
			insertCell = FLASH_POINT;
			break;
		case "ignitionPoint":
			insertCell = IGNITION_POINT;
			break;
		case "vaporPressure":
			insertCell = VAPOR_PRESSURE;
			break;
		case "SG":
			insertCell = SG;
			break;
		}
		insertJapFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertSection11(String str, String item) {
		int[] insertCell = null;
		switch (item) {
		case "s11急毒経口":
			insertCell = s11急毒経口;
			break;
		case "s11急毒経皮":
			insertCell = s11急毒経皮;
			break;
		case "s11急毒気体":
			insertCell = s11急毒気体;
			break;
		case "s11急毒蒸気":
			insertCell = s11急毒蒸気;
			break;
		case "s11急毒粉塵":
			insertCell = s11急毒粉塵;
			break;
		case "s11皮膚刺激":
			insertCell = s11皮膚刺激;
			break;
		case "s11眼刺激性":
			insertCell = s11眼刺激性;
			break;
		case "s11呼吸器感作固液体":
			insertCell = s11呼吸器感作固液体;
			break;
		case "s11呼吸器感作気体":
			insertCell = s11呼吸器感作気体;
			break;
		case "s11皮膚感作性":
			insertCell = s11皮膚感作性;
			break;
		case "s11変異原性":
			insertCell = s11変異原性;
			break;
		case "s11発がん性":
			insertCell = s11発がん性;
			break;
		case "s11生殖毒性":
			insertCell = s11生殖毒性;
			break;
		case "s11生殖毒性授乳":
			insertCell = s11生殖毒性授乳;
			break;
		case "s11単回ばく露1":
			insertCell = s11単回ばく露1;
			break;
		case "s11単回ばく露2":
			insertCell = s11単回ばく露2;
			break;
		case "s11単回ばく露3":
			insertCell = s11単回ばく露3;
			break;
		case "s11反復ばく露1":
			insertCell = s11反復ばく露1;
			break;
		case "s11反復ばく露2":
			insertCell = s11反復ばく露2;
			break;
		case "s11誤えん有害性":
			insertCell = s11誤えん有害性;
			break;
		}
		insertJapFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertSection12(String str, String item) {
		int[] insertCell = null;
		switch (item) {
		case "s12水生環境急性":
			insertCell = s12水生環境急性;
			break;
		case "s12水生環境慢性":
			insertCell = s12水生環境慢性;
			break;
		case "s12残留性分解性":
			insertCell = s12残留性分解性;
			break;
		case "s12生態蓄積性":
			insertCell = s12生態蓄積性;
			break;
		case "s12土壌中の移動性":
			insertCell = s12土壌中の移動性;
			break;
		case "s12オゾン層有害性":
			insertCell = s12オゾン層有害性;
			break;
		}
		insertJapFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertSection14(String str, String item) {
		int[] insertCell = null;
		switch (item) {
		case "unNo":
			insertCell = UN_NO;
			break;
		case "unName":
			insertCell = UN_NAME;
			break;
		case "unClass":
			insertCell = UN_CLASS;
			break;
		case "unYouki":
			insertCell = UN_YOUKI;
			break;
		case "marinePollu":
			insertCell = marinePollu;
			break;
		}
		insertJapFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertSection15(String str, String item) {
		int[] insertCell = null;
		switch (item) {
		case "ganGensei":
			insertCell = GAN_GENSEI;
			break;
		case "tokkasoku1":
			insertCell = TOKKASOKU1;
			break;
		case "tokkasoku2":
			insertCell = TOKKASOKU2;
			break;
		case "tokkasoku3":
			insertCell = TOKKASOKU3;
			break;
		case "yuukisoku":
			insertCell = YUUKISOKU;
			break;
		case "labelDisplay":
			insertCell = LABEL_DISPLAY;
			break;
		case "sdsNotification":
			insertCell = SDS_NOTIFICATION;
			break;
		case "prtr1":
			insertCell = PRTR1;
			break;
		case "prtr2":
			insertCell = PRTR2;
			break;
		case "prtrToku1":
			insertCell = PRTR_TOKU1;
			break;
		case "kasinhouYuusen":
			insertCell = KASINHOU_YUUSEN;
			break;
		case "syoubouhou":
			insertCell = SYOUBOUHOU;
			break;
		case "shipSaftyAct":
			insertCell = SHIP_SAFTY_ACT;
			break;
		case "aviationAct":
			insertCell = AVIATION_ACT;
			break;
		}
		insertJapFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertTableOfComponent(
			List<StructTableForNotice> tableForNotice) {

		insertJapFormat.insertTableOfComponent(tableForNotice,
				tableComponentSize);
	}

	@Override
	public void outputExcel() {
		insertJapFormat.outputExcel();
	}

}
