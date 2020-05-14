package com.kk.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.kk.mapper.WordMapper;
import com.kk.pojo.Word;
import com.kk.pojo.WordExample;
import com.kk.pojo.WordExample.Criteria;
import com.kk.service.WordService;
import com.kk.utils.PagedResult;

@Service
public class WordServiceImpl implements WordService{
	
	@Autowired
	private WordMapper wordMapper;

	@Override
	public PagedResult queryWord(Word word, Integer page, Integer pageSize) {
		
		String writter = "";
		if(word != null){
			writter = word.getWritter();
		}
		
		WordExample wordExample = new WordExample();
		Criteria wordCriteria = wordExample.createCriteria();
		if(StringUtils.isNotBlank(writter)){
			wordCriteria.andWritterLike("%" + writter + "%");
		}
		
		List<Word> wordList = wordMapper.selectByExample(wordExample);
		
		PageInfo<Word> pageList = new PageInfo<Word>(wordList);

		PagedResult grid = new PagedResult();
		grid.setTotal(pageList.getPages());
		grid.setRows(wordList);
		grid.setPage(page);
		grid.setRecords(pageList.getTotal());

		return grid;

	}

}
