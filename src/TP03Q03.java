import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class TP03Q03 {

    public static String[] totalMusicas = new String[175000];
    public static Lista minhaLista = new Lista(500);

    public static Musica LerArq(String entrada) throws ParseException{
        String[] array = Separa(entrada, 19);
        ehNull(array);
        Musica m = new Musica();
        m.setValence(Double.parseDouble(array[0]));
        m.setYear(Integer.parseInt(array[1]));
        m.setAcousticness(Double.parseDouble(array[2]));
        m.setArtists(array[3].replace("[", "").replace("]", "").replace(",", ", ").split(";"));
        m.setDanceability(Double.parseDouble(array[4]));
        m.setDuration_ms(Integer.parseInt(array[5]));
        m.setEnergy(Double.parseDouble(array[6]));
        m.setId(array[8]);
        m.setInstrumentalness(Double.parseDouble(array[9]));
        m.setKey(array[10]);
        m.setLiveness(Double.parseDouble(array[11]));
        m.setLoudness(Double.parseDouble(array[12]));
        m.setName(array[14].replace("[", "").replace("]", ""));
        m.setPopularity(Integer.parseInt(array[15]));
        m.setRelease_date(convertDate(array[16]));
        m.setSpeechiness(Double.parseDouble(array[17]));
        m.setTime(Float.parseFloat(array[18]));
        return m;
    }
    public static String[] Separa(String entrada, int array){
	    String[] linha = new String[array];
	    
	    String item = entrada.replace(", ", "##");
	    item = item.replace("\"[", "[");
	    item = item.replace("]\"","]");
	    
	    linha = item.split(",");
	   
	    for(int i = 0; i < array; i++)
	      linha[i] = linha[i].replace("##", ", ");

	    return linha;
	  }

    public static void ehNull(String[] array){
        for(int i = 0; i < array.length; i++){
            if(array[i] == null)
                array[i] = "01";
        }
    }
    private static Date convertDate(String string) throws ParseException {
        if (string.length() == 4)
            string += "-01-01";
        else if (string.length() == 7)
            string += "-01";
        return new SimpleDateFormat("yyyy-MM-dd").parse(string);
    }
    public static void main(String[] args) throws IOException, ParseException {


        MyIO.setCharset("UTF-8");

        try{
            totalMusicas = ler(175000);
            String entrada = new String();
            while(!entrada.equals("FIM")){
                entrada = MyIO.readLine();
                if(!entrada.equals("FIM"))
                    minhaLista.inserirFim(LerArq(Totalid(entrada)));
            }
            
        int tamanho = MyIO.readInt();
            for(int i = 0; i < tamanho; i++)
                acionaLista(MyIO.readLine());
            
            minhaLista.imprimir();
        }
        catch(Exception e){
            MyIO.println(e.toString());
            MyIO.println(Arrays.toString(e.getStackTrace()));
        }

    }

    public static String[] ler(int ConjuntoM)throws IOException{
        String[] musicas = new String[175000];
        int i = 0;
        FileInputStream stream = new FileInputStream("/tmp/dataAEDs.csv");
        InputStreamReader reader = new InputStreamReader(stream);
        BufferedReader br = new BufferedReader(reader);
        br.readLine();
        while(i < ConjuntoM)
            musicas[i++] = br.readLine();
        return musicas;
    }

    public static void LerLinhas()throws Exception{

    }

    public static void acionaLista(String entrada)throws Exception{
        String[] comandos = entrada.split(" ");
        String op = comandos[0];
        if(op.equals("II"))
            minhaLista.inserirInicio(LerArq(Totalid(comandos[1])));
        else if(op.equals("IF"))
            minhaLista.inserirFim(LerArq(Totalid(comandos[1])));
        else if (op.equals("I*"))
            minhaLista.inserir(LerArq(Totalid(comandos[2])), Integer.parseInt(comandos[1]));
        else if (op.equals("RI"))
            MyIO.println("(R) " + minhaLista.removerInicio().getName());
        else if (op.equals("RF"))
            MyIO.println("(R) " + minhaLista.removerFim().getName());
        else if (op.equals("R*"))
            MyIO.println("(R) " + minhaLista.remover(Integer.parseInt(comandos[1])).getName());

    }
    public static String Totalid(String id){
        String resp = new String();
        for(int i = 0; i < 175000;i++) {
            if(totalMusicas[i].contains(id) == true){
                resp = totalMusicas[i];
                i = 175000;
            }
        }
        return resp;
    }


}

