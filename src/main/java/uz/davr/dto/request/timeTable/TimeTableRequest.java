package uz.davr.dto.request.timeTable;

import lombok.Data;


@Data
public class TimeTableRequest {
    private int day_id;
    private String start_date;
    private String end_date;
}
