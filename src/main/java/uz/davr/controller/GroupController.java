package uz.davr.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.group.GroupRequest;
import uz.davr.dto.request.groupStudent.GroupStudentRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody GroupRequest request){
        RestAPIResponse apiResponse = groupService.addGroup(request);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable("id") int id){
        RestAPIResponse dayById = groupService.getById(id);
        return ResponseEntity.ok(dayById);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody GroupRequest request, @PathVariable int id){
        RestAPIResponse restAPIResponse = groupService.editGroup(request, id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") int id){
        RestAPIResponse restAPIResponse = groupService.deleteGroup(id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @GetMapping
    public HttpEntity<?> getList(){
        RestAPIResponse restAPIResponse = groupService.getList();
        return ResponseEntity.ok(restAPIResponse);
    }
}
