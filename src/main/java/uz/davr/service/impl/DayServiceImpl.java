package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.day.DayEditRequest;
import uz.davr.dto.request.day.DayRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.Day;
import uz.davr.service.DayService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class DayServiceImpl implements DayService {

    private final Database database;
    private final String INSERT_QUERY = "INSERT INTO day_tb (name) VALUES(?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM day_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE day_tb SET name = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM day_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM day_tb";
    private static final Logger logger = LoggerFactory.getLogger(DayServiceImpl.class);


    public DayServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public RestAPIResponse addDay(DayRequest dayRequest) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, dayRequest.getName());
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);


    }

    @Override
    public RestAPIResponse getDayById(int dayId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, dayId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Day day = new Day();
            if (resultSet.next()){
                day.setId(resultSet.getInt(1));
                day.setName(resultSet.getString(2));
                return new RestAPIResponse("Object is found!",true, HttpStatus.OK, day);
            }
            return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public RestAPIResponse editDay(DayEditRequest dayRequest) {
        if(dayRequest.getName() == null)
            return new RestAPIResponse("Day Name is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, dayRequest.getName());
            preparedStatement.setInt(2, dayRequest.getDayId());

            preparedStatement.execute();

            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse deleteDay(int dayId) {

        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, dayId);
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
            List<Day> dayList = new ArrayList<>();
            Day day;
            while(resultSet.next()) {
                day = new Day(resultSet.getInt("id"), resultSet.getString("name"));
                dayList.add(day);
            }
            return new RestAPIResponse("Day List!",true, HttpStatus.OK, dayList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);
    }
}
