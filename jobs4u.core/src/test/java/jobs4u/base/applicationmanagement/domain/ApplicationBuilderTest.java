package jobs4u.base.applicationmanagement.domain;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationBuilderTest {
    private ApplicationBuilder applicationBuilder;
    private ApplicationEmail email;
    private ApplicationFilesPath filesPath;
    private ApplicationAttachedFile attachedFile;

    /**
     * Test SetUp
     */
    public void setUp() {
        applicationBuilder = new ApplicationBuilder();
        email = new ApplicationEmail("blah blah blah \n\n blah blah blah");
        filesPath = new ApplicationFilesPath("path/to/files/");
        attachedFile = new ApplicationAttachedFile("resume.pdf");

    }

    /**
     * Test email is built correctly
     */
    @Test
    void withEmail() {
        setUp();
        applicationBuilder.withEmail(email);
        assertEquals(email, applicationBuilder.getEmail());
    }

    /**
     * Test path is built correctly under right conditions
     */
    @Test
    void withFilesPath() {
        setUp();
        applicationBuilder.withFilesPath(filesPath);
        assertEquals(filesPath, applicationBuilder.getFilesPath());
    }

    /**
     * Test malicious path is handled
     */
    @Test
    void withFilesPathFake(){
        setUp();
        try{
            filesPath = new ApplicationFilesPath("no");
            applicationBuilder.withFilesPath(filesPath);
            assertEquals(filesPath, applicationBuilder.getFilesPath());
        }catch (Exception e){
            assertEquals(e.getMessage(), "[ERROR] - Not a valid directory.");
        }
    }

    /**
     * Test attached file is built correctly
     */
    @Test
    void withAttachedFile() {
        setUp();
        applicationBuilder.withAttachedFile(attachedFile);
        assertEquals(attachedFile, applicationBuilder.getAttachedFile());
    }

    /**
     * Check if malicious Build is handled
     */
    @Test
    public void buildWithMissingData() {
        setUp();
        applicationBuilder.withEmail(email)
                .withFilesPath(filesPath)
                .withAttachedFile(attachedFile);
        try {
            applicationBuilder.build();
        } catch (IllegalStateException e) {
            // Verify that the exception message is correct
            assertEquals("Application Builder Failed!", e.getMessage());
            return;
        }
        // If the exception is not thrown, fail the test
        fail("Expected IllegalStateException was not thrown");

    }
}