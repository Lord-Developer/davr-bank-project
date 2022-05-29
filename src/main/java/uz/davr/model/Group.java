package uz.davr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Group {

    private int id;
    private String name;
    private int course_id;
    private int teacher_id;
    private int room_id;
    private int status_id;
    private Date start_date;
    private Date end_date;



}
