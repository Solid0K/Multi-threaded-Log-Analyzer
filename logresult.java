import java.util.Map;

public class logresult {
    Map<String,Long> Errors;
    Map<String,Long> userActivitys;
    
    public logresult(Map<String,Long> Errors,Map<String,Long> userActivitys){
        this.Errors=Errors;
        this.userActivitys=userActivitys;
    }
}
