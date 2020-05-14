package com.kk.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kk.pojo.Users;
import com.kk.pojo.Word;
import com.kk.service.WordService;
import com.kk.utils.KKJSONResult;
import com.kk.utils.PagedResult;

@Controller
@RequestMapping("word")
public class WordController {
    
	@Autowired
	private WordService wordService;
	
	@GetMapping("/showAddWord")
	public String showAddWord(){
		return "word/addWord";
	}
	
	@GetMapping("/showWordList")
	public String showWordList(){
		return "word/wordList";
	}
	
	@PostMapping("/list")
	@ResponseBody
	public PagedResult list(Word word , Integer page) {
		
		PagedResult result = wordService.queryWord(word, page == null ? 1 : page, 10);
		return result;
	}
	
	@PostMapping("/wordUpload")
	@ResponseBody
	public KKJSONResult bgmUpload(@RequestParam("file") MultipartFile[] files) throws Exception {
		
		
		//文件保存的命名空间
	//	String fileSpace = "D:/my ware/tiktok/kk_video_dev";
	    //String fileSpace = File.separator + "my ware" + File.separator + "tiktok" + File.separator + "kk_video_dev" + File.separator + "mvc_bgm";
	//    String fileSpace = "D:" + File.separator + "my ware" + File.separator + "tiktok" + File.separator + "kk_video_dev" + File.separator + "mvc_bgm";
	
		String fileSpace = "https://www.captainkk.cn/k";
		
		//保存到数据库中的相对路径
		String uploadPathDB = File.separator + "pic";
				
		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		
		try {
			if(files != null && files.length > 0){
				
				String fileName = files[0].getOriginalFilename();
				if(StringUtils.isNotBlank(fileName)){
					//文件上传的最终保存路径
					String finalPath = fileSpace + uploadPathDB + File.separator + fileName;
					//设置数据库保存的路径
					uploadPathDB += (File.separator + fileName);
										
					File outFile = new File(finalPath);
					if(outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()){
						//创建父文件夹
						outFile.getParentFile().mkdirs();
					}
					
					fileOutputStream = new FileOutputStream(outFile);
					inputStream = files[0].getInputStream();
//					IOUtils.copy(inputStream, fileOutputStream);
					System.out.println("输出finalPath为："+finalPath);

				}
			}else{
				return KKJSONResult.errorMsg("上传有问题");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return KKJSONResult.errorMsg("上传有问题");
		}finally{
			if(fileOutputStream !=null){
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		
		return KKJSONResult.ok(uploadPathDB);
	}
}
