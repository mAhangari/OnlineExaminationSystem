package ir.maktab56.service.impl;

import ir.maktab56.model.User;
import ir.maktab56.model.UserProfilePicture;
import ir.maktab56.repository.UserProfilePictureRepository;
import ir.maktab56.service.UserProfilePictureService;
import ir.maktab56.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfilePictureServiceImpl implements UserProfilePictureService {

    private final UserProfilePictureRepository repository;
    private UserService<User> userService;

    @Autowired
    public void setUserService(UserService<User> userService) {
        this.userService = userService;
    }

    @Override
    public Optional<UserProfilePicture> findByUser_Username(String username) {
        return repository.findByUser_Username(username);
    }

    @Override
    public Optional<UserProfilePicture> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<UserProfilePicture> getProfilePictureByCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return findByUser_Username(authentication.getName());
    }

    @Override
    public UserProfilePicture store(MultipartFile image) throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findUserByUsername(authentication.getName());
        String name = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));

        UserProfilePicture profilePicture =
                findByUser_Username(authentication.getName()).orElse(new UserProfilePicture());

        profilePicture.setName(name);
        profilePicture.setType(image.getContentType());
        profilePicture.setData(image.getBytes());
        profilePicture.setUser(user.orElseThrow());

        return repository.save(profilePicture);
    }
}
