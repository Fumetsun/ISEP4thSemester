package jobs4u.base.notificationmanagement.application;

import jobs4u.base.applicationmanagement.JobApplicationManagementService;
import jobs4u.base.applicationmanagement.domain.JobApplication;
import jobs4u.base.notificationmanagement.EmailNotificationHandler;

public class CandidateNotificationController {

    JobApplicationManagementService jobAppSvc = new JobApplicationManagementService();


    public Iterable<JobApplication> getMyCandidates() {
        return jobAppSvc.getMyCandidates();
    }

    public void sendEmail(JobApplication jobApplication, String password) {
        new Thread(new EmailNotificationHandler(jobApplication,password)).start();
    }
}
