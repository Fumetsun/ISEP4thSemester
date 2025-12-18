package jobs4u.base.pluginhandler;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.pluginhandler.domain.RegisteredPluginFactory;

class PluginHandlerServiceTest {

	// NOTE: because of working directory testing actual output for this service
	// is difficult to achieve with the way it's currently built, so these are skipped

	private PluginHandlerService service = new PluginHandlerService();
	private final String tablePath = "src/test/java/jobs4u/base/pluginhandler/helper/table/InterviewProgrammerSymbols.txt";
	private final String answersPath = "src/test/java/jobs4u/base/pluginhandler/helper/input/InterviewProgrammerInput.txt";
	private final RegisteredPluginFactory pluginFactory = new RegisteredPluginFactory();

    void tearDown() throws IOException {
		FileUtils.deleteDirectory(new File("symbolTables"));
	}

	@Test
	void ensureAnswerNotNull() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.activateCheckerFunction(null));
		assertEquals("Source file should neither be empty nor null.", exception.getMessage());
		try {
			tearDown();
		} catch (IOException e) {
		}
	}

	@Test
	void ensureAnswerNotEmpty() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.activateCheckerFunction(""));
		assertEquals("Source file should neither be empty nor null.", exception.getMessage());
		try {
			tearDown();
		} catch (IOException e) {
		}
	}

	@Test
	void ensureAnswerFileExists() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> service.activateCheckerFunction("src/invalidDir/invalidSubDir/hello.txt"));
		assertEquals("Source file not found.", exception.getMessage());
		try {
			tearDown();
		} catch (IOException e) {
		}
	}
}