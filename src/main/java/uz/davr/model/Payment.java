package uz.davr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {
    private int id;
    private int pay_type_id;
    private double sum;
    private String description;
    private int student_id;
    private Date created_date;

}
