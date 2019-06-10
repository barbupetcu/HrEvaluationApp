package com.facultate.disertatie.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.facultate.disertatie.BO.HomeReport;
import com.facultate.disertatie.BO.TaskReport;
import com.facultate.disertatie.DTO.ReportFilter;
import com.facultate.disertatie.utils.ExcelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.facultate.disertatie.entity.DicTask;
import com.facultate.disertatie.entity.DicTaskComment;
import com.facultate.disertatie.entity.DicTaskIteration;
import com.facultate.disertatie.entity.DicUserLevel;
import com.facultate.disertatie.repository.DicTaskComRepository;
import com.facultate.disertatie.repository.DicTaskIterationRepository;
import com.facultate.disertatie.service.TaskService;

import javax.servlet.http.HttpServletRequest;


@RestController
public class TaskController {
 
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private DicTaskComRepository dicTaskComRepository;
    
    @Autowired
    private DicTaskIterationRepository dicTaskIterationRepository;
    
    @RequestMapping(value = "/api/addtask", method = RequestMethod.POST)
	public HashMap<String, Object> addTask(@RequestBody DicTask task){
    	HashMap<String, Object> response = new HashMap<String, Object>();
    	response.put("success", false);
    	
    	if(taskService.addTask(task) != null) {
    		response.put("success", true);
    	} else {
    		response.put("message", "Task-ul nu a putut fi salvat");
    	}

		return response;
	}
    
    @RequestMapping(value = "/api/gettask", method = RequestMethod.GET)
	public DicTask getTask(@RequestParam long id){
    	return taskService.getTask(id);
	}
    
    @RequestMapping(value = "/api/gettasks", method = RequestMethod.GET)
	public List<DicTask> getTasks(@RequestParam long sprintId){
    	return taskService.getTaskbySprint(sprintId);
	}
    
    
    @RequestMapping(value = "/api/sendtasks", method = RequestMethod.PUT)
	public List<DicTask> sendTasks(@RequestBody List<DicTask> tasks){
    	return taskService.updateStatus(tasks);
	}
    
    @RequestMapping(value = "/api/gettasksByDept", method = RequestMethod.GET)
   	public List<DicTask> getTasksByDept(){
       	return taskService.getTaskbyDept();
   	}
    
    @RequestMapping(value = "/api/addcomment", method = RequestMethod.POST)
   	public DicTaskComment sendComments(@RequestBody DicTaskComment comment){
       	return dicTaskComRepository.save(comment);
   	}
    
    @RequestMapping(value = "/api/commentsbytask", method = RequestMethod.GET)
   	public List<DicTaskComment> sendComments(@RequestParam long taskId){
       	return dicTaskComRepository.findByTask_id(taskId);
   	}
    
    @RequestMapping(value = "/api/loadHome", method = RequestMethod.GET)
   	public HashMap<String, Object> loadHome(@RequestParam long id){
    	HashMap<String, Object> response = new HashMap<String, Object>();
    	DicUserLevel currentUser = taskService.getLevelDetails(id);
    	List<DicTask> myTasks = taskService.getUserTasks(id);
    	
    	response.put("currentUser", currentUser);
    	response.put("myTasks", myTasks);
    	response.put("countTasks", myTasks.size());
    	
    	return response;
   	}
    
    @RequestMapping(value = "/api/senditeration", method = RequestMethod.PUT)
	public DicTaskIteration sendIteration(@RequestBody DicTaskIteration iteration){
    	return dicTaskIterationRepository.save(iteration);
	}
    
    @RequestMapping(value = "/api/loadHomeManager", method = RequestMethod.GET)
   	public HashMap<String, Object> loadHomeManager(@RequestParam long id){
    	HashMap<String, Object> response = new HashMap<String, Object>();

    	List<DicTask> myTasks = taskService.getDeptTasks(id);
    	List<DicUserLevel> deptUsers = taskService.getAllLevelDetails(id);
    	
    	response.put("myTasks", myTasks);
    	response.put("countTasks", myTasks.size());
    	response.put("deptUsers", deptUsers);
    	
    	return response;
   	}

	@Deprecated
	@RequestMapping(value = "/api/exportDetails", method = RequestMethod.GET)
	public ResponseEntity exportExcel(@RequestParam long id) throws IOException {

		List<DicUserLevel> deptUsers = taskService.getAllLevelDetails(id);
		List<HomeReport> report = new ArrayList<>();



		for (DicUserLevel user: deptUsers){
			double progress = (double) (user.getTotalPoints()-user.getLevel().getTotalPoints())/user.getLevel().getPointsForNewLevel()*100;

			report.add(new HomeReport(
					user.getPerso().getId(),
					user.getPerso().getName(),
					user.getPerso().getLastName(),
					user.getTotalPoints(),
					user.getLevel().getId(),
					progress,
					user.getLevel().getPointsForNewLevel()));
		}

		ByteArrayInputStream in = ExcelGenerator.createExcel(report, false);
//		File file = new File("C:\\temp\\test.xlsx");
//		ByteArrayInputStream in = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=UserReport.xlsx");
		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new InputStreamResource(in));

	}

	@Deprecated
	@RequestMapping(value = "/api/taskReport", method = RequestMethod.POST)
	public ResponseEntity getTaskReport(@RequestBody ReportFilter reportFilter) throws IOException {

		List<DicTask> tasks = taskService.getTaskReport(reportFilter);
		List<TaskReport> report = new ArrayList<>();




		for (DicTask task: tasks) {
			boolean delay = false;
			if (task.getEnd_status() != null && task.getEnd_status().getId() == 4 ||
					task.getEnd_status() == null && LocalDateTime.now().isAfter(task.getDeadline())) {
				delay = true;
			}

			report.add(new TaskReport(
					task.getId(),
					task.getCreated(),
					task.getUser().getName() + ' ' + task.getUser().getLastName(),
					task.getTitle(),
					task.getDifficulty().getName(),
					task.getTaskIteration() != null ? task.getTaskIteration().getStartDate() : null,
					task.getTaskIteration() != null ? task.getTaskIteration().getEndDate() : null,
					task.getStatus().getName(),
					task.getDeadline(),
					task.getPriority().getName(),
					delay
			));
		}



		ByteArrayInputStream in = ExcelGenerator.createExcel(report, true);
//		File file = new File("C:\\temp\\test.xlsx");
//		ByteArrayInputStream in = new ByteArrayInputStream(FileUtils.readFileToByteArray(file));

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=UserReport.xlsx");
		return ResponseEntity
				.ok()
				.headers(headers)
				.body(new InputStreamResource(in));

	}

    
    
       

}