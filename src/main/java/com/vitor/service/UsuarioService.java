package com.vitor.service;

import com.vitor.usuario.Usuario;

import java.io.*;
import java.util.*;

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

        if (nome.length() < 10) {
            throw new IllegalArgumentException("O nome deve ter pelo menos 10 caracteres");
        }


        System.out.println(perguntas.get(1));
        String email = scanner.nextLine();

        if (!email.contains("@")) {
            throw new IllegalArgumentException("O email deve conter o símbolo '@' ");
        }

        System.out.println(perguntas.get(2));
        Integer idade = scanner.nextInt();

        if (idade < 18) {
            throw new IllegalArgumentException("O usuário deve ter 18 anos ou mais");
        }

        System.out.println(perguntas.get(3));
        Double altura = null;
        while (altura == null) {
            try {
                altura = Double.parseDouble(scanner.nextLine().replace(",", "."));
            } catch (NumberFormatException ex) {
                System.out.println("Erro: Use ponto (.) para separar decimais. Tente novamente:");
            }
        }


        Usuario usuario = new Usuario(nome, email, idade, altura);

        System.out.println(usuario);

        criarArquivoComDadosUsuario(nome, usuario);
    }

    public int definirIndiceArquivo() {

        File diretorio = new File("C:\\Users\\vitor\\OneDrive\\Documentos\\SistemaCadastroJava\\src\\main\\java\\com\\vitor\\arquivo\\cadastros");

        List<String> listaArquivos = Arrays.asList(diretorio.list());

        if (listaArquivos.isEmpty()) {
            return 1;
        } else {
            return listaArquivos.size() + 1;
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

            if (listaNomes.isEmpty()) {
                System.out.println("Usuário não encontrado");
            } else {
                listaNomes.stream()
                        .sorted(String::compareToIgnoreCase) // Ordena alfabeticamente
                        .limit(2) // Limita a 2 usuários
                        .forEach(usuario -> {
                            System.out.println(usuario);
                            System.out.println("***************************");
                        });
            }






        }




    }
}
