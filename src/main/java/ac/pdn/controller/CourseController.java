package ac.pdn.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ac.pdn.model.Course;
import ac.pdn.model.CourseRowMapper;

@Controller
public class CourseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/courses")
    public String getCourses(Model model, HttpSession session) {
        if (session.getAttribute("studentEmail") == null) {
            return "redirect:/login";
        }

        String studentEmail = (String) session.getAttribute("studentEmail");

        
        String studentId = jdbcTemplate.queryForObject(
            "SELECT student_id FROM students WHERE email = ?",
            new Object[]{studentEmail},
            String.class
        );

        
        String sql = "SELECT * FROM courses";
        List<Course> courses = jdbcTemplate.query(sql, new CourseRowMapper());
        model.addAttribute("courses", courses);

        
        List<String> registeredCourseIds = jdbcTemplate.queryForList(
            "SELECT course_id FROM registrations WHERE student_id = ?",
            new Object[]{studentId},
            String.class
        );
        model.addAttribute("registeredCourseIds", registeredCourseIds); 

        return "courses";
    }

    
    @PostMapping("/register/{courseId}")
    public String registerCourse(@PathVariable String courseId, HttpSession session) {
        String studentEmail = (String) session.getAttribute("studentEmail");

        if (studentEmail != null) {
            try {
                String sql = "SELECT student_id FROM students WHERE email = ?";
                String studentId = jdbcTemplate.queryForObject(sql, new Object[]{studentEmail}, String.class);

                String checkSql = "SELECT COUNT(*) FROM registrations WHERE student_id = ? AND course_id = ?";
                Integer count = jdbcTemplate.queryForObject(checkSql, new Object[]{studentId, courseId}, Integer.class);

                if (count != null && count == 0) {
                    jdbcTemplate.update(
                        "INSERT INTO registrations (student_id, course_id) VALUES (?, ?)",
                        studentId, courseId
                    );
                    return "success";
                } else {
                    return "redirect:/courses?error=alreadyRegistered";
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/courses?error=registrationFailed";
            }
        } else {
            return "redirect:/login";
        }
    }
}
