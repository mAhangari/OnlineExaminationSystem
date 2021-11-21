package ir.maktab56.service;

import ir.maktab56.model.UserProfilePicture;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserProfilePictureService {

    Optional<UserProfilePicture> findByUser_Username(String username);

    Optional<UserProfilePicture> findById(Long id);

    Optional<UserProfilePicture> getProfilePictureByCurrentUser();

    UserProfilePicture store(MultipartFile image) throws IOException;
}
