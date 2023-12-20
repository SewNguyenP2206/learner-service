package com.example.LeanerService.Service;


import com.example.LeanerService.Client.AccountClient;
import com.example.LeanerService.Config.ModelMapperObject;
import com.example.LeanerService.DTO.CreateLearnerDTO;
import com.example.LeanerService.DTO.ResponeAccountDTO;
import com.example.LeanerService.DTO.ResponeStudentInfor;
import com.example.LeanerService.DTO.ResponseLearnerDTO;
import com.example.LeanerService.Entity.Learner;
import com.example.LeanerService.Repository.LearnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceOfLearner implements ILearnerService {
    @Autowired
    LearnerRepository learnerRepository;

    @Autowired
    AccountClient accountClient;

    @Autowired
    ModelMapperObject modelMapperObject;


    @Override
    public Learner createLearner(CreateLearnerDTO dto) {
        Learner learner = new Learner();
        learner.setAccountId(dto.getAccountId());
        learner.setCourseId(dto.getCourseId());
        learner.setOwnerCourse(dto.getOwnerCourse());
        learner.setPrice(dto.getPrice());
        return learnerRepository.save(learner);

    }



    @Override
    public ResponeStudentInfor createStudentInfo(int accountId, int courseId) {
        ResponeAccountDTO dto = accountClient.getAccountInformation(accountId).getBody();
        ResponeStudentInfor studentInfo = new ResponeStudentInfor();
        studentInfo.setId(dto.getId());
        studentInfo.setEmail(dto.getEmail());
        studentInfo.setFacebook(dto.getFacebook());
        studentInfo.setLastname(dto.getLastname());
        studentInfo.setTwitter(dto.getTwitter());
        studentInfo.setDate(findLearnerByIDAndCourse(accountId,courseId).getDate());
        return studentInfo;
    }

   public List<Learner> showStudentPurchaseByAccountId(int accountId)
   {
       return learnerRepository.showStudentPurchaseByAccountId(accountId);
   }

    @Override
    public List<Learner> learnerListFromDB() {
        return learnerRepository.findAll();
    }



    @Override
    public int countDistinctAccountIdByOwner(int owner) {
        return learnerRepository.countDistinctAccountIdByOwner(owner);
    }

    @Override
    public double sumOfProfit(int owner) {
        return learnerRepository.sumOfProfit(owner);
    }


    @Override
    public Double getProfitByOwnerID(int id) {
        return learnerRepository.sumOfProfit(id);
    }


    @Override
    public Learner findLearnerByIDAndCourse(int id,int courseID) {
        return learnerRepository.findById(id,courseID);
    }

    @Override
    public List<ResponseLearnerDTO> findLearnersByCourseId(int courseId) {
        return learnerRepository.findLearnersByCourseId(courseId).stream().map(learner -> modelMapperObject.modelMapper().map(learner, ResponseLearnerDTO.class)).toList();
    }

    @Override
    public int getCourseLastMonthProfitsByCourseID(int id) {
        return learnerRepository.getCourseLastMonthProfitsByCourseID(id);
    }

    @Override
    public int getCourseProfitsByCourseID(int id) {
        return learnerRepository.getCourseProfitsByCourseID(id);
    }

    @Override
    public List<ResponeStudentInfor> findStudentsByCourseId(int courseId) {
        return learnerRepository.findLearnersByCourseId(courseId).stream().map(learner -> createStudentInfo(learner.getId(),courseId)).toList();

    }


}