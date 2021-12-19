//package com.fashion.celebrity.auth.jpa.domain.auth;
//
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class jpaAuthRepositoryTest {
//
//    @Autowired
//    JpaAuthRepository jpaAuthRepository;
//
//    @After
//    public void cleanup() {
//        jpaAuthRepository.deleteAll();
//    }
//
//    @Test
//    public void saveUser_import() {
//        // given
//        String userId = "guswlsapdlf@naver.com";
//        String status = "3";
//        Integer loginCnt = 0;
//        String cnlCd = "01";
//
//        jpaAuthRepository.save(Users.builder()
//                .userId(userId)
//                .status(status)
//                .loginCnt(loginCnt)
//                .cnlCd(cnlCd)
//                .build());
//
//        // when
//        List<Users> usersList = jpaAuthRepository.findAll();
//
//        // then
//        Users users = usersList.get(0);
//        assertThat(users.getUserId()).isEqualTo(userId);
//        assertThat(users.getStatus()).isEqualTo(status);
//        assertThat(users.getCnlCd()).isEqualTo(cnlCd);
//    }
//}
