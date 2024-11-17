package org.springproject.coffeeshopapp.service;

import org.springproject.coffeeshopapp.entity.User;

public interface IUserService {
    public User saveUser(User user);

    public void removeSessionMessage();
}
