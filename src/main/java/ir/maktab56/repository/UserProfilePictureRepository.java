package ir.maktab56.repository;

import ir.maktab56.model.UserProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfilePictureRepository extends JpaRepository<UserProfilePicture, Long> {

    Optional<UserProfilePicture> findByUser_Username(String username);
}
