package jobs4u.base.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import jobs4u.base.pluginhandler.domain.PluginType;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import org.apache.commons.io.FileUtils;

public class ImportFileService {
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$"),
			NAME_PATTERN = Pattern.compile("^[\\w]+( [\\w]+)+$"),
			NUMBER_PATTERN = Pattern.compile("^[\\d]{9}$");

	public static String[] importCandidateInfo(final String file) throws IOException {
		String[] info = { null, null, null, null };
		String ln;
		BufferedReader read = null;

		try {
			read = new BufferedReader(new FileReader(file));
		} catch (Exception e) {
			read.close();
			throw new FileNotFoundException();
		}
		while ((ln = read.readLine()) != null) {
			if (NUMBER_PATTERN.matcher(ln).matches()) {
				info[0] = ln.substring(0, ln.length());
			} else if (EMAIL_PATTERN.matcher(ln).matches()) {
				info[1] = ln.substring(0, ln.length());
			} else if (NAME_PATTERN.matcher(ln).matches()) {
				info[2] = ln.substring(0, ln.indexOf(" "));
				info[3] = ln.substring(ln.lastIndexOf(" ") + 1, ln.length());
			}
		}

		read.close();
		String s = "Missing Person info.";
		boolean err = false;
		for (int i = 0; i < info.length; i++) {
			if (info[i] == null) {
				switch (i) {
					case 0:
						s.concat(" Phone number not found.");
						break;
					case 1:
						s.concat(" Email not found.");
						break;
					case 2:
					case 3:
						s.concat(" Name not found.");
						break;
					default:
						break;
				}
				err = true;
			}
		}

		if (err)
			throw new IllegalArgumentException(s);
		return info;
	}

	public String importApplicantAnswers(String srcPath, String dstPath, PluginType type) throws IOException {
		if (srcPath == null || srcPath.isBlank())
			throw new IllegalArgumentException("Source file should neither be empty nor null.");
		if (dstPath == null || dstPath.isBlank())
			throw new IllegalArgumentException("Destination directory should neither be empty nor null.");
		File src = new File(srcPath);
		if (!src.exists() || !src.isFile())
			throw new IllegalArgumentException("Source file not found.");
		File dst = new File(dstPath);
		if (!dst.exists() || !dst.isDirectory())
			throw new IllegalArgumentException("Destination directory not found.");
		FileUtils.copyFileToDirectory(src, dst, false);

		File copiedFile = new File(dst, src.getName());

		File renamedFile;

		if (type == PluginType.JOBREQUIREMENTS) {
			renamedFile = new File(dst, src.getName().replace(".txt", "") + "_JOBREQUIREMENTS.txt");
		} else {
			renamedFile = new File(dst, src.getName().replace(".txt", "") + "_INTERVIEW.txt");
		}

		copiedFile.renameTo(renamedFile);

		return renamedFile.getName();
	}

	public String extractFileName(String filePath) {
		if (filePath == null || filePath.isBlank())
			throw new IllegalArgumentException("File should neither be empty nor null.");
		File f = new File(filePath);
		if (!f.exists() || !f.isFile())
			throw new IllegalArgumentException("File not found.");
		return f.getName();
	}

	public void removeFile(String path, String file) throws IOException {
		if (path == null || path.isBlank())
			throw new IllegalArgumentException("Path should neither be empty nor null.");
		if (file == null || file.isBlank())
			throw new IllegalArgumentException("File should neither be empty nor null.");
		String filePath = path;
		if (!(filePath.endsWith("/") || filePath.endsWith("\\")))
			filePath += File.separator;
		filePath += file;
		File src = new File(filePath);
		if (!src.exists() || !src.isFile())
			throw new IllegalArgumentException("Source file not found.");
		if (!src.delete())
			throw new IOException("Error deleting file.");
	}
}
