package uz.davr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TimeTable {
    private int id;
    private int day_id;
    private Date start_date;
    private Date end_date;
}
