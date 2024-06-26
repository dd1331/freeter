package com.hobos.freeter.comment;


import com.hobos.freeter.member.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentUpdateService {

    private final CommentRepository commentRepository;

    public void update(CommentUpdateRequest dto, Long memberId) {
        CommentEntity comment = commentRepository.findById(dto.getId()).orElseThrow();
        if (!comment.isMine(Optional.of(memberId))) throw new UnauthorizedAccessException("권한이 없습니다");
        comment.update(dto.getContent());
        commentRepository.save(comment);
    }


}
