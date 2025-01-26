import java.io.File;
import java.util.Arrays;
import java.util.List;

public class teste {

    public static void main(String[] args) {

        File diretorio = new File("C:\\Users\\vitor\\OneDrive\\Documentos\\SistemaCadastroJava\\src\\main\\java\\com\\vitor\\arquivo\\cadastros");


        File[] arquivos = diretorio.listFiles();

        System.out.println(Arrays.toString(arquivos));

       List<File> teste =  Arrays.stream(arquivos).toList();

       teste.forEach(System.out::println);



    }

}
