package top.easternday.Auxiliary;

import android.app.Activity;
import android.widget.Toast;

public class Tools {

    /**
     * Toast显示unity发送过来的内容
     * @param content           消息的内容
     * @return                  调用是否成功
     */
    public static boolean showToast(String content, Activity activity){
        Toast.makeText(activity,content,Toast.LENGTH_SHORT).show();
        return true;
    }

    public static boolean unityToast(String content){
        return showToast(content,UnityActivity.getActivity());
    }
}
