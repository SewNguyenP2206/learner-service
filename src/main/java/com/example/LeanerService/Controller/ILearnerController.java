    package com.example.LeanerService.Controller;


import com.example.LeanerService.DTO.CreateLearnerDTO;
import com.example.LeanerService.DTO.ResponeAccountDTO;
import com.example.LeanerService.DTO.ResponeStudentInfor;
import com.example.LeanerService.DTO.ResponseLearnerDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/learner")
public interface ILearnerController {

    @PostMapping("/showStudentPurchase")
    ResponseEntity<List<ResponseLearnerDTO>> showStudentPurchase(@RequestParam Integer accountId);

    @PostMapping("/addLearner")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseLearnerDTO createLearner(@RequestBody CreateLearnerDTO dto);

    @GetMapping("/getLearners")
    List<ResponseLearnerDTO> getLearnerList();


    @GetMapping("/getLearnersByCourseId")
    List<ResponseLearnerDTO> getLearnersByCourseId(int courseId);

    @GetMapping("/getCourseLastMonthProfitsByCourseID/{id}")
    int getCourseLastMonthProfitsByCourseID(@PathVariable int id);


    @GetMapping("getCourseProfitsByCourseID/{id}")
    int getCourseProfitsByCourseID( @PathVariable int id);


    @GetMapping("/getStudentsByCourseId")
    List<ResponeStudentInfor> getStudentsByCourseId(int courseId);

    @PostMapping("/getLearnerByIdAndCourse")
    ResponseLearnerDTO getLearnerByIdAndCourse(@RequestParam int accountId,@RequestParam int CourseId);

    @PostMapping("/countLearnerByOwner")
    @ResponseStatus(HttpStatus.CREATED)
    int countLearner(int owner);


    @PostMapping("/GetProfitByOwner")
    @ResponseStatus(HttpStatus.CREATED)
    double getProfitByOwner(int owner);


}