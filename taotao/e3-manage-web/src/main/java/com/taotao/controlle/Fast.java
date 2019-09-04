package com.taotao.controlle;

import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.qyt.utils.FastDFSClient;
import com.qyt.utils.JsonUtils;

@Controller
public class Fast {
	
	@Value("${IMAGE_SERVICE}")
	String IMAGE_SERVICE;
	
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String upload_image(MultipartFile uploadFile) {
		//uploadFile只能返回一个整个文件名，后缀无法确定
		try {
			FastDFSClient client=new FastDFSClient("classpath:conf/client.conf");
			
			byte[] content=uploadFile.getBytes();
			
			String origin=uploadFile.getOriginalFilename();
			System.out.println(origin+"--111111---");
			String extName=origin.substring(origin.lastIndexOf(".")+1);
			System.out.println(extName+"---222222--");
			System.out.println(uploadFile.toString()+"");
		//	String path=client.uploadFile(origin.getBytes(), extName);
			String path=client.uploadFile(content, extName);
			String  url=IMAGE_SERVICE+"/"+path;
			System.out.println(url+"---3333333--");
			Map map=new HashMap();
			map.put("error", 0);
			map.put("url", url);
			return JsonUtils.objectToJson(map);
			
		} catch (Exception e) {
			Map map=new HashMap();
			map.put("error", 1);
			map.put("message", "错误信息");
			return JsonUtils.objectToJson(map);
		}
	}
}
