package com.webapp.accompanyingparents.controller;

import com.webapp.accompanyingparents.config.constant.APConstant;
import com.webapp.accompanyingparents.model.Account;
import com.webapp.accompanyingparents.model.Comment;
import com.webapp.accompanyingparents.model.IPost;
import com.webapp.accompanyingparents.model.Post;
import com.webapp.accompanyingparents.model.criteria.PostCriteria;
import com.webapp.accompanyingparents.model.decorators.ForumPost;
import com.webapp.accompanyingparents.model.repository.AccountRepository;
import com.webapp.accompanyingparents.model.repository.CommentRepository;
import com.webapp.accompanyingparents.model.repository.PostRepository;
import com.webapp.accompanyingparents.view.dto.ApiMessageDto;
import com.webapp.accompanyingparents.view.dto.ErrorCode;
import com.webapp.accompanyingparents.view.dto.ResponseListDto;
import com.webapp.accompanyingparents.view.dto.post.PostDto;
import com.webapp.accompanyingparents.view.form.post.CreatePostForm;
import com.webapp.accompanyingparents.view.form.post.UpdatePostForm;
import com.webapp.accompanyingparents.view.mapper.PostMapper;
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
import java.util.List;

@RestController
@RequestMapping("/v1/post")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class PostController extends ABasicController {
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostMapper postMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CommentRepository commentRepository;

    @PreAuthorize("hasPermission('POST', 'C')")
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> createPost(@RequestBody @Valid CreatePostForm createPostForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        String email = getCurrentUser();
        Account account = accountRepository.findAccountByEmail(email);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.USER_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        Post post = postMapper.formCreatePost(createPostForm);
        // Apply Decorator Pattern
        IPost iPost = post;
        if (createPostForm.getTypePost().equals(2)) {
            iPost = new ForumPost(post);
            post.setType(iPost.getClassify());
        } else if (createPostForm.getTypePost().equals(1)) {
            post.setType(iPost.getClassify());
        } else {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_TYPE);
            return apiMessageDto;
        }
        post.setAccount(account);
        post.setCreatedBy(account.getEmail());
        post.setModifiedBy(account.getEmail());
        postRepository.save(post);
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Create post successfully.");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('POST', 'U')")
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ApiMessageDto<Long> updatePost(@RequestBody @Valid UpdatePostForm updatePostForm, BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(updatePostForm.getId()).orElse(null);
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
        if (post.getAccount().getId().longValue() != account.getId().longValue()) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_UPDATE);
            return apiMessageDto;
        }
        postMapper.mappingForUpdatePost(updatePostForm, post);
        postRepository.save(post);
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Update post successfully.");
        return apiMessageDto;
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListDto<PostDto>> listPost(PostCriteria postCriteria, Pageable pageable) {
        ApiMessageDto<ResponseListDto<PostDto>> apiMessageDto = new ApiMessageDto<>();
        Page<Post> page = postRepository.findAll(postCriteria.getSpecification(), pageable);
        ResponseListDto<PostDto> responseListDto = new ResponseListDto(postMapper.fromEntitiesToPostDtoList(page.getContent()), page.getNumber(), page.getTotalElements(), page.getTotalPages());
        apiMessageDto.setData(responseListDto);
        apiMessageDto.setMessage("Get post list success");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('POST', 'V')")
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<PostDto> getPost(@PathVariable("id") Long id) {
        ApiMessageDto<PostDto> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(id).orElse(null);
        if (post == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_NOT_FOUND);
            return apiMessageDto;
        }
        PostDto postDto = postMapper.fromEntityToPostDto(post);
        apiMessageDto.setData(postDto);
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Get post success.");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('POST', 'D')")
    @DeleteMapping(value = "/delete-status/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<Long> deletePostStatus(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(id).orElse(null);
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
        if (post.getAccount().getId().longValue() != account.getId().longValue()) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_UPDATE);
            return apiMessageDto;
        }
        post.setStatus(APConstant.STATUS_DELETE);
        postRepository.save(post);
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Delete post success.");
        return apiMessageDto;
    }

    @PreAuthorize("hasPermission('POST', 'D')")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<Long> deletePost(@PathVariable("id") Long id) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();
        Post post = postRepository.findById(id).orElse(null);
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
        if (post.getAccount().getId().longValue() != account.getId().longValue()) {
            apiMessageDto.setResult(false);
            apiMessageDto.setCode(ErrorCode.POST_ERROR_UPDATE);
            return apiMessageDto;
        }
        List<Comment> comments = commentRepository.findCommentsByPostId(post.getId());
        if (comments != null) {
            for (Comment c : comments) {
                commentRepository.deleteById(c.getId());
            }
        }
        postRepository.deleteById(post.getId());
        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Delete post success.");
        return apiMessageDto;
    }
}