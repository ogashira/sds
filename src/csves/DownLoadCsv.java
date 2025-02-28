package csves;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DownLoadCsv {

	private String file;
	private String encode;

	public DownLoadCsv(String file, String encode) {
		this.file = file;
		this.encode = encode;
	}

	public List<String[]> getCsvData() {
		BufferedReader br = null;
		List<String[]> csvData = new ArrayList<>();

		try {
			File f = new File(this.file);
			InputStreamReader osr = new InputStreamReader(new FileInputStream(f), this.encode);
			br = new BufferedReader(osr);

			//watyuuDb = new String[][6];
			//分割回数に-1を指定すると、末尾の空文字を削除しない
			String line = br.readLine();
			while (line != null) {
				line = line.replace("\"", "");
				csvData.add(line.split(",", -1));
				line = br.readLine();
			}

		} catch (Exception e) {
			System.out.println("csv ダウンロード失敗 :" + file);
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return csvData;
	}

	public List<String[]> getCsvData(Map<String, Float> dupliBunkai) {
		BufferedReader br = null;
		List<String[]> csvData = new ArrayList<>();

		try {
			File f = new File(this.file);
			InputStreamReader osr = new InputStreamReader(new FileInputStream(f), this.encode);
			br = new BufferedReader(osr);

			//watyuuDb = new String[][6];
			String line = br.readLine();
			while (line != null) {
				line = line.replace("\"", "");
				String[] array_line = line.split(",", -1);
				for (String key : dupliBunkai.keySet()) {
					if (array_line[0].equals(key)) {
						csvData.add(array_line);
					}
				}
				line = br.readLine();
			}

		} catch (Exception e) {
			System.out.println("csv ダウンロード失敗 :" + file);
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return csvData;
	}
}
