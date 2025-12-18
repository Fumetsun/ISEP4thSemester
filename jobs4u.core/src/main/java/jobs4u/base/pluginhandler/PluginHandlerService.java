package jobs4u.base.pluginhandler;

import java.io.File;
import java.io.IOException;
// import java.io.BufferedReader;
// import java.io.InputStream;
// import java.io.InputStreamReader;

import jobs4u.base.pluginhandler.domain.RegisteredPlugin;

public class PluginHandlerService {

	// private static final String pluginFilePath = "lib/JobApplication_ManagerPlugin.jar";
	private static final String pluginDir = "lib";
	private static final String pluginName = "JobApplication_Manager.jar";

	/**
	 * Activates the "Template" plugin function. It returns the final grade.
	 *
	 * @param symbolTable  -> Symbol Table Path
	 * @param outputFolder -> Output Folder All-Path
	 * @param type         -> 1 - Requirements / 2 - Interview
	 * @return boolean -> Success of the operation
	 */
	public boolean activateTemplateFunction(String symbolTable, String outputFolder, int type) {
		int exitCode;

		if (type == 1) {
			exitCode = activatePlugin("-template", "-requirements", symbolTable, outputFolder);
		} else {
			exitCode = activatePlugin("-template", "-interview", symbolTable, outputFolder);
		}

		return exitCode == 0;
	}

	/**
	 * Activates the "Evaluate" plugin function. It returns the final grade.
	 *
	 * @param symbolTable    -> Symbol Table Path
	 * @param candidateInput -> Candidate Input All-Path
	 * @param type           -> 1 - Requirements / 2 - Interview
	 * @return int -> grade
	 */
	public int activateEvaluationFunction(String symbolTable, String candidateInput, int type) {
		int grade;

		if (type == 1) {
			grade = activatePlugin("-evaluate", "-requirements", symbolTable, candidateInput);
		} else {
			grade = activatePlugin("-evaluate", "-interview", symbolTable, candidateInput);
			System.out.println("Evaluation Output: " + grade);
		}

		return grade;
	}

	private int activatePlugin(String action, String type, String symbolTable, String candidateInput) {
		try {
			String filePath = pluginDir + File.separator + pluginName;
			String[] command = { "java", "-jar", filePath, action, type, symbolTable, candidateInput };

			ProcessBuilder processBuilder = new ProcessBuilder(command);
			Process process = processBuilder.start();

			int exitCode = process.waitFor();
			if (action.equalsIgnoreCase("-evaluate")) {
				if (type.equalsIgnoreCase("-requirements")){
					System.out.println("[Plugin Executed] Requirements Validation command executed. Process exited with code: " + exitCode);
				} else {
					System.out.println("[Plugin Executed] Interview Evaluation command executed. Process exited with code: " + exitCode);
				}
			} else {
				System.out.println("[Plugin Executed] Template command executed. Process exited with code: " + exitCode);
			}
			return exitCode;

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean activateCheckerFunction(String answersFile) {
		if (answersFile == null || answersFile.isBlank())
			throw new IllegalArgumentException("Source file should neither be empty nor null.");
		File src = new File(answersFile);
		if (!src.exists() || !src.isFile())
			throw new IllegalArgumentException("Source file not found.");
		return activatePluginValidate(answersFile);
	}

	private boolean activatePluginValidate(String answers) {
		int exitCode = 0;
		try {
			String filePath = pluginDir + File.separator + pluginName;
			String[] command = { "java", "-jar", filePath, "-validate", answers };
			ProcessBuilder processBuilder = new ProcessBuilder(command);
			Process process = processBuilder.start();

			exitCode = process.waitFor();
			System.out.println("Validate command executed. Process exited with code: " + exitCode);

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		if (exitCode != 1)
			return false;
		return true;
	}
}