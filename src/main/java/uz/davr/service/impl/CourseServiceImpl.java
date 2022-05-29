package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.course.CourseRequest;
import uz.davr.dto.request.day.DayEditRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.Course;
import uz.davr.model.Day;
import uz.davr.service.CourseService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final Database database;

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final String INSERT_QUERY = "INSERT INTO course_tb (name, price, duration) VALUES(?, ?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM course_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE course_tb SET name = ?, price = ?, duration = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM course_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM course_tb";

    public CourseServiceImpl(Database database) {
        this.database = database;
    }


    @Override
    public RestAPIResponse addCourse(CourseRequest request) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, request.getName());
            preparedStatement.setDouble(2, request.getPrice());
            preparedStatement.setString(3, request.getDuration());
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse getCourseById(int courseId)
    {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Course course = new Course();
            if (resultSet.next()){
                course.setId(resultSet.getInt(1));
                course.setName(resultSet.getString(2));
                course.setPrice(resultSet.getDouble(3));
                course.setDuration(resultSet.getString(4));
                return new RestAPIResponse("Object is found!",true, HttpStatus.OK, course);
            }
            return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public RestAPIResponse editCourse(CourseRequest request, int id) {
        if(request == null)
            return new RestAPIResponse("Course Object is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, request.getName());
            preparedStatement.setDouble(2, request.getPrice());
            preparedStatement.setString(3, request.getDuration());
            preparedStatement.setInt(4, id);
            preparedStatement.execute();

            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public RestAPIResponse deleteCourse(int courseId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, courseId);
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
            List<Course> courseList = new ArrayList<>();
            Course course;
            while(resultSet.next()) {
                course = new Course(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getString("duration"));
                courseList.add(course);
            }
            return new RestAPIResponse("Day List!",true, HttpStatus.OK, courseList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);
    }
}
