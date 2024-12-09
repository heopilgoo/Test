    package com.example.MiddleTest.repository;

    import com.example.MiddleTest.entity.Post;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface PostRepository extends JpaRepository<Post, Long> {
    }
