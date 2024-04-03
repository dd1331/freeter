package com.hobos.freeter.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupDTO {

    @NotBlank(message = "providerId는 필수 입력값입니다.")
    private String providerId;

    @NotNull(message = "provider는 필수 입력값입니다.")
    private Member.Provider provider;

    private String name;

    private String profileImg;

//    public enum Provider {
//        GOOGLE,
//        FACEBOOK,
//        TWITTER
//        // 다른 provider 추가 가능
//    }
}