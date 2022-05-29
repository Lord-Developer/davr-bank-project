package uz.davr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupTimeTable {
    private int id;
    private int time_table_id;
    private int group_id;
}
