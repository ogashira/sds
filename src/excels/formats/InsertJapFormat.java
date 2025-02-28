package excels.formats;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.Units;

import csves.GetPath;
import structures.StructInputForm;
import structures.StructTableForNotice;

class InsertJapFormat {
	/*
	 * IppanJap,MetalJapなどそれぞれのフォーマットの処理後に
	 * 行う。実際にデータを入力したり、行幅変えたり、
	 * 余分な行を削除する。
	 */

	private StructInputForm inputForm;
	private Workbook wb;
	private Sheet sh;
	private FileInputStream in;


	InsertJapFormat(StructInputForm inputForm, String sdsType) {
		// sdsType = ippanJap, yuseiJap, bpoJap, metalJap...
		String ExcelPath = GetPath.getPath(sdsType);
		this.inputForm = inputForm;

		try {
			in = new FileInputStream(ExcelPath);
			wb = WorkbookFactory.create(in);
			sh = wb.getSheet(sdsType);
		} catch (FileNotFoundException e) {
			System.out.println("JapのFormatファイルが見つかりません");
		} catch (IOException e) {
			System.out.println("JapのFormatファイルにアクセスできません");
		}
	}

	void insertHeader() {
		Header header = sh.getHeader();
		String page = HSSFFooter.page() + "/" + HSSFFooter.numPages();
		header.setRight(inputForm.displayName + " : " + page);
	}

	void insertInfo(String str, String item, int[] insertCell) {
		// str : 実際に挿入する文字列
		// item : "tokkasoku1", "hinmei" など

		/* nullが来ても何もしないcodeは危険！！！！
		try {
			if (str==null || item==null || insertCell==null) {
				throw new NullPointerException("Nullだよ");
			}

		}catch (NullPointerException e) {
			System.out.println(e.getMessage());
			return;
		}
		*/

		Row row = sh.getRow(insertCell[0] - 1); //行を作成
		Cell cell = row.getCell(insertCell[1] - 1); //列のｾﾙ作成
		cell.setCellValue(str);

		if (insertCell[2] != 0) {
			try {
				int rowMulti = this.getRowMulti(insertCell[2],
						str.getBytes("Shift_JIS").length);

				//行高さをﾃﾞﾌｫﾙﾄのmaxMulti倍にする
				row.setHeightInPoints(rowMulti * sh.getDefaultRowHeightInPoints());

			} catch (UnsupportedEncodingException e) {
				System.out.println("適用法令をexcelに入力中にｴﾗｰ出ました_InsertJapFormat");
			}
		}
	}

	private int getRowMulti(int maxColByte, int colByte) {
		//行の高さが何倍なのかを返す
		int syou = colByte / maxColByte;
		int yo = colByte % maxColByte;

		if (yo > 0) {
			syou++;
		}

		return syou;
	}

	void insertPict(List<String> listPict) {

		for(int i = 0; i < listPict.size(); i++ ) {

			String fileName = listPict.get(i);
			String pictPath = GetPath.getPath(fileName);


			try {
				//画像を読み込む
				in = new FileInputStream(pictPath);
				byte[] b = IOUtils.toByteArray(in);
				in.close();
				int pidx = wb.addPicture(b, Workbook.PICTURE_TYPE_JPEG);

				//画像表示位置を設定する
				ClientAnchor ca = wb.getCreationHelper().createClientAnchor();
				ca.setCol1(i * 2 + 5);    //表示位置(開始列)
				ca.setRow1(62);           //表示位置(開始行)
				ca.setCol2(i * 2 + 7);    //表示位置(終了列)
				ca.setRow2(65);           //表示位置(終了行)

				ca.setDx1(Units.EMU_PER_PIXEL * 5);    //余白(左)
				ca.setDy1(Units.EMU_PER_PIXEL * 0);    //余白(上)
				ca.setDx2(Units.EMU_PER_PIXEL * -5);   //余白(右)
				ca.setDy2(Units.EMU_PER_PIXEL * 0);   //余白(下)

				//画像を挿入する
				sh.createDrawingPatriarch().createPicture(ca, pidx);
			} catch(Exception ex) {
				System.out.println("ﾋﾟｸﾄｸﾞﾗﾑ貼り付け失敗です");
				ex.printStackTrace();
			}
		}
	}

