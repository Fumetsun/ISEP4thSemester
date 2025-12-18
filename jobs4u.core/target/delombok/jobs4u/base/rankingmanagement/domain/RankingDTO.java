package jobs4u.base.rankingmanagement.domain;

public class RankingDTO {

    private Integer jobRefCode;

    private Integer appRefCode;
    private String applicationInfo;
    private Integer placement;

    // Default constructor
    public RankingDTO() {
    }

    // Parameterized constructor
    public RankingDTO(Integer jobRefCode, Integer appRefCode, String applicationInfo, Integer placement) {
        this.jobRefCode = jobRefCode;
        this.appRefCode = appRefCode;
        this.applicationInfo = applicationInfo;
        this.placement = placement;
    }

    // Getters and Setters
    public Integer getJobRefCode() {
        return jobRefCode;
    }

    public void setJobRefCode(Integer jobRefCode) {
        this.jobRefCode = jobRefCode;
    }



    public Integer getAppRefCode() {
        return appRefCode;
    }

    public void setAppRefCode(Integer appRefCode) {
        this.appRefCode = appRefCode;
    }

    public String getApplicationInfo() {
        return applicationInfo;
    }

    public void setApplicationInfo(String applicationInfo) {
        this.applicationInfo = applicationInfo;
    }

    public Integer getPlacement() {
        return placement;
    }

    public void setPlacement(Integer placement) {
        this.placement = placement;
    }

    // Optional: toString method for debugging purposes
    @Override
    public String toString() {
        return
                "Placement=" + placement +
                "| jobRefCode=" + jobRefCode +
                "| appRefCode=" + appRefCode +
                "|" + applicationInfo
                ;
    }
}
