package com.webers.applock.ui.vault.addingvaultdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.webers.applock.data.database.vault.VaultMediaType
import com.webers.applock.data.database.vault.VaultMediaType.TYPE_IMAGE
import com.webers.applock.data.database.vault.VaultMediaType.TYPE_VIDEO
import com.webers.applock.R
import com.webers.applock.databinding.DialogAddToVaultBinding
import com.webers.applock.ui.BaseBottomSheetDialog
import com.webers.applock.ui.vault.analytics.VaultAnalytics
import com.webers.applock.util.delegate.inflate

class AddToVaultDialog : BaseBottomSheetDialog<AddToVaultViewModel>() {

    var onDismissListener: (() -> Unit)? = null

    private val binding: DialogAddToVaultBinding by inflate(R.layout.dialog_add_to_vault)

    override fun getViewModel(): Class<AddToVaultViewModel> = AddToVaultViewModel::class.java

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val filePath = arguments?.getStringArrayList(KEY_FILE_PATH)
        val mediaType = arguments?.getSerializable(KEY_MEDIA_TYPE) as VaultMediaType

        viewModel.getAddToVaultViewStateLiveData().observe(viewLifecycleOwner, Observer { viewState ->
            binding.viewState = viewState
            binding.executePendingBindings()

            if (viewState.processState == ProcessState.COMPLETE) {
                onDismissListener?.invoke()
                dismiss()

                when (mediaType) {
                    TYPE_IMAGE -> activity?.let { activity ->
                        filePath?.let { list ->
                            VaultAnalytics.addedImageVault(activity, list.size)
                        }
                    }
                    TYPE_VIDEO -> activity?.let { activity ->
                        filePath?.let { list ->
                            VaultAnalytics.addedVideoVault(activity, list.size)
                        }
                    }
                }
            }
        })

        filePath?.let { viewModel.setSelectedFilePath(it, mediaType) }
    }

    companion object {

        private const val KEY_FILE_PATH = "KEY_FILE_PATH"

        private const val KEY_MEDIA_TYPE = "KEY_MEDIA_TYPE"

        fun newInstance(
            selectedFilePath: ArrayList<String?>,
            mediaType: VaultMediaType
        ): AddToVaultDialog {
            return AddToVaultDialog().apply {
                isCancelable = false
                arguments = Bundle().apply {
                    putStringArrayList(KEY_FILE_PATH, selectedFilePath)
                    putSerializable(KEY_MEDIA_TYPE, mediaType)
                }
            }
        }
    }
}