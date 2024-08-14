package ru.practicum.shareit.user;

public class UserValidate {

    public static User validate(User user, User currentUser) {
        if (user.getEmail() == null) {
            user.setEmail(currentUser.getEmail());
        }
        if (user.getName() == null) {
            user.setName(currentUser.getName());
        }
        return user;
    }
}
