package com.hobos.freeter.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest // JPA 테스트를 위한 어노테이션
public class SignupServiceIntegrationTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SignupService signupService;

    @Test
    public void testSignup() {
        // given
        SignupDTO dto = new SignupDTO();
        dto.setProviderId("exampleProviderId");
        dto.setProvider(Member.Provider.KAKAO);

        // when
        signupService.signup(dto);

        // then
        Member savedMember = memberRepository.findByProviderId("exampleProviderId");
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getProvider()).isEqualTo(Member.Provider.KAKAO);
    }

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
        signupService.login(loginDTO);

        // then
        Member loggedInMember = memberRepository.findByProviderId("exampleProviderId");
        assertThat(loggedInMember).isNotNull();
        assertThat(loggedInMember.getLoggedInAt()).isNotNull();
    }
}