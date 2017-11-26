package com.sofiwares.youtubesearcher.view

import android.app.AlertDialog
import android.content.Context
import com.sofiwares.youtubesearcher.ErrorID
import com.sofiwares.youtubesearcher.R

/**
 * Created by Ido Sofi on 11/25/2017.
 */
class ErrorDialogGenerator {
    companion object {
        fun showDialog(context: Context, errorID: ErrorID) {
            val messageBody =
                    when(errorID) {
                        ErrorID.NO_NETWORK -> context.getString(R.string.no_network_body)
                        ErrorID.GENERAL -> context.getString(R.string.general_error_body)
                        else -> return
                    }

            AlertDialog.Builder(context, R.style.DialogTheme).setTitle(context.getString(R.string.error_title)).
                    setMessage(messageBody).
                    setPositiveButton(R.string.dialog_ok, null).
                    create().
                    show()
        }
    }
}