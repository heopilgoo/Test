package com.example.MiddleTest.controller;

import com.example.MiddleTest.entity.Comment;
import com.example.MiddleTest.entity.Post;
import com.example.MiddleTest.repository.CommentRepository;
import com.example.MiddleTest.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostController(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping
    public String listPosts(@RequestParam(defaultValue = "1") int page, Model model) {
        Pageable pageable = PageRequest.of(page - 1, 10, Sort.by(Sort.Order.desc("createdAt")));
        Page<Post> postPage = postRepository.findAll(pageable);

        model.addAttribute("posts", postPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", postPage.getTotalPages());
        model.addAttribute("totalItems", postPage.getTotalElements());

        int startPage = Math.max(1, page - 4);
        int endPage = Math.min(postPage.getTotalPages(), page + 4);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("prevPage", (page > 1) ? page - 1 : null);
        model.addAttribute("nextPage", (page < postPage.getTotalPages()) ? page + 1 : null);

        return "posts/index";
    }

    @GetMapping("/new")
    public String showPostForm() {
        return "posts/new";
    }

    @PostMapping
    public String createPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String author,
            Model model) {
        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty() || author == null || author.trim().isEmpty()) {
            model.addAttribute("error", "Title, content, and author are required.");
            return "posts/new";
        }

        try {
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setAuthor(author);

            postRepository.save(post);
            return "redirect:/posts";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to create post. Please try again.");
            return "error/500";
        }
    }

    @GetMapping("/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        try {
            Post post = postRepository.findById(id).orElse(null);
            if (post == null) {
                model.addAttribute("errorMessage", "Post not found.");
                return "error/404";
            }

            post.setViews(post.getViews() + 1);
            postRepository.save(post);

            model.addAttribute("post", post);

            // 댓글 데이터 추가
            List<Comment> comments = commentRepository.findByPostId(id);
            model.addAttribute("comments", comments);

            return "posts/show";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Failed to load post. Please try again.");
            return "error/500";
        }
    }

    // 게시글 수정 폼
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post ID"));
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @PostMapping("/{id}/update")
    public String updatePost(
            @PathVariable Long id,
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam String author) {
        // 게시글 찾기
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post ID"));

        // 게시글 수정
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        postRepository.save(post);

        // 수정 후 상세 페이지로 이동
        return "redirect:/posts/" + id;
    }


    // 게시글 삭제
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "redirect:/posts"; // 삭제 후 목록 페이지로 리다이렉트
    }
}
