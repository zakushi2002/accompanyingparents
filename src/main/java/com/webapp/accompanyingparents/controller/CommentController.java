package com.webapp.accompanyingparents.controller;

import com.webapp.accompanyingparents.config.constant.APConstant;
import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Comment;
import com.webapp.accompanyingparents.model.Post;
import com.webapp.accompanyingparents.model.criteria.CommentCriteria;
import com.webapp.accompanyingparents.model.repository.AccountRepository;
import com.webapp.accompanyingparents.model.repository.CommentRepository;
import com.webapp.accompanyingparents.model.repository.PostRepository;
import com.webapp.accompanyingparents.view.dto.ApiMessageDto;
import com.webapp.accompanyingparents.view.dto.ErrorCode;
import com.webapp.accompanyingparents.view.dto.ResponseListDto;
import com.webapp.accompanyingparents.view.dto.comment.CommentDto;
import com.webapp.accompanyingparents.view.form.comment.CreateCommentForm;
import com.webapp.accompanyingparents.view.form.comment.UpdateCommentForm;
import com.webapp.accompanyingparents.view.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CommentController extends ABasicController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    PostRepository postRepository;
    @Autowired
    AccountRepository accountRepository;

    @PreAuthorize("hasPermission('COMMENT', 'C')")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> createComment(@RequestBody @Valid CreateCommentForm createCommentForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(createCommentForm.getPostId()).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        String email = getCurrentUser();
        Account account = accountRepository.findAccountByEmail(email);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Comment comment = commentMapper.formCreateComment(createCommentForm);
        if (createCommentForm.getParentId() != null) {
            Comment parent = commentRepository.findById(createCommentForm.getParentId()).orElse(null);
            if (parent != null) {
                comment.setParent(parent);
                parent.setHasChild(true);
                commentRepository.save(parent);
            }
        }
        //Comment comment = commentMapper.formCreateComment(createCommentForm);
        comment.setAccount(account);
        comment.setCreatedBy(email);
        comment.setModifiedBy(email);
        commentRepository.save(comment);
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Create comment successfully.");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('COMMENT', 'U')")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> updateComment(@RequestBody @Valid UpdateCommentForm updateCommentForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Comment comment = commentRepository.findById(updateCommentForm.getId()).orElse(null);
        if (comment == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.COMMENT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        String email = getCurrentUser();
        Account account = accountRepository.findAccountByEmail(email);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (comment.getAccount().getId().longValue() != account.getId().longValue()) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.COMMENT_ERROR_UPDATE);
            return apiMessageDto;
        }
        commentMapper.mappingForUpdateComment(updateCommentForm, comment);
        commentRepository.save(comment);
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Update comment successfully.");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('COMMENT', 'V')")
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<CommentDto> getComment(@PathVariable("id") Long id) {
        ApiMessageDto<CommentDto> apiMessageDto = new ApiMessageDto<>();
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.COMMENT_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        apiMessageDto.setData(commentMapper.fromEntityToCommentDto(comment));
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Get comment success.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<CommentDto>> listComment(CommentCriteria commentCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<CommentDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Comment> page = commentRepository.findAll(commentCriteria.getSpecification(), pageable);
        ResponseListDto<CommentDto> responseListDto = new ResponseListDto(commentMapper.fromEntitiesToCommentDtoList(page.getContent()), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get comment list success");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('COMMENT', 'D')")
    @DeleteMapping(value = "/delete-status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<Long> deleteCommentStatus(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        String email = getCurrentUser();
        Account account = accountRepository.findAccountByEmail(email);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        if (comment.getAccount().getId().longValue() != account.getId().longValue()) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.COMMENT_ERROR_UPDATE);
            return apiMessageDto;
        }
        comment.setStatus(APConstant.STATUS_DELETE);
        commentRepository.save(comment);
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Delete post success.");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('COMMENT', 'D')")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<Long> deleteComment(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        String email = getCurrentUser();
        Account account = accountRepository.findAccountByEmail(email);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        commentRepository.deleteById(comment.getId());
        if (comment.getParent() != null) {
            Comment parent = comment.getParent();
            if (commentRepository.findCommentsByParentId(parent.getId()).isEmpty()) {
                parent.setHasChild(false);
                commentRepository.save(parent);
            }
        }
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Delete post success.");
        return apiMessageDto;
    }
}