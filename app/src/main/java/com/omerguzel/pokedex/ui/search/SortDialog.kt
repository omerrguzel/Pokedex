package com.omerguzel.pokedex.ui.search

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import com.omerguzel.pokedex.databinding.DialogSortOptionsBinding

class SortDialog(
    private val context: Context,
    val defaultOption : SortOption = SortOption.NUMBER,
    val onSortOptionChanged: ((sortOption:SortOption) -> Unit)? = null,
    val onDialogDismissed: (() -> Unit)? = null
) {

    private val binding = DialogSortOptionsBinding.inflate(LayoutInflater.from(context))

    private var dialog: AlertDialog? = null

    private var isAutoClose = false

    init {
        with(binding) {
            if (defaultOption == SortOption.NUMBER) sortByNumber.isChecked =true
            else sortByName.isChecked=true
            sortOptionsGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    sortByNumber.id -> {
                        onSortOptionChanged?.invoke(SortOption.NUMBER)
                        sortByName.isChecked=false
                    }

                    sortByName.id -> {
                        onSortOptionChanged?.invoke(SortOption.NAME)
                        sortByNumber.isChecked=false
                    }
                }
                dismissDialog()
            }
        }
    }

    fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(context).setView(binding.root)

        dialog = dialogBuilder.show()
        dialog?.setOnDismissListener {
            if (isAutoClose.not()) {
                onDialogDismissed?.invoke()
            }
        }
        binding.root.setOnClickListener{
            dismissDialog()
        }
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.setCancelable(true)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setGravity(Gravity.END)
        dialog?.window?.setGravity(Gravity.TOP)
    }

    fun dismissDialog() {
        dialog?.dismiss()
    }
}

enum class SortOption{
    NUMBER,NAME
}
