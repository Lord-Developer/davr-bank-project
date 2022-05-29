package uz.davr.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.day.DayEditRequest;
import uz.davr.dto.request.day.DayRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.DayService;

@RestController
@RequestMapping("/api/days")
public class DayController {

    private final DayService dayService;

    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    @PostMapping
    public HttpEntity<?> add(@RequestBody DayRequest request){
        RestAPIResponse apiResponse = dayService.addDay(request);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable("id") int id){
        RestAPIResponse dayById = dayService.getDayById(id);
        return ResponseEntity.ok(dayById);
    }

    @PutMapping
    public HttpEntity<?> editDay(@RequestBody DayEditRequest request){
        RestAPIResponse restAPIResponse = dayService.editDay(request);
        return ResponseEntity.ok(restAPIResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteDay(@PathVariable("id") int id){
        RestAPIResponse restAPIResponse = dayService.deleteDay(id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @GetMapping
    public HttpEntity<?> getList(){
        RestAPIResponse restAPIResponse = dayService.getList();
        return ResponseEntity.ok(restAPIResponse);
    }
}
