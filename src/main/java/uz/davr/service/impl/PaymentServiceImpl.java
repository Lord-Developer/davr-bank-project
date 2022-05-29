package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.payment.PaymentRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.Payment;
import uz.davr.service.PaymentService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final Database database;

    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final String INSERT_QUERY = "INSERT INTO payment_tb (pay_type_id, sum, description, student_id, created_date) VALUES(?, ?, ?, ?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM payment_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE payment_tb SET pay_type_id = ?, sum = ?, description = ?, student_id = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM payment_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM payment_tb";

    public PaymentServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public RestAPIResponse addPayment(PaymentRequest request) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setInt(1, request.getPay_type_id());
            preparedStatement.setDouble(2, request.getSum());
            preparedStatement.setString(3, request.getDescription());
            preparedStatement.setInt(4, request.getStudent_id());
            preparedStatement.setDate(5, Date.valueOf(request.getCreated_date()));
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse getPaymentById(int paymentRequestId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, paymentRequestId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Payment payment = new Payment();
            if (resultSet.next()){
                payment.setId(resultSet.getInt(1));
                payment.setPay_type_id(resultSet.getInt("pay_type_id"));
                payment.setDescription(resultSet.getString("description"));
                payment.setStudent_id(resultSet.getInt("student_id"));
                payment.setCreated_date(resultSet.getDate("created_date"));
                return new RestAPIResponse("Object is found!",true, HttpStatus.OK, payment);
            }
            return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse editPayment(PaymentRequest request, int id) {
        if(request == null)
            return new RestAPIResponse("Payment Object is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setInt(1, request.getPay_type_id());
            preparedStatement.setDouble(2, request.getSum());
            preparedStatement.setString(3, request.getDescription());
            preparedStatement.setInt(4, request.getStudent_id());
            preparedStatement.setInt(5, id);
            preparedStatement.execute();

            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse deletePayment(int paymentRequestId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, paymentRequestId);
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
            List<Payment> paymentList = new ArrayList<>();
            Payment payment;
            while(resultSet.next()) {
                payment = new Payment(
                        resultSet.getInt(1),
                resultSet.getInt("pay_type_id"),
                resultSet.getDouble("sum"),
                resultSet.getString("description"),
                resultSet.getInt("student_id"),
                resultSet.getDate("created_date"));
                paymentList.add(payment);
            }
            return new RestAPIResponse("Status List!",true, HttpStatus.OK, paymentList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);
    }
}
