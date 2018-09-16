package com.profesores.demo.dao;

import java.util.List;

import com.profesores.demo.model.SocialMedia;
import com.profesores.demo.model.TeacherSocialMedia;

public interface SocialMediaDao {
	
	void saveSocialMedia(SocialMedia socialMedia);
	
	void deleteSocialMediaById(Long id);
	
	void updateSocialMedia(SocialMedia socialMedia);
	
	List<SocialMedia> findAllSocialMedias();
	
	SocialMedia findById(Long idSocialMedia);
	
	SocialMedia findByName(String name);
	
	TeacherSocialMedia findSocialMediaByIdAndName(Long idSocialMedia, String nickname);
	
}
