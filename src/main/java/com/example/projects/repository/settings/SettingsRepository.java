package com.example.projects.repository.settings;


import com.example.projects.entity.settings.Settings;
import com.example.projects.repository.base.BaseRepository;

import java.util.UUID;


public interface SettingsRepository extends BaseRepository<Settings, UUID> {

    public Settings findByAccountId(UUID id);
}
