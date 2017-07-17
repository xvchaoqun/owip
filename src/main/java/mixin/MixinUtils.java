package mixin;

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

        return baseMixins;
    }
}
