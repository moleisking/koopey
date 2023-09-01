package com.koopey.api.service.impl;

import com.koopey.api.model.entity.User;
import java.util.List;
import java.util.UUID;

public interface IUserService {
    
    void delete(User user);

    Boolean updateGdpr(UUID userId, Boolean gdpr);

    Boolean updateLanguage(UUID userId, String language);

    Boolean updateTrack(UUID userId, Boolean track);

    Boolean updateNotify(UUID userId, Boolean notify);

    User save(User user);
}
