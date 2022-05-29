package uz.davr.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.status.StatusRequest;
import uz.davr.dto.request.student.StudentRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.StatusService;

@RestController
@RequestMapping("/api/statuses")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }


    @PostMapping
        public HttpEntity<?> add(@RequestBody StatusRequest request){
        RestAPIResponse apiResponse = statusService.addStatus(request);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable("id") int id){
        RestAPIResponse dayById = statusService.getById(id);
        return ResponseEntity.ok(dayById);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody StatusRequest request, @PathVariable int id){
        RestAPIResponse restAPIResponse = statusService.editStatus(request, id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") int id){
        RestAPIResponse restAPIResponse = statusService.deleteStatus(id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @GetMapping
    public HttpEntity<?> getList(){
        RestAPIResponse restAPIResponse = statusService.getList();
        return ResponseEntity.ok(restAPIResponse);
    }
}
