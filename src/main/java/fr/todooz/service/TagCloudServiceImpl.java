package fr.todooz.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.todooz.domain.Task;
import fr.todooz.util.TagCloud;

@Service
public class TagCloudServiceImpl implements TagCloudService{
	
	@Inject
	private TaskService taskService;

	@Override
	@Transactional
	public TagCloud buildTagCloud() {
		List<Task> tasks = taskService.findAll();
		TagCloud tagCloud = new TagCloud();
		for (Task task : tasks){
			String[] allTags = StringUtils.split(task.getTags(), ",");
			if (allTags != null){
				for (String tag : allTags){
					tagCloud.add(tag);
				}
			}
		}
		return tagCloud;
	}

}
