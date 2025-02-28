package csves;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class CreateCsv {

	public void toCsv(List<String[]> data, String filePath, String encode) {
		try {
			//出力ﾌｧｲﾙの作成
			File file = new File(filePath);
			//PrintWriterｸﾗｽのｵﾌﾞｼﾞｪｸﾄを生成
			PrintWriter pw = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file), encode)));
			//ﾍｯﾀﾞｰの指定
			/*
			for(int i = 0; i< header.length -1; i++) {
			    pw.print(header[i] + ",");
			}
			pw.print(header[header.length -1]);
			pw.println();
			*/

			//ﾃﾞｰﾀを書き込む
			for (int i = 0; i < data.size(); i++) {
				for (int j = 0; j < data.get(i).length -1 ; j++) {
					pw.print(data.get(i)[j] + ",");
				}
				pw.print(data.get(i)[data.get(i).length -1]); //最後の一列を記入
				pw.println();
			}

			pw.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	//ｵｰﾊﾞｰﾛｰﾄﾞ
	public void toCsv(Map<String, Float> data, String encode) {
		try {
			//出力ﾌｧｲﾙの作成
			File file = new File("/mnt/c/users/oga/documents/java.csv");
			//PrintWriterｸﾗｽのｵﾌﾞｼﾞｪｸﾄを生成
			PrintWriter pw = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file), encode)));
			//ﾍｯﾀﾞｰの指定
			/*
			for(int i = 0; i< header.length -1; i++) {
			    pw.print(header[i] + ",");
			}
			pw.print(header[header.length -1]);
			pw.println();
			*/

			//ﾃﾞｰﾀを書き込む
			for (String genryou : data.keySet()) {
				pw.print(genryou + ",");
				pw.print(data.get(genryou));
				pw.println();
			}

			pw.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
