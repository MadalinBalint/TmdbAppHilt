package com.mendelin.tmdb_hilt.domain.logging

import android.util.Log
import timber.log.Timber

class TimberDebugger : Timber.Tree() {

    override fun isLoggable(tag: String?, priority: Int): Boolean { // determine what type of entries to log
        return priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isLoggable(tag, priority)) {
            if (message.length < LOG_MAX_LENGTH) { // If Message is within the prescribed length. no need to split
                doLog(priority, tag, message)
                return
            }

            // Split by line, then ensure each line can fit into Log's maximum length.
            var i = 0
            val length = message.length
            while (i < length) {
                var newLine = message.indexOf('\n', i)
                newLine =
                    if (newLine != -1) newLine else length // if newLine != -1, return newline otherwise return length
                do {
                    val end = Math.min(newLine, i + LOG_MAX_LENGTH)
                    val part = message.substring(i, end)
                    doLog(priority, tag, part)
                    i = end
                } while (i < newLine)
                i++
            }
        }
    }

    private fun doLog(priority: Int, tag: String?, message: String) {
        if (priority == Log.ASSERT) {
            Timber.wtf(message)
        } else {
            Log.println(priority, tag, message)
        }
    }

    companion object {

        private const val LOG_MAX_LENGTH = 3000 // set maximum length of log
    }
}