	void insertTableOfComponent(
			List<StructTableForNotice> tableForNotice,
			Map<String, Integer> tableComponentSize) {

		//int rowMaxA = sh.getLastRowNum();
		//int columnMax1 = sh.getLastColumnNum();

		/*
		//罫線ｽﾀｲﾙの設定
		CellStyle styleLeft = wb.createCellStyle();
		styleLeft.setBorderLeft(BorderStyle.THIN);
		styleLeft.setBorderBottom(BorderStyle.THIN);

		CellStyle styleBottom = wb.createCellStyle();
		styleBottom.setBorderBottom(BorderStyle.THIN);

		CellStyle styleRight = wb.createCellStyle();
		styleRight.setBorderRight(BorderStyle.THIN);
		styleRight.setBorderBottom(BorderStyle.THIN);
		*/

		final int TABLE_FOR_NOTICE_STT_ROW = tableComponentSize.get("tableForNoticeSttRow");
		final int MAX_COMPONENT = tableComponentSize.get("maxComponentCol");
		final int MAX_CAS_NO = tableComponentSize.get("maxCasNoCol");
		final int MAX_ANEIHOU_NO = tableComponentSize.get("maxAneihouNoCol");
		final int MAX_PRTR_NO = tableComponentSize.get("maxPrtrNoCol");
		final int MAX_KASINHOU_NO = tableComponentSize.get("maxKasinhouNoCol");
		final int TABLE_FOR_NOTICE_END_ROW = tableComponentSize.get("tableForNoticeEndRow");

		int rowCnt = TABLE_FOR_NOTICE_STT_ROW - 1; //119行目
		for (StructTableForNotice line : tableForNotice) {
			Row row = sh.getRow(rowCnt); //行を作成
			Cell cellComponent = row.getCell(0); //A列のｾﾙ作成
			Cell cellContentRate = row.getCell(7); //H列のｾﾙ作成
			Cell cellCasNo = row.getCell(9); //J列のｾﾙ作成
			Cell cellAneihouNo = row.getCell(12); //M列のｾﾙ作成
			Cell cellPrtrNo = row.getCell(15); //P列のｾﾙ作成
			Cell cellKasinhouNo = row.getCell(19); //T列のｾﾙ作成
			//ｾﾙに値を代入
			cellComponent.setCellValue(line.component);
			cellContentRate.setCellValue(line.contentRate);
			cellCasNo.setCellValue(line.casNo);
			cellAneihouNo.setCellValue(line.aneihouNo);
			cellPrtrNo.setCellValue(line.prtrNo);
			cellKasinhouNo.setCellValue(line.kasinhouNo);

			/*
			//中央揃えにする  うまくいかない
			CellStyle cs = wb.createCellStyle();
			cs.setAlignment(HorizontalAlignment.CENTER);
			cellComponent.setCellStyle(cs);
			cellContentRate.setCellStyle(cs);
			cellCasNo.setCellStyle(cs);
			cellAneihouNo.setCellStyle(cs);
			cellPrtrNo.setCellStyle(cs);
			cellKasinhouNo.setCellStyle(cs);
			*/

			//行の高さを調整する
			//行と最大ﾊﾞｲﾄ数とﾊﾞｲﾄ数をgetRowMultiに渡して、
			//行の高さを何倍にするかを求めて、行高さを調整する。
			//getRowMultiはinterfaces.SdsFormat.javaにdefaultﾒｿｯﾄﾞとして
			//定義されている。
			int componentMulti = 0;
			int casNoMulti = 0;
			int aneihouNoMulti = 0;
			int prtrNoMulti = 0;
			int kasinhouNoMulti = 0;
			try {
				componentMulti = this.getRowMulti(MAX_COMPONENT,
						line.component.getBytes("Shift_JIS").length);
				casNoMulti = this.getRowMulti(MAX_CAS_NO,
						line.casNo.getBytes("Shift_JIS").length);
				aneihouNoMulti = this.getRowMulti(MAX_ANEIHOU_NO,
						line.aneihouNo.getBytes("Shift_JIS").length);
				prtrNoMulti = this.getRowMulti(MAX_PRTR_NO,
						line.prtrNo.getBytes("Shift_JIS").length);
				kasinhouNoMulti = this.getRowMulti(MAX_KASINHOU_NO,
						line.kasinhouNo.getBytes("Shift_JIS").length);
			} catch (UnsupportedEncodingException e) {
				System.out.println("エンコードをサポートしてませんてば");
			}

			//列のmultiの最大値を求める
			int multies[] = { componentMulti, casNoMulti,
					aneihouNoMulti, prtrNoMulti, kasinhouNoMulti };
			int maxMulti = multies[0];
			for (int i = 1; i < multies.length; i++) {
				maxMulti = Math.max(maxMulti, multies[i]);
			}

			//行高さをﾃﾞﾌｫﾙﾄのmaxMulti倍にする
			row.setHeightInPoints(maxMulti * sh.getDefaultRowHeightInPoints());

			//ｾﾙに罫線を適用する
			/*
			cellComponent.setCellStyle(styleLeft);
			cell1.setCellStyle(styleBottom);
			cell2.setCellStyle(styleBottom);
			cell3.setCellStyle(styleBottom);
			cell4.setCellStyle(styleBottom);
			cell5.setCellStyle(styleBottom);
			cell6.setCellStyle(styleBottom);
			cellContentRate.setCellStyle(styleLeft);
			cell8.setCellStyle(styleBottom);
			cellCasNo.setCellStyle(styleLeft);
			cell10.setCellStyle(styleBottom);
			cell11.setCellStyle(styleBottom);
			cellAneihouNo.setCellStyle(styleLeft);
			cell13.setCellStyle(styleBottom);
			cell14.setCellStyle(styleBottom);
			cellPrtrNo.setCellStyle(styleLeft);
			cell16.setCellStyle(styleBottom);
			cell17.setCellStyle(styleBottom);
			cell18.setCellStyle(styleBottom);
			cellKasinhouNo.setCellStyle(styleLeft);
			cell20.setCellStyle(styleBottom);
			cell21.setCellStyle(styleRight);
			*/

			rowCnt++;
		}

		//余分な行を削除する
		/*
		 * 行の削除のﾒｿｯﾄﾞが無い。代わりに、shiftRowsを使って、行をｼﾌﾄさせて
		 * 削除したように見せる。
		 * ただし、shiftRowsは行中のｾﾙがmergeされているとうまく動いてくれない。
		 * ので、mergeを解除してからshiftRowsを行った。
		 * getNumMergedRegionsでsheet中のmergeされているセルの総数を求める
		 * forで回してgetMergedRegion(int)でﾏｰｼﾞｾﾙを1個1個見ていって、
		 * 該当するｾﾙにA119, H119, J119.....が含まれていたらmergeを解除する。
		 * はっきり言って使いづらい。めんどくせえ！ でもできた!☺
		 */
		int delRow = TABLE_FOR_NOTICE_STT_ROW + tableForNotice.size() - 1;
		int delLastRow = TABLE_FOR_NOTICE_END_ROW - 1;
		for (int i = delLastRow; i >= delRow; i--) {
			//Row row = sh.getRow(delRow);
			int size = sh.getNumMergedRegions() - 1;

			for (int j = size; j >= 0; j--) {
				CellRangeAddress range = sh.getMergedRegion(j);
				if (range.isInRange(i, CellReference.convertColStringToIndex("A"))
						|| range.isInRange(i, CellReference.convertColStringToIndex("H"))
						|| range.isInRange(i, CellReference.convertColStringToIndex("J"))
						|| range.isInRange(i, CellReference.convertColStringToIndex("M"))
						|| range.isInRange(i, CellReference.convertColStringToIndex("P"))
						|| range.isInRange(i, CellReference.convertColStringToIndex("T"))) {
					sh.removeMergedRegion(j);
				}
			}
			sh.shiftRows(i + 1, sh.getLastRowNum(), -1);
		}

	}


