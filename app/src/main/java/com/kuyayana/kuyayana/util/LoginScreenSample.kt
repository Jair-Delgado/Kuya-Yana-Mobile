package com.kuyayana.kuyayana.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kuyayana.kuyayana.R

@Composable
fun Login(modifier: Modifier = Modifier){
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
            value = "Correo Electronico",
            onValueChange = { "Coreero" } ,
            enabled = true,
            label = { Text(text = "Correo electronico") },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.mail), contentDescription = "email" )},
            placeholder = { Text(text = "Ingrese su correo electrónico")},
            supportingText = { Text(text = "Email mal escrito")},
            isError = false
        )
        Spacer(modifier = Modifier.height(24.dp))
        OutlinedTextField(
            value = "Contraseña",
            onValueChange = { "Coreero" } ,
            enabled = true,
            label = { Text(text = "Contraseña") },
            leadingIcon = { Icon(painter = painterResource(id = R.drawable.pass), contentDescription = "password" )},
            placeholder = { Text(text = "Ingrese su contraseña")},
            supportingText = { Text(text = "Contraseña incorrecta")},
            isError = false
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )


        ) {
            Text(
                text = "Ingresar"
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text(text = "¿No tienes un cuenta?")
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Registrate aquí",
                color = Color.Red
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun LoginPrev (){
    Login()
}