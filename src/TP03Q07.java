import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class TP03Q07 {

	public static String[] totalMusicas = new String[175000];
	public static Lista minhaLista = new Lista();
	
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
	          minhaLista.inserirFinal(LerArq(Totalid(entrada)));
	      } 
	      
	      minhaLista.sort();
	      minhaLista.mostrar();
	    }

	    catch(Exception e){
	      MyIO.println(e.toString());
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

class Celula{
	public Musica item;
	public Celula anterior;
	public Celula proximo;
	  
	  public Celula(){
	    this(new Musica());
	  }

	  public Celula(Musica m){
	    item = m.clone();
	    anterior = null;
	    proximo = null;
	  }
	}

	
	class Lista{
		private Celula primeiro;
		private Celula ultimo;
		
		static int compara = 0;
	    static int move = 0;
	  
	  public Lista(){
	    primeiro = new Celula();
	    ultimo = primeiro;
	  }
	  
	 
	  public int tamanho(){
	    int cont = 0;
	    for(Celula i = primeiro; i != ultimo; i = i.proximo, cont++);
	    return cont;
	  }

	  public boolean listaVazia() {
			
			boolean resp;
			
			if (primeiro == ultimo)
				resp = true;
			else
				resp = false;
			
			return resp;
		}
	  	  
	  public void removerFinal() throws Exception{
	    if(! listaVazia())
	    	throw new Exception ("Não foi possível remover o item da lista: a lista está vazia!");
	    else{  
	      Musica desempilhado = ultimo.item.clone();
	      ultimo = ultimo.anterior;
	      ultimo.proximo.anterior = null;
	      ultimo.proximo = null;
	      MyIO.println("(R) " + desempilhado.getName());
	    }
	  }
	  
	  public void inserirFinal(Musica m){
		    ultimo.proximo = new Celula(m.clone());
		    ultimo.proximo.anterior = ultimo;
		    ultimo = ultimo.proximo;
		  }
		  

	  public Celula getCelula(int pos){
	    Celula i = primeiro.proximo;
	    for(int cont = 0; cont < pos; cont++)
	      i = i.proximo;
	    return i;
	  }

		  
	  private void troca(Celula a, Celula b){
	    Celula aux  = new Celula(a.item);
	    a.item = b.item.clone();
	    b.item = aux.item.clone();
	  }

	  public void mostrar(){
		    for(Celula i = primeiro.proximo; i != null; i = i.proximo)
		      MyIO.println(i.item.toString());
		  }

	  
	  public void sort()throws Exception{
	    quicksort(0, tamanho() - 1);
	    move++;
	  }
	  
	  private void quicksort(int inicio, int fim) throws IOException{
		  
		Tempo tempo = new Tempo();
		
		tempo.start();
		  
	    int i = inicio, f = fim;
	    Celula pivo = new Celula(getCelula(((inicio + fim) / 2)).item);
	    
	    compara++;
	    while(i <= f){
	      while(Compara(getCelula(i), pivo)) {
	        i++;
	        compara++; 
	      }
	      while(Compara(pivo, getCelula(f))) {
	        f--;
	        compara++; 
	      }
	      if(i <= f){
	        troca(getCelula(i), getCelula(f));
	        i++;
	        f--;
	      }
	    }
	    if(inicio < f)
	      quicksort(inicio, f);
	    if(i < fim)
	      quicksort(i, fim);
	    
	    tempo.stop();
	    
	    CriaLog.Log(tempo, compara, move);
	  } 
	  
	  
	  private boolean Compara(Celula pivo, Celula pos){
	    boolean result;
	    if(pivo.item.getDuration_ms() < pos.item.getDuration_ms()){
	      result = true;
	    }
	    else if(pivo.item.getDuration_ms() == pos.item.getDuration_ms()){
	      result = (pivo.item.getName().compareTo(pos.item.getName()) < 0)? true : false;
	    }
	    else 
	      result = false;
	    return result;
	  }
	  
	 
	}
	
	class CriaLog{
		public static void Log(Tempo tempo, int compara, int move) throws IOException {
			  
			  FileWriter arq = new FileWriter("matrícula_quicksort2.txt");
			  PrintWriter gravarArq = new PrintWriter(arq);
		       
			  gravarArq.println("Matricula: 717233 - 724147 - 720003;\tTempo: "+ tempo.getTime() + ";\tVezes que foi comparado: " + compara + ";\tMovimentos: " + move + ".");

		      gravarArq.close();
		    }
	}

	class Tempo {
	    
	    private double time;

	    
	    public Tempo() {
	        this.time = 0.0;
	    }

	    public double getTime() {
	        return this.time;
	    }

	    public void setTime(double time) {
	        this.time = time;
	    }

	    
	    public void start() {
	        time = new Date().getTime();
	    }

	    public void stop() {
	        time = ((new Date().getTime()) - time)/1000;
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

		// IMPRESSÃO ATRAVÉS DO TOSTRING

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


