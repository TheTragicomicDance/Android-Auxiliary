package top.easternday.Auxiliary;

import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.util.Log;
import android.widget.Toast;


public class NativeDirectoryPickerFragment extends Fragment {
    /**
     * 用于返回消息的消息值
     */
    private static final int MEDIA_REQUEST_CODE = 987455;

    /**
     * 用于处理回调的接收器
     */
    private final NativeDirectoryReceiver dirReceiver;

    /**
     * 初始化函数，正常来说不会这样初始化
     */
    public NativeDirectoryPickerFragment ()
    {
        dirReceiver = null;
    }

    /**
     * @param dirReceiver 用于接受文件夹返回的接收器
     */
    public NativeDirectoryPickerFragment ( NativeDirectoryReceiver dirReceiver )
    {
        this.dirReceiver = dirReceiver;
    }

    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        // 安全性检查，防止回调为空
        if( dirReceiver == null ) {
            // 移除该分页
            getActivity().getFragmentManager().beginTransaction().remove( this ).commit();
        } else
        {
            // 调用安卓原生文件夹选择功能
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.addFlags( Intent.FLAG_GRANT_READ_URI_PERMISSION );
            try {
                startActivityForResult(Intent.createChooser(intent,"请选择文件"), MEDIA_REQUEST_CODE);
            }
            catch( ActivityNotFoundException e )
            {
                Toast.makeText( getActivity(), "No apps can perform this action.", Toast.LENGTH_LONG ).show();
                onActivityResult( MEDIA_REQUEST_CODE, Activity.RESULT_CANCELED, null );
            }
        }
    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data )
    {
        // 判定是否为文件夹选择的响应
        if( requestCode != MEDIA_REQUEST_CODE )
            return;
        // 安全性检查，防止回调为空
        if( dirReceiver == null )
            Log.d( "Unity Plugins", "dirReceiver became null!" );
        else if( resultCode != Activity.RESULT_OK || data == null )
        {
            // 相应失败返回空目录
            dirReceiver.OnDirectoryReceived ( "" );
        }
        else
        {
            // 解析返回的真实地址
            Uri uri = data.getData();
            Uri docUri = DocumentsContract.buildDocumentUriUsingTree(uri,
                                    DocumentsContract.getTreeDocumentId(uri));
            String path = GetRealPath (docUri);
            // 回调
            dirReceiver.OnDirectoryReceived ( path );
        }
        // 销毁本分页
        getFragmentManager().beginTransaction().remove( this ).commit();
    }

    /**
     * @param treeUri 获取到的uri
     * @return 文件的真实地址
     */
    public String GetRealPath ( Uri treeUri )
    {
        // 安全性检查，判断uri是否为空
        if (treeUri == null)
            return "";
        //获取uri内部返回的地址
        String path1 = treeUri.getPath ();
        // 地址解析
        if (path1.startsWith("/tree/"))
        {
            String path2 = path1.replace ("/tree/","");
            //返回主存储器地址
            if (path2.startsWith("primary:"))
            {
                String storeName = "/storage/emulated/0/";
                String[] list = path2.split( ":" );
                String last = list[list.length-1];
                return storeName + last;
            }
            //非主存储器返回地址
            else
            {
                String[] list = path2.split( ":" );
                String storeName = list[0];
                String last = list[list.length-1];
                String  realPath;
                realPath = "/" + storeName + "/" + last;
                return realPath;
            }
        }
        // 错误地址返回为空
        return "";
    }
}
