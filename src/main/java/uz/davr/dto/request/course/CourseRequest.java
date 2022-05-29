package uz.davr.dto.request.course;

import lombok.Data;

@Data
public class CourseRequest {
    private String name;
    private double price;
    private String duration;
}
