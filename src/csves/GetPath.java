package csves;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class GetPath {

	private static final String OS_NAME = System.getProperty("os.name")
			.toLowerCase();
	private static final String PROPERTY_PATH_LINUX = "/mnt/c/sdsJava/sds/"
			+ "masterData/sds.properties";
	private static final String PROPERTY_PATH_WIN = "D:/sdsJava/sds/"
			+ "masterData/sds.properties";
	private static final String PROPERTY_PATH_MAC = "/Users/ogashira/"
			+ "sdsJava/sds/masterData/sds.properties";
	private static final String PROPERTY_PATH_SONOTA = "./sds.properties";

	public static String getPath(String dbName) {
		String filePath = "";
		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからflashpoint.csvのﾊﾟｽを取得する
		Properties pr = new Properties();
		String prPath = null;
		String property = null;
		if (OS_NAME.startsWith("linux")) {
			prPath = PROPERTY_PATH_LINUX;
			property = dbName + "Linux";
		} else if (OS_NAME.startsWith("windows")) {
			prPath = PROPERTY_PATH_WIN;
			property = dbName + "Win";
		} else if (OS_NAME.startsWith("mac")) {
			prPath = PROPERTY_PATH_MAC;
			property = dbName + "Mac";
		} else {
			prPath = PROPERTY_PATH_SONOTA;
			property = dbName + "Sonota";
		}

		try {
			InputStream is = new FileInputStream(prPath);
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			pr.load(isr);
			is.close();
		} catch (IOException e) {
			System.out.println("UpdateCsvの溶剤DB読み込みでエラーです");
			e.printStackTrace();
		}

		//ﾌﾟﾛﾊﾟﾃｨﾌｧｲﾙからflashpoint.csvのﾊﾟｽを取得する
		filePath = pr.getProperty(property);


		return filePath;

	}

}
