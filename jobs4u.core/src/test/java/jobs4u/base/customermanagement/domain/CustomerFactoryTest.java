package jobs4u.base.customermanagement.domain;

import jobs4u.base.common.domain.Address;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerFactoryTest {

    /**
     * Verifies if the customerCode filtration is working correctly [Successful Test].
     */
    @Test
    void customerCodeFilteringSuccessful(){
        String code = "TEST";
        CustomerCode customerCode = new CustomerCode(code);

        assertEquals(code, customerCode.toString());
    }

    /**
     * Verifies if the customerCode filtration is working correctly [Unsuccessful Test Lowercase - Allows to check custom error message].
     */
    @Test
    void customerCodeFilteringUnsuccessful_Lowercase(){
        String code = "test";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CustomerCode customerCode = new CustomerCode(code);;
        });

        System.out.println("=== Error message: ===\n" + exception.getMessage());
    }

    /**
     * Verifies if the customerCode filtration is working correctly [Unsuccessful Test Limit - Allows to check custom error message].
     */
    @Test
    void customerCodeFilteringUnsuccessful_Limit(){
        String code = "123456789DEZ";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CustomerCode customerCode = new CustomerCode(code);;
        });

        System.out.println("=== Error message: ===\n" + exception.getMessage());
    }

    /**
     * Verifies if the customerName filtration is working correctly [Successful Test].
     */
    @Test
    void customerNameFilteringSuccessful(){
        String name = "Test";
        CustomerName customerName = new CustomerName(name);

        assertEquals(name, customerName.toString());
    }

    /**
     * Verifies if the customerName filtration is working correctly [Unsuccessful Test Capital - Allows to check custom error message].
     */
    @Test
    void customerNameFilteringUnsuccessful_CapitalLetters(){
        String name = "test";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CustomerName customerName = new CustomerName(name);;
        });

        System.out.println("=== Error message: ===\n" + exception.getMessage());
    }

    /**
     * Verifies if the customerName filtration is working correctly [Unsuccessful Test Numbers - Allows to check custom error message].
     */
    @Test
    void customerNameFilteringUnsuccessful_Numbers(){
        String name = "1234";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            CustomerName customerName = new CustomerName(name);;
        });

        System.out.println("=== Error message: ===\n" + exception.getMessage());
    }

    /**
     * Verifies if the address filtration is working correctly [Successful Test].
     */
    @Test
    void addressFilteringSuccessful(){
        String address = "Test";
        Address customerAddress = new Address(address);

        assertEquals(address, customerAddress.toString());
    }

    /**
     * Verifies if the address filtration is working correctly [Unsuccessful Test Empty - Allows to check custom error message].
     */
    @Test
    void addressFilteringUnsuccessful_Empty(){
        String address = "";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Address customerAddress = new Address(address);;
        });

        System.out.println("=== Error message: ===\n" + exception.getMessage());
    }

}