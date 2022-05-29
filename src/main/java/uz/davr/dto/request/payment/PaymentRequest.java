package uz.davr.dto.request.payment;

import lombok.Data;


@Data
public class PaymentRequest {

    private int id;
    private int pay_type_id;
    private double sum;
    private String description;
    private int student_id;
    private String created_date;
}
