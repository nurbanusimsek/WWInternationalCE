package simsekTI;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Question1 {

	public static void main(String[] args) throws IOException {

		printDictionary("Dictionary");

	}

	public static void printDictionary(String path) throws IOException {
		String entries = "";
		if (doesFileExist(path)) {
			try {
				entries = new String(Files.readAllBytes(Paths.get(path)));
			} catch (IOException e) {
				e.printStackTrace();
			}
			entries = entries.replaceAll(" – ", "\n").replaceAll(", ", "\n");
			System.out.println(entries);
		} else {
			System.out.println("No file to execute method.");
		}

	}

	public static boolean doesFileExist(String path) {
		File sampleFile = new File(path);
		boolean exists = sampleFile.exists();
		return exists;
	}
}
