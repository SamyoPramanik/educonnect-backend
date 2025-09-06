package com.educonnect.auth_service.service;

import java.lang.StackWalker.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.id.uuid.UuidGenerator;
import org.springframework.stereotype.Service;

import com.educonnect.auth_service.dto.SessionDto;
import com.educonnect.auth_service.model.Session;
import com.educonnect.auth_service.repository.SessionRepository;
import com.educonnect.auth_service.util.JwtUtil;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;
    private final JwtUtil jwtUtil;

    public SessionService(SessionRepository sessionRepository, JwtUtil jwtUtil) {
        this.sessionRepository = sessionRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<SessionDto> getUserSessions(String authToken) {
        UUID userId = jwtUtil.getIdFromToken(authToken);
        return sessionRepository.findByUserId(userId).stream()
                .map(session -> {
                    SessionDto dto = new SessionDto();
                    dto.setId(session.getId().toString());
                    dto.setUserId(session.getUserId().toString());
                    dto.setDeviceInfo(session.getDeviceInfo());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void createSession(UUID userId, String token, String deviceInfo) {
        Session session = new Session();
        session.setUserId(userId);
        session.setToken(token);
        session.setDeviceInfo(deviceInfo);
        sessionRepository.save(session);
    }

    public void invalidateAllSessions(UUID userId) {
        List<Session> sessions = sessionRepository.findByUserId(userId);
        sessionRepository.deleteAll(sessions);
    }

    public void invalidateSession(String sessionId) {
        UUID id = jwtUtil.getIdFromToken(sessionId);
        Optional<Session> sessionOpt = sessionRepository.findById(id);
        sessionOpt.ifPresent(sessionRepository::delete);
    }

    public void invalidateCurrentSession(String token) {
        Optional<Session> sessionOpt = sessionRepository.findAll().stream()
                .filter(session -> session.getToken().equals(token))
                .findFirst();
        sessionOpt.ifPresent(sessionRepository::delete);
    }

    public boolean isSessionValid(String token) {
        return sessionRepository.findByToken(token).isPresent();
    }
}
