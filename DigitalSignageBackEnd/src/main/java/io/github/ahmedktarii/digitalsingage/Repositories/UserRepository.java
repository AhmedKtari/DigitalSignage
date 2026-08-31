package io.github.ahmedktarii.digitalsingage.Repositories;

import io.github.ahmedktarii.digitalsingage.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
   // void saveMedia(Media media);
   // void setUserCode(String userCode);
}