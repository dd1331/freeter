package com.hobos.freeter.comment;

import com.hobos.freeter.member.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentDeleteService {
    private final CommentRepository commentRepository;

    public void delete(Long id, Long memberId) {
        CommentEntity found = commentRepository.findById(id)
                .orElseThrow();

        if (!found.isCommenter(memberId)) throw new UnauthorizedAccessException("권한이 없습니다");

        commentRepository.deleteById(id);
    }

}
