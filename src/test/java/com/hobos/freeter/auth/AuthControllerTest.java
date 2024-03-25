package com.hobos.freeter.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.hobos.freeter.member.LoginDTO;
import com.hobos.freeter.member.Member;
import com.hobos.freeter.member.SignupDTO;
import com.hobos.freeter.member.SignupService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private SignupService signupService;

    @Autowired
    private AuthService authService;


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testTestEndpointWithUser() throws Exception {
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
        System.out.println("token = " + token);
        assertThat(token).isNotNull();

        // when & then
        mockMvc.perform(get("/auth/test")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()) // HTTP 상태 코드 200 확인
                .andExpect(content().string("test")) // 응답 본문 확인
                .andDo(MockMvcResultHandlers.print()); // 출력을 통해 디버깅 정보 확인
    }
}