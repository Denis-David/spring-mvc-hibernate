package web.service;

import web.model.User;

import java.util.List;

public interface UserService extends Service<User> {
    void saveUser(User user);

    User getUserById(long id);

    void editUser(User user);

    List<User> getAllUsers();

    void deleteUser(long id);
}
