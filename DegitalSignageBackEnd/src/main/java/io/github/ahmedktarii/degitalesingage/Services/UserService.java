package io.github.ahmedktarii.degitalesingage.Services;

import io.github.ahmedktarii.degitalesingage.Entities.Media;
import io.github.ahmedktarii.degitalesingage.Entities.User;
import io.github.ahmedktarii.degitalesingage.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }
    public String grapPasswordByEmail(String email){
        User user = userRepository.findByEmail(email);
        return user != null ? user.getPassword() : null;
    }
    public String grapUsernameByEmail(String email){
        User user = userRepository.findByEmail(email) ;
        return user != null ? user.getUsername() : null;
    }
    public Boolean doesEmailExist(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ;
    }
    public Long grapIdByEmail(String email) {
        
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("No user found for email: " + email);
        }
        return user.getId();
    }

}