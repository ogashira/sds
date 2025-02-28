package excelsChn.format;

import java.util.List;

abstract class ABSChnFormat implements IFSdsFormatChn {

	//Section1
	//作成・改訂
	final int[] DATE = { 1, 22, 0 };
	//製品名
	final int[] HINMEI = { 7, 9, 0 };
	//作成者名
	final int[] TANTOU = { 11, 9, 0 };
	//推奨用途
	final int[] YOUTO = { 15, 9, 0 };

	//Section2
	//爆発物
	final int[] 爆発物 = { 21, 13, 0 };
	//可燃性ガス
	final int[] 可燃性ガス = { 22, 13, 0 };
	//エアゾール
	final int[] エアゾール = { 23, 13, 0 };
	//酸化性ガス
	final int[] 酸化性ガス = { 24, 13, 0 };
	//高圧ガス
	final int[] 高圧ガス = { 25, 13, 0 };
	//引火性液体
	final int[] 引火性液体 = { 26, 13, 0 };
	//可燃性固体
	final int[] 可燃性固体 = { 27, 13, 0 };
	//自己反応性化学品
	final int[] 自己反応性化学品 = { 28, 13, 0 };
	//自然発火性液体
	final int[] 自然発火性液体 = { 29, 13, 0 };
	//自然発火性固体
	final int[] 自然発火性固体 = { 30, 13, 0 };
	//自己発熱性化学品
	final int[] 自己発熱性化学品 = { 31, 13, 0 };
	//水反応可燃性化学品
	final int[] 水反応可燃性化学品 = { 32, 13, 0 };
	//酸化性液体
	final int[] 酸化性液体 = { 33, 13, 0 };
	//酸化性固体
	final int[] 酸化性固体 = { 34, 13, 0 };
	//有機過酸化物
	final int[] 有機過酸化物 = { 35, 13, 0 };
	//金属腐食性化学品
	final int[] 金属腐食性化学品 = { 36, 13, 0 };
	//純性化爆発物
	final int[] 純性化爆発物 = { 37, 13, 0 };
	//急性毒性経口
	final int[] 急毒経口 = { 38, 13, 0 };
	//急性毒性経皮
	final int[] 急毒経皮 = { 39, 13, 0 };
	//急性毒性吸入：気体
	final int[] 急毒気体 = { 40, 13, 0 };
	//急性毒性吸入：蒸気
	final int[] 急毒蒸気 = { 41, 13, 0 };
	//急性毒性吸入：粉塵、ミスト
	final int[] 急毒粉塵 = { 42, 13, 0 };
	//皮膚刺激/腐食性
	final int[] 皮膚刺激 = { 43, 13, 0 };
	//眼に対する重篤な損傷性/眼刺激性
	final int[] 眼刺激性 = { 44, 13, 0 };
	//呼吸器感さ性 固体/液体
	final int[] 呼吸器感作固液体 = { 45, 13, 0 };
	//呼吸器感さ性 気体
	final int[] 呼吸器感作気体 = { 46, 13, 0 };
	//皮膚感さ性
	final int[] 皮膚感作性 = { 47, 13, 0 };
	//生殖細胞変異原性
	final int[] 変異原性 = { 48, 13, 0 };
	//発がん性
	final int[] 発がん性 = { 49, 13, 0 };
	//生殖毒性
	final int[] 生殖毒性 = { 50, 13, 0 };
	//生殖毒性・授乳影響
	final int[] 生殖毒性授乳 = { 51, 13, 70 }; //この70は仮(適当)
	//特定標的臓器毒性(単回暴露) 区分1
	final int[] 単回ばく露1 = { 52, 13, 70 };
	//特定標的臓器毒性(単回暴露) 区分2
	final int[] 単回ばく露2 = { 53, 13, 70 };
	//特定標的臓器毒性(単回暴露) 区分3
	final int[] 単回ばく露3 = { 54, 13, 70 };
	//特定標的臓器毒性(反復暴露) 区分1
	final int[] 反復ばく露1 = { 55, 13, 70 };
	//特定標的臓器毒性(反復暴露) 区分2
	final int[] 反復ばく露2 = { 56, 13, 70 };
	//誤えん有害性
	final int[] 誤えん有害性 = { 57, 13, 0 };
	//水生環境有害性 短期(急性)
	final int[] 水生環境急性 = { 58, 13, 0 };
	//水生環境有害性 長期(慢性)
	final int[] 水生環境慢性 = { 59, 13, 0 };
	//オゾン層への有害性
	final int[] オゾン層有害性 = { 60, 13, 0 };

	//絵表示
	final int[] PICTOGRAM = { 63, 6, 0 };

	//注意喚起語
	final int[] SIGNAL_WORD = { 67, 6, 0 };
	//危険有害性情報
	final int[] HAZARDS_INFO = { 68, 6, 0 };