	void insertHazardInfo(List<String> listHazardInfo) {

		//int rowMaxA = sh.getLastRowNum();
		//int columnMax1 = sh.getLastColumnNum();

		/*
		//罫線ｽﾀｲﾙの設定
		CellStyle styleLeft = wb.createCellStyle();
		styleLeft.setBorderLeft(BorderStyle.THIN);
		styleLeft.setBorderBottom(BorderStyle.THIN);

		CellStyle styleBottom = wb.createCellStyle();
		styleBottom.setBorderBottom(BorderStyle.THIN);

		CellStyle styleRight = wb.createCellStyle();
		styleRight.setBorderRight(BorderStyle.THIN);
		styleRight.setBorderBottom(BorderStyle.THIN);
		*/


		final int HAZARD_INFO_STT_ROW = 68;
		final int HAZARD_INFO_END_ROW = 92;
		final int MAX_COL = 84; //21列×4=84 文字入力できるバイト数

		int rowCnt = HAZARD_INFO_STT_ROW - 1; //119行目
		for (String line : listHazardInfo) {
			Row row = sh.getRow(rowCnt); //行を作成
			Cell cellHazard = row.getCell(5); //A列のｾﾙ作成
			//ｾﾙに値を代入
			cellHazard.setCellValue(line);

			//行の高さを調整する
			//行と最大ﾊﾞｲﾄ数とﾊﾞｲﾄ数をgetRowMultiに渡して、
			//行の高さを何倍にするかを求めて、行高さを調整する。
			//getRowMultiはinterfaces.SdsFormat.javaにdefaultﾒｿｯﾄﾞとして
			//定義されている。
			int hazardMulti = 0;
			try {
				hazardMulti = this.getRowMulti(MAX_COL,
						line.getBytes("Shift_JIS").length);
			} catch (UnsupportedEncodingException e) {
				System.out.println("エンコードをサポートしてませんてば");
			}


			//行高さをﾃﾞﾌｫﾙﾄのmaxMulti倍にする
			row.setHeightInPoints(hazardMulti * sh.getDefaultRowHeightInPoints());


			rowCnt++;
		}

		//余分な行を削除する
		/*
		 * 行の削除のﾒｿｯﾄﾞが無い。代わりに、shiftRowsを使って、行をｼﾌﾄさせて
		 * 削除したように見せる。
		 * ただし、shiftRowsは行中のｾﾙがmergeされているとうまく動いてくれない。
		 * ので、mergeを解除してからshiftRowsを行った。
		 * getNumMergedRegionsでsheet中のmergeされているセルの総数を求める
		 * forで回してgetMergedRegion(int)でﾏｰｼﾞｾﾙを1個1個見ていって、
		 * 該当するｾﾙにA119, H119, J119.....が含まれていたらmergeを解除する。
		 * はっきり言って使いづらい。めんどくせえ！ でもできた!☺
		//listHazardInfoにデータが無かったら、最初の一行目は削除しない
		 */
		int delRow;
		if(listHazardInfo.size()==0) {
			delRow = HAZARD_INFO_STT_ROW;
		}else {
			delRow = HAZARD_INFO_STT_ROW + listHazardInfo.size() - 1;
		}
		int delLastRow = HAZARD_INFO_END_ROW - 1;
		for (int i = delLastRow; i >= delRow; i--) {
			//Row row = sh.getRow(delRow);
			int size = sh.getNumMergedRegions() - 1;

			for (int j = size; j >= 0; j--) {
				CellRangeAddress range = sh.getMergedRegion(j);
				if (range.isInRange(i, CellReference.convertColStringToIndex("F"))){
					sh.removeMergedRegion(j);
				}
			}
			sh.shiftRows(i + 1, sh.getLastRowNum(), -1);
		}

	}


