package com.hobos.freeter.auth;

import com.hobos.freeter.member.LoginDTO;
import com.hobos.freeter.member.Member;
import com.hobos.freeter.member.MemberRepository;
import com.hobos.freeter.member.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;

    String login(LoginDTO dto) {

        Member member = memberRepository.findByProviderId(dto.getProviderId()).orElseThrow();

        member.login(dto);

        memberRepository.save(member);
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.getProviderId(), null);
//
//        System.out.println(authToken);
//
//        try {
//            authenticationManager.authenticate(authToken);
//        } catch (Exception e) {
//            throw new UnauthorizedAccessException("로그인 실패");
//        }

        return jwtService.generateToken(member);

    }
}
