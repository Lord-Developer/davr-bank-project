package uz.davr.service;

import uz.davr.dto.request.group.GroupRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface GroupService extends BaseService {

    RestAPIResponse addGroup(GroupRequest request);
    RestAPIResponse getById(int groupId);
    RestAPIResponse editGroup(GroupRequest request, int id);
    RestAPIResponse deleteGroup(int groupId);
}
