/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nackademin.selenidetest.helpers;

import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Selenide.sleep;
import se.nackademin.selenidetest.model.User;

import se.nackademin.selenidetest.pages.AddUserPage;
import se.nackademin.selenidetest.pages.EditUserPage;
import se.nackademin.selenidetest.pages.MenuPage;
import se.nackademin.selenidetest.pages.SignInPage;
import se.nackademin.selenidetest.pages.MyProfilePage;

/**
 * @author testautomatisering
 */
public class UserHelper {
    
    public static void createNewUser(String username, String password) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToAddUser();

        AddUserPage addUserPage = page(AddUserPage.class);
        addUserPage.setUsername(username);
        addUserPage.setPassword(password);
        addUserPage.clickAddUserButton();
    }
    
    public static void createNewUserAsAdmin(String username, String password) {
        MenuPage menuPage = page(MenuPage.class);
        menuPage.navigateToAddUser();

        AddUserPage addUserPage = page(AddUserPage.class);
        addUserPage.setUsername(username);
        addUserPage.setPassword(password);
        addUserPage.clickAddUserButton();
    }

    public static void logInAsUser(String username, String password) {
        page(MenuPage.class).navigateToSignIn();
        SignInPage signInPage = page(SignInPage.class);
        signInPage.setUsername(username);
        signInPage.setPassword(password);
        signInPage.clickLogIn();

    }
    
    public static void setUser(User user) {
        page(MenuPage.class).navigateToMyProfile();
        MyProfilePage myProfilePage = page(MyProfilePage.class);
        myProfilePage.clickEditUserButton();
        
        EditUserPage editUserPage = page(EditUserPage.class);
        editUserPage.setUserNameField(user.getUserName());
        editUserPage.setFirstNameField(user.getFirstName());
        editUserPage.setLastNameField(user.getLastName());
        editUserPage.setPhoneField(user.getPhone());
        editUserPage.setEmailField(user.getEmail());
        editUserPage.clickSaveUserButton();
        
    }
    
    public static User fetchUser() {
        page(MenuPage.class).navigateToMyProfile();
        
        // Get data
        MyProfilePage myProfilePage = page(MyProfilePage.class);
        String username = myProfilePage.getUserName();
        String firstName = myProfilePage.getFirstName();
        String lastName = myProfilePage.getLastName();
        String phone = myProfilePage.getPhone();
        String email = myProfilePage.getEmail();
        
        // Put data in user class
        User user = new User();
        user.setUserName(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setEmail(email);
        return user;   
    }
    
    public static void signOut() {
        page(MenuPage.class).navigateToSignOut();
    }
    
    public static boolean isLoggedIn() {
        return page(MenuPage.class).isProfileDisplayed();
    }

    public static void logInAsAdmin() {
        String username = "admin";
        String password = "1234567890";
        logInAsUser(username, password);
    }

    public static void deleteUser() {
         page(MenuPage.class).navigateToMyProfile();
        
        MyProfilePage myProfilePage = page(MyProfilePage.class);
        myProfilePage.clickDeleteUserButton();
    }
}