class Lista {

    private Musica minhaLista[];
    private int primeiro;
    private int ultimo;
    private int tamanho;

    public Lista(int tamanho) {
        minhaLista = new Musica[tamanho];
        primeiro = 0;
        ultimo = 0;
        this.tamanho = tamanho;
    }
    
    public Lista(){

    }

    public boolean listaCheia() {
        
        boolean resp;
        
        if (ultimo == tamanho)
            resp = true;
        else
            resp = false;
            
        return resp;
    }
    
    public boolean listaVazia() {
        
        boolean resp;
        
        if (primeiro == ultimo)
            resp = true;
        else
            resp = false;
            
        return resp;
    }

    public void inserirInicio (Musica item) throws Exception {
        
        int posicao = 0;

        if (! listaCheia() ) {
            // Verificando se o item será inserido em uma posição válida da lista.
            if ((posicao >= 0) && (posicao <= ultimo)) {
            
                // Deslocando os itens da lista:
                for (int i = ultimo; i > posicao; i--)
                    minhaLista[i] = minhaLista[i-1];
                
                minhaLista[posicao] = item;
                
                ultimo++;
                
            } else
                throw new Exception ("Não foi possível inserir o item na lista: posição inválida!");
        } else
            throw new Exception ("Não foi possível inserir o item na lista: a lista está cheia!");  
    }

    public void inserir (Musica item, int posicao) throws Exception {
        
        if (! listaCheia() ) {
            // Verificando se o item será inserido em uma posição válida da lista.
            if ((posicao >= 0) && (posicao <= ultimo)) {
            
                // Deslocando os itens da lista:
                for (int i = ultimo; i > posicao; i--)
                    minhaLista[i] = minhaLista[i-1];
                
                minhaLista[posicao] = item;
                
                ultimo++;
                
            } else
                throw new Exception ("Não foi possível inserir o item na lista: posição inválida!");
        } else
            throw new Exception ("Não foi possível inserir o item na lista: a lista está cheia!");  
    }
    
    public void inserirFim (Musica item) throws Exception {
        
        int posicao = ultimo;

        if (! listaCheia() ) {
            // Verificando se o item será inserido em uma posição válida da lista.
            if ((posicao >= 0) && (posicao <= ultimo)) {

                minhaLista[posicao] = item;
                
                ultimo++;
                
            } else
                throw new Exception ("Não foi possível inserir o item na lista: posição inválida!");
        } else
            throw new Exception ("Não foi possível inserir o item na lista: a lista está cheia!");  
    }

    public Musica removerInicio () throws Exception {
        
        Musica removido = null;
        int posicao = 0;
        
        if (! listaVazia() ) {
            if ((posicao >= 0) && (posicao < ultimo)) {
                removido = minhaLista[posicao];
                
                ultimo--;
                
                // Deslocando os itens da lista:
                for (int i = posicao; i < ultimo; i++)
                    minhaLista[i] = minhaLista[i+1];
                
            } else
                throw new Exception ("Não foi possível retirar o item da lista: posição inválida!");
        } else
            throw new Exception ("Não foi possível retirar o item da lista: a lista está vazia!");
        
        return removido;
    }

    public Musica remover (int posicao) throws Exception {
        
        Musica removido = null;
        
        if (! listaVazia() ) {
            if ((posicao >= 0) && (posicao < ultimo)) {
                removido = minhaLista[posicao];
                
                ultimo--;
                
                // Deslocando os itens da lista:
                for (int i = posicao; i < ultimo; i++)
                    minhaLista[i] = minhaLista[i+1];
                
            } else
                throw new Exception ("Não foi possível retirar o item da lista: posição inválida!");
        } else
            throw new Exception ("Não foi possível retirar o item da lista: a lista está vazia!");
        
        return removido;
    }

    public Musica removerFim () throws Exception {
        
        Musica removido = null;
        int posicao = ultimo-1;
        
        if (! listaVazia() ) {
            if ((posicao >= 0) && (posicao <= ultimo)) {
                removido = minhaLista[posicao];
                
                ultimo--;
                
            } else
                throw new Exception ("Não foi possível retirar o item da lista: posição inválida!");
        } else
            throw new Exception ("Não foi possível retirar o item da lista: a lista está vazia!");
        
        return removido;
    }

