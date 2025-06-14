package org.rodeflow.RodeFlowServer.dto.mapper.user;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.user.PrivateProfilDTO;
import org.rodeflow.RodeFlowServer.dto.user.PublicProfilDTO;
import org.rodeflow.RodeFlowServer.dto.user.UserDTO;
import org.rodeflow.RodeFlowServer.entity.user.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDTO> toDTO(List<UserEntity> source);
    UserDTO toDTO(UserEntity source);
    UserEntity toEntity(UserDTO source);
    PublicProfilDTO toPublicProfilDTO(UserEntity source);
    PrivateProfilDTO toPrivateProfilDTO(UserEntity source);
}
