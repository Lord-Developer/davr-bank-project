package uz.davr.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.student.StudentRequest;
import uz.davr.dto.request.teacher.TeacherRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping
    public HttpEntity<?> add(@RequestBody StudentRequest request){
        RestAPIResponse apiResponse = studentService.addStudent(request);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable("id") int id){
        RestAPIResponse dayById = studentService.getStudentById(id);
        return ResponseEntity.ok(dayById);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody StudentRequest request, @PathVariable int id){
        RestAPIResponse restAPIResponse = studentService.editStudent(request, id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") int id){
        RestAPIResponse restAPIResponse = studentService.deleteStudent(id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @GetMapping
    public HttpEntity<?> getList(){
        RestAPIResponse restAPIResponse = studentService.getList();
        return ResponseEntity.ok(restAPIResponse);
    }
}
