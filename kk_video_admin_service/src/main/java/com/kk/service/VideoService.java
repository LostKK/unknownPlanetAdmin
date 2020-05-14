package com.kk.service;

import com.kk.pojo.Bgm;
import com.kk.utils.PagedResult;

public interface VideoService {

	public void addBgm(Bgm bgm);
	
	public PagedResult queryBgmList(Integer page);
	
	public void deleteBgm(String id);
	
	public PagedResult queryReportList(Integer page, Integer pageSize);

	public void updateVideoStatus(String videoId, Integer value);

}
