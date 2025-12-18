package jobs4u.base.candidatemanagement.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

	@Test
	void phoneNumberNull() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new PhoneNumber(null));
		assertEquals("Phone Number should neither be null nor empty", exception.getMessage());
	}

	@Test
	void phoneNumberEmpty() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new PhoneNumber(""));
		assertEquals("Phone Number should neither be null nor empty", exception.getMessage());
	}

	@Test
	void incorrectPhoneNumberLength() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new PhoneNumber("9123"));
		assertEquals("Phone number must be 9 digits", exception.getMessage());
	}

	@Test
	void incorrectPhoneNumberStart() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new PhoneNumber("123456789"));
		assertEquals("Phone number must start with 9", exception.getMessage());
	}

	@Test
	void incorrectPhoneNumberFormat() {
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> new PhoneNumber("92a456789"));
		assertEquals("Phone number must consist only of numbers", exception.getMessage());
	}

	@Test
	void phoneNumberSavedCorrectly() {
		String sNum = "999999999";
		PhoneNumber num =  new PhoneNumber(sNum);
		assertEquals(sNum, num.toString());
	}
}
