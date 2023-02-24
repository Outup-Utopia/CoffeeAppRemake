package dev.outup.coffeeapp.screen

import android.widget.Toast
import androidx.compose.material.icons.filled.Person

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.outup.coffeeapp.ui.theme.Purple80
import dev.outup.coffeeapp.viewModel.SignUpViewModel

@Composable
fun SignUpScreen(jumpButtonHandler: () -> Unit, handleOnSignUp: () -> Unit) {
    val viewModel = SignUpViewModel()
    Box {
        Surface(color = Purple80, modifier = Modifier.fillMaxSize()) {

        }
        Surface(
            color = Color.White,
            modifier = Modifier.height(600.dp).fillMaxWidth(),
            shape = RoundedCornerShape(20.dp).copy(topStart = ZeroCornerSize, topEnd = ZeroCornerSize)
        ) {
            Column(
                modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val modifier = Modifier.padding(horizontal = 16.dp)
                Spacer(modifier = Modifier.padding(16.dp))
                UserNameInputFieldForSignUp(viewModel, modifier)
                Spacer(modifier = Modifier.padding(6.dp))
                EmailInputFieldForSignUp(viewModel, modifier)
                Spacer(modifier = Modifier.padding(6.dp))
                PasswordInputFieldForSignUp(viewModel, modifier)
                Spacer(modifier = Modifier.padding(6.dp))
                PasswordConfirmInputFieldForSignUp(viewModel, modifier)
                Spacer(modifier = Modifier.padding(vertical = 12.dp))
                SignUpButton(viewModel, modifier, handleOnSignUp)
                JumpToLoginButton(modifier, jumpButtonHandler)
            }
        }
    }
}

@Composable
fun UserNameInputFieldForSignUp(viewModel: SignUpViewModel, modifier: Modifier) {
    val userName = remember { mutableStateOf("") }
    OutlinedTextField(
        value = userName.value,
        onValueChange = { newValue ->
            userName.value = newValue
            viewModel.userName = newValue
        },
        label = { Text("User Name") },
        leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "User") },
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun EmailInputFieldForSignUp(viewModel: SignUpViewModel, modifier: Modifier) {
    val email = remember { mutableStateOf("") }
    OutlinedTextField(
        value = email.value,
        onValueChange = {
            email.value = it.trim()
            viewModel.email = it.trim()
        },
        label = { Text("Email") },
        leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordInputFieldForSignUp(viewModel: SignUpViewModel, modifier: Modifier) {
    var password by remember { mutableStateOf(value = "") }
    var showPassword by remember { mutableStateOf(value = false) }

    OutlinedTextField(modifier = modifier.fillMaxWidth(),
        value = password,
        onValueChange = { newText ->
            password = newText
            viewModel.password = newText
        },
        label = {
            Text(text = "Password")
        },
        placeholder = { Text(text = "Please Input Your Password") },
        shape = RoundedCornerShape(percent = 20),
        visualTransformation = if (showPassword) {

            VisualTransformation.None

        } else {

            PasswordVisualTransformation()

        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility, contentDescription = "hide_password"
                    )
                }
            } else {
                IconButton(onClick = { showPassword = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff, contentDescription = "hide_password"
                    )
                }
            }
        })
}

@Composable
fun PasswordConfirmInputFieldForSignUp(viewModel: SignUpViewModel, modifier: Modifier) {

    var passwordForConfirm by remember { mutableStateOf(value = "") }
    var showPassword by remember { mutableStateOf(value = false) }

    OutlinedTextField(modifier = modifier.fillMaxWidth(),
        value = passwordForConfirm,
        onValueChange = { newText ->
            passwordForConfirm = newText
        },
        label = {
            Text(text = "Confirm Password")
        },
        placeholder = { Text(text = "Please Confirm Your Password") },
        shape = RoundedCornerShape(percent = 20),
        visualTransformation = if (showPassword) {

            VisualTransformation.None

        } else {

            PasswordVisualTransformation()

        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            if (showPassword) {
                IconButton(onClick = { showPassword = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility, contentDescription = "hide_password"
                    )
                }
            } else {
                IconButton(onClick = { showPassword = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff, contentDescription = "hide_password"
                    )
                }
            }
        })
}

@Composable
fun SignUpButton(viewModel: SignUpViewModel, modifier: Modifier, handleOnSignUp: () -> Unit) {
    val localContext = LocalContext.current
    Button(
        onClick = {
            if (!viewModel.signUp()) {
                Toast.makeText(
                    localContext, "SignUp authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                handleOnSignUp()
            }
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple80, contentColor = Color.White
        ),
        modifier = modifier.width(180.dp),
        contentPadding = PaddingValues(16.dp),
    ) {
        Text(text = "Sign Up", fontSize = 20.sp)
    }
}

@Composable
fun JumpToLoginButton(modifier: Modifier, jumpButtonHandler: () -> Unit) {
    TextButton(
        onClick = jumpButtonHandler,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White, contentColor = Color.Blue
        ),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(text = "Already have an account? Login", fontSize = 18.sp)
    }
}