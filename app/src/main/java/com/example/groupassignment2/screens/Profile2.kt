package com.example.groupassignment2.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
<<<<<<< Updated upstream
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.groupassignment2.service.CardsSection
import com.example.groupassignment2.MainViewModel
import com.example.groupassignment2.service.EventsFormSection
import com.example.groupassignment2.service.TopWelcomeSection
import java.time.Instant
import java.util.Date


@Composable
fun Profile2(viewModel: MainViewModel) {
    viewModel.profileString = "Bassam visited on ${Date.from(Instant.now())}"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding()
    ) {
        TopWelcomeSection()
        CardsSection()
        Spacer(modifier = Modifier.height(10.dp))
        EventsFormSection()
    }
=======
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import com.example.groupassignment2.CardsSection
import com.example.groupassignment2.CurrenciesSection
import com.example.groupassignment2.Drawer
import com.example.groupassignment2.MainViewModel
import com.example.groupassignment2.NavRoutes
import com.example.groupassignment2.WalletSection


@Composable
fun Profile2(navController: NavHostController, viewModel: MainViewModel) {
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//
//    ) {
//        Column (horizontalAlignment = Alignment.CenterHorizontally){
//            Text("Bassam screen page", style = MaterialTheme.typography.headlineSmall)
//        }
//    }
    CardsSection()
    WalletSection()
    CurrenciesSection()
//    Drawer(navController)
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}

