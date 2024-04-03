package com.hobos.freeter.comment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentUpdateService {

    private final CommentRepository commentRepository;

    public void update(CommentUpdateRequest dto) {
        CommentEntity comment = commentRepository.findById(dto.getId()).orElseThrow();
        comment.update(dto.getContent());
        commentRepository.save(comment);
    }


}
