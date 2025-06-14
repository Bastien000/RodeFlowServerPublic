package org.rodeflow.RodeFlowServer.dto.mapper.user;

import org.mapstruct.Mapper;
import org.rodeflow.RodeFlowServer.dto.user.NewsDTO;
import org.rodeflow.RodeFlowServer.dto.user.PostDTO;
import org.rodeflow.RodeFlowServer.entity.user.NewsEntity;
import org.rodeflow.RodeFlowServer.entity.user.PostEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface  NewsMapper {
    List<NewsDTO> toDTO(List<NewsEntity> source);
    NewsDTO toDTO(NewsEntity source);
    NewsEntity toEntity(NewsDTO source);
}
