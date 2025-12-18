/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package jobs4u.base.persistence.impl.jpa;

import jobs4u.base.Application;
import jobs4u.base.applicationmanagement.repositories.JobApplicationRepository;
import jobs4u.base.candidatemanagement.repositories.CandidateRepository;
import jobs4u.base.clientusermanagement.repositories.SignupRequestRepository;
import jobs4u.base.customermanagement.repositories.CustomerRepository;
import jobs4u.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.jpa.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;
import jobs4u.base.joboffermanagement.repositories.JobOfferRepository;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import jobs4u.base.pluginhandler.repositories.RegisteredPluginsRepository;
import jobs4u.base.rankingmanagement.repositories.RankingRepository;
import jobs4u.base.recruitmentprocessmanagement.repositories.RecruitmentProcessRepository;

/**
 *
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

    @Override
    public UserRepository users(final TransactionalContext autoTx) {
        return new JpaAutoTxUserRepository(autoTx);
    }

    @Override
    public UserRepository users() {
        return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

    @Override
    public JpaClientUserRepository clientUsers(final TransactionalContext autoTx) {
        return new JpaClientUserRepository(autoTx);
    }

    @Override
    public JpaClientUserRepository clientUsers() {
        return new JpaClientUserRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext autoTx) {
        return new JpaSignupRequestRepository(autoTx);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return new JpaSignupRequestRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public JobOfferRepository jobOffers(TransactionalContext autoTx) {
        return new JpaJobOfferRepository(autoTx);
    }

    @Override
    public JobOfferRepository jobOffers() {
        return new JpaJobOfferRepository(Application.settings().getPersistenceUnitName());
    }


    public JobApplicationRepository jobApplications(TransactionalContext autoTx){
        return  new JpaJobApplicationRepository(autoTx);
    }

    @Override
    public RankingRepository rankings() {
        return new JpaRankingRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public RankingRepository rankings(TransactionalContext autoTx) {
        return null;
    }

    @Override
    public JobApplicationRepository jobApplications(){
        return  new JpaJobApplicationRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public NotificationRepository notifications(TransactionalContext autoTx) {
        return  new JpaNotificationRepository(autoTx);
    }

    @Override
    public NotificationRepository notifications() {
        return new JpaNotificationRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public CustomerRepository customers(TransactionalContext autoTx) {
        return new JpaCustomersRepository(autoTx);
    }

    @Override
    public CustomerRepository customers() {
        return new JpaCustomersRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public RegisteredPluginsRepository plugins(TransactionalContext autoTx) {
        return new JpaRegisteredPluginsRepository(autoTx);
    }

    @Override
    public RegisteredPluginsRepository plugins() {
        return new JpaRegisteredPluginsRepository(Application.settings().getPersistenceUnitName());
    }

    @Override
    public RecruitmentProcessRepository processes(TransactionalContext autoTx) {
        return new JpaRecruitmentProcessRepository(autoTx);
    }

    @Override
    public RecruitmentProcessRepository processes() {
        return new JpaRecruitmentProcessRepository(Application.settings().getPersistenceUnitName());
    }


    @Override
	public CandidateRepository candidate(TransactionalContext autoTx) {
		return new JpaCandidateRepository(autoTx);
	}

	@Override
	public CandidateRepository candidate() {
		return new JpaCandidateRepository(Application.settings().getPersistenceUnitName());
	}

    @Override
    public TransactionalContext newTransactionalContext() {
        return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
                Application.settings().getExtendedPersistenceProperties());
    }

}
