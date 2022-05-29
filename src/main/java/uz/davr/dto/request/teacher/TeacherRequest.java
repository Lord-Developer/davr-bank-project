package uz.davr.dto.request.teacher;

import lombok.Data;

@Data
public class TeacherRequest {
    private String full_name;
    private String phone;
    private double salary;
}
