package com.hobos.freeter.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDTO {
    @NotBlank(message = "providerId는 필수 입력값입니다.")
    private String providerId;

    @NotNull(message = "provider는 필수 입력값입니다.")
    private Member.Provider provider;
}
