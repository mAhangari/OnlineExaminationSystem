package ir.maktab56.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = UserProfilePicture.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfilePicture extends BaseEntity<Long> {

    public static final String TABLE_NAME = "user_profile_picture_table";
    private static final String USER_ID = "user_id";

    private String name;

    private String type;

    @Lob
    private byte[] data;

    @OneToOne
    @JoinColumn(name = USER_ID, unique = true, nullable = false)
    private User user;

}
