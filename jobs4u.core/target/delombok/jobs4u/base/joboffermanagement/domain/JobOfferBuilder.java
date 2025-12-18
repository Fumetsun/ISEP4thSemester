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
package jobs4u.base.joboffermanagement.domain;

import eapli.framework.domain.model.DomainFactory;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.common.domain.Address;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;

public class JobOfferBuilder implements DomainFactory<JobOffer> {
    private JobOffer offer;

    private Customer customer;
    private JobTitle title;

    private ContractType contract;

    private JobMode mode;

    private NumberOfVacancies vacancies;

    private JobDescription description;

    private Address address;

    private RegisteredPlugin interviewModel;

    private RegisteredPlugin requirementSpecification;

    private RecruitmentProcess recruitmentProcess;

    public JobOfferBuilder withCustomer(final Customer customer) {
        this.customer = customer;
        return this;
    }

    public JobOfferBuilder withJobTitle(final String title) {
        this.title = new JobTitle(title);
        return this;
    }

    public JobOfferBuilder withContractType(ContractType contract) {
        this.contract = contract;
        return this;
    }

    public JobOfferBuilder withJobMode(JobMode mode) {
        this.mode = mode;
        return this;
    }

    public JobOfferBuilder withJobOffer(JobOffer offer){
        this.offer = offer;
        return this;
    }

    public JobOfferBuilder withVacancies(final Integer vacancies) {
        this.vacancies = new NumberOfVacancies(vacancies);
        return this;
    }

    public JobOfferBuilder withDescription(final String description) {
        this.description = new JobDescription(description);
        return this;
    }

    public JobOfferBuilder withAddress(final String address) {
        this.address = new Address(address);
        return this;
    }

    public JobOfferBuilder withInterviewModel(final RegisteredPlugin interviewModel) {
        this.interviewModel = interviewModel;
        return this;
    }

    public JobOfferBuilder withRequirementSpecification(final RegisteredPlugin requirementSpecification) {
        this.requirementSpecification = requirementSpecification;
        return this;
    }

    public JobOfferBuilder withRecruitmentProcess(final RecruitmentProcess recruitmentProcess){
        this.recruitmentProcess = recruitmentProcess;
        return this;
    }

    @Override
    public JobOffer build() {
        return new JobOffer(this.customer, this.title, this.contract, this.mode,
                this.vacancies, this.description, this.address, this.interviewModel, this.requirementSpecification, this.recruitmentProcess);
    }

    public JobOffer update(RegisteredPlugin plugin){
        this.offer.setRequirements(plugin);
        return this.offer;
    }
}
