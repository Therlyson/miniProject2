package view;

import apresentacao.LoginScreen;
import controller.Department;

public class Main {
    public static void main(String[] args) {
        Department department = new Department();
        department.clearFile();
        new LoginScreen();
    }
}