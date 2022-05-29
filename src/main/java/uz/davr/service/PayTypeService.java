package uz.davr.service;

import uz.davr.dto.request.payType.PayTypeRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface PayTypeService extends BaseService {

    RestAPIResponse addPayType(PayTypeRequest request);
    RestAPIResponse getById(int payTypeId);
    RestAPIResponse editPayType(PayTypeRequest request, int id);
    RestAPIResponse deletePayType(int payTypeId);
}
