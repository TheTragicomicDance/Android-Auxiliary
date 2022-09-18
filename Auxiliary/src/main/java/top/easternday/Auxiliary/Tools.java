package top.easternday.Auxiliary;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
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

    /**
     * 用于Unity显示文本弹窗
     * @param content 要在弹窗内显示内容
     * @return 是否成功
     */
    public static boolean unityToast(String content){
        return showToast(content, UnityActivityProxy.getActivity());
    }

    /**
     * 用于选择文件夹
     * @param context      对应的上下文
     * @param dirReceiver 文件接收器
     */
    public static void PickDirectory ( Context context , final NativeDirectoryReceiver dirReceiver) {
        Fragment request = new NativeDirectoryPickerFragment ( dirReceiver );
        ( ( Activity ) context ).getFragmentManager ( ).beginTransaction ( ).add ( 0 , request ).commit ( );
    }
}
