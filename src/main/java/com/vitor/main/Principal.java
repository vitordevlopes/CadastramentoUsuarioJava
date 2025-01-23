package com.vitor.main;

import com.vitor.usuario.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    Scanner scanner = new Scanner(System.in);

    public void start() {

        System.out.println("""
                ******************************************
                
                Bem vindo ao sistema de cadastramento!
                
                ******************************************
                """);

        try {
            FileReader file = new FileReader("C:\\Users\\vitor\\OneDrive\\Documentos\\SistemaCadastroJava\\src\\main\\java\\com\\vitor\\arquivo/formulario.txt");

            BufferedReader leitor = new BufferedReader(file);

            List<String> perguntas = new ArrayList<>();

           perguntas = leitor.lines().toList();

            System.out.println(perguntas.getFirst());
             String nome = scanner.nextLine();

            System.out.println(perguntas.get(1));
            String email = scanner.nextLine();

            System.out.println(perguntas.get(2));
            Integer idade = scanner.nextInt();

            System.out.println(perguntas.get(3));
            Double altura = scanner.nextDouble();

            Usuario usuario = new Usuario(nome, email, idade, altura);

            System.out.println(usuario);

            int contadorUsuario = 0;
            contadorUsuario += 1;

            FileWriter arquivo = new FileWriter("C:\\Users\\vitor\\OneDrive\\" +
                    "Documentos\\SistemaCadastroJava\\src\\main\\java\\com\\vitor\\arquivo/" +
                    contadorUsuario + "-" + nome.toUpperCase() + ".txt");

            PrintWriter gravadorArquivo = new PrintWriter()

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado!");
        } catch (IOException e) {
            System.out.println("Erro na criação do arquivo com os dados do usuário.");
        }

    }
}
