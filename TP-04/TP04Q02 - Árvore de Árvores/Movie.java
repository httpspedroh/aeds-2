import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

// ----------------------------------------------------------------------------------------------------------------- //

class Movie {

    static class Node_T1 {
    
        public char element;
        public Node_T1 left;
        public Node_T1 right;
        public Node_T2 alt;
    
        public Node_T1(char element) {
    
            this.element = element;
            this.left = this.right = null;
            this.alt = null;
        }
    
        public Node_T1(char element, Node_T1 left, Node_T1 right) {
    
            this.element = element;
            this.left = left;
            this.right = right;   
            this.alt = null;
        }
    }
    
    static class Node_T2 {
    
        public Movie element;
        public Node_T2 left, right;
        
        public Node_T2(Movie element) {
    
            this.element = element;
            this.left = this.right = null;
        }
    
        public Node_T2(Movie element, Node_T2 left, Node_T2 right) {
    
            this.element = element;
            this.left = left;
            this.right = right;
        }
    }
    
    static class BinaryTree {
    
        private Node_T1 root;
        public int c_compares;
        public long t_begin;
    
        public BinaryTree() { 
        
            // Initialize bench log
            c_compares = 0;
            t_begin = System.currentTimeMillis();
    
            // Initialize root
            root = null;
        }

        public void insert(char x) throws Exception {
            root = insert(x, root);
        }
    
        private Node_T1 insert(char x, Node_T1 i) throws Exception {
    
            if(i == null) {
                
                c_compares++;
                i = new Node_T1(x);
            }
            else if(x < i.element) {

                c_compares += 2;
                i.left = insert(x, i.left);
            }
            else if(x > i.element) {
                
                c_compares += 3;
                i.right = insert(x, i.right);
            }
            else {
                
                c_compares += 3;
                
                throw new Exception("Erro ao inserir!");
            }
            return i;
        }
    
        public void insert(Movie x)throws Exception {
    
            insert(x, root);
        }
      
        public void insert(Movie x, Node_T1 i) throws Exception {
    
            if(i == null) {
                
                c_compares++;
                
                throw new Exception("Erro ao inserir: caractere invalido!");
            }
            else if(x.getOriginalTitle().charAt(0) < i.element) {
                
                c_compares += 2;
                
                insert(x, i.left);
            }
            else if(x.getOriginalTitle().charAt(0) > i.element) {
                
                c_compares += 3;
                
                insert(x, i.right);
            }
            else {
                
                c_compares += 3;
                
                i.alt = insert(x, i.alt);
            }
        }
      
        public Node_T2 insert(Movie x, Node_T2 i) throws Exception {
    
            if(i == null) {
                
                c_compares++;
                
                i = new Node_T2(x);
            }
            else if(x.getOriginalTitle().compareTo(i.element.getOriginalTitle()) < 0) {
                
                c_compares += 2;
                
                i.left = insert(x, i.left);
            }
            else if(x.getOriginalTitle().compareTo(i.element.getOriginalTitle()) > 0) {
                
                c_compares += 3;
                
                i.right = insert(x, i.right);
            }
            else {
                
                c_compares += 3;
                
                throw new Exception("Erro ao insert: element existente!");
            }
            return i;
        }      
    
        public void search(String originalTitle) {

            boolean result = false;

            MyIO.println("=> " + originalTitle);
            MyIO.print("raiz ");

            result = search(root, originalTitle);

            MyIO.print(" " + (result ? "SIM" : "NAO") + "\n");
        }
    
        public boolean search(Node_T1 node, String originalTitle) {

            boolean result = false;

            if(node != null) {

                c_compares++;
                result = searchSecondTree(node.alt, originalTitle);

                if(!result) {

                    MyIO.print(" ESQ ");
    
                    c_compares++;
                    result = search(node.left, originalTitle);
                }

                if(!result) {

                    MyIO.print(" DIR ");
    
                    c_compares++;
                    result = search(node.right, originalTitle);
                }  
            }
            return result;
        }
    
