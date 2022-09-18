package top.easternday.Auxiliary;

/**
 * 这是暴露给Unity的一个端口
 */
public interface NativeDirectoryReceiver
{
    /**
     * 这是一个用于Unity覆写的接口
     * @param path 文件的真实地址
     * @see <a href="https://docs.unity3d.com/ScriptReference/AndroidJavaProxy.html">Unity与AndroidJavaProxy交互实现Unity委托事件到Android端</a>
     */
    void OnDirectoryReceived ( String path );
}