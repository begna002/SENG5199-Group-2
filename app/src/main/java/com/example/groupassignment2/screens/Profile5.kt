package com.example.groupassignment2.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.example.groupassignment2.MainViewModel
import java.time.Instant
import java.util.*

// This cache maps a string to its set of anagram permutations.
val anagramsCache = mutableStateOf(mutableMapOf<String, Set<String>>())

@Composable
fun Profile5(viewModel: MainViewModel) {
    viewModel.profileString = "WARNING: YOU VISITED JAMES' PAGE ON ${Date.from(Instant.now())}. " +
            "You should have not done this. Your mother has been called."

    // This box will ensure that the content for this screen is centered horizontally
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnagramApp()
    }
}

// This will show a simple text box that allows the user to enter up to 8 characters.
// All anagrams will be calculated, cached, and then displayed in a scrollable column.
@Composable
fun AnagramApp() {
    var text by remember { mutableStateOf("") }
    val anagrams by remember(text) { mutableStateOf(findAnagrams(text)) }

    Column(modifier = Modifier.padding(16.dp)) {

        val textFieldWidth = 120.dp // Approximately the width of 8 characters
        val maxTextLength = 8

        // Text box for user input
        OutlinedTextField(
            value = text,
            onValueChange = { newText ->

                if (newText.length <= maxTextLength) text = newText
            },
            label = { Text("Enter text") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.width(textFieldWidth)
        )

        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            item() {
                Text(text = "Anagrams:", style = MaterialTheme.typography.body1, modifier = Modifier.padding(2.dp))
            }
            items(anagrams.toList()) { anagram ->
                Text(text = anagram, style = MaterialTheme.typography.body1, modifier = Modifier.padding(2.dp))
            }
        }
    }
}



fun findAnagrams(input: String): Set<String> {
    if (input.isBlank()) return emptySet()

    val result = mutableSetOf<String>()
    fun permute(str: String, l: Int, r: Int) {
        if (l == r) result.add(str)
        else {
            for (i in l..r) {
                val newStr = str.toCharArray()
                // Swap characters at indices l and i in the newStr array. This is done as part of
                // the permutation process. The character at str[i] is placed in newStr[l], and
                // simultaneously, the character at str[l] is moved to newStr[i]. The use of .also
                // allows for the swap to occur in a single expression without a temporary variable.
                newStr[l] = str[i].also { newStr[i] = str[l] }
                // Recurse!
                permute(String(newStr), l + 1, r)
            }
        }
    }

    // Check cache first. This saves computation if the anagram is already known.
    anagramsCache.value[input]?.let {
        return it
    }

    permute(input, 0, input.length - 1)

    // Update cache
    anagramsCache.value[input] = result

    return result
}
