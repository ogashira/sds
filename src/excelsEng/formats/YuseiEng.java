package excelsEng.formats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import structures.StructInputForm;
import structures.StructTableForNotice;

public class YuseiEng extends ABSEngFormat {
	//public class YuseiEng extends ABSEngFormat {
	//列も行もexcelのとおり1ｽﾀｰﾄ。実際に使うときは-1
	//{行, 列, MAX文字数}

	//国/地域情報
	private final int[] TIIKI_INFO = { 121, 6, 0 };

	//Section3
	//単一化学物質・混合物の区別
	private final int[] IS_SINGLE = { 126, 7, 0 };
	//製品名２
	private final int[] HINMEI2 = { 127, 3, 0 };

	//Section8
	//管理濃度
	private final int[] KANRI_NOUDO = { 248, 8, 0 };
	//ACGIH
	private final int[] ACGIH = { 250, 8, 0 };
	//ACGIH 要素~より類推
	private final int[] ACGIH_YOUSO = { 250, 10, 75 };
	//日本産業衛生学会
	private final int[] NISSANEI = { 251, 8, 0 };
	//日本産業衛生学会 要素　～より類推
	private final int[] NISSANEI_YOUSO = { 251, 10, 75 };

	//Section9
	//物理的状態
	private final int[] CONDITION = { 267, 9, 0 };
	//色
	private final int[] COLOR = { 268, 9, 0 };
	//沸点または初留点
	private final int[] BOIL_POINT = { 271, 9, 0 };
	//爆発限界上限
	private final int[] UPPER_LIMIT = { 275, 9, 0 };
	//爆発限界下限
	private final int[] LOWER_LIMIT = { 276, 9, 0 };
	//引火点
	private final int[] FLASH_POINT = { 277, 9, 0 };
	//自然発火点
	private final int[] IGNITION_POINT = { 278, 9, 0 };
	//蒸気圧
	private final int[] VAPOR_PRESSURE = { 286, 9, 0 };
	//密度/相対密度
	private final int[] SG = { 287, 9, 0 };

	//Section11
	//急性毒性経口
	final int[] s11急毒経口 = { 307, 10, 80 }; //この70は仮(適当)
	//急性毒性経皮
	final int[] s11急毒経皮 = { 308, 10, 80 };
	//急性毒性吸入：気体
	final int[] s11急毒気体 = { 309, 10, 80 };
	//急性毒性吸入：蒸気
	final int[] s11急毒蒸気 = { 310, 10, 80 };
	//急性毒性吸入：粉塵、ミスト
	final int[] s11急毒粉塵 = { 311, 10, 80 };
	//皮膚刺激/腐食性
	final int[] s11皮膚刺激 = { 312, 10, 80 };
	//眼に対する重篤な損傷性/眼刺激性
	final int[] s11眼刺激性 = { 313, 10, 80 };
	//呼吸器感さ性 固体/液体
	final int[] s11呼吸器感作固液体 = { 314, 10, 80 };
	//呼吸器感さ性 気体
	final int[] s11呼吸器感作気体 = { 315, 10, 80 };
	//皮膚感さ性
	final int[] s11皮膚感作性 = { 316, 10, 80 };
	//生殖細胞変異原性
	final int[] s11変異原性 = { 317, 10, 80 };
	//発がん性
	final int[] s11発がん性 = { 318, 10, 80 };
	//生殖毒性
	final int[] s11生殖毒性 = { 319, 10, 80 };
	//生殖毒性・授乳影響
	final int[] s11生殖毒性授乳 = { 320, 10, 80 };
	//特定標的臓器毒性(単回暴露) 区分1
	final int[] s11単回ばく露1 = { 321, 10, 80 };
	//特定標的臓器毒性(単回暴露) 区分2
	final int[] s11単回ばく露2 = { 322, 10, 80 };
	//特定標的臓器毒性(単回暴露) 区分3
	final int[] s11単回ばく露3 = { 323, 10, 80 };
	//特定標的臓器毒性(反復暴露) 区分1
	final int[] s11反復ばく露1 = { 324, 10, 80 };
	//特定標的臓器毒性(反復暴露) 区分2
	final int[] s11反復ばく露2 = { 325, 10, 80 };
	//誤えん有害性
	final int[] s11誤えん有害性 = { 326, 10, 80 };

