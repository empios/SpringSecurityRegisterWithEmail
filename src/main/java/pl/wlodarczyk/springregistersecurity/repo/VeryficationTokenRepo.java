package pl.wlodarczyk.springregistersecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wlodarczyk.springregistersecurity.models.VeryficationToken;

public interface VeryficationTokenRepo extends JpaRepository<VeryficationToken, Long> {
    VeryficationToken findByValue(String value);
}
