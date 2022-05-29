package uz.davr.service;

import uz.davr.dto.request.room.RoomRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface RoomService extends BaseService {

    RestAPIResponse addRoom(RoomRequest request);
    RestAPIResponse getRoomById(int roomId);
    RestAPIResponse editRoom(RoomRequest request, int id);
    RestAPIResponse deleteRoom(int roomId);
}
