package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.payType.PayTypeRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.PayType;
import uz.davr.model.Status;
import uz.davr.service.PayTypeService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayTypeServiceImpl implements PayTypeService {

    private final Database database;

    private static final Logger logger = LoggerFactory.getLogger(PayTypeServiceImpl.class);

    private final String INSERT_QUERY = "INSERT INTO pay_type_tb (name) VALUES(?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM pay_type_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE pay_type_tb SET name = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM pay_type_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM pay_type_tb";

    public PayTypeServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public RestAPIResponse addPayType(PayTypeRequest request) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, request.getName());
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse getById(int payTypeId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, payTypeId);
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
    public RestAPIResponse editPayType(PayTypeRequest request, int id) {
        if(request == null)
            return new RestAPIResponse("PayType Object is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, request.getName());
            preparedStatement.setInt(2, id);
            preparedStatement.execute();

            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse deletePayType(int payTypeId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, payTypeId);
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
            List<PayType> payTypeList = new ArrayList<>();
            PayType payType;
            while(resultSet.next()) {
                payType = new PayType(
                        resultSet.getInt("id"),
                        resultSet.getString("name"));
                payTypeList.add(payType);
            }
            return new RestAPIResponse("Status List!",true, HttpStatus.OK, payTypeList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }
}
