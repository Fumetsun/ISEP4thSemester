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
package jobs4u.base.persistence.impl.inmemory;

import jobs4u.base.applicationmanagement.repositories.JobApplicationRepository;
import jobs4u.base.candidatemanagement.repositories.CandidateRepository;
import jobs4u.base.clientusermanagement.repositories.ClientUserRepository;
import jobs4u.base.clientusermanagement.repositories.SignupRequestRepository;
import jobs4u.base.customermanagement.repositories.CustomerRepository;
import jobs4u.base.infrastructure.bootstrapers.BaseBootstrapper;
import jobs4u.base.infrastructure.persistence.RepositoryFactory;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.inmemory.InMemoryUserRepository;
import jobs4u.base.joboffermanagement.repositories.JobOfferRepository;
import jobs4u.base.notificationmanagement.repositories.NotificationRepository;
import jobs4u.base.pluginhandler.repositories.RegisteredPluginsRepository;
import jobs4u.base.rankingmanagement.repositories.RankingRepository;
import jobs4u.base.recruitmentprocessmanagement.repositories.RecruitmentProcessRepository;

/**
 *
 * Created by nuno on 20/03/16.
 */
public class InMemoryRepositoryFactory implements RepositoryFactory {

    static {
        // only needed because of the in memory persistence
        new BaseBootstrapper().execute();
    }

    @Override
    public UserRepository users(final TransactionalContext tx) {
        return new InMemoryUserRepository();
    }

    @Override
    public UserRepository users() {
        return users(null);
    }

    @Override
    public ClientUserRepository clientUsers(final TransactionalContext tx) {

        return new InMemoryClientUserRepository();
    }

    @Override
    public ClientUserRepository clientUsers() {
        return clientUsers(null);
    }

    @Override
    public SignupRequestRepository signupRequests() {
        return signupRequests(null);
    }

    @Override
    public JobOfferRepository jobOffers(TransactionalContext autoTx) {
        return new InMemoryJobOfferRepository();
    }

    @Override
    public JobOfferRepository jobOffers() {
        return jobOffers(null);
    }

    @Override
    public JobApplicationRepository jobApplications(TransactionalContext autoTx) {
        return new InMemoryJobApplicationRepository();
    }

    @Override
    public RankingRepository rankings() {
        return rankings(null);
    }

    @Override
    public RankingRepository rankings(TransactionalContext autoTx) {
        return new InMemoryRankingRepository();
    }

    @Override
    public JobApplicationRepository jobApplications() {
        return jobApplications(null);
    }

    @Override
    public NotificationRepository notifications(TransactionalContext autoTx) {
        return new InMemoryNotificationRepository();
    }

    @Override
    public NotificationRepository notifications() {
        return notifications(null);
    }

    @Override
    public CustomerRepository customers(TransactionalContext autoTx) {
        return new InMemoryCustomerRepository();
    }

    @Override
    public CustomerRepository customers() {
        return customers(null);
    }

	@Override
	public CandidateRepository candidate(TransactionalContext autoTx) {
		return new InMemoryCandidateRepository();
	}

	@Override
	public CandidateRepository candidate() {
		return candidate(null);
	}

    public RegisteredPluginsRepository plugins(TransactionalContext autoTx) {
        return new InMemoryRegisteredPluginsRepository();
    }

    @Override
    public RegisteredPluginsRepository plugins() {
        return plugins(null);
    }

    @Override
    public RecruitmentProcessRepository processes(TransactionalContext autoTx) {
        return null;
    }

    @Override
    public RecruitmentProcessRepository processes() {
        return null;
    }

    @Override
    public SignupRequestRepository signupRequests(final TransactionalContext tx) {
        return new InMemorySignupRequestRepository();
    }

    @Override
    public TransactionalContext newTransactionalContext() {
        // in memory does not support transactions...
        return null;
    }

}
