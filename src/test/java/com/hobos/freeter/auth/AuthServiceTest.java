package com.hobos.freeter.auth;

import com.hobos.freeter.member.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SignupService signupService;

    @Autowired
    private AuthService authService;

    @Test
    public void testLogin() {
        // given
        SignupDTO dto = new SignupDTO();
        dto.setProviderId("exampleProviderId");
        dto.setProvider(Member.Provider.KAKAO);
        signupService.signup(dto);

        // when
        LoginDTO loginDTO = LoginDTO.builder()
                .providerId("exampleProviderId")
                .provider(Member.Provider.KAKAO)
                .build();

        // then
        String token = authService.login(loginDTO);
        assertThat(token).isNotNull();
    }

    @Test
    public void testLoginWithToken() {
        // given
        SignupDTO dto = new SignupDTO();
        dto.setProviderId("exampleProviderId");
        dto.setProvider(Member.Provider.KAKAO);
        signupService.signup(dto);

        // when
        LoginDTO loginDTO = LoginDTO.builder()
                .providerId("exampleProviderId")
                .provider(Member.Provider.KAKAO)
                .build();

        // then
        String token = authService.login(loginDTO);
        assertThat(token).isNotNull();
    }
}