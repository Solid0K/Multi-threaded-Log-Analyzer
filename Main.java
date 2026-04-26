import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main{
    public static void main(String[] args){
        try(DirectoryStream<Path> paths = Files.newDirectoryStream(Paths.get("logs"))){
            ExecutorService executor=Executors.newFixedThreadPool(3);
            List<Future<logresult>> results=new ArrayList<>();
            LogManager lm=new LogManager();
            for(Path path:paths){
                Callable<logresult> task=()->lm.loadLogsData(path);
                results.add(executor.submit(task));
            }
            Map<String,Long> totalErrors=new HashMap<>();
            Map<String,Long> totalUserActivity=new HashMap<>();
            for(Future<logresult> fileResult:results){
                logresult result=fileResult.get();
                result.Errors.forEach(
                    (k,v)->totalErrors.merge(k,v,Long::sum)
                );
                result.userActivitys.forEach(
                    (k,v)->totalUserActivity.merge(k,v,Long::sum)
                );
            }
            executor.shutdown();
            System.out.println(totalErrors);
            System.out.println(totalUserActivity);
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("Somthing gone wrong");
        }
    }
}