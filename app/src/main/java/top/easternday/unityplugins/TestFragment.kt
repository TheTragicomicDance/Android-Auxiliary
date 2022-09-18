package top.easternday.unityplugins

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import top.easternday.Auxiliary.NativeDirectoryReceiver
import top.easternday.Auxiliary.Tools
import top.easternday.Auxiliary.Tools.PickDirectory
import top.easternday.unityplugins.databinding.FragmentTestBinding

/**
 * 用于测试功能的测试页。
 */
class TestFragment : Fragment(),NativeDirectoryReceiver {

    private var _binding: FragmentTestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonShowToast.setOnClickListener {
            //findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            Tools.showToast(binding.inputToast.text.toString(), context as Activity?)
        }

        binding.buttonFilePicker.setOnClickListener {
            PickDirectory(context,this)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }
    override fun OnDirectoryReceived(path : String?) {
        Log.d("Unity Plugins" , "OnMediaReceived: $path")
        binding.textPickDirectory.text = "您选择的路径为： $path"

    }
}
