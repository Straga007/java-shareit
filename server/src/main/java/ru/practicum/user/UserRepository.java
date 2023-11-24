package ru.practicum.user;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.user.object.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
