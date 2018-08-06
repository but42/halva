package com.but42.halva.ui

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import com.but42.halva.databinding.DialogTimeOutBinding

/**
 * Created by Mikhail Kuznetsov on 06.08.2018.
 *
 * @author Mikhail Kuznetsov
 */
class TimeOutDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogTimeOutBinding.inflate(LayoutInflater.from(activity))
        binding.timeOutButton.setOnClickListener { dismiss() }
        return AlertDialog.Builder(activity!!)
                .setView(binding.root)
                .create()
    }

    companion object {
        fun newInstance() = TimeOutDialogFragment()
    }
}