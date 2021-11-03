package ir.maktab56.service.dto;

import ir.maktab56.model.enumeration.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {

    private String firstName;

    private String lastName;

    private String username;

    private UserType userType;

    private String nationalCode;

    private Long personnelId;

}