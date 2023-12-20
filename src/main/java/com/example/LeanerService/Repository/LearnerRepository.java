package com.example.LeanerService.Repository;


import com.example.LeanerService.Entity.Learner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LearnerRepository extends JpaRepository<Learner, Integer> {

    @Query("SELECT l FROM Learner l WHERE l.accountId = ?1 AND l.price > 0")
    List<Learner> showStudentPurchaseByAccountId(Integer accountId);


    @Query(value = "SELECT SUM(Learner.price) as SumProfit FROM Learner where Learner.courseId  = ?1",nativeQuery = true)
    Integer getCourseProfitsByCourseID(int id);
    

    @Query(value = "SELECT SUM(CASE WHEN MONTH(l.date) = MONTH(DATEADD(month, -1, GETDATE())) THEN l.price ELSE 0 END) AS lastMonthProfit FROM  Learner l WHERE l.courseId = ?1", nativeQuery = true)
    Integer getCourseLastMonthProfitsByCourseID(int id);


    @Query("SELECT COUNT(DISTINCT l.accountId) FROM Learner l WHERE l.ownerCourse = ?1 GROUP BY l.ownerCourse")
    int countDistinctAccountIdByOwner(Integer owner);

    @Query("SELECT SUM(l.price) FROM Learner l WHERE l.ownerCourse = :owner")
    Double sumOfProfit(@Param("owner") int owner);

    @Query("SELECT l from Learner l where l.accountId = ?1 and l.courseId = ?2")
    Learner findById(int id,int courseId);

    Optional<Learner> findByAccountIdAndCourseId(Integer accountId,Integer courseId);
    @Query("SELECT l from Learner l where l.courseId = ?1")
    List<Learner> findLearnersByCourseId(Integer courseId);

    List<Learner> findByAccountId(Integer accountId);
}
