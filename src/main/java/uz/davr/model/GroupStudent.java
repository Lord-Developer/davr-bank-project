package uz.davr.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupStudent {

    private int id;
    private int group_id;
    private int student_id;

}
