package domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Currency;
import java.math.BigDecimal;

class PersonalAccountFactoryTest {
    private final PersonalAccountFactory factory = PersonalAccountFactory.getInstance();

    @Test
    void testCreateEURAccount() {
        PersonalAccount account = factory.createPersonalAccount(Currency.getInstance("EUR"));
        assertNotNull(account);
        assertEquals(Currency.getInstance("EUR"), account.getCurrency());
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void testCreateGBPAccount() {
        PersonalAccount account = factory.createPersonalAccount(Currency.getInstance("GBP"));
        assertNotNull(account);
        assertEquals(Currency.getInstance("GBP"), account.getCurrency());
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void testCreateUSDAccount() {
        PersonalAccount account = factory.createPersonalAccount(Currency.getInstance("USD"));
        assertNotNull(account);
        assertEquals(Currency.getInstance("USD"), account.getCurrency());
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void testCreateUnsupportedCurrency() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createPersonalAccount(Currency.getInstance("JPY"));
        });
    }

    @Test
    void testSingletonInstance() {
        PersonalAccountFactory instance1 = PersonalAccountFactory.getInstance();
        PersonalAccountFactory instance2 = PersonalAccountFactory.getInstance();
        assertSame(instance1, instance2);
    }
} 