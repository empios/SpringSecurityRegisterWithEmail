package pl.wlodarczyk.springregistersecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wlodarczyk.springregistersecurity.models.User;

public interface UserRepo extends JpaRepository<User,Long> {

    User findAllByUsername(String username);



}
