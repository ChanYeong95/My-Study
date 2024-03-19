package pcy.study.sns.domain.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pcy.study.sns.domain.post.converter.PostConverter;
import pcy.study.sns.domain.post.dto.DailyPostCount;
import pcy.study.sns.domain.post.dto.DailyPostCountRequest;
import pcy.study.sns.domain.post.dto.PostDto;
import pcy.study.sns.domain.post.entity.Post;
import pcy.study.sns.domain.post.repository.PostRepository;
import pcy.study.sns.util.CursorRequest;
import pcy.study.sns.util.PageCursor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostReadService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
        /*
        반환 값 -> 리스트 [작성일자, 작성회원, 작성 게시물 갯수]
         */
        return postRepository.groupByCreatedDate(request);
    }

    public Page<PostDto> getPosts(Long memberId, Pageable pageable) {
        var posts = postRepository.findAllByMemberId(memberId, pageable);
        return posts.map(postConverter::toDto);
    }

    public PageCursor<PostDto> getPosts(Long memberId, CursorRequest cursorRequest) {
        var posts = findAllBy(memberId, cursorRequest);
        var nextKey = getNextKey(posts);
        var postDtos = postConverter.toDto(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), postDtos);
    }

    public PageCursor<PostDto> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        var posts = findAllBy(memberIds, cursorRequest);
        var nextKey = getNextKey(posts);
        var postDtos = postConverter.toDto(posts);
        return new PageCursor<>(cursorRequest.next(nextKey), postDtos);
    }

    private List<Post> findAllBy(Long memberId, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndMemberIdOrderByIdDesc(cursorRequest.key(), memberId, cursorRequest.size());
        }

        return postRepository.findAllByMemberIdOrderByIdDesc(memberId, cursorRequest.size());
    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        if (cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndInMemberIdOrderByIdDesc(cursorRequest.key(), memberIds, cursorRequest.size());
        }

        return postRepository.findAllByInMemberIdOrderByIdDesc(memberIds, cursorRequest.size());
    }

    private static long getNextKey(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .min()
                .orElse(CursorRequest.NONE_KEY);
    }
}
