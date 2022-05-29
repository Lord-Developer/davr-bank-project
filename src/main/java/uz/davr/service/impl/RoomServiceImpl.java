package uz.davr.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.davr.database.Database;
import uz.davr.dto.request.room.RoomRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.model.Room;
import uz.davr.service.RoomService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final Database database;

    private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);

    private final String INSERT_QUERY = "INSERT INTO room_tb (name, capacity) VALUES(?, ?)";
    private final String GET_BY_ID_QUERY = "SELECT * FROM room_tb WHERE id = ?";
    private final String UPDATE_QUERY = "UPDATE room_tb SET name = ?, capacity = ? WHERE id = ?";
    private final String DELETE_QUERY = "DELETE FROM room_tb WHERE id = ?";
    private final String GET_ALL_QUERY = "SELECT * FROM room_tb";

    public RoomServiceImpl(Database database) {
        this.database = database;
    }

    @Override
    public RestAPIResponse addRoom(RoomRequest request) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(INSERT_QUERY);
            preparedStatement.setString(1, request.getName());
            preparedStatement.setInt(2, request.getCapacity());
            preparedStatement.execute();
            return new RestAPIResponse("Successfully added!",true, HttpStatus.CREATED);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!", false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public RestAPIResponse getRoomById(int roomId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(GET_BY_ID_QUERY);
            preparedStatement.setInt(1, roomId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Room room = new Room();
            if (resultSet.next()){
                room.setId(resultSet.getInt(1));
                room.setName(resultSet.getString(2));
                room.setCapacity(resultSet.getInt(3));
                return new RestAPIResponse("Object is found!",true, HttpStatus.OK, room);
            }
            return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Object is not  found!",false, HttpStatus.BAD_REQUEST);

    }

    @Override
    public RestAPIResponse editRoom(RoomRequest request, int id) {
        if(request == null)
            return new RestAPIResponse("Course Object is Null", false, HttpStatus.BAD_REQUEST);
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, request.getName());
            preparedStatement.setInt(2, request.getCapacity());
            preparedStatement.setInt(3, id);
            preparedStatement.execute();
            return new RestAPIResponse("Successfully updated!",true, HttpStatus.OK);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public RestAPIResponse deleteRoom(int roomId) {
        try {
            PreparedStatement preparedStatement = database.connect().prepareStatement(DELETE_QUERY);
            preparedStatement.setInt(1, roomId);
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
            List<Room> roomList = new ArrayList<>();
            Room room;
            while(resultSet.next()) {
                room = new Room(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("capacity"));
                roomList.add(room);
            }
            return new RestAPIResponse("Room List!",true, HttpStatus.OK, roomList);

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }

        return new RestAPIResponse("Failed!",false, HttpStatus.BAD_REQUEST);

    }
}
