package hei.school;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GradeReport gradeReport;

    @GetMapping("/students")
    public List<Map<String, Object>> list() {
        return jdbcTemplate.queryForList("SELECT id, reference, full_name, email, phone, cohort FROM student ORDER BY full_name");
    }

    @GetMapping("/students/{id}/transcript")
    public ResponseEntity<?> transcript(@PathVariable long id) {
        Map<String, Object> report = gradeReport.build(id);
        if (report == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Étudiant introuvable"));
        }
        return ResponseEntity.ok(report);
    }
}
