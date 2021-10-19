package com.hanghae.velog.service;

import com.hanghae.velog.dto.CommentDto;
import com.hanghae.velog.model.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private PostRepository postRepository;



    @Transactional
    public void createComment(CommentDto requestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("로그인 정보를 불러올 수 없습니다.")
        );
        Post post = postRepository.findById(requestDto.getPostId()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.")
        );

        Comment comment = commentRepository.save(new Comment(requestDto,post.getId()));
        post.addComment(comment);
        postRepository.save(post);

        return "댓글이 작성 되었습니다.";
    }
    @Transactional
    public void editComment(Long id, CommentDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않습니다.")
        );

        comment.update(reqDto);
    }
    @Transactional
    public void deleteComment(Long id, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElse(null);
        Post post = postRepository.findById(comment.getPostId()).orElse(null);
        post.getCommentList().remove(comment);
        commentRepository.delete(comment);
    }
}
