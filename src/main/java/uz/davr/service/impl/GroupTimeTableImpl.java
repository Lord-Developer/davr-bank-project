package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.groupTimeTable.GroupTimeTableRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.GroupTimeTable;
import uz.davr.model.PayType;
import uz.davr.model.Status;
import uz.davr.service.GroupTimeTableService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupTimeTableImpl implements GroupTimeTableService {

    private final Database database;

    private static final Logger logger = LoggerFactory.getLogger(GroupTimeTableImpl.class);

    private final String INSERT_QUERY = "INSERT INTO group_time_table_tb (time_table_id, group_id) VALUES(?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM group_time_table_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE group_time_table_tb SET time_table_id = ?, group_id = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM group_time_table_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM group_time_table_tb";

    public GroupTimeTableImpl(Database database) {
        this.database = database;
    }

    @Override
    public RestAPIResponse addGroupTimeTable(GroupTimeTableRequest request) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setInt(1, request.getTime_table_id());
            preparedStatement.setInt(2, request.getGroup_id());
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse getGroupTimeTableById(int groupTimeTableId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, groupTimeTableId);
            ResultSet resultSet = preparedStatement.executeQuery();
            GroupTimeTable groupTimeTable = new GroupTimeTable();
            if (resultSet.next()){
                groupTimeTable.setId(resultSet.getInt(1));
                groupTimeTable.setTime_table_id(resultSet.getInt(2));
                groupTimeTable.setGroup_id(resultSet.getInt(3));
                return new RestAPIResponse("Object is found!",true, HttpStatus.OK, groupTimeTable);
            }
            return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse editGroupTimeTable(GroupTimeTableRequest request, int id) {
        if(request == null)
            return new RestAPIResponse("GroupTimeTable Object is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1, request.getTime_table_id());
            preparedStatement.setInt(2, request.getGroup_id());
            preparedStatement.setInt(3, id);
            preparedStatement.execute();

            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse deleteGroupTimeTable(int groupTimeTableId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, groupTimeTableId);
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
            List<GroupTimeTable> groupTimeTableList = new ArrayList<>();
            GroupTimeTable groupTimeTable;
            while(resultSet.next()) {
                groupTimeTable = new GroupTimeTable(
                        resultSet.getInt("id"),
                        resultSet.getInt("time_table_id"),
                        resultSet.getInt("group_id"));
                groupTimeTableList.add(groupTimeTable);
            }
            return new RestAPIResponse("Status List!",true, HttpStatus.OK, groupTimeTableList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }
}
