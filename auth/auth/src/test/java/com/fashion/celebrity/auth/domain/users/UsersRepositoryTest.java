package com.fashion.celebrity.auth.domain.users;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersRepositoryTest {

    @Autowired
    UsersRepository usersRepository;

    @After
    public void cleanup() {
        usersRepository.deleteAll();
    }

    @Test
    public void saveUser_import() {
        // given
        String userId = "guswlsapdlf@naver.com";
        String status = "3";
        Integer loginCnt = 0;
        String cnlCd = "01";

        usersRepository.save(Users.builder()
                .userId(userId)
                .status(status)
                .loginCnt(loginCnt)
                .cnlCd(cnlCd)
                .build());

        // when
        List<Users> usersList = usersRepository.findAll();

        // then
        Users users = usersList.get(0);
        assertThat(users.getUserId()).isEqualTo(userId);
        assertThat(users.getStatus()).isEqualTo(status);
        assertThat(users.getCnlCd()).isEqualTo(cnlCd);
    }
}
