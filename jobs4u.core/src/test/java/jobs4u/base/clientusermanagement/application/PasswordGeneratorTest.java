package jobs4u.base.clientusermanagement.application;

import org.junit.Test;
import static org.junit.Assert.*;

public class PasswordGeneratorTest {

    @Test
    public void testValidLengthWithAllCategories() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .usePunctuation(true)
                .build();

        String password = passwordGenerator.generate(10);
        assertEquals(10, password.length());
        assertTrue(password.matches("[a-zA-Z0-9!\"#$%&'()*+,-./:;<=>?@\\[\\\\\\]^_`{|}~]+"));

    }

    @Test
    public void testValidLengthWithOnlyLowercaseCharacters() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useLower(true)
                .build();
        String password = passwordGenerator.generate(8);
        assertEquals(8, password.length());
        assertTrue(password.matches("[a-z]+"));
    }

    @Test
    public void testValidLengthWithOnlyUppercaseCharacters() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useUpper(true)
                .build();
        String password = passwordGenerator.generate(6);
        assertEquals(6, password.length());
        assertTrue(password.matches("[A-Z]+"));
    }

    @Test
    public void testValidLengthWithOnlyDigits() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .build();
        String password = passwordGenerator.generate(12);
        assertEquals(12, password.length());
        assertTrue(password.matches("[0-9]+"));
    }
    @Test
    public void testInvalidLengthZero() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .usePunctuation(true)
                .build();
        String password = passwordGenerator.generate(0);
        assertEquals("", password);
    }

    @Test
    public void testInvalidLengthNegative() {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .usePunctuation(true)
                .build();
        String password = passwordGenerator.generate(-5);
        assertEquals("", password);
    }
}
