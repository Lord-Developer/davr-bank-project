package uz.davr.service;

import uz.davr.dto.request.timeTable.TimeTableRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface TimeTableService extends BaseService {

    RestAPIResponse addTimeTable(TimeTableRequest request);
    RestAPIResponse getTimeTableById(int timeTableId);
    RestAPIResponse editTimeTable(TimeTableRequest request, int id);
    RestAPIResponse deleteTimeTable(int timeTableId);
}
