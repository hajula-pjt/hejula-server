package com.hejula.server.util;

import com.hejula.server.entities.FileEntity;
import com.hejula.server.enums.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Component
public class FileUtil {

    @Value("${custom.file.path}")
    private String SAVE_PATH;

    public List<FileEntity> makeFile(List<MultipartFile> multipartFiles) throws Exception { //seq:각 se
        log.info("into makeFile()");

        List<FileEntity> resultFileList = new ArrayList<>();

        //1. null일 경우 바로 반환
        if(multipartFiles == null) return resultFileList;

        //디렉토리 존재하는지 판단
        File file = new File(SAVE_PATH);
        if(!file.exists()){ //디렉토리가 없을 경우 생성
            file.mkdirs();
        }

        for (MultipartFile multipartFile : multipartFiles) {
            log.info("multipartFile : " + multipartFile);
            if(multipartFile == null) return resultFileList;

            String originalFileNm = multipartFile.getOriginalFilename();

            //2. 파일 확장자 검사
            String extension = FilenameUtils.getExtension(originalFileNm);
            String pattern = "jpg|jpge|gif|png";
            if(Pattern.matches(pattern, extension)){
                throw new Exception(ErrorCode.INVALID_EXTENSION.getMessage());
            }

            //3. 파일명 변경
            SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
            String dateStr = sdf.format(new Date());
            String path = SAVE_PATH + dateStr + "." + extension;

            //4. 파일 생성
            file = new File(path);
            multipartFile.transferTo(file);
            log.info("file '{}' transfer success ", path);

            //5. 객체에 담음
            FileEntity fEntity = FileEntity.builder()
                    .fileNm(dateStr+"."+extension)
                    .originalFileNm(originalFileNm)
                    .filePath(SAVE_PATH)
                    .registDate(new Date())
                    .build();

            resultFileList.add(fEntity);
        }

        return resultFileList;
    }

}
