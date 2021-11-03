package ir.maktab56.mapper;

import ir.maktab56.model.User;
import ir.maktab56.service.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User convertDTOtoEntity(UserDTO userDTO);

    UserDTO convertEntityToDTO(User user);

    List<User> convertListDTOtoEntity(List<UserDTO> userDTOList);

    List<UserDTO> convertListEntityToDTO(List<User> userList);

}
