package org.springproject.coffeeshopapp.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springproject.coffeeshopapp.entity.User;
import org.springproject.coffeeshopapp.repository.UserRepo;
@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Override
    public User saveUser(User user) {
        String password = encoder.encode(user.getPassword());
        user.setPassword(password);
        user.setRole("ROLE_USER");
        User newUser = userRepo.save(user);
        return newUser;
    }

    @Override
    public void removeSessionMessage() {
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest().getSession();
        session.removeAttribute("msg");
        session.removeAttribute("succMsg");
        session.removeAttribute("errorMsg");
    }
}
