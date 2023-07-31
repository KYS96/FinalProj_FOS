package com.kh.slumber.common.awsS3Server.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@RestController
public class S3Controller {

	@Value("${cloud.aws.s3.bucket}")
    private String S3Bucket;
	
	@Autowired
	AmazonS3Client amazonS3Client;
	
	@PostMapping("upload")
	public ResponseEntity<Object> upload(MultipartFile[] multipartFileList) throws Exception {
		List<String> imagePathList = new ArrayList<>();
		
		for(MultipartFile multipartFile: multipartFileList) {
			if(multipartFile != null && !multipartFile.isEmpty()){
				String originalName = multipartFile.getOriginalFilename();
				String randomUUID = UUID.randomUUID().toString();
				String reName = randomUUID + "-" + originalName;
				
				long size = multipartFile.getSize(); 
				
				ObjectMetadata objectMetaData = new ObjectMetadata();
				objectMetaData.setContentType(multipartFile.getContentType());
				objectMetaData.setContentLength(size);
				
				// S3에 업로드
				amazonS3Client.putObject(
					new PutObjectRequest(S3Bucket, reName, multipartFile.getInputStream(), objectMetaData)
						.withCannedAcl(CannedAccessControlList.PublicRead)
				);
				
				// URL 가져오기
				String imagePath = URLDecoder.decode(amazonS3Client.getUrl(S3Bucket, reName).toString(), "UTF-8"); 
				imagePathList.add(imagePath);
			}
		}
		
		return new ResponseEntity<Object>(imagePathList, HttpStatus.OK);
	}
}