	//Section12
	//水生環境有害性 短期(急性)
	final int[] s12水生環境急性 = { 332, 10, 80 };
	//水生環境有害性 長期(慢性)
	final int[] s12水生環境慢性 = { 333, 10, 80 };
	//残留性・分解性
	//final int[] s12残留性分解性 = { 324, 10, 80 };
	//生態蓄積性
	//final int[] s12生態蓄積性 = { 325, 10, 80 };
	//土壌中の移動性
	//final int[] s12土壌中の移動性 = { 326, 10, 80 };
	//オゾン層への有害性
	final int[] s12オゾン層有害性 = { 334, 10, 80 };

	//Section14
	//国連番号
	private final int[] UN_NO = { 356, 9, 0 };
	//品名（国連輸送名）
	private final int[] UN_NAME = { 357, 9, 0 };
	//国連分類
	private final int[] UN_CLASS = { 359, 9, 0 };
	//容器等級
	private final int[] UN_YOUKI = { 360, 9, 0 };
	//海洋汚染物質
	private final int[] marinePollu = { 361, 9, 0 };

	//Section15
	//がん原生物質の挿入行
	private final int[] GAN_GENSEI = { 381, 10, 80 };
	//特化則の挿入行
	private final int[] TOKKASOKU1 = { 383, 10, 80 };
	private final int[] TOKKASOKU2 = { 385, 10, 80 };
	private final int[] TOKKASOKU3 = { 387, 10, 80 };
	//有機則の挿入行
	private final int[] YUUKISOKU = { 389, 10, 80 };
	//名称等を表示すべき有害物の行
	private final int[] LABEL_DISPLAY = { 391, 10, 80 };
	//名称等を通知すべき有害物
	private final int[] SDS_NOTIFICATION = { 393, 10, 80 };
	//第一種指定化学物質prtrの行
	private final int[] PRTR1 = { 397, 10, 80 };
	//第二種指定化学物質prtrの行
	private final int[] PRTR2 = { 399, 10, 80 };
	//特定第一種特定化学物質prtrの行
	private final int[] PRTR_TOKU1 = { 401, 10, 80 };
	//化審法優先評価化学物質の行
	private final int[] KASINHOU_YUUSEN = { 404, 10, 80 };
	//消防法
	private final int[] SYOUBOUHOU = { 406, 9, 0 };
	//船舶安全法
	private final int[] SHIP_SAFTY_ACT = { 408, 9, 0 };
	//航空法
	private final int[] AVIATION_ACT = { 410, 9, 0 };

	//成分表の最初と最後の行、列の最大バイト数 MSｺﾞｼｯｸでﾌｫﾝﾄｻｲｽﾞ10の場合
	private Map<String, Integer> tableComponentSize;

	public YuseiEng(StructInputForm inputForm) {

		insertEngFormat = new InsertEngFormat(inputForm, "yuseiEng");

		tableComponentSize = new HashMap<>();
		tableComponentSize.put("tableForNoticeSttRow", 132);
		tableComponentSize.put("tableForNoticeEndRow", 152);
		tableComponentSize.put("maxComponentCol", 30);
		tableComponentSize.put("maxCasNoCol", 12);
	}

	@Override
	public void insertSection3(String str, String item) {
		// str : 実際に挿入する文字列
		// item : "tokkasoku1", "hinmei" など
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
		insertEngFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertSection8(String str, String item) {
		// str : 実際に挿入する文字列
		// item : "tokkasoku1", "hinmei" など
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
		insertEngFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertSection9(String str, String item) {
		// str : 実際に挿入する文字列
		// item : "tokkasoku1", "hinmei" など
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
		insertEngFormat.insertInfo(str, item, insertCell);
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
		insertEngFormat.insertInfo(str, item, insertCell);
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
	//	case "s12残留性分解性":
	//		insertCell = s12残留性分解性;
	//		break;
	//	case "s12生態蓄積性":
	//		insertCell = s12生態蓄積性;
	//		break;
	//	case "s12土壌中の移動性":
	//		insertCell = s12土壌中の移動性;
	//		break;
		case "s12オゾン層有害性":
			insertCell = s12オゾン層有害性;
			break;
		}
		insertEngFormat.insertInfo(str, item, insertCell);
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
		insertEngFormat.insertInfo(str, item, insertCell);
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

		insertEngFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertTableOfComponent(
			List<StructTableForNotice> tableForNotice) {

		insertEngFormat.insertTableOfComponent(tableForNotice,
				tableComponentSize);
	}

	@Override
	public void outputExcel() {
		insertEngFormat.outputExcel();
	}
}
