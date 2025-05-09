package ac.pdn.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,@RequestParam String password,HttpSession session,Model model) {
        try {
            String sql = "SELECT student_id FROM students WHERE email = ? AND password = ?";
            String studentId = jdbcTemplate.queryForObject(sql, new Object[]{email, password}, String.class);

            if (studentId != null) {
                session.setAttribute("studentId", studentId);
                session.setAttribute("studentEmail", email);
                return "redirect:/courses";
            } else {
                model.addAttribute("error", "Invalid credentials.");
                return "login";
            }

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Login failed. Please try again.");
            return "login";
        }
    }
}
