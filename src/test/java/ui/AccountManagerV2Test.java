package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import dao.PersonalAccountDAO;
import dao.PersonalAccountService;
import domain.PersonalAccount;
import domain.PersonalAccountFactory;
import domain.InsufficientFundsException;

@ExtendWith(MockitoExtension.class)
public class AccountManagerV2Test {

    @Mock
    private PersonalAccountDAO dao;
    
    @Mock
    private PersonalAccountService service;
    
    @Mock
    private PersonalAccountFactory factory;
    
    @Mock
    private PersonalAccount mockAccount;

    private AccountManagerV2 accountManager;
    private static final UUID TEST_UUID = UUID.fromString("a7cc40d0-cbd5-44d5-85ef-8cbbf8af450b");
    private InputStream originalSystemIn;

    @BeforeEach
    void setUp() {
        accountManager = new AccountManagerV2(dao, service);
        originalSystemIn = System.in;
    }

    @Test
    void testCreateAccount() {
        String input = "0\n0\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        accountManager.manage();
        verify(dao).create(any(PersonalAccount.class));
    }

    @Test
    void testCheckBalance() {
        String input = "1\n" + TEST_UUID + "\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        when(service.readOne(TEST_UUID)).thenReturn(mockAccount);
        accountManager.manage();
        verify(service).readOne(TEST_UUID);
    }

    @Test
    void testDeposit() {
        String input = "2\n" + TEST_UUID + "\n100.00\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        when(service.readOne(TEST_UUID)).thenReturn(mockAccount);
        accountManager.manage();
        verify(service).readOne(TEST_UUID);
        verify(mockAccount).deposit(any(BigDecimal.class));
        verify(service).update(mockAccount);
    }

    @Test
    void testWithdraw() throws InsufficientFundsException {
        String input = "3\n" + TEST_UUID + "\n50.00\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        when(service.readOne(TEST_UUID)).thenReturn(mockAccount);
        accountManager.manage();
        verify(service).readOne(TEST_UUID);
        verify(mockAccount).withdraw(any(BigDecimal.class));
        verify(service).update(mockAccount);
    }

    @Test
    void testWithdrawInsufficientFunds() throws InsufficientFundsException {
        String input = "3\n" + TEST_UUID + "\n1000.00\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        when(service.readOne(TEST_UUID)).thenReturn(mockAccount);
        doThrow(new InsufficientFundsException("Insufficient funds"))
            .when(mockAccount).withdraw(any(BigDecimal.class));
        accountManager.manage();
        verify(service).readOne(TEST_UUID);
        verify(mockAccount).withdraw(any(BigDecimal.class));
        verify(service, never()).update(mockAccount);
    }

    @Test
    void testReadAccount() {
        String input = "5\n" + TEST_UUID + "\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        when(service.readOne(TEST_UUID)).thenReturn(mockAccount);
        accountManager.manage();
        verify(service).readOne(TEST_UUID);
    }

    @Test
    void testReadAllAccounts() {
        String input = "6\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        List<PersonalAccount> accounts = new ArrayList<>();
        accounts.add(mockAccount);
        when(dao.readAll()).thenReturn(accounts);
        accountManager.manage();
        verify(dao).readAll();
    }

    @Test
    void testDeleteAccount() {
        String input = "7\n" + TEST_UUID + "\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        accountManager.manage();
        verify(dao).delete(TEST_UUID);
    }

    @Test
    void testInvalidAccountId() {
        String input = "5\ninvalid-uuid\n4\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        accountManager.manage();
        verify(service, never()).readOne(any(UUID.class));
    }
} 