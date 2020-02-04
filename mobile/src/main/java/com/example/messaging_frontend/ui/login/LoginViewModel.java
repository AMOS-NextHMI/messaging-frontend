package com.example.messaging_frontend.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.example.messaging_frontend.data.LoginRepository;
import com.example.messaging_frontend.data.Result;
import com.example.messaging_frontend.data.model.LoggedInUser;
import com.example.messaging_frontend.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private MutableLiveData<LoginResult> registerResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {

        return loginFormState;
    }
    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    LiveData<LoginResult> getRegisterResult() {
        return registerResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getEmail(), data.getLoginToken())));

        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void register(String username, String email, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.register(username,email, password);

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            registerResult.setValue(new LoginResult(new LoggedInUserView(data.getEmail(), data.getLoginToken())));
        } else {
            registerResult.setValue(new LoginResult(R.string.registration_failed));
        }
    }

    public void loginDataChanged(String email, String password) {
        if (!isEmailValid(email)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }
//    RegisterFormState(@Nullable Integer usernameError, @Nullable Integer emailError,@Nullable Integer passwordError) {
    public void registerDataChanged(String username,String email, String password,String password2) {

        if (!isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null,null));
        }else if (!isEmailValid(email)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_email,null));
        }else if (!isPasswordValid(password)) {
            registerFormState.setValue(new RegisterFormState(null, null,R.string.invalid_password));
        }else if (!(password2.equals(password))) {
            registerFormState.setValue(new RegisterFormState(null, null,R.string.not_matching_pws));
        }  else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        boolean validEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        return validEmail;

    }

    // A placeholder password validation check
    // TODO: set up passwork specifications
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