        public boolean searchSecondTree(Node_T2 node, String originalTitle) {
          
            boolean result = false;

            if(node == null) {

                c_compares++;
                result = false;
            }
            else if(originalTitle.compareTo(node.element.getOriginalTitle()) < node.element.getOriginalTitle().compareTo(originalTitle)) { 
                
                MyIO.print("esq "); 
        
                c_compares += 2;
                result = searchSecondTree(node.left, originalTitle); 
            } 
            else if(originalTitle.compareTo(node.element.getOriginalTitle()) > node.element.getOriginalTitle().compareTo(originalTitle)) { 

                MyIO.print("dir ");
            
                c_compares += 3;
                result = searchSecondTree(node.right, originalTitle); 
            } 
            else {
                
                c_compares += 3;
                result = true; 
            }
            return result;
        }
    }

    // ----------------------------------------------------------------------------------------------------------------- //

    static SimpleDateFormat default_dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private String name, original_title, genre, original_language, situation;
    private ArrayList<String> keywords;
    private Date release_date;
    private int duration;
    private float budget;

    public Movie() {

        this.name = "";
        this.original_title = "";
        this.genre = "";
        this.original_language = "";
        this.situation = "";
        this.keywords = new ArrayList<String>();
        this.release_date = new Date();
        this.duration = 0;
        this.budget = 0;
    }

    public Movie(String name, String original_title, String genre, String original_language, String situation, ArrayList<String> keywords, Date release_date, int duration, float budget) {

        this.name = name;
        this.original_title = original_title;
        this.genre = genre;
        this.original_language = original_language;
        this.situation = situation;
        this.keywords = keywords;
        this.release_date = release_date;
        this.duration = duration;
        this.budget = budget;
    }

    public void setName(String name) { this.name = name; }
    public void setOriginalTitle(String original_title) { this.original_title = original_title; }
    public void setgenre(String genre) { this.genre = genre; }
    public void setOriginalLanguage(String original_language) { this.original_language = original_language; }
    public void setSituation(String situation) { this.situation = situation; }
    public void setKeywords(ArrayList<String> keywords) { this.keywords = keywords; }
    public void setReleaseDate(Date release_date) { this.release_date = release_date; }
    public void setDuration(int duration) { this.duration = duration; }
    public void setBudget(float budget) { this.budget = budget; }

    public String getName() { return this.name; }
    public String getOriginalTitle() { return this.original_title; }
    public String getGenre() { return this.genre; }
    public String getOriginalLanguage() { return this.original_language; }
    public String getSituation() { return this.situation; }
    public ArrayList<String> getKeywords() { return this.keywords; }
    public Date getReleaseDate() { return this.release_date; }
    public int getDuration() { return this.duration; }
    public float getBudget() { return this.budget; }
    
    public Movie clone() {

        Movie cloned = new Movie();

        cloned.name = this.name;
        cloned.original_title = this.original_title;
        cloned.genre = this.genre;
        cloned.original_language = this.original_language;
        cloned.situation = this.situation;
        cloned.keywords = this.keywords;
        cloned.release_date = this.release_date;
        cloned.duration = this.duration;
        cloned.budget = this.budget;

        return cloned;
    }

    public void print() {

        MyIO.println(this.name + " " + this.original_title + " " + default_dateFormat.format(this.release_date) + " " + this.duration + " " + this.genre + " " + this.original_language + " " + this.situation + " " + this.budget + " " + this.keywords);
    }

