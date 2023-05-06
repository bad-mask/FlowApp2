package com.zly.flowapp2.util.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun FragmentActivity.replaceFragmentInActivity(fragment: Fragment,
                                               containerViewId: Int,
                                               addToBack: Boolean = false,
                                               tag: String? = null) {
    supportFragmentManager.transact {
        replace(containerViewId, fragment, tag)
        if (addToBack) {
            addToBackStack(null)
        }
    }
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun FragmentActivity.addFragmentToActivity(fragment: Fragment,
                                           containerViewId: Int,
                                           addToBack: Boolean = true,
                                           tag: String? = null,
                                           animate: Boolean = false) {
    supportFragmentManager.transact {
//        if (animate) {
//            setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
//        }
        add(containerViewId, fragment, tag)
        if (addToBack) {
            addToBackStack(null)
        }
    }
}

fun FragmentActivity.removeFragmentFromActivity(fragment: Fragment) {
    supportFragmentManager.transact {
        remove(fragment)
    }
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 * #commitAllowingStateLoss
 */
fun FragmentActivity.addFragmentToActivityAllowingStateLoss(fragment: Fragment,
                                                            containerViewId: Int,
                                                            addToBack: Boolean = true,
                                                            tag: String? = null,
                                                            animate: Boolean = true) {
    supportFragmentManager.transactAllowingStateLoss {
//        if (animate) {
//            setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
//        }
        add(containerViewId, fragment, tag)
        if (addToBack) {
            addToBackStack(null)
        }
    }
}

fun FragmentActivity.removeFragmentFromActivityAllowingStateLoss(fragment: Fragment) {
    supportFragmentManager.transactAllowingStateLoss {
        remove(fragment)
    }
}


/**
 * Runs a FragmentTransaction, then calls commit().
 */
inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commit()
}

inline fun FragmentManager.transactNow(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitNow()
}


/**
 * Runs a FragmentTransaction, then calls commitAllowingStateLoss().
 * 解决onBackPressed bug
 * Exception:Can not perform this action after onSaveInstanceState
 */
inline fun FragmentManager.transactAllowingStateLoss(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitAllowingStateLoss()
}
