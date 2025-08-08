package com.codeit.mvc.controller;

//  ■ REST 전용 어노테이션
//  - @PathVariable("값") : restful 방식으로 구현할때 URL에 있는 데이터를 가져올때 사용
//  - @ResponseBody : 클라이언트에게 응답할때 메소드 리턴값을 JSON 형태로 반환해주는 어노테이션
//  - @RequestBody : 클라이언트가 요청한 JSON 파싱할때 사용

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeit.mvc.domain.User;
import com.codeit.mvc.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserControllerForRest {

	private final UserService userService;

	// 회원 전체 목록 조회 (GET) - JSON 응답!

	// produces : 클라이언트에게 응답할 컨텐츠 타입 (MIME Type)을 지정하는 속성
	// -> 클라이언트에서 Accept : application/xml로 요청하면 실패 !!!
	@GetMapping(path = "/", produces = "application/json")
	public ResponseEntity<List<User>> getUsers() {
		List<User> users = userService.getAllUsers();
		// return ResponseEntity.ok(users);
		// return ResponseEntity.status(200).body(users);
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	// 회원 정보에서 단건 조회 (Get) - Json + XML 응답!
	// -> Aceept에서 application/xml 과 application/json을 둘다 허용하고 응답값을 json 또는 xml로 변환
	@GetMapping(path = "/{id}", produces = {"application/json", "application/xml"})
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		Optional<User> user = userService.getUserById(id);
		user.orElseThrow(NoSuchElementException::new);
		return ResponseEntity.ok(user.get());
	}

	// 회원 등록 (POST) - Json + XML 요청 & 응답을 모두 처리
	// consumes : 사용자의 요청의 포멧
	@PostMapping(path = "/",
		consumes = {"application/json", "application/xml"},
		produces = {"application/json", "application/xml"})
	public ResponseEntity<User> register(@RequestBody User user) {
		User savedUser = userService.register(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	// 회원 등록 (POST) - Form 데이터, MultiPart file 처리용
	@PostMapping(path = "/create",
		consumes = {"application/x-www-form-urlencoded"},
		produces = {"application/json"})
	public ResponseEntity<User> createUser(User user) {
		User savedUser = userService.register(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	@PostMapping(path = "/create2")
	public ResponseEntity<User> createUser2(User user) {
		User savedUser = userService.register(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	// 회원 삭제 (DELETE) - 편리성이 좋은 방법 (비정석)
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<User> deleteUser2(@PathVariable Long id) {

		Optional<User> user = userService.getUserById(id);
		user.orElseThrow(NoSuchElementException::new);

		userService.deleteUser(id);
		return ResponseEntity.ok(user.get());
	}

	// 공통 예외 처리
	@ExceptionHandler(Exception.class)
	public ResponseEntity<User> handleException(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
