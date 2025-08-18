package dao;

import model.User;
import java.util.List;

public interface UserDAOInterface {

    User validateLogin(String username, String password);

    List<User> getAllUsers();

    void addUser(User user);

    User getUserById(int id);

    void updateUser(User user);

    void deleteUser(int id);
}

