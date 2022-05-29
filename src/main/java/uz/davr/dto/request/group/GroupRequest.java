package uz.davr.dto.request.group;

import lombok.Data;

import java.util.Date;

@Data
public class GroupRequest {

    private String name;
    private int course_id;
    private int teacher_id;
    private int room_id;
    private String start_date;
    private String end_date;
    private int status_id;
}
