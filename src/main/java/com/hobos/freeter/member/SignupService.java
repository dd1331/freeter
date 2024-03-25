package com.hobos.freeter.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final MemberRepository memberRepository;

    public void signup(SignupDTO dto) {

        Member member = new Member();

        // TODO: duplicated exception
        member.signup(dto);

        memberRepository.save(member);
    }


}
