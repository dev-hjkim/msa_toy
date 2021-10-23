package com.fashion.celebrity.auth.domain.users;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(length=30, nullable = false)
    private String userId;

    @Column(length=1, nullable = false)
    private String status;

    @Column(columnDefinition = "INT")
    private Integer loginCnt;

    @Column(length=2, nullable = false)
    private String cnlCd;

    @Builder
    public Users(String userId, String status, Integer loginCnt, String cnlCd) {
        this.userId = userId;
        this.status = status;
        this.loginCnt = loginCnt;
        this.cnlCd = cnlCd;
    }
}