	protected InsertChnFormat insertChnFormat;

	@Override
	public void insertHeader() {
		insertChnFormat.insertHeader();
	}

	@Override
	public void insertSection1(String str, String item) {

		// str : 実際に挿入する文字列
		// item : "tokkasoku1", "hinmei" など
		//tiikiInfo, syoubouhouはここでは挿入しない。GHS関連データ
		//挿入の時に行う。
		int[] insertCell = null;
		switch (item) {
		case "date":
			insertCell = DATE;
			break;
		case "hinmei":
			insertCell = HINMEI;
			break;
		case "tantou":
			insertCell = TANTOU;
			break;
		case "youto":
			insertCell = YOUTO;
			break;
		}
		insertChnFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertSection2(String str, String item) {
		int[] insertCell = null;
		switch (item) {
		case "爆発物":
			insertCell = 爆発物;
			break;
		case "可燃性ガス":
			insertCell = 可燃性ガス;
			break;
		case "エアゾール":
			insertCell = エアゾール;
			break;
		case "酸化性ガス":
			insertCell = 酸化性ガス;
			break;
		case "高圧ガス":
			insertCell = 高圧ガス;
			break;
		case "引火性液体":
			insertCell = 引火性液体;
			break;
		case "可燃性固体":
			insertCell = 可燃性固体;
			break;
		case "自己反応性化学品":
			insertCell = 自己反応性化学品;
			break;
		case "自然発火性液体":
			insertCell = 自然発火性液体;
			break;
		case "自然発火性固体":
			insertCell = 自然発火性固体;
			break;
		case "自己発熱性化学品":
			insertCell = 自己発熱性化学品;
			break;
		case "水反応可燃性化学品":
			insertCell = 水反応可燃性化学品;
			break;
		case "酸化性液体":
			insertCell = 酸化性液体;
			break;
		case "酸化性固体":
			insertCell = 酸化性固体;
			break;
		case "有機過酸化物":
			insertCell = 有機過酸化物;
			break;
		case "金属腐食性化学品":
			insertCell = 金属腐食性化学品;
			break;
		case "純性化爆発物":
			insertCell = 純性化爆発物;
			break;
		case "急毒経口":
			insertCell = 急毒経口;
			break;
		case "急毒経皮":
			insertCell = 急毒経皮;
			break;
		case "急毒気体":
			insertCell = 急毒気体;
			break;
		case "急毒蒸気":
			insertCell = 急毒蒸気;
			break;
		case "急毒粉塵":
			insertCell = 急毒粉塵;
			break;
		case "皮膚刺激":
			insertCell = 皮膚刺激;
			break;
		case "眼刺激性":
			insertCell = 眼刺激性;
			break;
		case "呼吸器感作固液体":
			insertCell = 呼吸器感作固液体;
			break;
		case "呼吸器感作気体":
			insertCell = 呼吸器感作気体;
			break;
		case "皮膚感作性":
			insertCell = 皮膚感作性;
			break;
		case "変異原性":
			insertCell = 変異原性;
			break;
		case "発がん性":
			insertCell = 発がん性;
			break;
		case "生殖毒性":
			insertCell = 生殖毒性;
			break;
		case "生殖毒性授乳":
			insertCell = 生殖毒性授乳;
			break;
		case "単回ばく露1":
			insertCell = 単回ばく露1;
			break;
		case "単回ばく露2":
			insertCell = 単回ばく露2;
			break;
		case "単回ばく露3":
			insertCell = 単回ばく露3;
			break;
		case "反復ばく露1":
			insertCell = 反復ばく露1;
			break;
		case "反復ばく露2":
			insertCell = 反復ばく露2;
			break;
		case "誤えん有害性":
			insertCell = 誤えん有害性;
			break;
		case "水生環境急性":
			insertCell = 水生環境急性;
			break;
		case "水生環境慢性":
			insertCell = 水生環境慢性;
			break;
		case "オゾン層有害性":
			insertCell = オゾン層有害性;
			break;
		case "SIGNAL_WORD":
			insertCell = SIGNAL_WORD;
			break;
		}
		insertChnFormat.insertInfo(str, item, insertCell);
	}

	@Override
	public void insertPictogram(List<String> listPict) {
		insertChnFormat.insertPict(listPict);

	}

	@Override
	public void insertHazardInfo(List<String> listHazardInfo) {
		insertChnFormat.insertHazardInfo(listHazardInfo);

	}

	/*
	abstract public void insertSection3();

	abstract public void insertTableOfComponent(List<StructTableForNotice> tableForNotice);

	abstract public void insertSection8();

	abstract public void insertSection9();

	abstract public void insertSection11();

	abstract public void insertSection12();

	abstract public void insertSection14();

	abstract public void insertSection15();
	*/
}
