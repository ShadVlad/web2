package service;

import model.User;
import util.PageGenerator;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {
    private static UserService userService;
    /* хранилище данных */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());


    public static UserService instance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }
    public List<User> getAllUsers() {

        return new ArrayList<>(dataBase.values());
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public User getUser(String login) {
        List<User> users = new ArrayList<>(dataBase.values());
        for (User user : users) {
            if(user.getEmail().intern() == login.intern()) return user;
        }
        return null;
    }

    public boolean addUser(User user) {
        if (!isExistsThisUser(user)) {
            user.setId(maxId.incrementAndGet());
            dataBase.put(user.getId(), user);
            return true;
        }
        return false;
    }


    public boolean authUser(User user) {

        if (user.getId() == null) {
            return false;
        }
        if (isExistsThisUser(user) && !isAuthUser(user)) {
            authMap.put(user.getId(), user);
            return true;
        }
        return false;
    }
    public void deleteAllUser() {
        dataBase.clear();
    }

    public boolean isExistsThisUser(User user) {
        return dataBase.containsValue(user);
    }

    public List<User> getAllAuth() {
        return  new ArrayList<>(authMap.values());
    }

    public boolean isAuthUser(User user) {
        return authMap.containsValue(user);
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return authMap.containsKey(id);
    }

}
