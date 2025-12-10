package com.codeit.graphql.service;


import com.codeit.graphql.dto.post.PostCreateRequest;
import com.codeit.graphql.dto.post.PostDto;
import com.codeit.graphql.dto.post.PostPageDto;
import com.codeit.graphql.dto.post.PostUpdateRequest;
import com.codeit.graphql.entity.Post;
import com.codeit.graphql.entity.User;
import com.codeit.graphql.mapper.PostMapper;
import com.codeit.graphql.repository.PostRepository;
import com.codeit.graphql.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Transactional(readOnly = true)
    public PostDto getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return postMapper.toDto(post);
    }

    @Transactional(readOnly = true)
    public PostPageDto getPosts(Pageable pageable) {
        Page<Post> page = postRepository.findAll(pageable);
        return postMapper.toPageDto(page);
    }

    @Transactional(readOnly = true)
    public PostPageDto searchByTitle(String keyword, Pageable pageable) {
        Page<Post> page = postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        return postMapper.toPageDto(page);
    }

    @Transactional(readOnly = true)
    public PostPageDto searchByContent(String keyword, Pageable pageable) {
        Page<Post> page = postRepository.findByContentContainingIgnoreCase(keyword, pageable);
        return postMapper.toPageDto(page);
    }

    @Transactional(readOnly = true)
    public PostPageDto searchByTitleOrContent(String keyword, Pageable pageable) {
        Page<Post> page = postRepository.findByTitleOrContentContainingIgnoreCase(keyword, pageable);
        return postMapper.toPageDto(page);
    }

    @Transactional(readOnly = true)
    public PostPageDto searchByAuthorNickName(String nickName, Pageable pageable) {
        Page<Post> page = postRepository.findByAuthorNickNameContainingIgnoreCase(nickName, pageable);
        return postMapper.toPageDto(page);
    }

    @Transactional
    public PostDto createPost(PostCreateRequest request) {
        User author = userRepository.findById(request.authorId())
                .orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다."));

        boolean published = request.isPublished() != null ? request.isPublished() : true;

        Post post = Post.builder()
                .author(author)
                .title(request.title())
                .content(request.content())
                .isPublished(published)
                .isDeleted(false)
                .build();

        Post saved = postRepository.save(post);
        return postMapper.toDto(saved);
    }

    @Transactional
    public PostDto updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        if (request.title() != null) {
            post.setTitle(request.title());
        }
        if (request.content() != null) {
            post.setContent(request.content());
        }
        if (request.isPublished() != null) {
            post.setPublished(request.isPublished());
        }

        Post saved = postRepository.save(post);
        return postMapper.toDto(saved);
    }

    @Transactional
    public boolean deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        post.setDeleted(true);
        return true;
    }
}
