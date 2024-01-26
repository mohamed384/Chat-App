package org.example.services;


import org.example.DTOs.UserDTO;
import org.example.DAO.UserDAOImpl;
import org.example.Utils.SessionManager;
import org.example.models.Enums.UserStatus;
import org.example.models.Mapper.UserMapper;
import org.example.models.User;
import org.example.utils.PasswordHashing;


import java.sql.Timestamp;

public class UserService {
    private UserDAOImpl userDAO;
    private UserMapper userMapper = UserMapper.INSTANCE;

    public UserService() {
        this.userDAO = new UserDAOImpl();
    }

    public Boolean signup(UserDTO userDto) {
        if (userDto ==null) {
            System.out.println("UserDTO is null");
            return false;
        } else if (userDto.getConfirmPassword() == null) {
            System.out.println("Confirm password is null");
            return false;
        } else if (userDto.getPasswordHash() == null) {
            System.out.println(" password is null");
            return false;
        }else if (userDto.getEmailAddress() == null) {
            System.out.println("Email is null");
            return false;
        }

        if (!userDto.getPasswordHash().equals(userDto.getConfirmPassword())) {
            System.out.println("Passwords don't match");
            return false;
        }
        //User user = userMapper.toUser(userDto);

        User user = UserMapper.INSTANCE.toUser(userDto);
        SessionManager.getInstance().startSession(userDto);
        System.out.println("I'm SessionManager from Server Service before accessing userDAO.Create()" + userDto.toString());
        return userDAO.create(user);
    }
    public UserDTO login (String phoneNumber,  String password) {

        User user = UserDAOImpl.findByPhoneNumber(phoneNumber);
        String passwordHash = user.getPasswordHash();
        password = PasswordHashing.hashPassword(password);
        System.out.println(passwordHash);

        if (passwordHash.equals(password)) {
            System.out.println("Login successful");
            //UserDTO userDto = userMapper.toDTO(user);
            UserDTO userDto = UserMapper.INSTANCE.toDTO(user);

            SessionManager.getInstance().startSession(userDto);

            return userDto;
        }

        System.out.println("Login failed");
        return null;

    }

    public boolean logout(UserDTO userDto) {
        if (userDto == null) {
            System.out.println("From logout: UserDTO is null");
            return false;
        }

        User user = UserMapper.INSTANCE.toUser(userDto);

        user.setUserStatus(UserStatus.Offline);

        user.setLastLogin(new Timestamp(System.currentTimeMillis()));

        //User user = userMapper.toUser(userDto);
        boolean result = userDAO.update(user);

        if (result) {
            SessionManager.getInstance().endSession();
        }
        return result;
    }

    public UserDTO getUser(String phoneNumber) {

        User user = userDAO.findByPhoneNumber(phoneNumber);
        if (user == null) {
            System.out.println("From getUser: User not found");
            return null;
        }

        UserDTO userDto = UserMapper.INSTANCE.toDTO(user);
        return userDto;
    }

}