package pcy.study.springframe.controller;

import jakarta.servlet.ServletException;
import pcy.study.springframe.domain.Member;
import pcy.study.springframe.repository.MemberRepository;

import java.io.IOException;
import java.util.Map;

public class MemberSaveController implements ModelController {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) throws ServletException, IOException {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.put("member", member);
        return "save-result";
    }
}
