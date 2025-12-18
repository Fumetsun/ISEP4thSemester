package jobs4u.base.candidatemanagement.domain.dto;

public class CandidateDto {
    private String phoneNumber;
    private String userInfo;

    public CandidateDto(String phoneNumber, String userInfo) {
        this.phoneNumber = phoneNumber;
        this.userInfo = userInfo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUserInfo() {
        return userInfo;
    }
}
