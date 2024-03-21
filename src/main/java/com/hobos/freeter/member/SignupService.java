package com.hobos.freeter.member;

import org.springframework.stereotype.Service;

@Service
public class SignupService {
    private final MemberRepository memberRepository;

    public SignupService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    void signup(SignupDTO dto) {

        Member member = new Member();

        // TODO: duplicated exception
        member.signup(dto);

        memberRepository.save(member);
    }

    void login(LoginDTO dto) {

        Member member = memberRepository.findByProviderId(dto.getProviderId());

        if (member == null) {
            throw new RuntimeException("존재하지 않는 회원입니다.");
        }

        // TODO: 로그인처리
        member.login(dto);

        memberRepository.save(member);


    }
}
