package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.status.StatusRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.Status;
import uz.davr.model.Student;
import uz.davr.service.StatusService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    private final Database database;

    private static final Logger logger = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final String INSERT_QUERY = "INSERT INTO status_tb (name, description) VALUES(?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM status_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE status_tb SET name = ?, description = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM status_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM status_tb";

    public StatusServiceImpl(Database database) {
        this.database = database;
    }


    @Override
    public RestAPIResponse addStatus(StatusRequest request) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, request.getName());
            preparedStatement.setString(2, request.getDescription());
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse getById(int statusId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, statusId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Status status = new Status();
            if (resultSet.next()){
                status.setId(resultSet.getInt(1));
                status.setName(resultSet.getString(2));
                status.setDescription(resultSet.getString(3));
                return new RestAPIResponse("Object is found!",true, HttpStatus.OK, status);
            }
            return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse editStatus(StatusRequest request, int id) {
        if(request == null)
            return new RestAPIResponse("Status Object is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, request.getName());
            preparedStatement.setString(2, request.getDescription());
            preparedStatement.setInt(3, id);
            preparedStatement.execute();

            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse deleteStatus(int statusId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, statusId);
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
            List<Status> statusList = new ArrayList<>();
            Status status;
            while(resultSet.next()) {
                status = new Status(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"));
                statusList.add(status);
            }
            return new RestAPIResponse("Status List!",true, HttpStatus.OK, statusList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }




}


