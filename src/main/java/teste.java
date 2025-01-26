import java.io.File;
import java.util.Arrays;
import java.util.List;

public class teste {

    public static void main(String[] args) {

        File diretorio = new File("C:\\Users\\vitor\\OneDrive\\Documentos\\SistemaCadastroJava\\src\\main\\java\\com\\vitor\\arquivo\\cadastros");

        List<String> lista = Arrays.asList(diretorio.list());

        lista.forEach(System.out::println);

        System.out.println(lista.size());

    }

}
