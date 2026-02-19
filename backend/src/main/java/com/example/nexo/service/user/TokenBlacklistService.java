package com.example.nexo.service.user;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.nexo.entity.user.TokenBlacklist;
import com.example.nexo.repository.user.TokenBlacklistRepository;

@Service
public class TokenBlacklistService {
    private final TokenBlacklistRepository tokenBlacklistRepository;
    public TokenBlacklistService(TokenBlacklistRepository tokenBlacklistRepository) {
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }

    public void addTokenToBlacklist(String token) {
        TokenBlacklist blacklist = new TokenBlacklist();
        blacklist.setToken(token);
        blacklist.setExpirationDate(LocalDateTime.now().plusHours(2));
        tokenBlacklistRepository.save(blacklist);
    }

    public boolean isTokenBlackListed(String token) {
        return tokenBlacklistRepository.existsByToken(token);    
    }
}
