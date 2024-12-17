package com.example.projects.dto.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BaseSearchDto {
    private UUID id;
    private List<UUID> ids;
    private Boolean isDeleted;
}