package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Currency;

class PersonalAccountTest {
    private PersonalAccount account;
    private static final Currency USD = Currency.getInstance("USD");

    @BeforeEach
    void setUp() {
        account = new PersonalAccount(USD);
    }

    @Test
    void testInitialBalance() {
        assertEquals(BigDecimal.ZERO, account.getBalance());
        assertEquals(USD, account.getCurrency());
        assertNotNull(account.getId());
    }

    @Test
    void testDeposit() {
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal newBalance = account.deposit(amount);
        
        assertEquals(amount, account.getBalance());
        assertEquals(amount, newBalance);
    }

    @Test
    void testDepositNegativeAmount() {
        BigDecimal amount = new BigDecimal("-50.00");
        BigDecimal newBalance = account.deposit(amount);
        
        assertEquals(BigDecimal.ZERO, account.getBalance());
        assertEquals(BigDecimal.ZERO, newBalance);
    }

    @Test
    void testWithdraw() throws InsufficientFundsException {
        // First deposit some money
        account.deposit(new BigDecimal("200.00"));
        
        // Then withdraw
        BigDecimal amount = new BigDecimal("100.00");
        BigDecimal newBalance = account.withdraw(amount);
        
        assertEquals(new BigDecimal("100.00"), account.getBalance());
        assertEquals(new BigDecimal("100.00"), newBalance);
    }

    @Test
    void testWithdrawInsufficientFunds() {
        // Try to withdraw without having money
        BigDecimal amount = new BigDecimal("100.00");
        
        assertThrows(InsufficientFundsException.class, () -> {
            account.withdraw(amount);
        });
    }

    @Test
    void testWithdrawNegativeAmount() {
        BigDecimal amount = new BigDecimal("-50.00");
        
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(amount);
        });
    }

    @Test
    void testWithdrawZeroAmount() {
        BigDecimal amount = BigDecimal.ZERO;
        
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(amount);
        });
    }
} 