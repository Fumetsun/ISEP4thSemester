package jobs4u.base.common;

import jobs4u.base.pluginhandler.domain.PluginType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

class ImportFileServiceTest {
	private ImportFileService service = new ImportFileService();
	private final String srcFile = "src/test/java/jobs4u/base/common/helper/source/testFile.txt"; //path from content root
	private final String dstPath = "src/test/java/jobs4u/base/common/helper/dest/";
	private final String fileTest = "src/test/java/jobs4u/base/common/helper/dest/testFile.txt";
	private final String fl = "testFile.txt";

	@Test
	void ensureImportSrcNotNull() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.importApplicantAnswers(null, dstPath, PluginType.INTERVIEW));
		assertEquals("Source file should neither be empty nor null.", exception.getMessage());
	}

	@Test
	void ensureImportSrcNotEmpty() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.importApplicantAnswers("", dstPath, PluginType.INTERVIEW));
		assertEquals("Source file should neither be empty nor null.", exception.getMessage());
	}

	@Test
	void ensureImportSrcExists() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.importApplicantAnswers("helper/src/testFile.txt", dstPath, PluginType.INTERVIEW));
		assertEquals("Source file not found.", exception.getMessage());
	}

	@Test
	void ensureImportDstNotNull() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.importApplicantAnswers(srcFile, null, PluginType.INTERVIEW));
		assertEquals("Destination directory should neither be empty nor null.", exception.getMessage());
	}

	@Test
	void ensureImportDstNotEmpty() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.importApplicantAnswers(srcFile, "", PluginType.INTERVIEW));
		assertEquals("Destination directory should neither be empty nor null.", exception.getMessage());
	}

	@Test
	void ensureImportDstExists() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.importApplicantAnswers(srcFile, "helper/dst", PluginType.INTERVIEW));
		assertEquals("Destination directory not found.", exception.getMessage());
	}

	@Test
	void ensureImportSuccessfulCopy() throws IOException {
		service.importApplicantAnswers(srcFile, dstPath, PluginType.INTERVIEW);
		File f = new File("src/test/java/jobs4u/base/common/helper/dest/testFile_INTERVIEW.txt");
		assertTrue(f.exists() && f.isFile());
		f.delete();
	}

	@Test
	void ensureExtractNotNull() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.extractFileName(null));
		assertEquals("File should neither be empty nor null.", exception.getMessage());
	}

	@Test
	void ensureExtractNotEmpty() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.extractFileName(""));
		assertEquals("File should neither be empty nor null.", exception.getMessage());
	}
	@Test

	void ensureExtractExists() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.extractFileName("helper/src/testFile.txt"));
		assertEquals("File not found.", exception.getMessage());
	}
	
	@Test
	void ensureExtractSuccessful() {
		assertEquals("testFile.txt", service.extractFileName(srcFile));
	}

	@Test
	void ensureRemovePathNotNull() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.removeFile(null, fl));
		assertEquals("Path should neither be empty nor null.", exception.getMessage());
	}

	@Test
	void ensureRemovePathNotEmpty() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.removeFile("", fl));
		assertEquals("Path should neither be empty nor null.", exception.getMessage());
	}


	@Test
	void ensureRemoveFileNotNull() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.removeFile(dstPath, null));
		assertEquals("File should neither be empty nor null.", exception.getMessage());
	}

	@Test
	void ensureRemoveFileNotEmpty() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.removeFile(dstPath, ""));
		assertEquals("File should neither be empty nor null.", exception.getMessage());
	}

	@Test
	void ensureRemoveFileExists() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.removeFile(dstPath, fl));
		assertEquals("Source file not found.", exception.getMessage());
	}

	@Test
	void ensureCorrectRemoval() {
		File src = new File(fileTest);
		try {
			src.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			service.removeFile(dstPath, fl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertFalse(src.exists());
	}
}
