package jobs4u.base.candidatemanagement.domain;

import eapli.framework.domain.model.ValueObject;
import eapli.framework.strings.util.StringPredicates;
import jakarta.persistence.Embeddable;

@Embeddable
public class PhoneNumber implements ValueObject, Comparable<PhoneNumber> {

	private static final long serialVersionUID = 1L;

	private String number;

	public PhoneNumber(final String number) {
		if (StringPredicates.isNullOrEmpty(number)) {
			throw new IllegalArgumentException(
					"Phone Number should neither be null nor empty");
		} else if (number.length() != 9) {
			throw new IllegalArgumentException("Phone number must be 9 digits");
		} else if (number.charAt(0) != '9') {
			throw new IllegalArgumentException("Phone number must start with 9");
		} else {
			try {
				@SuppressWarnings("unused")
				int num = Integer.parseInt(number);
			} catch (Exception e) {
				throw new IllegalArgumentException("Phone number must consist only of numbers");
			}
		}
		this.number = number;
	}

	protected PhoneNumber() {
		// for ORM
	}

	public static PhoneNumber valueOf(final String number) {
		return new PhoneNumber(number);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PhoneNumber)) {
			return false;
		}

		final PhoneNumber that = (PhoneNumber) o;
		return this.number.equals(that.number);
	}

	@Override
	public int hashCode() {
		return this.number.hashCode();
	}

	@Override
	public String toString() {
		return this.number;
	}

	@Override
	public int compareTo(final PhoneNumber arg0) {
		return number.compareTo(arg0.number);
	}
}
