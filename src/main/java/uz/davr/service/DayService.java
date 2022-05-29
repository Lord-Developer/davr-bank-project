package uz.davr.service;

import uz.davr.dto.request.day.DayEditRequest;
import uz.davr.dto.request.day.DayRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface DayService extends BaseService {

    RestAPIResponse addDay(DayRequest dayRequest);
    RestAPIResponse getDayById(int dayId);
    RestAPIResponse editDay(DayEditRequest dayRequest);
    RestAPIResponse deleteDay(int dayId);

}
