package uz.davr.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.room.RoomRequest;
import uz.davr.dto.request.teacher.TeacherRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.RoomService;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {


    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }


    @PostMapping
    public HttpEntity<?> add(@RequestBody RoomRequest request){
        RestAPIResponse apiResponse = roomService.addRoom(request);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable("id") int id){
        RestAPIResponse dayById = roomService.getRoomById(id);
        return ResponseEntity.ok(dayById);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody RoomRequest request, @PathVariable int id){
        RestAPIResponse restAPIResponse = roomService.editRoom(request, id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") int id){
        RestAPIResponse restAPIResponse = roomService.deleteRoom(id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @GetMapping
    public HttpEntity<?> getList(){
        RestAPIResponse restAPIResponse = roomService.getList();
        return ResponseEntity.ok(restAPIResponse);
    }
}
