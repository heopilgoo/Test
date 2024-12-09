package com.example.MiddleTest.controller;

import com.example.MiddleTest.entity.User;
import com.example.MiddleTest.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "auth/register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           Model model) {

        // 아이디 중복 체크
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "아이디가 이미 존재합니다.");
            return "auth/register"; // 중복된 아이디가 있으면 회원가입 페이지로 돌아감
        }

        // 이메일 중복 체크
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "이메일이 이미 존재합니다.");
            return "auth/register"; // 중복된 이메일이 있으면 회원가입 페이지로 돌아감
        }

        // 아이디와 이메일 중복이 없으면 회원가입 처리
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);  // 비밀번호는 해시 처리 필요
        user.setEmail(email);

        userRepository.save(user);

        return "redirect:/auth/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }

    // 로그인 화면
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        // 아이디가 존재하고 비밀번호가 일치하는지 확인
        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            model.addAttribute("message", "로그인 성공!");
            return "redirect:/posts"; // 로그인 성공 후 게시판으로 리다이렉트
        }

        // 비밀번호가 틀린 경우
        model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
        return "auth/login"; // 로그인 실패 시 다시 로그인 화면으로
    }

}
