/*
 * Copyright (c) 2013-2024 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package jobs4u.base.joboffermanagement.domain;

import eapli.framework.domain.model.AggregateRoot;
import eapli.framework.domain.model.DomainEntities;
import eapli.framework.representations.dto.DTOable;
import jakarta.persistence.*;
import jobs4u.base.common.domain.Address;
import jobs4u.base.customermanagement.domain.Customer;
import jobs4u.base.joboffermanagement.domain.dto.JobOfferDto;
import jobs4u.base.pluginhandler.domain.RegisteredPlugin;
import jobs4u.base.recruitmentprocessmanagement.domain.RecruitmentProcess;

import java.io.Serializable;

@Entity
@SuppressWarnings("unused")
public class JobOffer implements AggregateRoot<JobRefCode>, Serializable, DTOable<JobOfferDto> {
    @EmbeddedId
    private JobRefCode jobRefCode = new JobRefCode();

    private JobTitle title;

    private ContractType contract;

    private JobMode mode;

    private NumberOfVacancies vacancies;

    private JobDescription description;

    private Address address;

    @ManyToOne
    private Customer customer;


    @ManyToOne(
            optional = true
    )
    private RegisteredPlugin interviewModel;

    @ManyToOne(
            optional = true
    )
    private RegisteredPlugin requirements;

    @OneToOne(cascade = CascadeType.ALL)
    private RecruitmentProcess recruitmentProcess;

    public JobOffer(final Customer customer, final JobTitle title, final ContractType contract,
                    final JobMode mode, final NumberOfVacancies vacancies, final JobDescription description, final Address address,
                    final RegisteredPlugin interviewModel, final RegisteredPlugin requirementSpecification, RecruitmentProcess recruitmentProcess) {
        this.customer = customer;
        this.title = title;
        this.contract = contract;
        this.mode = mode;
        this.vacancies = vacancies;
        this.description = description;
        this.address = address;
        this.interviewModel = interviewModel;
        this.requirements = requirementSpecification;
        this.recruitmentProcess = recruitmentProcess;
    }

    public JobOffer(final Customer customer, final JobTitle title, final ContractType contract,
                    final JobMode mode, final NumberOfVacancies vacancies, final JobDescription description, final Address address, RecruitmentProcess recruitmentProcess) {
        this.customer = customer;
        this.title = title;
        this.contract = contract;
        this.mode = mode;
        this.vacancies = vacancies;
        this.description = description;
        this.address = address;
        this.recruitmentProcess = recruitmentProcess;
    }

    public JobOffer() {
        // for ORM only
    }


    @Override
    public boolean equals(final Object o) {
        return DomainEntities.areEqual(this, o);
    }

    @Override
    public int hashCode() {
        return DomainEntities.hashCode(this);
    }

    @Override
    public boolean sameAs(final Object other) {
        return DomainEntities.areEqual(this, other);
    }

    public NumberOfVacancies vacancies(){ return this.vacancies; }

    public Customer customer() {
        return this.customer;
    }

    public JobRefCode reference() {
        return identity();
    }

    public JobTitle title(){return this.title;}

    public RecruitmentProcess recruitmentProcess() {
        return recruitmentProcess;
    }

    @Override
    public JobRefCode identity() {
        return this.jobRefCode;
    }

    @Override
    public JobOfferDto toDTO() {
        return new JobOfferDto(this.jobRefCode.toString(), this.title.toString(), this.mode.toString(), this.vacancies.toString(), this.recruitmentProcess);
    }

    public void setJobRefCode(Integer a) {
        this.jobRefCode = new JobRefCode(a);
    }

    @Override
    public String toString() {
        return "Job Title: " + this.title + " | Ref Code: " + this.jobRefCode.getRef() + "\n" + this.interviewModel + "\n" + this.requirements;
    }

    public void setRequirements(RegisteredPlugin requirements) {
        this.requirements = requirements;
    }

    public boolean sameCustomer(Customer customer) {
        return this.customer.equals(customer);
    }

    public RegisteredPlugin getInterview() {
        return this.interviewModel;
    }

    public RegisteredPlugin requirements() {
        return this.requirements;
    }
}
