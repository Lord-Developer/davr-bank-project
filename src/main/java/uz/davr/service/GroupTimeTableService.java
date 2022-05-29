package uz.davr.service;

import uz.davr.dto.request.groupTimeTable.GroupTimeTableRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface GroupTimeTableService extends BaseService {

    RestAPIResponse addGroupTimeTable(GroupTimeTableRequest request);
    RestAPIResponse getGroupTimeTableById(int groupTimeTableId);
    RestAPIResponse editGroupTimeTable(GroupTimeTableRequest request, int id);
    RestAPIResponse deleteGroupTimeTable(int groupTimeTableId);
}
