package com.hobos.freeter.comment;

import com.hobos.freeter.member.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentDeleteService {
    private final CommentRepository commentRepository;

    public void delete(Long id, Long memberId) {
        CommentEntity found = commentRepository.findById(id)
                .orElseThrow();

        if (!found.isMine(Optional.of(memberId))) throw new UnauthorizedAccessException("권한이 없습니다");


        found.delete();

    }

}
