package jobs4u.base.pluginhandler.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisteredPluginFactoryTest {

    /**
     * Verifies if the PluginFileName filtration is working correctly [Successful Test].
     */
    /*@Test
    void pluginFileNameFilteringSuccessful(){
        String fileName = "tEsT123.jar";
        PluginFilePath pluginFilepATH = new PluginFilePath(fileName);

        assertEquals(fileName, pluginFilepATH.toString());
    }

    *//**
     * Verifies if the PluginFileName filtration is working correctly [Unsuccessful Without JAR - Allows to check custom error message].
     *//*
    @Test
    void pluginFileNameFilteringUnsuccessful_WithoutJAR(){
        String fileName = "test";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PluginFilePath pluginFilepATH = new PluginFilePath(fileName);;
        });

        System.out.println("=== Error message: ===\n" + exception.getMessage());
    }

    *//**
     * Verifies if the PluginFileName filtration is working correctly [Unsuccessful With JAR - Allows to check custom error message].
     *//*
    @Test
    void pluginFileNameFilteringUnsuccessful_WithJAR(){
        String fileName = "test.JAR";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PluginFilePath pluginFilepATH = new PluginFilePath(fileName);;
        });

        System.out.println("=== Error message: ===\n" + exception.getMessage());
    }

    *//**
     * Verifies if the PluginName filtration is working correctly [Successful Test].
     *//*
    @Test
    void pluginNameFilteringSuccessful(){
        String name = "Test";
        PluginName pluginName = new PluginName(name);

        assertEquals(name, pluginName.toString());
    }

    *//**
     * Verifies if the PluginName filtration is working correctly [Unsuccessful Test Capital Letter - Allows to check custom error message].
     *//*
    @Test
    void pluginNameFilteringUnsuccessful_CapitalLetter(){
        String name = "test";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PluginName pluginFileName = new PluginName(name);;
        });

        System.out.println("=== Error message: ===\n" + exception.getMessage());
    }

    *//**
     * Verifies if the PluginName filtration is working correctly [Unsuccessful Number - Allows to check custom error message].
     *//*
    @Test
    void pluginNameFilteringUnsuccessful_Numbers(){
        String name = "1234";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            PluginName pluginFileName = new PluginName(name);;
        });

        System.out.println("=== Error message: ===\n" + exception.getMessage());
    }*/


}