package com.hanghae.velog.service;

import com.hanghae.velog.domain.User;
import com.hanghae.velog.dto.CommentDto;
import com.hanghae.velog.model.Comment;
import com.hanghae.velog.model.Posting;
import com.hanghae.velog.repository.CommentRepository;
import com.hanghae.velog.repository.PostingRepository;
import com.hanghae.velog.repository.UserRepository;
import com.hanghae.velog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private UserRepository userRepository;
    private CommentRepository commentRepository;
    private PostingRepository postRepository;



    @Transactional
    public void createComment(CommentDto requestDto, UserDetailsImpl userDetails) {
        User user = userRepository.findByUserId(userDetails.getUser().getUserId()).orElseThrow(
                () -> new IllegalArgumentException("로그인 정보를 불러올 수 없습니다.")
        );
        Posting post = postRepository.findById(requestDto.getPostingId()).orElseThrow(
                () -> new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.")
        );
        Comment comment = new Comment(requestDto);
        commentRepository.save(comment);
        post.addComment(comment);
        postRepository.save(post);
    }

    @Transactional
    public void editComment(Long id, CommentDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않습니다.")
        );

        comment.update(requestDto);
    }

    @Transactional
    public void deleteComment(Long id, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(id).orElse(null);
        Posting post = postRepository.findById(comment.getPostingId()).orElse(null);
        post.getComments().remove(comment);
        commentRepository.delete(comment);
    }

    public List<Comment> getComment(Long postingId) {
        return commentRepository.findAllByPostingIdOrderByCreatedAtDesc(postingId);
    }
}
