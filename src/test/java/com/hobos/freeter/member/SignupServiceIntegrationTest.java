package com.hobos.freeter.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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
        Member savedMember = memberRepository.findByProviderId("exampleProviderId").orElseThrow();
        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getProvider()).isEqualTo(Member.Provider.KAKAO);
    }


}