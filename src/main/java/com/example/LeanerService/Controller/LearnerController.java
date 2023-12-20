
package com.example.LeanerService.Controller;

import com.example.LeanerService.Client.AccountClient;
import com.example.LeanerService.DTO.CreateLearnerDTO;
import com.example.LeanerService.DTO.ResponeAccountDTO;
import com.example.LeanerService.DTO.ResponeStudentInfor;
import com.example.LeanerService.DTO.ResponseLearnerDTO;
import com.example.LeanerService.Entity.Learner;
import com.example.LeanerService.Repository.LearnerRepository;
import com.example.LeanerService.Service.ServiceOfLearner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://academy.arthub.s3-website-ap-southeast-1.amazonaws.com/", maxAge = 3600)
@RestController
public class LearnerController implements ILearnerController {

    @Autowired
    ServiceOfLearner learnerService;

    @Autowired
    AccountClient accountClient;



    public ResponseEntity<List<ResponseLearnerDTO>> showStudentPurchase(Integer accountId) {
        try {
            ResponeAccountDTO accountOptional = accountClient.getAccountInformation(accountId).getBody();
            if(accountOptional == null) {
                return ResponseEntity.badRequest().body(null);
            }

            else{

                return ResponseEntity.ok(fromLearnerListToResponseLearnerDTOList(learnerService.showStudentPurchaseByAccountId(accountId)));
            }

        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    public ResponseLearnerDTO fromLearnerToResponseLearnerDTO (Learner learner) {

        ResponseLearnerDTO learnerDTO = new ResponseLearnerDTO();
        learnerDTO.setId(learner.getId());
        learnerDTO.setAccountId(learner.getAccountId());//FeignCall
        learnerDTO.setCourseId(learner.getCourseId());//FeignCall
        learnerDTO.setOwnerCourse(learner.getOwnerCourse());//FeignCall
        learnerDTO.setDate(learner.getDate());
        learnerDTO.setPrice(learner.getPrice());
        learnerDTO.setStatus(learner.getStatus());
        learnerDTO.setSenderId(learner.getSenderId());
        learnerDTO.setMessage(learner.getMessage());
        return learnerDTO;


    }

    public List<ResponseLearnerDTO> fromLearnerListToResponseLearnerDTOList(List<Learner> LearnerList) {
        List<ResponseLearnerDTO> ResponseLearnerDTOList = new ArrayList<>();
        for (Learner learner: LearnerList) {
            ResponseLearnerDTOList.add(fromLearnerToResponseLearnerDTO(learner));
        }
        return ResponseLearnerDTOList;
    }

    public List<ResponseLearnerDTO> getLearnerList() {

        List<ResponseLearnerDTO> responseLearnerDTOList = fromLearnerListToResponseLearnerDTOList(learnerService.learnerListFromDB());
        return responseLearnerDTOList;
    }

    @Override
    public List<ResponseLearnerDTO> getLearnersByCourseId(int courseId) {
        return learnerService.findLearnersByCourseId(courseId);
    }

    @Override
    public int getCourseLastMonthProfitsByCourseID(int id) {
        return learnerService.getCourseLastMonthProfitsByCourseID(id);
    }

    @Override
    public int getCourseProfitsByCourseID(int id) {
        return learnerService.getCourseProfitsByCourseID(id);
    }

    @Override
    public List<ResponeStudentInfor> getStudentsByCourseId(int courseId) {
        return learnerService.findStudentsByCourseId(courseId);
    }

    @Override
    public ResponseLearnerDTO getLearnerByIdAndCourse(int accountId,int CourseId) {
        return fromLearnerToResponseLearnerDTO(learnerService.findLearnerByIDAndCourse(accountId,CourseId));
    }

    public ResponseLearnerDTO createLearner(CreateLearnerDTO dto) {
        Learner learner = learnerService.createLearner(dto);
        return fromLearnerToResponseLearnerDTO(learner);

    }

    @Override
    public int countLearner(int owner)  {
        return learnerService.countDistinctAccountIdByOwner(owner);
    }

    @Override
    public double getProfitByOwner(int owner) {
        return learnerService.getProfitByOwnerID(owner);
    }


}