package ac.pdn.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setCourseCode(rs.getString("course_id"));
        course.setName(rs.getString("name"));
        course.setInstructor(rs.getString("instructor"));
        course.setCredits(rs.getInt("credits"));
        return course;
    }
}

