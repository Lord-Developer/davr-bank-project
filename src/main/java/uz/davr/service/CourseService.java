package uz.davr.service;

import uz.davr.dto.request.course.CourseRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface CourseService extends BaseService {

    RestAPIResponse addCourse(CourseRequest request);
    RestAPIResponse getCourseById(int courseId);
    RestAPIResponse editCourse(CourseRequest request, int id);
    RestAPIResponse deleteCourse(int dayId);
}
