package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.teacher.TeacherRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.Course;
import uz.davr.model.Teacher;
import uz.davr.service.TeacherService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final Database database;

    private static final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);

    private final String INSERT_QUERY = "INSERT INTO teacher_tb (full_name, phone, salary) VALUES(?, ?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM teacher_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE teacher_tb SET full_name = ?, phone = ?, salary = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM teacher_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM teacher_tb";

    public TeacherServiceImpl(Database database) {
        this.database = database;
    }


    @Override
    public RestAPIResponse addTeacher(TeacherRequest request) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, request.getFull_name());
            preparedStatement.setString(2, request.getPhone());
            preparedStatement.setDouble(3, request.getSalary());
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public RestAPIResponse getTeacherById(int teacherId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, teacherId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Teacher teacher = new Teacher();
            if (resultSet.next()){
                teacher.setId(resultSet.getInt(1));
                teacher.setFull_name(resultSet.getString(2));
                teacher.setPhone(resultSet.getString(3));
                teacher.setSalary(resultSet.getDouble(4));
                return new RestAPIResponse("Object is found!",true, HttpStatus.OK, teacher);
            }
            return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse editTeacher(TeacherRequest request, int id) {
        if(request == null)
            return new RestAPIResponse("Course Object is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, request.getFull_name());
            preparedStatement.setString(2, request.getPhone());
            preparedStatement.setDouble(3, request.getSalary());
            preparedStatement.setInt(4, id);
            preparedStatement.execute();

            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public RestAPIResponse deleteTeacher(int teacherId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, teacherId);
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
            List<Teacher> teacherList = new ArrayList<>();
            Teacher teacher;
            while(resultSet.next()) {
                teacher = new Teacher(
                        resultSet.getInt("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("phone"),
                        resultSet.getDouble("salary"));
                teacherList.add(teacher);
            }
            return new RestAPIResponse("Teacher List!",true, HttpStatus.OK, teacherList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }
}
