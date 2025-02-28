package excelsEng.formats;

import java.util.List;

import structures.StructTableForNotice;

public interface IFSdsFormatEng {

	void insertHeader();

	void insertSection1(String str, String item);

	void insertSection2(String str, String item);

	void insertPictogram(List<String> listPict);

	void insertHazardInfo(List<String> listHazardInfo);

	void insertSection3(String str, String item);

	void insertTableOfComponent(List<StructTableForNotice> tableForNotice);

	void insertSection8(String str, String item);

	void insertSection9(String str, String item);

	void insertSection11(String str, String item);

	void insertSection12(String str, String item);

	void insertSection14(String str, String item);

	void insertSection15(String str, String item);

	void outputExcel();
}
