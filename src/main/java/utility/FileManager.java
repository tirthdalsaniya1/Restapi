package utility;

import java.io.InputStream;

public class FileManager {

	public static InputStream readFileToInputStream(String path) {

		return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

	}
}
