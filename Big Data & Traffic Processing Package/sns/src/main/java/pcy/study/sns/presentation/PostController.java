package pcy.study.sns.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import pcy.study.sns.domain.post.dto.DailyPostCount;
import pcy.study.sns.domain.post.dto.DailyPostCountRequest;
import pcy.study.sns.domain.post.dto.PostCommand;
import pcy.study.sns.domain.post.dto.PostDto;
import pcy.study.sns.domain.post.service.PostReadService;
import pcy.study.sns.domain.post.service.PostWriteService;
import pcy.study.sns.util.CursorRequest;
import pcy.study.sns.util.PageCursor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("posts")
public class PostController {

    private final PostReadService postReadService;
    private final PostWriteService postWriteService;

    @PostMapping
    public Long register(PostCommand command) {
        // TODO 회원 검증
        return postWriteService.register(command);
    }

    @GetMapping("/daily-counts")
    public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
        return postReadService.getDailyPostCounts(request);
    }

    @GetMapping("/members/{memberId}")
    public Page<PostDto> getPosts(
            @PathVariable("memberId") Long memberId,
            @RequestParam("size") int size,
            @RequestParam("page") int page
    ) {
        var pageRequest = PageRequest.of(page, size);
        return postReadService.getPosts(memberId, pageRequest);
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<PostDto> getPostsByCursor(
            @PathVariable("memberId") Long memberId,
            @ModelAttribute CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }
}
