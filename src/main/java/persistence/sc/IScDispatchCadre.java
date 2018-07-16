package persistence.sc;

import domain.abroad.Passport;
import domain.dispatch.DispatchCadre;

import java.util.List;

/**
 * Created by lm on 2018/7/15.
 *
 * 从任免文件中提取新任干部交证件情况
 */
public class IScDispatchCadre extends DispatchCadre {

    public boolean hasImport; // 是否已经添加了
    public List<Passport> passports; // 干部现有的证件信息

    public boolean isHasImport() {
        return hasImport;
    }

    public void setHasImport(boolean hasImport) {
        this.hasImport = hasImport;
    }

    public List<Passport> getPassports() {
        return passports;
    }

    public void setPassports(List<Passport> passports) {
        this.passports = passports;
    }
}
