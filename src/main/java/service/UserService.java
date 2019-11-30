package service;

import dao.UserDao;
import models.User;
import models.WishList;

import java.util.List;

public class UserService {

    private UserDao userDao= new UserDao();

    public UserService() {
    }

    public User findUser(String login) {
        return userDao.findByLogin(login);
    }

    public void saveUser (User user) {
        userDao.save(user);
    }

    public void updateUser (User user) {
        userDao.update(user);
    }

    public void deleteUser (User user) {
        userDao.delete(user);
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    public WishList wishList (int id) {
        return userDao.findListById(id);
    }
}
