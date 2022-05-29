package uz.davr.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.davr.dto.request.payType.PayTypeRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.PayTypeService;

@RestController
@RequestMapping("/api/payTypes")
public class PayTypeController {


    private final PayTypeService payTypeService;

    public PayTypeController(PayTypeService payTypeService) {
        this.payTypeService = payTypeService;
    }


    @PostMapping
    public HttpEntity<?> add(@RequestBody PayTypeRequest request){
        RestAPIResponse apiResponse = payTypeService.addPayType(request);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/{id}")
    public HttpEntity<?> getById(@PathVariable("id") int id){
        RestAPIResponse dayById = payTypeService.getById(id);
        return ResponseEntity.ok(dayById);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> edit(@RequestBody PayTypeRequest request, @PathVariable int id){
        RestAPIResponse restAPIResponse = payTypeService.editPayType(request, id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable("id") int id){
        RestAPIResponse restAPIResponse = payTypeService.deletePayType(id);
        return ResponseEntity.ok(restAPIResponse);
    }

    @GetMapping
    public HttpEntity<?> getList(){
        RestAPIResponse restAPIResponse = payTypeService.getList();
        return ResponseEntity.ok(restAPIResponse);
    }
}
