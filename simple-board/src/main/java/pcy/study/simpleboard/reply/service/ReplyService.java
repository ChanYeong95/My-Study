package pcy.study.simpleboard.reply.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pcy.study.simpleboard.reply.model.ReplyCreateRequest;
import pcy.study.simpleboard.reply.db.Reply;
import pcy.study.simpleboard.reply.db.ReplyRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public Long save(ReplyCreateRequest replyCreateRequest) {
        log.info("Save Reply Info = {}", replyCreateRequest);
        var entity = replyCreateRequest.toReply();
        replyRepository.save(entity);
        return entity.getId();
    }

    public List<Reply> findReplyAllByPostId(Long postId) {
        return replyRepository.findAllByPostIdOrderByIdDesc(postId);
    }
}
