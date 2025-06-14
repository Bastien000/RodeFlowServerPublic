package org.rodeflow.RodeFlowServer.dto.mapper.user;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.user.PostDTO;
import org.rodeflow.RodeFlowServer.entity.user.PostEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    List<PostDTO> toDTO(List<PostEntity> source);
    PostDTO toDTO(PostEntity source);
    PostEntity toEntity(PostDTO source);
}
