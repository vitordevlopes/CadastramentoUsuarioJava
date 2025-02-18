package com.vitor.service;

import com.vitor.usuario.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UsuarioService {

    private final String arquivoPerguntas = "C:\\Users\\vitor\\OneDrive\\Documentos\\SistemaCadastroJava" +
            "\\src\\main\\java\\com\\vitor\\arquivo/formulario/formulario.txt";

    private Scanner scanner = new Scanner(System.in);

    private final String caminhoDiretorioCadastro = "C:\\Users\\vitor\\OneDrive\\Documentos\\SistemaCadastroJava\\src\\main\\java\\com\\vitor\\arquivo\\cadastros";

    public void cadastrarUsuario() {
        FileReader file = null;
        try {
            file = new FileReader(arquivoPerguntas);
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado");
        }

        BufferedReader leitor = new BufferedReader(file);

        List<String> perguntas;

        perguntas = leitor.lines().toList();

        System.out.println(perguntas.getFirst());
        String nome = scanner.nextLine().trim();

        System.out.println(perguntas.get(1));
        String email = scanner.nextLine();

        System.out.println(perguntas.get(2));
        Integer idade = scanner.nextInt();

        System.out.println(perguntas.get(3));
        Double altura = scanner.nextDouble();

        Usuario usuario = new Usuario(nome, email, idade, altura);

        System.out.println(usuario);

        criarArquivoComDadosUsuario(nome, usuario);
    }

    public int definirIndiceArquivo() {

        File diretorio = new File("C:\\Users\\vitor\\OneDrive\\Documentos\\SistemaCadastroJava\\src\\main\\java\\com\\vitor\\arquivo\\cadastros");

        int contadorUsuario = 0;

        List<String> listaArquivos = Arrays.asList(diretorio.list());

        if (listaArquivos.size() == 0) {
            return contadorUsuario = 1;
        } else {
            return  contadorUsuario = listaArquivos.size() + 1;
        }

    }

    public void criarArquivoComDadosUsuario(String nome, Usuario usuario) {

        String arquivoDadosUsuario = "C:\\Users\\vitor\\OneDrive\\" +
                "Documentos\\SistemaCadastroJava\\src\\main\\java\\com\\vitor\\arquivo/cadastros/" +
                definirIndiceArquivo() + "-" + nome.toUpperCase().replace(" ", "") + ".txt";

        try {
            FileWriter arquivo = new FileWriter(arquivoDadosUsuario);
            arquivo.write(usuario.getNome() + "\n" + usuario.getEmail() + "\n" +
                    usuario.getIdade() + "\n" + usuario.getAltura() + "\n");
            arquivo.close();

        } catch (IOException ex) {
            System.out.println("Erro");
        }



    }

    public void listarUsuarios() {

        File diretorio = new File(caminhoDiretorioCadastro);

        File[] arquivos = diretorio.listFiles();

        List<File> listaArquivos = Arrays.stream(arquivos).toList();

        for (File listaArquivo : listaArquivos) {

            try {
                FileReader arquivo = new FileReader(listaArquivo);

                BufferedReader leitor = new BufferedReader(arquivo);

                var nome = leitor.readLine();

                System.out.println(nome);

            } catch (FileNotFoundException e) {
                System.out.println("Arquivo não encontrado!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }

    }

    public void adicionarNovaPergunta() {

        System.out.println("Adicione uma nova pergunta: ");
        String pergunta = scanner.nextLine();



        File file = new File(arquivoPerguntas);

       Integer qntdPerguntas = descobrirQuantidadePerguntas();

        try {
            FileWriter arquivo = new FileWriter(file, true);
            arquivo.write("\n" + (qntdPerguntas + 1) + " - " + pergunta);
            arquivo.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public Integer descobrirQuantidadePerguntas() {
        File diretorio = new File(arquivoPerguntas);

        Integer qntdPerguntas = 0;

        try {
            FileReader fileReader = new FileReader(arquivoPerguntas);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
           List<String> listaPerguntas = bufferedReader.lines().toList();

            for (String pergunta : listaPerguntas) {
                qntdPerguntas++;
            }

            return qntdPerguntas;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    public void deletarPergunta() {


        System.out.println("Selecione a pergunta que será deletada: ");
        int linhaEscolhida = scanner.nextInt();
        scanner.nextLine();

        if (linhaEscolhida <= 4) {
            System.out.println("A pergunta precisa ser maior que 4!");
        } else {
            File arquivoOriginal = new File(arquivoPerguntas);
            File arquivoTemporario = new File("C:\\Users\\vitor\\OneDrive\\Documentos\\SistemaCadastroJava" +
                    "\\src\\main\\java\\com\\vitor\\arquivo/formulario/arquivo_temp.txt");

            try (BufferedReader reader = new BufferedReader(new FileReader(arquivoOriginal));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoTemporario))) {

                String linhaAtual;
                int numeroLinha = 1;

                // Leia e escreva as linhas
                while ((linhaAtual = reader.readLine()) != null) {
                    if (numeroLinha != linhaEscolhida) {
                        writer.write(linhaAtual);
                        writer.newLine();
                    }
                    numeroLinha++;
                }
            } catch (IOException e) {
                System.out.println("Erro ao processar o arquivo: " + e.getMessage());
                return;
            }

            // Verifique a exclusão do arquivo original
            if (arquivoOriginal.delete()) {
                // Verifique o renomeio do arquivo temporário
                if (arquivoTemporario.renameTo(arquivoOriginal)) {
                    System.out.println("Linha " + linhaEscolhida + " deletada com sucesso!");
                } else {
                    System.out.println("Erro ao renomear o arquivo temporário.");
                }
            } else {
                System.out.println("Erro ao deletar o arquivo original.");
            }

//            O Arquivo Temporário Torna-se o Original: Após a renomeação, o arquivo temporário não existe mais sob o
//           nome "temporário". Ele agora tem o mesmo nome do arquivo original.

        }

    }

    public void pesquisarUsuarioPorNome() {

        System.out.println("Digite um nome: ");
       var nome =  scanner.nextLine().toLowerCase();

        File diretorio = new File(caminhoDiretorioCadastro);

        File[] arquivos = diretorio.listFiles();



        if (arquivos != null) {

            List<File> listaArquivos = Arrays.stream(arquivos).toList();

            List<String> listaNomes = new ArrayList<>();

            for (File arquivo : listaArquivos) {

                try {
                    BufferedReader leitor = new BufferedReader(new FileReader(arquivo));

                    var primeiraLinha = leitor.readLine();
                    String dadosCompletos = primeiraLinha;


                    if (primeiraLinha.toLowerCase().contains(nome)) {

                        String linha;
                        while ((linha = leitor.readLine()) != null) {
                            dadosCompletos += "\n" + linha;
                        }

                        listaNomes.add(dadosCompletos);

                    }

                } catch (FileNotFoundException e) {
                    System.out.println("Erro");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            listaNomes.sort(String::compareToIgnoreCase); // estudar essa ordenação

            System.out.println(listaNomes);

            System.out.println("Usuários cadastrados com esse nome:");

            if (listaNomes.isEmpty()) {
                System.out.println("Usuário não encontrado.");
            } else {
                for (String nomeUsuario : listaNomes) {
                    System.out.println(nomeUsuario);
                    System.out.println("*************************");
                }
            }




        }




    }
}
