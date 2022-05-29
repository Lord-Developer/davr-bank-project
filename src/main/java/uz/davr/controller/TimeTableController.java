package uz.davr.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.payType.PayTypeRequest;
import uz.davr.dto.request.timeTable.TimeTableRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.TimeTableService;

@RestController
@RequestMapping("/api/timeTables")
public class TimeTableController {

    private final TimeTableService timeTableService;

    public TimeTableController(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody TimeTableRequest request){
        RestAPIResponse apiResponse = timeTableService.addTimeTable(request);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable("id") int id){
        RestAPIResponse dayById = timeTableService.getTimeTableById(id);
        return ResponseEntity.ok(dayById);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody TimeTableRequest request, @PathVariable int id){
        RestAPIResponse restAPIResponse = timeTableService.editTimeTable(request, id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") int id){
        RestAPIResponse restAPIResponse = timeTableService.deleteTimeTable(id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @GetMapping
    public HttpEntity<?> getList(){
        RestAPIResponse restAPIResponse = timeTableService.getList();
        return ResponseEntity.ok(restAPIResponse);
    }
}
