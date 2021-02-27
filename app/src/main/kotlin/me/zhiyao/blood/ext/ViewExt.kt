package me.zhiyao.blood.ext

import android.view.View
import com.google.android.material.snackbar.Snackbar

/**
 *
 * @author WangZhiYao
 * @date 2021/2/27
 */
fun <T : View> T.showSnackBar(msgId: Int) {
    showSnackBar(context.getString(msgId))
}

fun <T : View> T.showSnackBar(msg: String) {
    showSnackBar(msg, Snackbar.LENGTH_SHORT)
}

fun <T : View> T.showSnackBar(msgId: Int, length: Int) {
    showSnackBar(context.getString(msgId), length)
}

fun <T : View> T.showSnackBar(msg: String, length: Int) {
    showSnackBar(msg, length, null, {})
}

fun <T : View> T.showSnackBar(
    msgId: Int,
    length: Int,
    actionMessageId: Int,
    action: (View) -> Unit
) {
    showSnackBar(context.getString(msgId), length, context.getString(actionMessageId), action)
}

fun <T : View> T.showSnackBar(
    msg: String,
    length: Int,
    actionMessage: CharSequence?,
    action: (View) -> Unit
) {
    val snackBar = Snackbar.make(this, msg, length)
    if (actionMessage != null) {
        snackBar.setAction(actionMessage) {
            action(this)
        }
    }
    snackBar.show()
}