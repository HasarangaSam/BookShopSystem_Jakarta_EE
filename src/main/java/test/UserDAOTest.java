package test;

import dao.UserDAO;
import model.User;
import org.junit.Before;
import org.junit.Test;
import util.PasswordUtil;

import java.util.List;

import static org.junit.Assert.*;

public class UserDAOTest {

    private UserDAO userDAO;

    @Before
    public void setUp() {
        userDAO = new UserDAO();
    }

    @Test
    public void testAddAndGetUserById() {
        // Create new user with constructor
        String username = "testuser_junit";
        String password = "test123";
        String hashedPassword = PasswordUtil.hashPassword(password);
        User user = new User(0, username, hashedPassword, "cashier");

        // Add user
        userDAO.addUser(user);

        // Fetch the last inserted user
        List<User> users = userDAO.getAllUsers();
        assertFalse(users.isEmpty());

        User latest = users.get(users.size() - 1);
        assertEquals(username, latest.getUsername());
        assertEquals("cashier", latest.getRole());

        // Fetch by ID and verify username
        User fetched = userDAO.getUserById(latest.getUserId());
        assertNotNull(fetched);
        assertEquals(username, fetched.getUsername());
    }

    @Test
    public void testValidateLogin() {
        String plainPassword = "valid123";
        String hashedPassword = PasswordUtil.hashPassword(plainPassword);
        User user = new User(0, "login_user_junit", hashedPassword, "stockkeeper");

        // Add user
        userDAO.addUser(user);

        // Valid login
        User validUser = userDAO.validateLogin("login_user_junit", plainPassword);
        assertNotNull(validUser);
        assertEquals("stockkeeper", validUser.getRole());

        // Invalid login
        User invalidUser = userDAO.validateLogin("login_user_junit", "wrongpass");
        assertNull(invalidUser);
    }

    @Test
    public void testUpdateUserWithoutPassword() {
        String oldUsername = "update_user_junit";
        User user = new User(0, oldUsername, PasswordUtil.hashPassword("oldpass"), "cashier");
        userDAO.addUser(user);

        // Fetch user to update
        User last = userDAO.getAllUsers().get(userDAO.getAllUsers().size() - 1);
        last.setUsername("updated_username");
        last.setRole("stockkeeper");
        last.setPassword(""); // skip password update

        userDAO.updateUser(last);

        // Verify update
        User updated = userDAO.getUserById(last.getUserId());
        assertEquals("updated_username", updated.getUsername());
        assertEquals("stockkeeper", updated.getRole());
    }

    @Test
    public void testUpdateUserWithPassword() {
        String oldUsername = "pwd_user_junit";
        User user = new User(0, oldUsername, PasswordUtil.hashPassword("initial123"), "cashier");
        userDAO.addUser(user);

        // Fetch user and update
        User last = userDAO.getAllUsers().get(userDAO.getAllUsers().size() - 1);
        last.setUsername("pwd_updated");
        last.setPassword(PasswordUtil.hashPassword("newpass456"));
        last.setRole("cashier");

        userDAO.updateUser(last);

        // Login with updated credentials
        User loggedIn = userDAO.validateLogin("pwd_updated", "newpass456");
        assertNotNull(loggedIn);
        assertEquals("cashier", loggedIn.getRole());
    }

    @Test
    public void testDeleteUser() {
        String username = "delete_user_junit";
        User user = new User(0, username, PasswordUtil.hashPassword("del123"), "cashier");
        userDAO.addUser(user);

        // Fetch and delete user
        User last = userDAO.getAllUsers().get(userDAO.getAllUsers().size() - 1);
        userDAO.deleteUser(last.getUserId());

        // Assert that user is deleted
        User deleted = userDAO.getUserById(last.getUserId());
        assertNull(deleted);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userDAO.getAllUsers();
        assertNotNull(users);
        users.forEach(user -> assertTrue(user.getRole().equals("cashier") || user.getRole().equals("stockkeeper")));
    }
}
