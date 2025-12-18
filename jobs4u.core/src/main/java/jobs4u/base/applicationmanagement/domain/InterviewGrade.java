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
package jobs4u.base.applicationmanagement.domain;

import eapli.framework.domain.model.ValueObject;
import jakarta.persistence.Embeddable;

@Embeddable
public class InterviewGrade implements ValueObject, Comparable<InterviewGrade> {

    private static final long serialVersionUID = 1L;

    private Integer grade;

    public InterviewGrade(final Integer grade) {
        if (grade == null || (grade < 0 && grade != -1)) {
            throw new IllegalArgumentException(
                    "The grade shouldn't be null, empty or negative");
        }
        this.grade = grade;
    }

    protected InterviewGrade() {
        // for ORM
    }

    public static InterviewGrade valueOf(final Integer grade) {
        return new InterviewGrade(grade);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterviewGrade)) {
            return false;
        }

        final InterviewGrade that = (InterviewGrade) o;
        return this.grade.equals(that.grade);
    }

    @Override
    public int hashCode() {
        return this.grade.hashCode();
    }

    @Override
    public String toString() {
        return this.grade.toString();
    }

    @Override
    public int compareTo(final InterviewGrade arg0) {
        return grade.compareTo(arg0.grade);
    }

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade() {
		return this.grade;
	}
}
