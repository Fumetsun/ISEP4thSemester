package jobs4u.base.notificationmanagement.domain;

import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import jobs4u.base.usermanagement.domain.BaseRoles;
import jobs4u.base.usermanagement.domain.UserBuilderHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class NotificationFactoryTest {

    @Test
    void assertCandidateNotNull() {
        final SystemUserBuilder userBuilder = UserBuilderHelper.builder();
        userBuilder.withUsername("johndoe@email.org").withPassword("John!Password6").withName("John", "Doe")
                .withEmail("johndoe@email.com").withRoles(BaseRoles.CUSTOMER_MANAGER);

        SystemUser customerManager = userBuilder.build();
        NotificationTypes notifType = NotificationTypes.EMAIL;

        NotificationFactory factory = new NotificationFactory();

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            factory.newNotification(null, customerManager,"text", notifType);
        });
    }

    @Test
    void assertCustomerManagerNotNull() {
        final SystemUserBuilder userBuilder = UserBuilderHelper.builder();
        userBuilder.withUsername("johndoe@email.org").withPassword("John!Password6").withName("John", "Doe")
                .withEmail("johndoe@email.com").withRoles(BaseRoles.CANDIDATE);

        SystemUser candidate = userBuilder.build();
        NotificationTypes notifType = NotificationTypes.EMAIL;

        NotificationFactory factory = new NotificationFactory();

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            factory.newNotification(candidate, null,"text", notifType);
        });
    }

    @Test
    void assertNotificationTypeNotNull() {
        final SystemUserBuilder userBuilder = UserBuilderHelper.builder();
        userBuilder.withUsername("johndoe@email.org").withPassword("John!Password6").withName("John", "Doe")
                .withEmail("johndoe@email.com").withRoles(BaseRoles.CUSTOMER_MANAGER);

        SystemUser customerManager = userBuilder.build();

        userBuilder.withUsername("johndoe@email.org").withPassword("John!Password6").withName("John", "Doe")
                .withEmail("johndoe@email.com").withRoles(BaseRoles.CANDIDATE);

        SystemUser candidate = userBuilder.build();


        NotificationFactory factory = new NotificationFactory();

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            factory.newNotification(candidate, customerManager,"text", null);
        });
    }

    @Test
    void assertBuildWithoutSettingAttributesThrows() {
        // Arrange
        NotificationFactory factory = new NotificationFactory();

        // Act & Assert
        IllegalStateException thrown = assertThrows(IllegalStateException.class, factory::build);
        assertEquals("Candidate is not set", thrown.getMessage());
    }
}
