package persistence.cet;

import domain.cet.CetTraineeCourseCadreView;
import domain.cet.CetTraineeCourseCadreViewExample;
import java.util.List;
import org.apache.ibatis.session.RowBounds;

public interface CetTraineeCourseCadreViewMapper {
    long countByExample(CetTraineeCourseCadreViewExample example);

    List<CetTraineeCourseCadreView> selectByExampleWithRowbounds(CetTraineeCourseCadreViewExample example, RowBounds rowBounds);

    List<CetTraineeCourseCadreView> selectByExample(CetTraineeCourseCadreViewExample example);
}