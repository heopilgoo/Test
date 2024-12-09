package com.example.MiddleTest.controller;

import com.example.MiddleTest.entity.Comment;
import com.example.MiddleTest.entity.Post;
import com.example.MiddleTest.repository.CommentRepository;
import com.example.MiddleTest.repository.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentController(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // 댓글 추가
    @PostMapping
    public String addComment(@PathVariable Long postId,
                             @RequestParam String content,
                             @RequestParam(defaultValue = "Anonymous") String author) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post ID"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setContent(content);
        comment.setAuthor(author);
        commentRepository.save(comment);

        return "redirect:/posts/" + postId; // 댓글 작성 후 게시글 상세 페이지로 이동
    }

    // 댓글 삭제
    @GetMapping("/{id}/delete")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Comment ID"));

        commentRepository.delete(comment);

        return "redirect:/posts/" + postId; // 댓글 삭제 후 게시글 상세 페이지로 이동
    }
}
