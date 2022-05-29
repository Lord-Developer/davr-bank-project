package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.student.StudentRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.Student;
import uz.davr.model.Teacher;
import uz.davr.service.StudentService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final Database database;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final String INSERT_QUERY = "INSERT INTO student_tb (full_name, phone) VALUES(?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM student_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE student_tb SET full_name = ?, phone = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM student_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM student_tb";

    public StudentServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public RestAPIResponse addStudent(StudentRequest request) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, request.getFull_name());
            preparedStatement.setString(2, request.getPhone());
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public RestAPIResponse getStudentById(int studentId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Student student = new Student();
            if (resultSet.next()){
                student.setId(resultSet.getInt(1));
                student.setFull_name(resultSet.getString(2));
                student.setPhone(resultSet.getString(3));
                return new RestAPIResponse("Object is found!",true, HttpStatus.OK, student);
            }
            return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse editStudent(StudentRequest request, int id) {
        if(request == null)
            return new RestAPIResponse("Course Object is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, request.getFull_name());
            preparedStatement.setString(2, request.getPhone());
            preparedStatement.setInt(3, id);
            preparedStatement.execute();

            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public RestAPIResponse deleteStudent(int studentId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, studentId);
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
            List<Student> studentList = new ArrayList<>();
            Student student;
            while(resultSet.next()) {
                student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("phone"));
                studentList.add(student);
            }
            return new RestAPIResponse("Teacher List!",true, HttpStatus.OK, studentList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }
}
