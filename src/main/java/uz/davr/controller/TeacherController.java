package uz.davr.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.course.CourseRequest;
import uz.davr.dto.request.teacher.TeacherRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.TeacherService;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {


    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }


    @PostMapping
    public HttpEntity<?> add(@RequestBody TeacherRequest request){
        RestAPIResponse apiResponse = teacherService.addTeacher(request);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable("id") int id){
        RestAPIResponse dayById = teacherService.getTeacherById(id);
        return ResponseEntity.ok(dayById);
    }

    @PutMapping("/{id}")
        public HttpEntity<?> edit(@RequestBody TeacherRequest request, @PathVariable int id){
        RestAPIResponse restAPIResponse = teacherService.editTeacher(request, id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") int id){
        RestAPIResponse restAPIResponse = teacherService.deleteTeacher(id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @GetMapping
    public HttpEntity<?> getList(){
        RestAPIResponse restAPIResponse = teacherService.getList();
        return ResponseEntity.ok(restAPIResponse);
    }
}
