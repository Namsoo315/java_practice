package com.codeit.blog.service;

import com.codeit.blog.dto.base.SlicePageResponse;
import com.codeit.blog.dto.post.*;
import com.codeit.blog.entity.BinaryContent;
import com.codeit.blog.entity.Category;
import com.codeit.blog.entity.Post;
import com.codeit.blog.entity.User;
import com.codeit.blog.mapper.PageResponseMapper;
import com.codeit.blog.mapper.PostMapper;
import com.codeit.blog.repository.PostRepository;
import com.codeit.blog.repository.UserRepository;
import com.codeit.blog.storage.FileStorage;
import com.querydsl.core.types.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;
    private final PageResponseMapper pageResponseMapper;
    private final PostMapper postMapper;
    private final FileStorage fileStorage;

    @Transactional
    public PostResponse create(PostCreateRequest req, List<MultipartFile> attachments) {
        User author = userRepository.findById(req.authorId())
                .orElseThrow(() -> new NoSuchElementException("작성자를 찾을 수 없습니다: " + req.authorId()));

        Post post = postMapper.toPost(req, author);
        post = postRepository.save(post);

        if (attachments != null && !attachments.isEmpty()) {
            saveAttachments(post, attachments);
            post = postRepository.save(post);
        }

        return postMapper.toPostResponse(post);
    }

    private void saveAttachments(Post post, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) return;

        for (MultipartFile mf : files) {
            if (mf == null || mf.isEmpty()) continue;
            BinaryContent meta = fileStorage.saveAttachFile(mf);
            post.getAttachments().add(meta);
        }
    }

    @Transactional(readOnly = true)
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("post not found: " + id));
        return postMapper.toPostResponse(post);
    }


    @Transactional(readOnly = true)
    public List<PostResponse> findAll() {
        List<Post> list = postRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return postMapper.toPostResponseList(list);
    }


    @Transactional
    public PostResponse update(Long id, PostUpdateRequest req, List<MultipartFile> newFiles) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("post not found: " + id));
        if (req.title() != null)    post.setTitle(req.title());
        if (req.content() != null)  post.setContent(req.content());
        if (req.tags() != null)     post.setTags(req.tags());
        if (req.category() != null) post.setCategory(req.category());

        if (newFiles != null) {
            fileStorage.deleteAllAttachments(post.getAttachments());
            post.getAttachments().clear();
            saveAttachments(post, newFiles);
        }

        postRepository.save(post);

        return postMapper.toPostResponse(post);
    }


    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new NoSuchElementException("아이디가 없습니다. id : " + id));
        if(post.getAttachments() != null && !post.getAttachments().isEmpty()){
            fileStorage.deleteAllAttachments(post.getAttachments());
        }
        postRepository.deleteById(id);
    }

    @Transactional
    public void deleteSoft(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new NoSuchElementException("아이디가 없습니다. id : " + id));
        if(post.getAttachments() != null && !post.getAttachments().isEmpty()){
            fileStorage.deleteAllAttachments(post.getAttachments());
        }
        postRepository.DeleteByIdForSoft(id);
    }

    @Transactional(readOnly = true)
    public SlicePageResponse<PostSimpleResponse> findPage(PostSearchRequest search) {
        int page = search.cursor() == null ? 0 : search.cursor();
        int size = (search.limit() != null && search.limit() > 0) ? search.limit() : 10;
        Sort.Direction sortDir = (search.direction() == Order.ASC)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDir, "id"));
        Slice<Post> result = postRepository.searchPostsAnd(search.title(), search.content(),search.nickname(), pageable);
        var items = result.stream().map(postMapper::toPostSimpleResponse).toList();
        return pageResponseMapper.toSlice(result, items);
    }


    @Transactional(readOnly = true)
    public SlicePageResponse<PostFlatDetailDto> findPageForDsl(PostSearchRequest search) {
        Slice<PostFlatDetailDto> slice = postRepository.searchPageSortedFlat(search);
        return pageResponseMapper.toSlice(slice);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findByAuthorId(Long authorId) {
        return postRepository
                .findByAuthor_IdOrderByIdDesc(authorId)
                .stream()
                .map(postMapper::toPostResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public long count() {
        return postRepository.count();
    }
    @Transactional(readOnly = true)
    public List<PostResponse> findTitle(String title) {
        List<Post> posts = postRepository.findByTitleContaining(title);
        return postMapper.toPostResponseList(posts);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findTag(String tags) {
        List<Post> posts = postRepository.findByTagsContainingIgnoreCase(tags);
        return postMapper.toPostResponseList(posts);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> findCategory(List<String> category) {
        List<Category> categories = category.stream()
                .map(s -> Category.valueOf(s.trim().toUpperCase()))
                .toList();
        List<Post> posts = postRepository.findByCategoryIn(categories);
        return postMapper.toPostResponseList(posts);
    }

    // N+1 해결 findAllV2
    @Transactional(readOnly = true)
    public List<PostResponse> findAllV2() {
        List<Post> posts = postRepository.findAllV2();
        return postMapper.toPostResponseList(posts);
    }

}
