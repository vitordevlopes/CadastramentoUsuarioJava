package com.vitor.main;

import com.vitor.service.UsuarioService;
import java.util.Scanner;

public class Principal {

    UsuarioService usuarioService = new UsuarioService();

    private Scanner scanner = new Scanner(System.in);

    public void start() {

        System.out.println("""
                ******************************************
                
                Bem vindo ao sistema de cadastramento!
                
                ******************************************
                """);


        System.out.println("""
                1 - Cadastrar o usuário
                2 - Listar todos usuários cadastrados
                3 - Cadastrar nova pergunta no formulário
                4 - Deletar pergunta do formulário
                5 - Pesquisar usuário por nome ou idade ou email
                
                Escolha uma opção:
                """);

        Integer opcao = scanner.nextInt();
        scanner.nextLine();

        switch (opcao) {
            case 1:
                usuarioService.cadastrarUsuario();
                break;
            case 2:
                usuarioService.listarUsuarios();
                break;
            case 3:
                usuarioService.adicionarNovaPergunta();
                break;
            case 4:
                usuarioService.deletarPergunta();
                break;
            case 5:
                usuarioService.pesquisarUsuarioPorNome();
                break;
        }




    }



}
