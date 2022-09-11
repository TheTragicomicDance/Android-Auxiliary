# Android Auxiliary for Unity

``` JAVA
package top.easternday.unitilities;

import android.app.Activity;
import android.widget.Toast;

public class Unity2Android {
    /**
     * unity项目启动时的的上下文
     */
    private Activity _unityActivity;

    /**
     * 获取unity项目的上下文
     */
    Activity getActivity(){
        if(null == _unityActivity) {
            try {
                Class<?> classtype = Class.forName("com.unity3d.player.UnityPlayer");
                _unityActivity = (Activity) classtype.getDeclaredField("currentActivity").get(classtype);
            } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException ignored) {

            }
        }
        return _unityActivity;
    }

    /**
     * Toast显示unity发送过来的内容
     * @param content           消息的内容
     * @return                  调用是否成功
     */
    public boolean showToast(String content,Activity activity){
        Toast.makeText(activity,content,Toast.LENGTH_SHORT).show();
        return true;
    }
    public boolean showToast(String content){
        return showToast(content,getActivity());
    }
}

```