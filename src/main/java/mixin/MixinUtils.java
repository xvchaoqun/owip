package mixin;

import domain.cadre.Cadre;
import domain.cadre.CadreView;
import domain.member.Member;
import domain.member.MemberView;
import domain.sys.SysUser;
import domain.sys.SysUserView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lm on 2017/7/16.
 */
public class MixinUtils {

    public static Map<Class<?>, Class<?>> baseMixins() {

        Map<Class<?>, Class<?>> baseMixins = new HashMap<>();
        baseMixins.put(SysUser.class, SysUserMixin.class);
        baseMixins.put(SysUserView.class, SysUserMixin.class);
        baseMixins.put(Cadre.class, CadreMixin.class);
        baseMixins.put(CadreView.class, CadreMixin.class);
        baseMixins.put(Member.class, MemberMixin.class);
        baseMixins.put(MemberView.class, MemberMixin.class);

        return baseMixins;
    }
}
