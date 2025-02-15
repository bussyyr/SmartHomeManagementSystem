package domain.ports;

import domain.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    // rückgabetyp User aber nicht void?
    User createUser(User user);
    User updateUser(long userId, User user);
    boolean deleteUser(long userId);

    Optional<User> getUserById(long UserId);
    Optional<User>  getUserByEmail(String email);
    List<User> getUserByName(String name);
    List<User> getAllUsers();
}
