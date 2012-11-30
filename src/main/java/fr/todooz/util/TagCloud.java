package fr.todooz.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TagCloud {
	
	private List<String> tags;
	
	public TagCloud(){
		tags = new ArrayList<String>();
	}
	
	public void add(String tag){
		if ((tag != null) && (! contains(tag))){
			tags.add(tag);
		}
	}
	
	public int size(){
		return tags.size();
	}
	
	public void add(String ... tags_){
		if (tags_ != null){
			for (String tag : tags_){
				this.add(tag);
			}
		}
	}
	
	public boolean contains(String tag){
		return tags.contains(tag);
	}
	
	public void top(int tailleMax){
		if (tailleMax >= 0){
			if (tailleMax < size()){
				tags = tags.subList(0, tailleMax);
			}
		}
		else {
			for (int i = size() - 1; i >= 0; i--){
				tags.remove(i);
			}
		}
	}
	
	public void shuffle(){
		Collections.shuffle(tags);
	}
	
	public List<String> getTags() {
	    return tags;
	}

}
