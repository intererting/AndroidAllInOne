package com.lqd.commonimp.cstmview

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.lqd.commonimp.extend.topMargin
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.dip

/**
 * 网络加载中
 */
class LoadingFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDimAmount(0.5f)
        }
        return codeCreateView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.apply {
            val msg = getString("msg") ?: "正在努力加载中..."
            infoMsgTextView.text = msg
        }
    }

    private val infoMsgTextView by lazy {
        TextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            textSize = 15f
            setTextColor(Color.WHITE)
            topMargin = dip(5)
        }
    }

    private fun codeCreateView(): View {
        val linearLayout = LinearLayout(context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            gravity = Gravity.CENTER
            orientation = LinearLayout.VERTICAL
        }
        linearLayout.addView(infoMsgTextView)
        return linearLayout
    }

    override fun showNow(manager: FragmentManager, tag: String?) {
        manager.beginTransaction().setCustomAnimations(0, 0).commit()
        super.showNow(manager, tag)
    }

    companion object {
        /**
         * 构造方法
         */
        fun newInstance(cancelable: Boolean, msg: String?): LoadingFragment {
            val loadingFragment = newInstance(cancelable)
            loadingFragment.arguments = bundleOf("msg" to msg)
            return loadingFragment
        }

        /**
         * 构造方法
         */
        private fun newInstance(cancelable: Boolean): LoadingFragment {
            val loadingFragment = LoadingFragment()
            loadingFragment.isCancelable = cancelable
            return loadingFragment
        }
    }
}