package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.group.GroupRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.Group;
import uz.davr.service.GroupService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final Database database;

    private static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final String INSERT_QUERY = "INSERT INTO group_tb (name, course_id, teacher_id, room_id, status_id, start_date, end_date) VALUES(?, ?, ?, ?, ?, ?, ? )";
    private final String GET_BY_ID_QUERY = "SELECT * FROM group_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE group_tb SET name = ?, course_id = ?, teacher_id = ?, room_id = ?, status_id = ?, start_date = ?, end_date = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM group_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM group_tb";

    public GroupServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public RestAPIResponse addGroup(GroupRequest request) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, request.getName());
            preparedStatement.setInt(2, request.getCourse_id());
            preparedStatement.setInt(3, request.getTeacher_id());
            preparedStatement.setInt(4, request.getRoom_id());
            preparedStatement.setInt(5, request.getStatus_id());
            preparedStatement.setDate(6, Date.valueOf(request.getStart_date()));
            preparedStatement.setDate(7, Date.valueOf(request.getEnd_date()));
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse getById(int groupId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Group group = new Group();
            if (resultSet.next()){
                group.setName(resultSet.getString("name"));
                group.setCourse_id(resultSet.getInt("course_id"));
                group.setTeacher_id(resultSet.getInt("teacher_id"));
                group.setRoom_id(resultSet.getInt("room_id"));
                group.setStatus_id(resultSet.getInt("status_id"));
                group.setStart_date(resultSet.getDate("start_date"));
                group.setEnd_date(resultSet.getDate("end_date"));
                return new RestAPIResponse("Object is found!",true, HttpStatus.OK, group);
            }
            return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse editGroup(GroupRequest request, int id) {
        if(request == null)
            return new RestAPIResponse("Group Object is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, request.getName());
            preparedStatement.setInt(2, request.getCourse_id());
            preparedStatement.setInt(3, request.getTeacher_id());
            preparedStatement.setInt(4, request.getRoom_id());
            preparedStatement.setInt(5, request.getStatus_id());
            preparedStatement.setDate(6, Date.valueOf(request.getStart_date()));
            preparedStatement.setDate(7, Date.valueOf(request.getEnd_date()));
            preparedStatement.setInt(8, id);
            preparedStatement.execute();

            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse deleteGroup(int groupId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, groupId);
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
            List<Group> groupList = new ArrayList<>();
            Group group;
            while(resultSet.next()) {
                group = new Group();
                group.setName(resultSet.getString("name"));
                group.setCourse_id(resultSet.getInt("course_id"));
                group.setTeacher_id(resultSet.getInt("teacher_id"));
                group.setRoom_id(resultSet.getInt("room_id"));
                group.setStatus_id(resultSet.getInt("status_id"));
                group.setStart_date(resultSet.getDate("start_date"));
                group.setEnd_date(resultSet.getDate("end_date"));
                groupList.add(group);
            }
            return new RestAPIResponse("GroupStudent List!",true, HttpStatus.OK, groupList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }
}
