package me.zhiyao.blood.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 *
 * @author WangZhiYao
 * @date 2021/2/25
 */
abstract class BaseFragment(@LayoutRes private val resId: Int) : Fragment(resId)