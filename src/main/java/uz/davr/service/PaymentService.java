package uz.davr.service;

import uz.davr.dto.request.payment.PaymentRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface PaymentService extends BaseService {

    RestAPIResponse addPayment(PaymentRequest request);
    RestAPIResponse getPaymentById(int paymentRequestId);
    RestAPIResponse editPayment(PaymentRequest request, int id);
    RestAPIResponse deletePayment(int paymentRequestId);
}
