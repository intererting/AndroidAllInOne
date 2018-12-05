package com.lqd.commonimp.client

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*
import kotlin.properties.Delegates

open class BaseApplication : Application(), Application.ActivityLifecycleCallbacks {

    private val activityStack: Stack<Activity> = Stack()

    fun getActivityStack(): List<Activity> = Collections.unmodifiableList(activityStack).reversed()

    //栈顶Activity
    val topActivityInStack: Activity
        get() = getActivityStack().first()

    companion object {

        @JvmStatic
        private var instance: BaseApplication by Delegates.notNull()

        @JvmStatic
        fun provideInstance(): BaseApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        registerActivityLifecycleCallbacks(this)
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
        activityStack.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activityStack.push(activity)
    }
}