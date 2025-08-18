package com.codeit.rest.service.impl;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.codeit.rest.dto.post.PostCreateRequest;
import com.codeit.rest.dto.post.PostPageRequest;
import com.codeit.rest.dto.post.PostUpdateRequest;
import com.codeit.rest.entity.Category;
import com.codeit.rest.entity.Post;
import com.codeit.rest.repository.PostRepository;
import com.codeit.rest.repository.UserRepository;
import com.codeit.rest.service.PostService;
import com.codeit.rest.service.StorageService;

import lombok.RequiredArgsConstructor;

@Service("postService")
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	private final StorageService storageService;
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Override
	public Post create(PostCreateRequest req, MultipartFile image) {
		// 작성자 ID 검증 (인증이 있으면 인증이 되어 있는지 검증)
		if (req.getAuthorId() == null || !userRepository.existsById(req.getAuthorId())) {
			throw new NoSuchElementException("작성자를 찾을 수 없습니다. " + req.getAuthorId());
		}

		Post post = req.toPost();
		post.setCreatedAt(Instant.now());    // DB에서 time을 저장하는것이 강사님 권장.
		post.setUpdatedAt(Instant.now());

		String uploadPath = null;
		try {
			if (hasFile(image)) {
				uploadPath = buildStoredPath(Objects.requireNonNull(image.getOriginalFilename()));
				boolean success = storageService.store(uploadPath, image.getInputStream());
				if (success) {
					post.setImagePath(uploadPath);
					post.setImageRealName(image.getOriginalFilename());
				} else {
					uploadPath = null;
				}
			}

		} catch (Exception e) {
			if(uploadPath != null) {
				try {
					storageService.delete(uploadPath);
				} catch (Exception e2) {
					throw new RuntimeException("파일 삭제에 실패 하였습니다.");
				}
			}
			throw new RuntimeException("파일 저장에 실패하였습니다.");
		}

		return postRepository.save(post);
	}

	@Override
	public Post findById(Long id) {
		return postRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("아이디를 찾을 수 없습니다. : " + id));
	}

	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public Post update(Long id, PostUpdateRequest req, MultipartFile image) {
		Post updatePost = findById(id);
		updatePost.setTitle(req.getTitle());
		updatePost.setContent(req.getContent());
		updatePost.setTags(req.getTags());
		updatePost.setCategory(req.getCategory());

		String oldPath = updatePost.getImagePath();
		String newPath = null;

		// 만일 기존 이미지가 있는 경우 삭제 및 교체
		try {
			if(hasFile(image)) {
				newPath = buildStoredPath(Objects.requireNonNull(image.getOriginalFilename()));
				boolean success = storageService.store(newPath, image.getInputStream());
				if (success) {
					updatePost.setImagePath(newPath);
					updatePost.setImageRealName(image.getOriginalFilename());
				} else {
					newPath = null;
				}
			}
		} catch (Exception e) {
			if (newPath != null) {
				try {
					storageService.delete(newPath);
				} catch (Exception e2) {
					throw new RuntimeException("파일 삭제에 실패 하였습니다.");
				}
			}
			throw new RuntimeException("파일 저장에 실패하였습니다.");
		}
		// 경로가 바뀐 경우 기존 파일 삭제 로직
		if (newPath != null && oldPath != null && !oldPath.equals(newPath)) {
			try {
				storageService.delete(oldPath);
			} catch (IOException e) {
				System.out.println("파일 삭제에 실패하였습니다.");
				// 날리지 않기.
			}
		}


		// 파일 저장 까지 성공 하였을 때	성공 케이스
		updatePost.setUpdatedAt(Instant.now());
		return postRepository.save(updatePost);
	}

	@Override
	public void delete(Long id) {
		Post deletePost = findById(id);
		String path = deletePost.getImagePath();

		if (path != null) {
			try {
				storageService.delete(path);
			} catch (Exception e) {
				throw new RuntimeException("파일 삭제에 실패하였습니다.");
			}
		}

		postRepository.deleteById(id);
	}

	@Override
	public List<Post> findByTitle(String keyword) {
		return postRepository.findByTitleContaining(keyword);
	}

	@Override
	public List<Post> findByContent(String keyword) {
		return postRepository.findByContentContaining(keyword);
	}

	@Override
	public List<Post> findByCategory(Category category) {
		return postRepository.findByCategory(category);
	}

	@Override
	public List<Post> findByTagsAny(List<String> tags) {
		return postRepository.findByTagsIn(tags);
	}

	@Override
	public List<Post> findByAuthor(Long authorId) {
		return postRepository.findByAuthorId(authorId);
	}

	@Override
	public Page<Post> findPage(PostPageRequest postPageRequest) {
		int size = Math.max(postPageRequest.getSize(), 1);		// default 10, 최소 1, 보여줄 목록의 갯수
		int page = Math.max(postPageRequest.getPage() - 1, 0);	// base 1일 경우 -1, offset 계산을 위해 0으로 변경
		// int page = Math.max(postPageRequest.getPage(), 0);	// base 0일 경우 0, offset 계산을 위해 0으로 변경 (백엔드 관점)

		// 총 데이터의 갯수 전체 페이지수 계산
		long totalCount = postRepository.count();	// DB에서 저장된 row 갯수
		long totalPages = (long) Math.ceil((double) totalCount / size);

		// 페이지 번호 유효성 검사
		if (page >= totalPages && totalPages > 0) {
			throw new IllegalArgumentException(
				String.format("요청한 페이지 %d는 전체 페이지 수(%d)를 초과합니다.", page, totalPages)
			);
		}

		return postRepository.findAllByPage(size, page);
	}

	@Override
	public boolean exists(Long id) {
		return postRepository.existsById(id);
	}

	@Override
	public long count() {
		return postRepository.count();
	}

	private boolean hasFile(MultipartFile f) {
		return f != null && !f.isEmpty() && f.getOriginalFilename() != null;
	}

	private String buildStoredPath(String originalName) {
		String dateTime = LocalDateTime.now()
			.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

		String shortUuid = UUID.randomUUID().toString()
			.replace("-", "")
			.substring(0, 8);

		String ext = "";
		int dotIdx = originalName.lastIndexOf('.');
		if (dotIdx != -1 && dotIdx < originalName.length() - 1) {
			ext = originalName.substring(dotIdx).toLowerCase();
		}

		return "posts/" + dateTime + "_" + shortUuid + ext;
	}
}
