package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
            private final MemberRepository memberRepository;
            private final SessionManager sessionManager;


   // @GetMapping("/")
    public String home() {
        return "home";
    }

   // @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model){

        if(memberId == null) {
            return "home";
        }

        //로그인
        Member loginMember = memberRepository.findById(memberId);
        if (loginMember == null) { //db에서 찾은 member가 없으면 home 화면으로
            return "home";
        }
       //있으면 model addAttribute에 담고 loginhome
        model.addAttribute("member",loginMember);
        return "loginHome";
    }


    //@GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model){

       //세션관리제아 저장된 회원 정보 조회
        Member member = (Member) sessionManager.getSession(request);
        //로그인
        if (member == null) { //db에서 찾은 member가 없으면 home 화면으로
            return "home";
        }
        //있으면 model addAttribute에 담고 loginhome
        model.addAttribute("member",member);
        return "loginHome";
    }

   // @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model){

        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
        //로그인
        if (loginMember == null) { //db에서 찾은 member가 없으면 home 화면으로
            return "home";
        }
        //세션이 유지되면 로그인홈으로 이동
        model.addAttribute("member",loginMember);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV3Spring(
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model){


        //로그인
        if (loginMember == null) { //db에서 찾은 member가 없으면 home 화면으로
            return "home";
        }
        //세션이 유지되면 로그인홈으로 이동
        model.addAttribute("member",loginMember);
        return "loginHome";
    }
}