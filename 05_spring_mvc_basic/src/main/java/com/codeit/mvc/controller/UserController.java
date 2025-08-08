package com.codeit.mvc.controller;

import com.codeit.mvc.domain.User;
import com.codeit.mvc.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Controller
// class 레벨에서 /users 경로를 붙여주는 역할
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;

	// @RequestMapping : 가장 기본적인 요청 처리 어노테이션 최초의 url 맵핑 어노테이션
	// /index.do <- 레거시한 표현 스프링 프레임워크에서 사용했던 패턴 중 하나 (동적인 확장자)
	@RequestMapping(value = {"/index.do", "/index", "/"}, method = RequestMethod.GET)
	public String index(/*파라미터 주입 자리*/) {
		return "users/index";
	}
	// ------------------- 헨들러 메서드 스타일 정리 --------------------//
	// 1. 서블릿 스타일
	// 장점 : 서블릿 프로젝트 호환용
	// 단점 : 복잡함, 거의 사용안함.

	// get 처리
	@GetMapping("/userServlet")
	public String userServlet() {
		return "users/userEnrollForServlet";
	}

	@PostMapping("/userServlet")
	public String userServlet(HttpServletRequest request, HttpServletResponse response
		, HttpSession httpSession) {
		User user = new User();
		user.setId(Long.parseLong(request.getParameter("id")));
		user.setUsername(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setRoles(request.getParameter("roles"));
		user.setDevLang(Arrays.asList(request.getParameter("devLang").split(",")));

		// model을 대신하는 방법
		request.setAttribute("user", user);

		// 쿠키 활용법
		Cookie cookie = new Cookie("saveUsername", user.getUsername());
		cookie.setMaxAge(60 * 60);  // 1 시간
		response.addCookie(cookie);

		// 세션 가져오는 법
		request.getSession().setAttribute("username", user.getUsername());

		// 세션 가져오는 방법 2, spring 에서 세션 사용 하는 방법 중 하나.
		httpSession.setAttribute("username", user.getUsername());

		return "users/userView";
	}

	// 2. @RequestParam을 통해 View - Form의 파라미터를 받아오는 방법
	// - RequestParam을 통해 form에 있는 name과 핸들러 메서드의 파라미터를 맵핑시키는 방법
	// - 파라미터가 1~2개 까지는 해당 방법을 권장, 3개 이상 넘어가면 DTO 활용 권장
	// - model : view로 데이터를 보낼때 사용하는 객체
	// - @RequestParam(value="form의 name") : 파라미터를 받아오는 방법
	//    -> 만일 form name과 메소드의 파라미터 인자명이 일치하면 생략 가능, 만일 다르면 반드시 있어야한다.
	// - 옵션 : defaultValue = 만일 값이 없으면 기본값 셋팅, required = 필수값 셋팅, 없으면 처리X
	// - 만일 배열의 파라미터인 경우, List<String>, String[] 둘다 처리가능
	@GetMapping("/userParams")
	public String userParamsPage() {
		return "users/userEnrollForParams";
	}

	//@PostMapping("/userParams")
	public String userParams1(Model model,
		@RequestParam Long id,
		@RequestParam String username,
		@RequestParam String password,
		@RequestParam String name,
		@RequestParam String roles,
		@RequestParam(value = "devLang", required = false) List<String> devLang) {
		User user = new User(id, username, password, name, roles, devLang);
		model.addAttribute("user", user);

		return "users/userView";
	}

	@PostMapping("/userParams")
	public String userParams(Model model, Long id, String username, String password,
		String name, String roles, String[] devLang) {
		User user = new User(id, username, password, name, roles, Arrays.asList(devLang));
		model.addAttribute("user", user);

		return "users/userView";
	}

	// 3. Command 객체(VO, DTO)로 파라미터 처리하는 방법
	// - 자바의 객체와 From의 name을 일치시켜 파라미터를 바인딩하는 방식
	// - 반드시 변수 이름과 type 일치해야 자동으로 바인딩 된다.
	// - ※ 주의 : 바인딩 될 객체에 기본 자료형, 배열, List만 허용 가능, 이외 객체형은 처리 안됨, ex) Date, LocalDate
	// - 복잡한 타입은 Converter 또는 Formatter가 필요 -> 날짜는 Formatter로 쉽게 처리가능하다.
	//   ex) @DateTimeFormat(pattern = "yyyy-MM-dd") private LocalDate birthDate;
	// - 실제 현업에서 가장 많이 활용되는 스타일로 반드시 알고있자.

	// @ModelAttribute : View에 넘길 데이터를 지정된 이름으로 바인딩하거나, 커맨드 객체 바인딩에 사용, 약관 관례적!, Rest에서 빼도됨.
	@GetMapping("/userCommand")
	public String userCommandPage() {
		return "users/userEnrollForCommand";
	}

	//@PostMapping("/userCommand")
	public String userCommand(Model model, User user) {
		model.addAttribute("user", user);
		return "users/userView";
	}

	// Model 없이 @ModelAttribute로 사용하는 법 권장 X
	@PostMapping("/userCommand")
	public String userCommand1(@ModelAttribute User user) {
		return "users/userView";
	}

	@GetMapping("/userMap")
	public String userMapPage() {
		return "users/userEnrollForMap";
	}

	// list는 사용하지 못한다.
	//@PostMapping("/userMap")
	public String userMap1(Model model, @RequestParam Map<String, Object> map) {
		model.addAttribute("user", map);
		return "users/userView";
	}

	// List를 따로 추가하는 버전
	@PostMapping("/userMap")
	public String userMap2(Model model, @RequestParam Map<String, Object> map,
		@RequestParam(value = "devLang", required = false) List<String> devLang) {

		map.put("devLang", devLang);
		model.addAttribute("user", map);
		return "users/userView";
	}

	// 5. header, cookie, Session 정보 받아 오기
	// Writer : 사용자가 직접 본문을 작성할때 사용할수 있다. -> response에 있었던 객체
	// @RequestHeader : header값 가져오는 어노테이션
	// @CookieValue : 쿠기 정보를 가져올때 사용하는 어노테이션, required = false 일때 null 허용된다.
	// @SessionAttribute : 세션 정보를 가져올때 사용하는 어노테이션, required = false 일때 null 허용된다.
	// Locale : 사용자 요청 언어
	// void 인경우는 return 없을때 활용 -> view가 존재하지 않는다!
	// 참고로 REST는 더 좋게 만드는 방법이 있음으로 Writer 쓰지 말것.
	@GetMapping("/userAddInfo")
	public void userAddInfo(Writer writer,
		@RequestHeader(value = "User-Agent") String userAgent,
		@RequestHeader(value = "Accept", required = false) String accept,
		@RequestHeader(value = "Content-Type", required = false) String contentType,
		@RequestHeader(value = "referer") String referer,
		@CookieValue(value = "saveUsername", required = false) String cookieUsername,
		@SessionAttribute(value = "username", required = false) String sessionUsername,
		Locale locale
	) throws IOException {
		writer.append("<html>");
		writer.append("userAgent : " + userAgent + "<br>");
		writer.append("Accept : " + accept + "<br>");
		writer.append("Content-Type : " + contentType + "<br>");
		writer.append("referer : " + referer + "<br>");
		writer.append("saveUsername : " + cookieUsername + "<br>");
		writer.append("username : " + sessionUsername + "<br>");
		writer.append("locale : " + locale + "<br>");
		writer.append("</html>");

	}

	@GetMapping("/userAddInfo2")
	@ResponseBody
	public String userAddInfo2(@RequestHeader(value = "User-Agent") String userAgent,
		@RequestHeader(value = "Accept", required = false) String accept,
		@RequestHeader(value = "Content-Type", required = false) String contentType,
		@RequestHeader(value = "referer") String referer,
		@CookieValue(value = "saveUsername", required = false) String cookieUsername,
		@SessionAttribute(value = "username", required = false) String sessionUsername) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("userAgent : " + userAgent + "<br>");
		sb.append("Accept : " + accept + "<br>");
		sb.append("Content-Type : " + contentType + "<br>");
		sb.append("referer : " + referer + "<br>");
		sb.append("saveUsername : " + cookieUsername + "<br>");
		sb.append("username : " + sessionUsername + "<br>");
		sb.append("</html>");

		return sb.toString();
	}

	@RequestMapping(value = "/userEnroll", method = RequestMethod.GET)
	public ModelAndView userEnrollPage(ModelAndView modelAndView) {
		modelAndView.setViewName("users/userEnroll");
		return modelAndView;
	}

	@RequestMapping(value = "/userEnroll", method = RequestMethod.POST)
	public ModelAndView userEnroll(ModelAndView modelAndView, User user) {
		User register = userService.register(user);
		if (register != null && register.getId() != 100L) {
			modelAndView.addObject("msg", "회원가입에 성공하였습니다.");
			modelAndView.addObject("location", "/users/index.do");
			modelAndView.setViewName("common/msg");
		} else {
			modelAndView.setViewName("redirect:/users/error.do"); // 핸들러 끼리 연결시키는 방법 중 하나.
		}
		return modelAndView;
	}

	// 7. List를 통한 회원정보 보기
	@GetMapping("/userList")
	public String userListPage(Model model) {
		List<User> list = userService.getAllUsers();
		model.addAttribute("list", list);
		return "users/userList";
	}

	// 8. 에러 페이지 (redirect 실험용)
	@GetMapping("/error.do")
	public String error(Model model) {
		return "common/error";
	}
}
