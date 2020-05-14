package com.kk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.support.json.JSONUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.enums.BGMOperatorTypeEnum;
import com.kk.mapper.BgmMapper;
import com.kk.mapper.UsersReportMapperCustom;
import com.kk.mapper.VideosMapper;
import com.kk.pojo.Bgm;
import com.kk.pojo.BgmExample;
import com.kk.pojo.Videos;
import com.kk.pojo.vo.Reports;
import com.kk.service.VideoService;
import com.kk.utils.JsonUtils;
import com.kk.utils.PagedResult;
import com.kk.web.utils.ZKCurator;

@Service
public class VideoServiceImpl implements VideoService {
	
	public static final Integer PAGE_SIZE = 5;
	
	@Autowired
	private BgmMapper bgmMapper;
	
	@Autowired
	private Sid sid;
	
	@Autowired
	private ZKCurator curator;
	
	@Autowired
	private UsersReportMapperCustom usersReportMapperCustom;
	
	@Autowired
	private VideosMapper videosMapper;

	@Override
	public PagedResult queryBgmList(Integer page) {
		
		PageHelper.startPage(page,PAGE_SIZE);
		
		BgmExample example = new BgmExample();
		List<Bgm> list = bgmMapper.selectByExample(example);
		
		PageInfo<Bgm> pageList = new PageInfo<>(list);
		
		PagedResult result = new PagedResult();
		result.setTotal(pageList.getPages());
		result.setRows(list);
		result.setPage(page);
		result.setRecords(pageList.getTotal());
		
		return result;
	}
	
	@Override
	public PagedResult queryReportList(Integer page, Integer pageSize) {

		PageHelper.startPage(page, pageSize);

		List<Reports> reportsList = usersReportMapperCustom.selectAllVideoReport();

		PageInfo<Reports> pageList = new PageInfo<Reports>(reportsList);

		PagedResult grid = new PagedResult();
		grid.setTotal(pageList.getPages());
		grid.setRows(reportsList);
		grid.setPage(page);
		grid.setRecords(pageList.getTotal());

		return grid;
	}
	
	@Override
	public void addBgm(Bgm bgm) {
		String bgmId = sid.nextShort();
		bgm.setId(bgmId);
		bgmMapper.insert(bgm);
		
		Map<String, String> map = new HashMap<>();
		map.put("operType", BGMOperatorTypeEnum.ADD.type);
		map.put("path", bgm.getPath());
		
        curator.sendBgmOperator(bgmId,JsonUtils.objectToJson(map) );
	}

	@Override
	public void deleteBgm(String id) {
		Bgm bgm = bgmMapper.selectByPrimaryKey(id);
		bgmMapper.deleteByPrimaryKey(id);
		
		Map<String, String> map = new HashMap<>();
		map.put("operType", BGMOperatorTypeEnum.DELETE.type);
		map.put("path", bgm.getPath());
		
        curator.sendBgmOperator(id,JsonUtils.objectToJson(map) );
	}

	@Override
	public void updateVideoStatus(String videoId, Integer status) {
		Videos video = new Videos();
		video.setId(videoId);
		video.setStatus(status);
		videosMapper.updateByPrimaryKeySelective(video);
		
	}

}
