package com.facultate.disertatie.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facultate.disertatie.entity.DicTask;

public interface DicTaskRepository extends JpaRepository<DicTask, Long> {
	
	List<DicTask> findByTaskIteration_id(long id);
	
	DicTask findById(long id);
	
	List<DicTask> findByUser_Dept_deptIdOrderByCreatedDesc(long id);
	
	List<DicTask> findByUser_IdAndStatus_idNot(long id, int statusId);
	
	List<DicTask> findByUser_dept_deptIdAndStatus_idNot(long id, int statusId);

	List<DicTask> findAllByUser_Dept_DeptIdAndCreatedBetween(Long deptId, LocalDateTime startDate, LocalDateTime endDate);

	List<DicTask> findAllByUser_Dept_DeptIdAndDeadlineBetween(Long deptId, LocalDateTime startDate, LocalDateTime endDate);
	
}