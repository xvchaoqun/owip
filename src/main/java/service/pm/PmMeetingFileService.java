package service.pm;


import domain.pm.PmMeetingFile;
import domain.pm.PmMeetingFileExample;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("PmMeetingFileService")
public class PmMeetingFileService extends PmBaseMapper {

    @Transactional
        public List<PmMeetingFile> getMeetingFiles(int id) {

            PmMeetingFileExample example = new PmMeetingFileExample();
            example.createCriteria().andMeetingIdEqualTo(id);
            List<PmMeetingFile> pmMeetingFiles =pmMeetingFileMapper.selectByExample(example);

            return pmMeetingFiles;
        }

    }

