package uz.davr.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.groupStudent.GroupStudentRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.GroupStudentService;

@RestController
@RequestMapping("/api/groupStudents")
public class GroupStudentController {

    private final GroupStudentService groupStudentService;

    public GroupStudentController(GroupStudentService groupStudentService) {
        this.groupStudentService = groupStudentService;
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody GroupStudentRequest request){
        RestAPIResponse apiResponse = groupStudentService.addGroupStudent(request);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable("id") int id){
        RestAPIResponse dayById = groupStudentService.getById(id);
        return ResponseEntity.ok(dayById);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody GroupStudentRequest request, @PathVariable int id){
        RestAPIResponse restAPIResponse = groupStudentService.editGroupStudent(request, id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") int id){
        RestAPIResponse restAPIResponse = groupStudentService.deleteGroupStudent(id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @GetMapping
    public HttpEntity<?> getList(){
        RestAPIResponse restAPIResponse = groupStudentService.getList();
        return ResponseEntity.ok(restAPIResponse);
    }
}
