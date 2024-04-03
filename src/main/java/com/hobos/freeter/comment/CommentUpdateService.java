package com.hobos.freeter.comment;


import com.hobos.freeter.member.UnauthorizedAccessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentUpdateService {

    private final CommentRepository commentRepository;

    public void update(CommentUpdateRequest dto, Long memberId) {
        CommentEntity comment = commentRepository.findById(dto.getId()).orElseThrow();
        if (!comment.isCommenter(memberId)) throw new UnauthorizedAccessException("권한이 없습니다");
        comment.update(dto.getContent());
        commentRepository.save(comment);
    }


}
