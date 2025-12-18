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

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;

@Embeddable
public class NumberOfVacancies implements ValueObject, Comparable<NumberOfVacancies> {

    private static final long serialVersionUID = 1L;

    private Integer vacancies;

    public NumberOfVacancies(final Integer vacancyQty) {
        if (vacancyQty == null || vacancyQty < 0) {
            throw new IllegalArgumentException(
                    "The vacancies shouldn't be null, empty or negative");
        }
        this.vacancies = vacancyQty;
    }

    protected NumberOfVacancies() {
        // for ORM
    }

    public static NumberOfVacancies valueOf(final Integer vacancyQtd) {
        return new NumberOfVacancies(vacancyQtd);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NumberOfVacancies)) {
            return false;
        }

        final NumberOfVacancies that = (NumberOfVacancies) o;
        return this.vacancies.equals(that.vacancies);
    }

    @Override
    public int hashCode() {
        return this.vacancies.hashCode();
    }

    @Override
    public String toString() {
        return this.vacancies.toString();
    }

    @Override
    public int compareTo(final NumberOfVacancies arg0) {
        return vacancies.compareTo(arg0.vacancies);
    }
}
