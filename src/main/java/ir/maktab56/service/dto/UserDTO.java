package ir.maktab56.service.dto;

import ir.maktab56.model.enumeration.UserType;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firstName;

    private String lastName;

    private String username;

    private UserType userType;

    private String nationalCode;

}