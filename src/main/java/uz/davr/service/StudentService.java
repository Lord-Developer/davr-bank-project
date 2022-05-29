package uz.davr.service;

import uz.davr.dto.request.student.StudentRequest;
import uz.davr.dto.response.RestAPIResponse;
import uz.davr.service.base.BaseService;

public interface StudentService extends BaseService {

    RestAPIResponse addStudent(StudentRequest request);
    RestAPIResponse getStudentById(int studentId);
    RestAPIResponse editStudent(StudentRequest request, int id);
    RestAPIResponse deleteStudent(int studentId);
}
