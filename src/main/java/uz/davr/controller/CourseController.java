package uz.davr.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.course.CourseRequest;

import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {


    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @PostMapping
    public HttpEntity<?> add(@RequestBody CourseRequest request){
        RestAPIResponse apiResponse = courseService.addCourse(request);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable("id") int id){
        RestAPIResponse dayById = courseService.getCourseById(id);
        return ResponseEntity.ok(dayById);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> editCourse(@RequestBody CourseRequest request, @PathVariable int id){
        RestAPIResponse restAPIResponse = courseService.editCourse(request, id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") int id){
        RestAPIResponse restAPIResponse = courseService.deleteCourse(id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @GetMapping
    public HttpEntity<?> getList(){
        RestAPIResponse restAPIResponse = courseService.getList();
        return ResponseEntity.ok(restAPIResponse);
    }
}
