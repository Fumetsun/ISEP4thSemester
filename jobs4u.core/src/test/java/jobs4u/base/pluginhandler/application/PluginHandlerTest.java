//package jobs4u.base.pluginhandler.application;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PluginHandlerTest {
//
//    /**
//     * Verifies if the Handler can activate the Template Function [Successful Test].
//     */
//    @Test
//    void activateTemplateFunctionSuccessfully() {
//        PluginHandlerService handler = new PluginHandlerService();
//
//        assertTrue(handler.activateTemplateFunction());
//
//    }
//
//    /**
//     * Verifies if the Handler can activate the Template Function [Unsuccessful Test - Incorrect Path].
//     */
//    @Test
//    void activateTemplateFunctionUnsuccessfully_IncorrectPath() {
//        PluginHandlerService handler = new PluginHandlerService();
//
//        int exitCode = handler.activateTemplateFunction("lib/InterviewPlugin-Lifeguard.jar", "C:\\Users\\Utilizador\\ISEP\\2_Ano\\2_Semestre\\SEM4PI\\sem4pi-23-24-2dg1\\exportedfiles\\");
//
//        assertEquals(1,exitCode);
//    }
//
//    /**
//     * Verifies if the Handler can activate the Evaluation Function [Successful Test].
//     */
//    @Test
//    void activateEvaluationFunctionSuccessfully() {
//        PluginHandlerService handler = new PluginHandlerService();
//
//        int exitCode = handler.activateEvaluationFunctions("C:\\Users\\Utilizador\\ISEP\\2_Ano\\2_Semestre\\SEM4PI\\sem4pi-23-24-2dg1\\lib\\InterviewPlugin-Lifeguard.jar", "C:\\Users\\Utilizador\\ISEP\\2_Ano\\2_Semestre\\SEM4PI\\sem4pi-23-24-2dg1\\plugins\\InterviewModelLifeguard\\InterviewPlugin-Lifeguard\\templates\\LifeGuardInput.txt");
//
//        assertEquals(1,exitCode);
//    }
//
//    /**
//     * Verifies if the Handler can activate the Evaluation Function [Unsuccessful Test - Incorrect Path].
//     */
//    @Test
//    void activateEvaluationFunctionUnsuccessfully_IncorrectPath() {
//        PluginHandlerService handler = new PluginHandlerService();
//
//        int exitCode = handler.activateEvaluationFunctions("lib/InterviewPlugin-Lifeguard.jar", "C:\\Users\\Utilizador\\ISEP\\2_Ano\\2_Semestre\\SEM4PI\\sem4pi-23-24-2dg1\\plugins\\InterviewModelLifeguard\\InterviewPlugin-Lifeguard\\templates\\LifeGuardInput.txt");
//
//        assertEquals(1,exitCode);
//    }
//
//
//}