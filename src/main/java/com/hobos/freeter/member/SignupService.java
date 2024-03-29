package com.hobos.freeter.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignupService {
    private final MemberRepository memberRepository;

    public Member signup(SignupDTO dto) {

        Member member = new Member();

        // TODO: duplicated exception
        member.signup(dto);

        return memberRepository.save(member);
    }


}
