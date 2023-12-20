package com.example.LeanerService.Service;


import com.example.LeanerService.DTO.CreateLearnerDTO;
import com.example.LeanerService.DTO.ResponeAccountDTO;
import com.example.LeanerService.DTO.ResponeStudentInfor;
import com.example.LeanerService.DTO.ResponseLearnerDTO;
import com.example.LeanerService.Entity.Learner;

import java.util.List;

public interface ILearnerService {
    Learner createLearner(CreateLearnerDTO dto);

    Double getProfitByOwnerID(int id);

    Learner findLearnerByIDAndCourse(int id,int courseID);
    List<ResponseLearnerDTO> findLearnersByCourseId(int courseId);

    int getCourseLastMonthProfitsByCourseID(int id);

    int getCourseProfitsByCourseID(int id);


    List<ResponeStudentInfor> findStudentsByCourseId(int courseId);

    List<Learner> showStudentPurchaseByAccountId(int accountId);

    List<Learner> learnerListFromDB();

    ResponeStudentInfor createStudentInfo(int accountId, int courseId);

    int countDistinctAccountIdByOwner(int owner);

    double sumOfProfit(int owner);
}
