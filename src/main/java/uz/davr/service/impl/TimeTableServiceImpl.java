package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.timeTable.TimeTableRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.Status;
import uz.davr.model.TimeTable;
import uz.davr.service.TimeTableService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TimeTableServiceImpl implements TimeTableService {

    private final Database database;

    private static final Logger logger = LoggerFactory.getLogger(TimeTableServiceImpl.class);

    private final String INSERT_QUERY = "INSERT INTO time_table_tb (day_id, start_date, end_date) VALUES(?, ?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM time_table_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE time_table_tb SET day_id = ?, start_date = ?, end_date = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM time_table_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM time_table_tb";

    public TimeTableServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public RestAPIResponse addTimeTable(TimeTableRequest request) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setInt(1, request.getDay_id());
            preparedStatement.setDate(2, Date.valueOf(request.getStart_date()));
            preparedStatement.setDate(3, Date.valueOf(request.getEnd_date()));
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse getTimeTableById(int timeTableId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, timeTableId);
            ResultSet resultSet = preparedStatement.executeQuery();
            TimeTable timeTable = new TimeTable();
            if (resultSet.next()){
                timeTable.setId(resultSet.getInt(1));
                timeTable.setDay_id(resultSet.getInt(2));
                timeTable.setStart_date(resultSet.getDate(3));
                timeTable.setEnd_date(resultSet.getDate(4));
                return new RestAPIResponse("Object is found!",true, HttpStatus.OK, timeTable);
            }
            return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse editTimeTable(TimeTableRequest request, int id) {
        if(request == null)
            return new RestAPIResponse("TimeTable Object is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1, request.getDay_id());
            preparedStatement.setDate(2, Date.valueOf(request.getStart_date()));
            preparedStatement.setDate(3, Date.valueOf(request.getEnd_date()));
            preparedStatement.setInt(4, id);
            preparedStatement.execute();

            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse deleteTimeTable(int timeTableId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, timeTableId);
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
            List<TimeTable> timeTableList = new ArrayList<>();
            TimeTable timeTable;
            while(resultSet.next()) {
                timeTable = new TimeTable(
                        resultSet.getInt("id"),
                        resultSet.getInt("day_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("end_date"));
                timeTableList.add(timeTable);
            }
            return new RestAPIResponse("TimeTable List!",true, HttpStatus.OK, timeTableList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }
}
