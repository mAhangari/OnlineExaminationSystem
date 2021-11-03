package ir.maktab56.mapper;

import ir.maktab56.model.Admin;
import ir.maktab56.service.dto.AdminDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {

    Admin convertDTOtoEntity(AdminDTO adminDTO);

    AdminDTO convertEntityToDTO(Admin admin);

    List<Admin> convertListDTOtoEntity(List<AdminDTO> adminDTOList);

    List<AdminDTO> convertListEntityToDTO(List<Admin> adminList);

}
