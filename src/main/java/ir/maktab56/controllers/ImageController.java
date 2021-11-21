package ir.maktab56.controllers;

import ir.maktab56.message.ResponseMessage;
import ir.maktab56.model.UserProfilePicture;
import ir.maktab56.service.UserProfilePictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping(value = "/image")
@RequiredArgsConstructor
public class ImageController {

    private final UserProfilePictureService profilePictureService;

    // upload profile picture
    @PostMapping(value = "/upload-profile-picture")
    public ResponseEntity<ResponseMessage> addProfilePicture(@RequestParam("image") MultipartFile image) {
        String message;
        try {
            profilePictureService.store(image);

            message = "Uploaded The Image successfully: " + image.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));

        } catch (IOException | NullPointerException exception) {
            message = "Could not upload the Image: " + image.getOriginalFilename() + "!!!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    // get profile picture
    @GetMapping(value = "/get-profile-picture")
    public ResponseEntity<byte[]> getProfilePicture() {

        UserProfilePicture profilePicture = profilePictureService.getProfilePictureByCurrentUser().orElseThrow();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + profilePicture.getName() + "\"")
                .body(Base64.getEncoder().encode(profilePicture.getData()));
    }

}
