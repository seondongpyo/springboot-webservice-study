package com.study.springboot.controller;

import com.study.springboot.config.auth.LoginUser;
import com.study.springboot.config.auth.dto.SessionUser;
import com.study.springboot.dto.PostResponseDto;
import com.study.springboot.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostService postService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {
        model.addAttribute("posts", postService.findAllDesc());
//        SessionUser user = (SessionUser) httpSession.getAttribute("user"); // 어노테이션으로 대체

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/post/save")
    public String savePost() {
        return "post-save";
    }

    @GetMapping("/post/update/{id}")
    public String updatePost(@PathVariable Long id,
                             Model model) {
        PostResponseDto dto = postService.findById(id);
        model.addAttribute("post", dto);

        return "post-update";
    }
}