    public void imprimir() throws Exception{
        for(int i = primeiro; i < ultimo; i++)
            System.out.println("[" + i + "]" + minhaLista[i].toString());
    }
}

class Musica {

    // ATRIBUTOS

    private String id;
    private String name;
    private String key;
    private String artists[];
    private Date release_date;
    private double acousticness;
    private double danceability;
    private double energy;
    private int duration_ms;
    private double instrumentalness;
    private double valence;
    private int popularity;
    private double time;
    private double liveness;
    private double loudness;
    private double speechiness;
    private int year;

    // CONSTRUTORES

    public Musica(double valence, int year, double acousticness, String[] artists, double danceability, int duration_ms,
                  double energy, String id, double instrumentalness, String key, double liveness, double loudness,
                  String name, int popularity, Date release_date, double speechiness, double time) {
        super();
        this.id = id;
        this.name = name;
        this.setKey(key);
        this.artists = artists;
        this.release_date = release_date;
        this.acousticness = acousticness;
        this.danceability = danceability;
        this.energy = energy;
        this.duration_ms = duration_ms;
        this.instrumentalness = instrumentalness;
        this.setValence(valence);
        this.setPopularity(popularity);
        this.setTime(time);
        this.liveness = liveness;
        this.loudness = loudness;
        this.speechiness = speechiness;
        this.setYear(year);
    }

    public Musica() {
        id = "";
        name = "";
        key = "";
        acousticness = 0.0;
        danceability = 0.0;
        energy = 0.0;
        duration_ms = 0;
        instrumentalness = 0.0;
        valence = 0.0;
        popularity = 0;
        time = 0.0;
        liveness = 0.0;
        loudness = 0.0;
        speechiness = 0.0;
        year = 0;
    }

    // GETTERS

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String[] getArtists() {
        return artists;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public double getAcousticness() {
        return acousticness;
    }

    public double getDanceability() {
        return danceability;
    }

    public double getEnergy() {
        return energy;
    }

    public int getDuration_ms() {
        return duration_ms;
    }

    public double getInstrumentalness() {
        return instrumentalness;
    }

    public double getValence() {
        return valence;
    }

    public int getPopularity() {
        return popularity;
    }

    public double getTime() {
        return time;
    }

    public double getLiveness() {
        return liveness;
    }

    public double getLoudness() {
        return loudness;
    }

    public double getSpeechiness() {
        return speechiness;
    }

    public int getYear() {
        return year;
    }

    // SETTERS

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setArtists(String[] artists) {
        this.artists = artists;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public void setAcousticness(double acousticness) {
        this.acousticness = acousticness;
    }

    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }

    public void setInstrumentalness(double instrumentalness) {
        this.instrumentalness = instrumentalness;
    }

    public void setValence(double valence) {
        this.valence = valence;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public void setLiveness(double liveness) {
        this.liveness = liveness;
    }

    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }

    public void setSpeechiness(double speechiness) {
        this.speechiness = speechiness;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // IMPRESS�O ATRAV�S DO TOSTRING

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return id + " ## " + Arrays.toString(artists).replaceAll("'", "").replaceAll(",\\s", ",") + " ## "
                + name.replaceAll(";", ",") + " ## " + sdf.format(release_date) + " ## " + acousticness + " ## "
                + danceability + " ## " + instrumentalness + " ## " + liveness + " ## " + loudness + " ## "
                + speechiness + " ## " + energy + " ## " + duration_ms;
    }

    // CLONE

    public Musica clone(){
        Musica m = new Musica();


        m.id = this.id;
        m.name = this.name;
        m.artists = this.artists;
        m.release_date = this.release_date;
        m.acousticness = this.acousticness;
        m.danceability = this.danceability;
        m.energy = this.energy;
        m.duration_ms = this.duration_ms;
        m.instrumentalness = this.instrumentalness;
        m.valence = this.valence;
        m.popularity = this.popularity;
        m.time = this.time;
        m.liveness = this.liveness;
        m.loudness = this.loudness;
        m.speechiness = this.speechiness;
        m.year = this.year;

        return m;
    }


}