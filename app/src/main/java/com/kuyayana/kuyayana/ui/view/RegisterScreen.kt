package com.kuyayana.kuyayana.ui.view

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kuyayana.kuyayana.R
import com.kuyayana.kuyayana.ui.viewmodel.auth.AuthViewModel
import com.kuyayana.kuyayana.ui.viewmodel.auth.RegisterState

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel = viewModel(),
    onRegisterSuccess: () -> Unit,
    onLoginClick: () -> Unit
    ) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var validatePassword by remember { mutableStateOf("") }
    val registerState by authViewModel.registerState.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_transparent),
            contentDescription ="Kuya Yana Logo",
            modifier = Modifier
                .size(width = 300.dp, height = 200.dp)
        )
        Text(
            text = "Registro de Usuario",
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(24.dp))

        /*TextField(
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
        )*/
        OutlinedTextField(
            value = email,
            onValueChange = { email = it} ,
            enabled = true,
            label = { Text(text = stringResource(R.string.email)) },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.mail), contentDescription = "email" ) },
            placeholder = { Text(text = stringResource(R.string.ingrese_su_correo_electr_nico))},
            //supportingText = { Text(text = "Email mal escrito")},
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
            //supportingText = { Text(text = "Contraseña incorrecta")},
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
        OutlinedTextField(
            value = validatePassword,
            onValueChange = { validatePassword = it } ,
            enabled = true,
            label = { Text("Repetir Contraseña" )},
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.pass), contentDescription = stringResource(
                id = R.string.pass
            ) ) },
            placeholder = { Text(text = "Ingrese su contraseña")},
            supportingText = {
                if (password != validatePassword && password != ""){
                    Text(text = "Las contraseñas no coinciden", color = Color.Red)
                }
                             },
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


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (password == validatePassword && password != ""){
                    authViewModel.register(email, password)
                    onRegisterSuccess()
                }
                      },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(text = "¿Tienes una cuenta?")
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Inicia Sesión",
                color = Color.Red,
                modifier = Modifier
                    .clickable{onLoginClick()}
            )
        }

        when (registerState) {
            is RegisterState.Loading -> {
                CircularProgressIndicator()
            }
            is RegisterState.Success -> {
                LaunchedEffect(Unit) {
                    onRegisterSuccess()
                }
            }
            is RegisterState.Error -> {
                Text(text = (registerState as RegisterState.Error).message, color = Color.Red)
            }
            else -> {}
        }
    }
}
