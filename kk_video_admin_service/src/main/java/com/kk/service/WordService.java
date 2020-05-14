package com.kk.service;

import org.springframework.stereotype.Service;

import com.kk.pojo.Word;
import com.kk.utils.PagedResult;


public interface WordService {

	public PagedResult queryWord(Word word, Integer page,Integer pageSize);

}
