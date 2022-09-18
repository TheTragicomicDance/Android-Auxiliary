package top.easternday.Auxiliary;

import android.app.Activity;

public class UnityActivityProxy {

    /**
     * 获取unity项目的上下文
     */
    public static Activity getActivity(){
            try {
                Class<?> classType = Class.forName("com.unity3d.player.UnityPlayer");
                return (Activity) classType.getDeclaredField("currentActivity").get(classType);
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException ignored) {
            }
        return null;
    }
}
