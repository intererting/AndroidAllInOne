package com.lqd.commonimp.client

import com.lqd.commonimp.db.DatabaseFactory

open class BaseDbRepository {

    protected val db by lazy {
        DatabaseFactory.db
    }

    protected fun dbWithTransaction(modifier: () -> Unit) {
        try {
            db.beginTransaction()
            modifier()
            db.setTransactionSuccessful()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            db.endTransaction()
        }
    }
}