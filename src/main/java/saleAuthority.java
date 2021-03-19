import java.util.ArrayList;
import java.util.List;



public class saleAuthority {
    static saleAuthority instance;
    ArrayList<String> desired = new ArrayList<String>();
    String secureCopyOfSearchTerm;
    Boolean done = true;


    static saleAuthority getInstance(){
        if (instance==null){

            instance=new saleAuthority();
            return instance;
        }
        else return instance;
    }

    saleAuthority(){

    }

    public boolean checkIfDesired(char onOffer){ //prüft ob der Buchstabe gewollt ist, und ob wir fertig sind.
        if(desired.size()==0&&done==false){
            System.out.println("WE WON- HECK YEAH, WE ACTUALLY CODED SOMETHING THAT WORKS");
            System.exit(0);
        }

        if (desired.contains(onOffer+"")){
            return true;
        }
        else return false;
    }

    public void fillDesiredArray(String wanted){
        char[] temp = wanted.toCharArray();
        secureCopyOfSearchTerm=wanted;
        done = false;



        for (char c:temp){
            desired.add(c+"");
        }
    }

    public void desireHandler(String letter){ //entfernt gekaufte buchstaben aus der liste der zu kaufenden buchstaben #
        System.out.println(String.format("Desire Handler called, removing %s, length of desireList is %s, or in words %s", letter,neededItemsCount(),arrayListToString()));
        if (desired.contains(letter)){
            int position =desired.indexOf(letter);
            desired.remove(position);
        }
    }

    public int neededItemsCount(){
        return desired.size();
    }

    public String arrayListToString(){
        String result="";
        for (String s:desired){
            result+=s;
        }
        return result;
    }

    public String originalTerm(){
        return secureCopyOfSearchTerm;
    }

    public int calculateCompletedLetters(){
        return secureCopyOfSearchTerm.length()-desired.size()+1;
    }




}
