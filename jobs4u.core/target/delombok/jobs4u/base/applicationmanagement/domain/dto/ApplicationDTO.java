package jobs4u.base.applicationmanagement.domain.dto;

public class ApplicationDTO {

    private int refCode;
    private String email;
    private String filesPath;
    private String attachedFile;
    private int jobRefCode;
    private String candidate;
    private String state;
    private String grade;

    public ApplicationDTO(int refCode, String email, String filesPath, String attachedFile, int jobRefCode,
                          String candidate, String grade, String state) {
        this.refCode = refCode;
        this.email = email;
        this.filesPath = filesPath;
        this.attachedFile = attachedFile;
        this.jobRefCode = jobRefCode;
        this.candidate = candidate;
        this.grade = grade;
        this.state = state;
    }

    public String toString() {
        return String.format(
                "Application details:\n\t- Ref Code: %d\n\t- Files path: %s\n\t- Attached files: \n\t\t%s\t- Job code: %d\n\t- Candidate: %s\n\t- State: %s\n\t- Grade: %s\n",
                refCode, filesPath, attachedFile, jobRefCode, candidate, state, grade);
    }

    public int getRefCode() {
        return this.refCode;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFilesPath() {
        return this.filesPath;
    }

    public String getAttachedFile() {
        return this.attachedFile;
    }

    public int getJobRefCode() {
        return this.jobRefCode;
    }

    public String getCandidate() {
        return this.candidate;
    }

    public String getState() {
        return state;
    }
}
