package uz.davr.service;

import uz.davr.dto.request.day.DayEditRequest;
import uz.davr.dto.request.day.DayRequest;
import uz.davr.dto.request.teacher.TeacherRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface TeacherService extends BaseService {
    RestAPIResponse addTeacher(TeacherRequest request);
    RestAPIResponse getTeacherById(int teacherId);
    RestAPIResponse editTeacher(TeacherRequest request, int id);
    RestAPIResponse deleteTeacher(int teacherId);
}
