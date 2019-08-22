package service.pm;


import domain.pm.PmMeetingFile;
import domain.pm.PmMeetingFileExample;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sys.HttpResponseMethod;
import sys.constants.LogConstants;

import java.io.IOException;
import java.util.*;

@Service("PmMeetingFileService")
public class PmMeetingFileService extends PmBaseMapper implements HttpResponseMethod{

    @Transactional
        public List<PmMeetingFile> getMeetingFiles(int id) {

            PmMeetingFileExample example = new PmMeetingFileExample();
            example.createCriteria().andMeetingIdEqualTo(id);
            List<PmMeetingFile> pmMeetingFiles =pmMeetingFileMapper.selectByExample(example);

            return pmMeetingFiles;
        }

    @Transactional
    public void insertMeetingFiles(int meetingId,MultipartFile[] files) throws IOException, InterruptedException {

        for (MultipartFile file : files) {

            PmMeetingFile record = new PmMeetingFile();
            record.setMeetingId(meetingId);
            record.setFileName(file.getOriginalFilename());
            record.setFilePath(upload(file, "pmMeetingFile"));

            pmMeetingFileMapper.insertSelective(record);

        }
       return;
     }

    }

