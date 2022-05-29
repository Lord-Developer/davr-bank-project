package uz.davr.service;

import uz.davr.dto.request.status.StatusRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface StatusService extends BaseService {
    RestAPIResponse addStatus(StatusRequest request);
    RestAPIResponse getById(int statusId);
    RestAPIResponse editStatus(StatusRequest request, int id);
    RestAPIResponse deleteStatus(int statusId);
}
