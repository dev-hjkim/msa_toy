package com.fashion.celebrity.auth.jpa.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthRepository extends JpaRepository<Users, Long> {


    String findByUserId(String userId);

    void saveCert(String userId, String certCode);
}
