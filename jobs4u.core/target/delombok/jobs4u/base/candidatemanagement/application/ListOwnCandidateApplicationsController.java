package jobs4u.base.candidatemanagement.application;

import jobs4u.base.candidatemanagement.ServerCandidateApplicationHandler;
import jobs4u.base.notificationmanagement.domain.MessageDTO;

public class ListOwnCandidateApplicationsController {

    public Iterable<MessageDTO> listOwnApplications(String password) {
        ServerCandidateApplicationHandler handler = new ServerCandidateApplicationHandler(password);
        return handler.getCandidateOwnApplications();
    }
}