    public void readHTML(String filename) {

        try {

            // Read HTML file
            String basefile = "/tmp/filmes/" + filename;

            FileInputStream fstream = new FileInputStream(basefile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            // ------------------------------------ //

            // Create movie object
            String name = null, original_title = null, genre = null, original_language = null, situation = null;
            ArrayList<String> keywords = new ArrayList<String>();
            Date release_date = null;
            int duration = -1;
            float budget = -1;

            // ------------------------------------ //
        
            // Start to explode HMTL file
            String line;

            while((line = br.readLine()) != null) {

                // --------------------------- //

                // Find movie name
                if(name == null) {
                    
                    if(line.indexOf("<title>") != -1) {

                        name = removeTags(line.replace("&#8212;", "?"));
                        name = name.substring(4, name.length() - 35);
                    }
                }

                // --------------------------- //

                // Find movie original title
                if(original_title == null) {
                    
                    if(line.indexOf("<p class=\"wrap\"><strong>") != -1) {

                        original_title = removeTags(line);
                        original_title = original_title.substring(20, original_title.length());
                    }
                }

                // --------------------------- //

                // Find movie release date
                if(release_date == null) {
                    
                    if(line.indexOf("<span class=\"release\">") != -1) {

                        // Read util line cleaning it
                        line = onlyDigitsAndSym(br.readLine());

                        try { release_date = default_dateFormat.parse(line); } 
                        catch (java.text.ParseException e) { e.printStackTrace(); }
                    }
                }

                // --------------------------- //

                // Find movie duration
                if(duration == -1) {
                    
                    if(line.indexOf("<span class=\"runtime\">") != -1) {

                        br.readLine(); // Skip one line
                        line = br.readLine(); // Read util line

                        int h_pos = line.indexOf("h"),
                            m_pos = line.indexOf("m"),
                            hours = 0,
                            minutes = 0;

                        if(h_pos != -1) hours = Integer.parseInt(line.substring(firstDigit(line), h_pos));
                        if(m_pos != -1) minutes = Integer.parseInt(line.substring(firstDigit(line, h_pos == -1 ? 0 : h_pos), line.length() - 1));

                        duration = (hours * 60) + minutes;
                    }
                }

                // --------------------------- //

                // Find movie genres
                if(genre == null) {
                    
                    if(line.indexOf("<span class=\"genres\">") != -1) {

                        br.readLine(); // Skip one line
                        genre = removeTags(br.readLine()).substring(6); // Read util line cleaning it
                    }
                }

                // --------------------------- //

                // Find movie original language
                if(original_language == null) {
                    
                    if(line.indexOf("<bdi>Idioma original</bdi>") != -1) {

                        original_language = removeTags(line.substring(line.indexOf("</strong>") + 10, line.length()));
                    }
                }

                // --------------------------- //

                // Find movie situation
                if(situation == null) {
                    
                    if(line.indexOf("<bdi>Situa��o</bdi>") != -1) {

                        situation = removeTags(line.substring(line.indexOf("</strong>") + 10, line.length()));
                    }
                }

                // --------------------------- //

                // Find movie budget
                if(budget == -1) {
                    
                    if(line.indexOf("<bdi>Or�amento</bdi>") != -1) {

                        // Read util line cleaning it
                        line = removeTags(line.substring(line.indexOf("</strong>") + 10, line.length()));
                        line = line.replace(",", "");
                        
                        if(line.charAt(0) == '-') budget = 0;
                        else budget = Float.parseFloat(line.substring(1));
                    }
                }

                // --------------------------- //

                // Find movie keywords
                if(keywords.size() == 0) {
                    
                    if(line.indexOf("<h4><bdi>Palavras-chave</bdi></h4>") != -1) {

                        // Skip two lines until keywords starts
                        for(int x = 0; x < 2; x++) line = br.readLine();

                        // Verify if keywords == 0
                        if(line.indexOf("<p><bdi>Nenhuma palavra-chave foi adicionada.</bdi></p>") == -1)
                        {
                            // Skip more two lines until keywords starts
                            for(int x = 0; x < 2; x++) line = br.readLine();

                            while(true) {

                                if(line.compareTo("    </ul>") == 0) break;

                                line = removeTags(line);

                                // Add to list only keywords
                                if(line.length() > 6) keywords.add(line.substring(8));

                                line = br.readLine();
                            }
                        }
                    }
                }
            }

            // ------------------------------------ //
            
            // Verify variables still "null"
            if(original_title == null) original_title = name;

            // Set movie object
            this.setName(name);
            this.setOriginalTitle(original_title);
            this.setgenre(genre);
            this.setOriginalLanguage(original_language);
            this.setSituation(situation);
            this.setKeywords(keywords);
            this.setReleaseDate(release_date);
            this.setDuration(duration);
            this.setBudget(budget);

            // ------------------------------------ //
        
            // Close HTML file
            fstream.close();
        }
        catch(IOException e) { e.printStackTrace(); }
    }

    // -------------------------------------------------------------------------------------- //

    public static boolean isFim(String s) { return (s.length() == 3 && s.charAt(0) == 'F' && s.charAt(1) == 'I' && s.charAt(2) == 'M'); }

    public static String removeTags(String str) {

        String removed = "";
        
        for(int i = 0; i < str.length(); i++) {

            if(str.charAt(i) == '<') {

                i++;

                while(str.charAt(i) != '>') i++;
            } 
            else if(str.charAt(i) == '&') { 
                
                i++;

                if(str.charAt(i) != 'a') while(str.charAt(i) != ';') i++; // Ignore "amps"
                else {

                    removed += str.charAt(i - 1);
                    removed += str.charAt(i);
                }
            } 
            else removed += str.charAt(i);
        }
        return removed;
    }

    public static String onlyDigitsAndSym(String str) {

        String removed = "";

        for(int i = 0; i < str.length(); i++) {

            if((str.charAt(i) >= '0' && str.charAt(i) <= '9') || str.charAt(i) == ',' || str.charAt(i) == '/' || str.charAt(i) == '.') removed += str.charAt(i);
            else i++;
        }
        return removed;
    }

    public static int firstDigit(String str, int start) {
         
        for(int i = start; i != str.length(); i++) if(str.charAt(i) >= '0' && str.charAt(i) <= '9') return i;
        return -1;
    }

    public static int firstDigit(String str) { return firstDigit(str, 0); }


    // -------------------------------------------------------------------------------------- //

    public static void main(String[] args) throws Exception {

        MyIO.setCharset("utf-8");

        Scanner scr = new Scanner(System.in);
        String line = null;
        char[] order = { 'D', 'R', 'Z', 'X', 'V', 'B', 'F', 'P', 'U', 'I', 'G', 'E', 'J', 'L', 'H', 'T', 'A', 'W', 'S', 'O', 'M', 'N', 'K', 'C', 'Y', 'Q'};

        BinaryTree tree = new BinaryTree();

        for(char c : order) tree.insert(c);

        // ------------------------------------------------------------------------------------------------------------ //
        
        // First part - Insert
        line = scr.nextLine().trim();

        while(true) {

            if(isFim(line)) break;
            
            // ------------------------------------ //
            
            // Create movie object
            Movie movie = new Movie();

            movie.readHTML(line);
            tree.insert(movie);

            // ------------------------------------ //
            
            line = scr.nextLine().trim();
        }

        // ------------------------------------------------------------------------------------------------------------ //
        
        // Second part - Insert and remove
        line = scr.nextLine().trim();

        int ops = Integer.parseInt(line);

        // Execute operations
        for(int i = 0; i < ops; i++) {

            line = scr.nextLine();

            // -------------------------------- //

            String movie_name = line.substring(2, line.length());
            Movie movie = new Movie();

            movie.readHTML(movie_name);
            tree.insert(movie);
        }

        // ------------------------------------------------------------------------------------------------------------ //
        
        // Third part - Search
        line = scr.nextLine().trim();

        while(true) {

            if(isFim(line)) break;
            
            // ------------------------------------ //
            
            tree.search(line);

            // ------------------------------------ //
            
            line = scr.nextLine().trim();
        }

        // ------------------------------------------------------------------------------------------------------------ //
        
        Arq.openWrite("753045_arvoreArvore.txt");
        Arq.println("753045\t" + (System.currentTimeMillis() - tree.t_begin) + "ms\t" + tree.c_compares);
        Arq.close();

        scr.close();
    }
}