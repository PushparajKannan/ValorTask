package com.example.valortask.view.activity.users_module.dialog

import android.app.Dialog
import android.graphics.Color
import android.graphics.Insets
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowInsets
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.valortask.R
import com.example.valortask.databinding.FragmentDialogLogoutBinding
import com.example.valortask.view.activity.users_module.viewmodel.HomeActivityViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LogOutDialog : DialogFragment() {

    private var isDialogShowing = false

    val viewModel by activityViewModels<HomeActivityViewModel>()

    private var _binding: FragmentDialogLogoutBinding? = null
    val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_logout , container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        isDialogShowing = true
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNo.setOnClickListener {
            dismiss()
        }
        binding.btnYes.setOnClickListener {
            viewModel.userLogOut()
            dismiss()
        }
    }

    fun isDialogShowing() = isDialogShowing


    override fun onDestroyView() {
        isDialogShowing = false
        super.onDestroyView()
        _binding = null
    }

    override fun dismiss() {
        isDialogShowing = false
        super.dismiss()
    }




    override fun onStart() {
        super.onStart()
        val dialog = dialog
        dialog?.setCancelable(false)
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.WRAP_CONTENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
            dialog.setCancelable(false)


        }
    }


    override fun onResume() {
        super.onResume()
        isDialogShowing = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics =
                requireActivity().windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            val width = windowMetrics.bounds.width() - insets.left -
                    insets.right
            val height = windowMetrics.bounds.height() - insets.top -
                    insets.bottom
            val height1 = ViewGroup.LayoutParams.WRAP_CONTENT

            val window = dialog!!.window
            if (window != null) {
                window.setLayout(
                    (width * 0.90).toInt(),
                    height1
                ) // for width and height to be 90 % of screen
                window.setGravity(Gravity.CENTER)
            }
            super.onResume()
        } else {
            val window = dialog!!.window
            val size = Point()
            // Store dimensions of the screen in `size`
            val display = window?.windowManager?.defaultDisplay
            display?.getSize(size)
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            val defaultWidth = ViewGroup.LayoutParams.MATCH_PARENT

            // Set the width of the dialog proportional to 90% of the screen width
            window?.setLayout(
                (display?.width?.times(0.90))?.toInt() ?: defaultWidth,
                height
            )
            window?.setGravity(Gravity.CENTER)
            // Call super onResume after sizing
            super.onResume()
        }
    }




}