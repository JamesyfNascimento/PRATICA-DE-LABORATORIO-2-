import java.util.Calendar;
import java.util.Locale;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.io.Serializable;

public class Mensagem implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userNameRemetente;
    private String titulo;
    private String texto;
    private Date data;

    public Mensagem(String userNameRemetente, String titulo, String texto) {
        this.userNameRemetente = userNameRemetente;
        this.titulo = titulo;
        this.texto = texto;
        Calendar c = Calendar.getInstance();
        this.data = c.getTime();
    }

    public String getUserNameRemetente() {
        return userNameRemetente;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getTexto() {
        return texto;
    }

    public Date getData() {
        return data;
    }

    @Override
    public String toString() {
        Locale brasil = new Locale("pt", "BR"); // Retorna do país e a língua

        DateFormat f2 = DateFormat.getDateInstance(DateFormat.FULL, brasil);
        return "Remetente: " + userNameRemetente + "\nTitulo: " + titulo + "\nTexto: " + texto + "\nData: "
                + f2.format(data);
    }

}