	void outputExcel() {
		String outPath = null;
		String hinban = inputForm.hinban;

		//計算日>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dtf = new SimpleDateFormat("yyyyMMdd");
		String update = dtf.format(cal.getTime());

		outPath = GetPath.getPath("outJap");
		String outFile = outPath + "j_" + hinban + "_" + update + ".xlsx";
		FileOutputStream out = null;

		try {
			//出力用のｽﾄﾘｰﾑを用意
			out = new FileOutputStream(outFile);
		} catch (FileNotFoundException e) {
			System.out.println("ファイルが見つからないってば");
		}
		try {
			wb.write(out); //ﾌｧｲﾙへ出力
		} catch (IOException e) {
			System.out.println("ファイル出力失敗だってば");
		}


		//ToPdf.createPdf();
	}




	/*
	//これは使っていない
	public String getStringValue(Cell cell) {

	    if (cell == null) {
	        return null;
	    }

	    switch (cell.getCellType()) {

	    case STRING:
	        return cell.getStringCellValue();

	    case NUMERIC:
	        return Double.toString(cell.getNumericCellValue());

	    case BOOLEAN:
	        return Boolean.toString(cell.getBooleanCellValue());

	    case FORMULA:
	        //計算結果を取得する
	        Workbook book = cell.getSheet().getWorkbook();
	        CreationHelper helper = book.getCreationHelper();
	        FormulaEvaluator evaluator = helper.createFormulaEvaluator();
	        CellValue value = evaluator.evaluate(cell);
	        switch (value.getCellType()) {
	            case STRING:
	                return cell.getStringCellValue();
	            case NUMERIC:
	                return Double.toString(cell.getNumericCellValue());
	            case BOOLEAN:
	                return Boolean.toString(cell.getBooleanCellValue());
	            default:
	        }


	    default:
	        return null;

	    }

	}
	*/

}
