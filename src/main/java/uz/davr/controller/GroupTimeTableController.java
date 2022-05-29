package uz.davr.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.groupTimeTable.GroupTimeTableRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.GroupTimeTableService;

@RestController
@RequestMapping("/api/groupTimeTables")
public class GroupTimeTableController {

    private final GroupTimeTableService groupTimeTableService;

    public GroupTimeTableController(GroupTimeTableService groupTimeTableService) {
        this.groupTimeTableService = groupTimeTableService;
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody GroupTimeTableRequest request){
        RestAPIResponse apiResponse = groupTimeTableService.addGroupTimeTable(request);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable("id") int id){
        RestAPIResponse dayById = groupTimeTableService.getGroupTimeTableById(id);
        return ResponseEntity.ok(dayById);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody GroupTimeTableRequest request, @PathVariable int id){
        RestAPIResponse restAPIResponse = groupTimeTableService.editGroupTimeTable(request, id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") int id){
        RestAPIResponse restAPIResponse = groupTimeTableService.deleteGroupTimeTable(id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @GetMapping
    public HttpEntity<?> getList(){
        RestAPIResponse restAPIResponse = groupTimeTableService.getList();
        return ResponseEntity.ok(restAPIResponse);
    }
}
