package com.kuyayana.kuyayana.ui.view

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.kuyayana.kuyayana.R
import com.kuyayana.kuyayana.ui.viewmodel.auth.AuthViewModel
import com.kuyayana.kuyayana.ui.viewmodel.auth.LoginState

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onRegisterClick:() -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by authViewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_transparent),
            contentDescription ="Kuya Yana Logo",
            modifier = Modifier
                .size(width = 300.dp, height = 300.dp)
        )
        Text(
            text = "Iniciar Sesion",
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it} ,
            enabled = true,
            label = { Text(text = stringResource(R.string.email)) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.mail), contentDescription = "email" ) },
            placeholder = { Text(text = stringResource(R.string.ingrese_su_correo_electr_nico))},
            supportingText = { Text(text = "Email mal escrito")},
            isError = false,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
        //Spacer(modifier = Modifier.height(2.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it } ,
            enabled = true,
            label = { Text(text = stringResource(R.string.pass)) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.pass), contentDescription = stringResource(
                id = R.string.pass
            ) ) },
            placeholder = { Text(text = "Ingrese su contraseña")},
            supportingText = { Text(text = "Contraseña incorrecta")},
            isError = false,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { authViewModel.login(email, password) },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )


        ) {
            Text(
                text = "Ingresar"
            )
        }
            when (loginState) {
                is LoginState.Loading -> {
                    CircularProgressIndicator()
                }

                is LoginState.Success -> {
                    LaunchedEffect(Unit) {
                        onLoginSuccess()
                    }
                }

                is LoginState.Error -> {
                    Text(text = (loginState as LoginState.Error).message, color = Color.Red)
                }

                else -> {}
            }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(text = "¿No tienes una cuenta?")
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Registrate aquí",
                color = Color.Red,
                modifier = Modifier
                    .clickable{onRegisterClick()}
            )
        }

    }


    /*Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Login", )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { authViewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login with Google")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onRegisterClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Register")
        }

        when (loginState) {
            is LoginState.Loading -> {
                CircularProgressIndicator()
            }
            is LoginState.Success -> {
                LaunchedEffect(Unit) {
                    onLoginSuccess()
                }
            }
            is LoginState.Error -> {
                Text(text = (loginState as LoginState.Error).message, color = Color.Red)
            }
            else -> {}
        }
    }*/
}


