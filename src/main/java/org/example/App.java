package org.example;

import org.example.entity.User;
import org.example.service.UserService;

import java.util.Scanner;

public class App {
    private static final UserService service = new UserService();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        do {
            System.out.println("""
                    === User Service ===
                    1. Create User
                    2. Get User by ID
                    3. Get All Users
                    4. Update User
                    5. Delete User
                    0. Exit
                    """);
            System.out.println("Choose number:");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> create();
                case 2 -> getById();
                case 3 -> getAll();
                case 4 -> update();
                case 5 -> delete();
                case 0 -> exit = true;
                default -> System.out.println("Incorrect number!");
            }
        } while(!exit);
    }

    public static void create() {
        System.out.println("Name: ");
        String name = sc.nextLine();
        System.out.println("Email: ");
        String email = sc.nextLine();
        System.out.println("Age: ");
        int age = Integer.parseInt(sc.nextLine());
        service.create(name, email, age);
    }

    public static void getById() {
        System.out.println("Enter ID: ");
        Long id = Long.parseLong(sc.nextLine());
        User user = service.get(id);
        System.out.println(user != null ? user : "User not found");
    }

    public static void getAll() {
        service.getAll().forEach(System.out::println);
    }

    public static void update() {
        System.out.println("Enter ID: ");
        Long id = Long.parseLong(sc.nextLine());
        System.out.println("New name: ");
        String name = sc.nextLine();
        System.out.println("New email: ");
        String email = sc.nextLine();
        System.out.println("New age: ");
        int age = Integer.parseInt(sc.nextLine());
        service.update(id, name, email, age);
    }

    public static void delete() {
        System.out.println("Enter ID: ");
        Long id = Long.parseLong(sc.nextLine());
        service.delete(id);
    }
}
