package uz.davr.service;

import uz.davr.dto.request.groupStudent.GroupStudentRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface GroupStudentService extends BaseService {

    RestAPIResponse addGroupStudent(GroupStudentRequest request);
    RestAPIResponse getById(int groupStudentId);
    RestAPIResponse editGroupStudent(GroupStudentRequest request, int id);
    RestAPIResponse deleteGroupStudent(int groupStudentId);
}
