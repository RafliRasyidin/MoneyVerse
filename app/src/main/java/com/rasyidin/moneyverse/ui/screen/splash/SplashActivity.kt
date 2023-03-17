package com.rasyidin.moneyverse.ui.screen.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.ui.screen.home.HomeActivity
import com.rasyidin.moneyverse.ui.screen.home.HomeViewModel
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            MoneyVerseTheme {
                ScreenSplash()
                LaunchedEffect(Unit) {
                    delay(1500)
                    startActivity(Intent(context, HomeActivity::class.java))
                }
            }
        }
    }
}

@Composable
fun ScreenSplash(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(120.dp),
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenSplash() {
    ScreenSplash()
}