package fr.todooz.web.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.joda.time.DateMidnight;
import org.joda.time.Interval;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.todooz.domain.Task;
import fr.todooz.service.TagCloudService;
import fr.todooz.service.TaskService;
import fr.todooz.util.TagCloud;

@Controller
public class IndexController {
	@Inject
	private TaskService taskService;

	@Inject
	private TagCloudService tagCloudService;

	@RequestMapping({ "/", "/index" })
	public String index(Model model) {
		model.addAttribute("tasks", taskService.findAll());
		return "index";
	}

	@RequestMapping("/search")
	public String search(String query, Model model) {
		model.addAttribute("tasks", taskService.findByQuery(query));
		return "index";
	}

	@PostConstruct
	public void bootstrap() {
		if (taskService.count() == 0) {
			for (int i = 0; i < 5; i++) {
				Task task = createTask1();
				taskService.save(task);
				task = createTask2();
				taskService.save(task);
			}
		}
	}

	@PreDestroy
	public void clearAll() {
		List<Task> allTasks = taskService.findAll();
		for (Task task : allTasks) {
			taskService.delete(task.getId());
		}
	}

	@RequestMapping("/tag/{tag}")
	public String tag(@PathVariable String tag, Model model) {
		model.addAttribute("tasks", taskService.findByTag(tag));
		return "index";
	}

	@RequestMapping("/today")
	public String getTodayTasks(Model model) {
		model.addAttribute("tasks", taskService.findByInterval(todayInterval()));
		return "index";
	}
	
	@RequestMapping("/tomorrow")
	public String getTomorrowTasks(Model model) {
		model.addAttribute("tasks", taskService.findByInterval(tomorrowInterval()));
		return "index";
	}
	
	@ModelAttribute
	public TagCloud tagCloud() {
	    return tagCloudService.buildTagCloud();
	}
	
	private Interval todayInterval() {
		DateMidnight today = new DateMidnight();
		return new Interval(today, today.plusDays(1));
	}
	
	private Interval tomorrowInterval() {
		DateMidnight today = new DateMidnight();
		return new Interval(today.plusDays(1), today.plusDays(2));
	}

	public Task createTask1() {
		Task task = new Task();
		task.setTitle("Read effective java");
		task.setTags("java,python,lol");
		task.setText("hahahahahaha");
		return task;
	}

	public Task createTask2() {
		Task task = new Task();
		task.setTitle("Do not Read effective java");
		task.setTags("oooo");
		task.setText("hahahahahaha");
		return task;
	}
}
