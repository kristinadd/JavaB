package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import dao.PersonalAccountDAO;
import dao.PersonalAccountService;
import ui.AccountManager;

@ExtendWith(MockitoExtension.class)
public class AccountManagerTest {

    @Mock
    private PersonalAccountDAO dao;
    
    @Mock
    private PersonalAccountService service;
    
    @Mock
    private PersonalAccountFactory factory;
    
    @Mock
    private PersonalAccount mockAccount;

    private AccountManager accountManager;
    private static final UUID TEST_UUID = UUID.fromString("a7cc40d0-cbd5-44d5-85ef-8cbbf8af450b");

    @BeforeEach
    void setUp() {
        // Reset the singleton before each test
        try {
            var field = AccountManager.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);
        } catch (Exception e) {
            fail("Could not reset singleton");
        }
        
        AccountManager.initialize(dao, service, factory);
        accountManager = AccountManager.getInstance();
    }

    @Test
    void testInitialization() {
        // First verify we can get an instance after initialization
        assertNotNull(AccountManager.getInstance());
        
        // Then verify we get an exception if we try to get an instance without initialization
        try {
            var field = AccountManager.class.getDeclaredField("instance");
            field.setAccessible(true);
            field.set(null, null);
        } catch (Exception e) {
            fail("Could not reset singleton");
        }
        
        assertThrows(IllegalStateException.class, AccountManager::getInstance);
    }

    @Test
    void testReadOne() {
        // Setup
        when(service.readOne(TEST_UUID)).thenReturn(mockAccount);
        
        // Execute
        PersonalAccount result = accountManager.readOne(TEST_UUID.toString());
        
        // Verify
        assertNotNull(result);
        assertSame(mockAccount, result);
        verify(service).readOne(TEST_UUID);
    }

    @Test
    void testReadAll() {
        // Setup
        List<PersonalAccount> accounts = new ArrayList<>();
        accounts.add(mockAccount);
        when(dao.readAll()).thenReturn(accounts);
        
        // Execute
        List<PersonalAccount> result = accountManager.readAll();
        
        // Verify
        assertNotNull(result);
        assertEquals(1, result.size());
        assertSame(mockAccount, result.get(0));
        verify(dao).readAll();
    }

    @Test
    void testDelete() {
        // Execute
        accountManager.delete(TEST_UUID.toString());
        
        // Verify
        verify(dao).delete(TEST_UUID);
    }

    @Test
    void testUpdate() {
        // Setup
        when(dao.update(mockAccount)).thenReturn(mockAccount);
        
        // Execute
        PersonalAccount result = accountManager.update(mockAccount);
        
        // Verify
        assertNotNull(result);
        assertSame(mockAccount, result);
        verify(dao).update(mockAccount);
    }
}
