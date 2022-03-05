//package com.fashion.celebrity.auth.jpa.domain.auth;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Getter
//@NoArgsConstructor
//@Entity
//public class Users {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userSeq;
//
//    @Column(length=30, nullable = false)
//    private String userId;
//
//    @Column(length=1, nullable = false)
//    private String status;
//
//    @Column(columnDefinition = "INT")
//    private Integer loginCnt;
//
//    @Column(length=2, nullable = false)
//    private String cnlCd;
//
//    private String certNum;
//
//    @Builder
//    public Users(String userId, String status, Integer loginCnt, String cnlCd, String certNum) {
//        this.userId = userId;
//        this.status = status;
//        this.loginCnt = loginCnt;
//        this.cnlCd = cnlCd;
//        this.certNum = certNum;
//    }
//}
