package kr.co.kandedu.base.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;
import kr.co.kandedu.base.domain.FileVo;

import org.springframework.web.multipart.MultipartFile;

import kr.co.kandedu.base.util.StringUtil;

@Component("FileMngUtil")
public class FileMngUtil {
	public static final int BUFF_SIZE = 2048;

	/**
	 * 첨부파일에 대한 목록 정보를 취득한다.
	 * 
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public FileVo parseFileInf(Map<String, MultipartFile> files,
			String KeyStr, int fileKeyParam, String storePath)
			throws Exception {
		int fileKey = fileKeyParam;

		String storePathString = storePath;
		//String atchFileIdString = nextId;

		File saveFolder = new File(storePathString);

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet()
				.iterator();
		MultipartFile file;
		String filePath = "";
		FileVo fvo = new FileVo();
		
		while (itr.hasNext()) {
			Entry<String, MultipartFile> entry = itr.next();

			file = entry.getValue();
			String orginFileName = file.getOriginalFilename();
			
			//첨부명이 없을 경우 다음으로
			if ("".equals(orginFileName)) {
				continue;
			}

			int index = orginFileName.lastIndexOf(".");
			String fileExt = orginFileName.substring(index + 1);
			if(fileExt != null) {
				fileExt = fileExt.toLowerCase();
			}
			String newName = KeyStr + StringUtil.getTimeStamp() + fileKey + "." + fileExt;
			long size = file.getSize();

			if (!"".equals(orginFileName)) {
				filePath = storePathString + newName;
				file.transferTo(new File(filePath));
			}
			
			fvo.setFile_ext(fileExt);
			fvo.setFile_path(storePathString);
			fvo.setFile_size(Long.toString(size));
			fvo.setFile_sn(String.valueOf(fileKey));
			fvo.setOri_file_nm(orginFileName);
			fvo.setFile_nm(newName);

			fileKey++;
		}

		return fvo;
	}
	
	
	/**
	 * 첨부파일에 대한 목록 정보를 취득한다.
	 * 
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public List<FileVo> parseFileInf(MultipartFile file, String KeyStr, String storePath) throws Exception {
		String orginFileName = file.getOriginalFilename();
		if ("".equals(orginFileName)) {
			return null;
		}
		
		int fileKey = 1;
		String storePathString = storePath;
		File saveFolder = new File(storePathString);

		if (!saveFolder.exists() || saveFolder.isFile()) {
			saveFolder.mkdirs();
		}

		String filePath = "";
		List<FileVo> result = new ArrayList<FileVo>();
		FileVo fvo;
		//String orginFileName = file.getOriginalFilename();
		int index = orginFileName.lastIndexOf(".");
		String fileExt = orginFileName.substring(index + 1);
		if(fileExt != null) {
			fileExt.toLowerCase();
		}
		String newName = KeyStr + StringUtil.getTimeStamp() + fileKey + "." + fileExt;
		long size = file.getSize();

		if (!"".equals(orginFileName)) {
			filePath = storePathString + newName;
			file.transferTo(new File(filePath));
		}
		fvo = new FileVo();
		fvo.setFile_ext(fileExt);
		fvo.setFile_path(storePathString);
		fvo.setFile_size(Long.toString(size));
		fvo.setFile_sn(String.valueOf(fileKey));
		fvo.setOri_file_nm(orginFileName);
		fvo.setFile_nm(newName);
		result.add(fvo);

		return result;
	}
}

