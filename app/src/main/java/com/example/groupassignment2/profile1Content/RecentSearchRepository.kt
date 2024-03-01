package com.example.groupassignment2.profile1Content

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


@Composable
fun SaveRecentSearch(query: String) {
    val context = LocalContext.current
    val composableScope = rememberCoroutineScope()
    var savedSearches = getRecentSearch(context)

    var savedSearchesArray: ArrayList<String>?

    if (savedSearches != null) {
        savedSearchesArray = ArrayList(savedSearches)
    } else {
        savedSearchesArray = null
    }

    var searchExists = false
    savedSearchesArray?.forEach() { text -> if (text == query) searchExists = true }

    if (!searchExists && query != "") {

        if (savedSearchesArray == null) {
            savedSearchesArray = arrayListOf(query)
        } else {
            savedSearchesArray.add(query)
        }

        val filename = "recentSearches"
        val file = File(context.filesDir, filename)
        var fileOutputStream = FileOutputStream(file)


        var gsonItem = Gson().toJson(savedSearchesArray)

        LaunchedEffect(key1 = Unit) {
            composableScope.launch {
                fileOutputStream.write(gsonItem.toByteArray())
            }
        }
    }
}

fun getRecentSearch(context: Context): List<String>? {
    val jsonFileString = getFile(context = context, fileName = "recentSearches")
    val type = object : TypeToken<ArrayList<String>>() {}.type
    if (jsonFileString != "") {
        return Gson().fromJson(jsonFileString, type)
    }

    return null
}