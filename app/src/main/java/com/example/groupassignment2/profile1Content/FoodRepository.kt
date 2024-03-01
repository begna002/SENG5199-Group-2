package com.example.groupassignment2.profile1Content

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.groupassignment2.data.FoodItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Composable
fun SaveRecipe(context: Context, item: FoodItem, previousRecipes: ArrayList<FoodItem>?) {
    val composableScope = rememberCoroutineScope()
    var savedRecipes = previousRecipes

    val filename = "recipes"
    val file = File(context.filesDir, filename)
    var fileOutputStream = FileOutputStream(file)

    if (savedRecipes == null) {
        savedRecipes = arrayListOf(item)
    } else {
        savedRecipes.add(item)
    }

    var gsonItem = Gson().toJson(savedRecipes)

    LaunchedEffect(key1 = Unit) {
        composableScope.launch {
            fileOutputStream.write(gsonItem.toByteArray())
        }
    }
}

@Composable
fun SaveAllRecipes(context: Context, recipes: List<FoodItem>?) {
    val composableScope = rememberCoroutineScope()

    val filename = "recipes"
    val file = File(context.filesDir, filename)
    var fileOutputStream = FileOutputStream(file)

    var gsonItem = Gson().toJson(recipes)

    LaunchedEffect(key1 = Unit) {
        composableScope.launch {
            fileOutputStream.write(gsonItem.toByteArray())
        }
    }
}

fun deleteAll(context: Context, clearResponse: () -> Unit) {
    val fileName = "recipes"
    val file = File(context.filesDir, fileName)

    if (file.exists()) {
        System.out.println("Deleted!")
        file.delete();
        clearResponse()
    }
}

@Composable
fun deleteRecipe(context: Context, item: FoodItem, clearResponse: () -> Unit) {
    var savedRecipes = getSavedRecipes(context)
    var filteredList = savedRecipes?.filter { recipe -> recipe.id != item.id }

    if (filteredList != null) {
        if (filteredList.isNotEmpty()) {
            SaveAllRecipes(context, filteredList)
            clearResponse()
        } else {
            deleteAll(context, clearResponse)
        }
    }
}


fun getFile(context: Context, fileName: String): String {
    val file = File(context.filesDir, fileName)

    try {
        if (file.exists()) {
            System.out.println("Found!")
            return file.readText()
        } else {
            System.out.println("Not Found!")
        }

    } catch (exp: IOException) {
        exp.printStackTrace()
    }

    return ""
}

fun getSavedRecipes(context: Context): List<FoodItem>? {
    val jsonFileString = getFile(context = context, fileName = "recipes")
    val type = object : TypeToken<ArrayList<FoodItem>>() {}.type
    if (jsonFileString != "") {
        return Gson().fromJson(jsonFileString, type)
    }

    return null
}
