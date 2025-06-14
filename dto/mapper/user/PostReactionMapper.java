package org.rodeflow.RodeFlowServer.dto.mapper.user;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.user.PostReactionDTO;
import org.rodeflow.RodeFlowServer.entity.user.PostReactionEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostReactionMapper {
    List<PostReactionDTO> toDTO(List<PostReactionEntity> source);
    PostReactionDTO toDTO(PostReactionEntity source);
    PostReactionEntity toEntity(PostReactionDTO source);
}
