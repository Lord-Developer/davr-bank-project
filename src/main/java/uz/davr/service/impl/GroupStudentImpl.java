package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.groupStudent.GroupStudentRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.GroupStudent;
import uz.davr.model.PayType;
import uz.davr.model.Status;
import uz.davr.service.GroupStudentService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupStudentImpl implements GroupStudentService {

    private final Database database;

    private static final Logger logger = LoggerFactory.getLogger(GroupStudentImpl.class);

    private final String INSERT_QUERY = "INSERT INTO group_student_tb (group_id, student_id) VALUES(?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM group_student_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE group_student_tb SET group_id = ?, student_id = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM group_student_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM group_student_tb";

    public GroupStudentImpl(Database database) {
        this.database = database;
    }

    @Override
    public RestAPIResponse addGroupStudent(GroupStudentRequest request) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setInt(1, request.getGroup_id());
            preparedStatement.setInt(2, request.getStudent_id());
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse getById(int groupStudentId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, groupStudentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Status status = new Status();
            if (resultSet.next()){
                status.setId(resultSet.getInt(1));
                status.setName(resultSet.getString(2));
                return new RestAPIResponse("Object is found!",true, HttpStatus.OK, status);
            }
            return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse editGroupStudent(GroupStudentRequest request, int id) {
        if(request == null)
            return new RestAPIResponse("GroupStudent Object is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1, request.getGroup_id());
            preparedStatement.setInt(2, request.getStudent_id());
            preparedStatement.setInt(3, id);
            preparedStatement.execute();

            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse deleteGroupStudent(int groupStudentId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, groupStudentId);
            preparedStatement.execute();
            return new RestAPIResponse("Successfully deleted!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse getList() {
        try {
            Statement statement = database.connect().createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_QUERY);
            int index = 1;
            List<GroupStudent> groupStudentList = new ArrayList<>();
            GroupStudent groupStudent;
            while(resultSet.next()) {
                groupStudent = new GroupStudent(
                        resultSet.getInt("id"),
                        resultSet.getInt("group_id"),
                        resultSet.getInt("student_id"));
                groupStudentList.add(groupStudent);
            }
            return new RestAPIResponse("GroupStudent List!",true, HttpStatus.OK, groupStudentList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }
}
